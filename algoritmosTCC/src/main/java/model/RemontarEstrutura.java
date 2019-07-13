/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.File;
import java.io.FileInputStream;
import model.estruturas.ElementoBloco;
import model.aux.InfoJSON;
import model.dao.ListasDAO;
import model.dao.EstruturaConsolidadaDAO;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import javax.json.Json;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

/**
 *
 * @author ezequielrr
 */
public class RemontarEstrutura {

    private final Queue<List<ElementoBloco>> filaBlocos;

    public RemontarEstrutura() {
        this.filaBlocos = new LinkedList<>();
    }

    public void remontarUnicoBloco() throws FileNotFoundException, IOException {
        ListasDAO l = new ListasDAO();
        List<String[]> listaReferencias2 = l.lerListaReferencias2();
        List<List<ElementoBloco>> listaConsolidada
                = l.lerListaCamposConsolidados();
        List<String> visitados = new ArrayList<>();
        EstruturaConsolidadaDAO e = new EstruturaConsolidadaDAO();

        String arquivo = arquivoComMaisBlocos();
        InfoJSON info = new InfoJSON(arquivo);
        ElementoBloco raiz = new ElementoBloco("RAIZ", ElementoBloco.OBJETO);

        //Verifica se o documento tem um objeto raiz dos demais
        if (info.temUnicoObjetoRaiz()) {
            // Se sim, seta o nome do raiz com o nome do primeiro objeto
            File f = new File(arquivo);
            FileInputStream fi = new FileInputStream(f);
            JsonParser parser = Json.createParser(fi);
            Event evt = null;
            while (parser.hasNext()) {
                evt = parser.next();
                if (evt == Event.KEY_NAME) {
                    raiz.setNome(parser.getString());
                    /*Posicionando o ponteiro no '{' que vem depois do nome do 
                     * objeto raiz */
                    evt = parser.next();
                    break;
                }
            }
            // Chama a função recursiva para formar a árvore 
            // a partir da raiz dada
            montaArvore(raiz, parser, listaReferencias2, listaConsolidada, visitados);
        } else {
            File f = new File(arquivo);
            FileInputStream fi = new FileInputStream(f);
            JsonParser parser = Json.createParser(fi);
            /* Posicionando o ponteiro no '{' da raiz do documento */
            parser.next();
            // Chama a função recursiva para formar a árvore 
            // a partir da raiz dada
            montaArvore(raiz, parser, listaReferencias2, listaConsolidada, visitados);
        }
        e.gravarEstruturaConsolidada(raiz);
    }

    /**
     * Método auxiliar para obter o arquvivo JSON com mais blocos
     */
    private String arquivoComMaisBlocos() throws FileNotFoundException, IOException {
        List<String> arquivos = getListaArquivos();
        int qtdeBlocos = 0;
        int qtdeBlocosMax = 0;
        int iMax = 0;
        if (arquivos.size() > 0) {
            InfoJSON info = new InfoJSON(arquivos.get(0));
            qtdeBlocos = qtdeBlocosMax = info.numeroBlocosDocumento();
            for (int i = 1; i < arquivos.size(); i++) {
                info = new InfoJSON(arquivos.get(i));
                qtdeBlocos = info.numeroBlocosDocumento();
                if (qtdeBlocos > qtdeBlocosMax) {
                    qtdeBlocosMax = qtdeBlocos;
                    iMax = i;
                }
            }
            return arquivos.get(iMax);
        }
        return null;
    }

    /**
     * Método auxiliar para "detectar" os arquivos presentes. o nome dos
     * arquivos para serem encontrados deve ser docX.json, sendo X o número do
     * arquivo
     */
    private List<String> getListaArquivos() {
        int i = 1;
        List<String> listaArquivos = new ArrayList<>();
        while (true) {
            String strNomeArq = "doc" + i + ".json";
            File f = new File(strNomeArq);
            if (!f.exists()) {
                break;
            }
            listaArquivos.add(strNomeArq);
            i++;
        }
        return listaArquivos;
    }

    /**
     * Método auxiliar recursivo para remontar a estrutura do JSON segundo um
     * único bloco
     */
    private void montaArvore(ElementoBloco elementoAtual, JsonParser parser,
            List<String[]> listaReferencias2,
            List<List<ElementoBloco>> listaConsolidada, List<String> visitados) {
        String nomeTemp = "";

        // Adicionar os itens pertencentes ao nodo raiz em questão 
        //(objetos, tudo)
        while (parser.hasNext()) {
            Event evento = parser.next();
            // Fim do objeto, retorna
            if (evento == Event.END_OBJECT) {
                //Diferenciar o fechamento de objeto normal de objeto
                //aninhado em um array
                if (elementoAtual.getTipo() == ElementoBloco.OBJETO) {
                    if(elementoAtual.getBlocoFilho().isEmpty()) {
                        elementoAtual.setTipo(ElementoBloco.ATRIBUTO);
                    }
                    return;
                } else if (elementoAtual.getTipo() == ElementoBloco.ARR_OBJETO) {
                    evento = parser.next();
                    if (evento == Event.END_ARRAY) {
                        return;
                    }
                }
            } else if (evento == Event.END_ARRAY && elementoAtual.getTipo()
                    == ElementoBloco.ARR_OBJETO) {
                return;
            } else if (evento == Event.KEY_NAME) {
                // Encontrou um nome, mas não se sabe o que ele faz ainda
                nomeTemp = parser.getString();
                if (!estaNaListaConsolidada(nomeTemp, listaConsolidada)) {
                    String nomeT = obterTermoEquivalente(nomeTemp, listaReferencias2);
                    nomeTemp = nomeT;
                }
            } else if (evento == Event.VALUE_STRING
                    || evento == Event.VALUE_NUMBER
                    || evento == Event.VALUE_FALSE
                    || evento == Event.VALUE_NULL
                    || evento == Event.VALUE_TRUE) {
                // Encontrou um atributo, apenas adiciona
                // se ele já não tiver sido visitado anteriormente
                if (!estaNaLista(nomeTemp, visitados)) {
                    ElementoBloco novo = new ElementoBloco(nomeTemp,
                            ElementoBloco.ATRIBUTO);
                    //Adicionar depois regras (buscar na lista de consolidados e visitados)
                    elementoAtual.addBlocoFilho(novo);
                    visitados.add(nomeTemp);
                }
            } else if (evento == Event.START_ARRAY) {
                // Caso encontre um array, 2 casos:
                if (parser.hasNext()) {
                    evento = parser.next();
                    // 1-array simples
                    if (evento != Event.START_OBJECT) {
                        ElementoBloco novo = new ElementoBloco(nomeTemp,
                                ElementoBloco.ARRAY);
                        elementoAtual.addBlocoFilho(novo);
                    } else {
                        // 2-array de objetos
                        if (!estaNaLista(nomeTemp, visitados)) {
                            visitados.add(nomeTemp);
                        }
                        ElementoBloco novo = new ElementoBloco(nomeTemp,
                                ElementoBloco.ARR_OBJETO);
                        elementoAtual.addBlocoFilho(novo);
                        montaArvore(novo, parser, listaReferencias2,
                                listaConsolidada, visitados);
                    }
                }
            } else if (evento == Event.START_OBJECT) {
                // Encontrou um objeto filho
                ElementoBloco novo = new ElementoBloco(nomeTemp, ElementoBloco.OBJETO);
                elementoAtual.addBlocoFilho(novo);
                montaArvore(novo, parser, listaReferencias2,
                        listaConsolidada, visitados);
                if (!estaNaLista(nomeTemp, visitados)) {
                    visitados.add(nomeTemp);
                } else {
                    if(novo.getBlocoFilho().isEmpty()) {
                        elementoAtual.getBlocoFilho().remove(novo);
                    }
                }
            }
        }
    }

    /**
     * Método auxiliar para obter o termo equivalente na lista de referências 2,
     * usada quando o termo não é encontrado na lista consolidada. Caso o termo
     * não seja localizado, retorna null.
     */
    private String obterTermoEquivalente(String termo, List<String[]> listaReferencias2) {
        for (String[] item : listaReferencias2) {
            if (item[0].equalsIgnoreCase(termo)) {
                return item[1];
            } else if (item[1].equalsIgnoreCase(termo)) {
                return item[0];
            }
        }
        return null;
    }

    private boolean estaNaLista(String termo, List<String> lista) {
        for (String elemento : lista) {
            if (elemento.equalsIgnoreCase(termo)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Método auxiliar para busca na lista consolidada
     */
    private boolean estaNaListaConsolidada(String termo,
            List<List<ElementoBloco>> listaConsolidados) {
        for (List<ElementoBloco> lista : listaConsolidados) {
            for (ElementoBloco elemento : lista) {
                if (elemento.getNome().equalsIgnoreCase(termo)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void remontarPorBlocos() throws FileNotFoundException, IOException {
        //Carrega os artefatos de entrada
        ListasDAO l = new ListasDAO();
        //Lista de referencias 1
        List<List<String>> listaRef1 = l.lerListaReferencias1();
        //Lista de referencias 2
        List<String[]> listaRef2 = l.lerListaReferencias2();
        //Configuracoes especialista
        List<List<String>> listaEspecialista = l.lerListaEspecialista();
        //Lista de campos consolidados
        List<List<ElementoBloco>> blocosCamposConsolidados = l.lerListaCamposConsolidados();

        // Remonta a estrutura
        List<ElementoBloco> blocoPrincipal = remontarPorBlocos(blocosCamposConsolidados, listaRef2, listaRef1, listaEspecialista);

        // Grava a estrutura em arquivo, como estrutura consolidada
        EstruturaConsolidadaDAO est = new EstruturaConsolidadaDAO();
        est.gravarEstruturaConsolidada(blocoPrincipal);
    }

    private List<ElementoBloco> remontarPorBlocos(
            List<List<ElementoBloco>> blocosCamposConsolidados,
            List<String[]> listaRef2, List<List<String>> listaRef1,
            List<List<String>> listaEspecialista) throws IOException {

        int i;
        for (i = blocosCamposConsolidados.size() - 1; i >= 0; i--) {
            List<ElementoBloco> elementos = blocosCamposConsolidados.get(i);
            for (int j = elementos.size() - 1; j >= 0; j--) {
                ElementoBloco eb = elementos.get(j);
                atualizaElementoBloco(eb, listaRef1, listaEspecialista, i);
                if (eb.getTipo() == ElementoBloco.OBJETO
                        || eb.getTipo() == ElementoBloco.ARR_OBJETO) {

                    if (!filaBlocos.isEmpty()) {
                        eb.setBlocoFilho(filaBlocos.remove());
                    } else {
                        eb.setTipo(ElementoBloco.ATRIBUTO);
                    }

                }
            }
            filaBlocos.add(elementos);
        }
        // Seta o primeiro nó como o raiz do objeto
        return filaBlocos.poll();
    }

    private void atualizaElementoBloco(ElementoBloco elem,
            List<List<String>> listaRef1, List<List<String>> especialista,
            int numeroBloco) throws IOException {

        int tipoElemento = this.getTipoBloco(elem.getNome(), listaRef1);
        if (tipoElemento == -1) {
            tipoElemento = this.getTipoBlocoEspecialista(elem.getNome(),
                    especialista, numeroBloco);
        }

        switch (tipoElemento) {
            case InfoJSON.T_ARRAY:
                elem.setTipo(ElementoBloco.ARRAY);
                break;
            case InfoJSON.T_ARRAY_OBJETO:
                elem.setTipo(ElementoBloco.ARR_OBJETO);
                break;
            case InfoJSON.T_CAMPO:
                elem.setTipo(ElementoBloco.ATRIBUTO);
                break;
            case InfoJSON.T_OBJETO:
                elem.setTipo(ElementoBloco.OBJETO);
                break;
            default:
                // Fazer a busca na estrutura que representa as escolhas
                // do especialista e descobrir o tipo do elemento em um dos
                // documentos apontados(fazer)
                break;
        }
    }

    private int getTipoBlocoEspecialista(String nomeElem,
            List<List<String>> listaEspecialista, int numeroBloco)
            throws FileNotFoundException, IOException {
        int tipoDado = 0;
        List<String> documentos = listaEspecialista.get(numeroBloco);
        for (String documento : documentos) {
            InfoJSON info = new InfoJSON(documento);
            tipoDado = info.getTipoElemento(nomeElem);
            if (tipoDado != InfoJSON.T_NADA) {
                return tipoDado;
            }
        }
        return -1;
    }

    /**
     *
     */
    private int getTipoBloco(String nomeElem,
            List<List<String>> listaRef1) throws FileNotFoundException, IOException {
        for (List<String> referencia : listaRef1) {
            if (referencia.get(0).equalsIgnoreCase(nomeElem)) {
                InfoJSON info = new InfoJSON(referencia.get(1));
                int tipo = info.getTipoElemento(nomeElem);
                return tipo;
            }
        }
        return -1;
    }

    private String getStringFile(ElementoBloco elemento) {
        String str = "";
        str += "{" + elemento.getNome() + ";" + elemento.getAbreDelimitador()
                + getStringArquivo(elemento.getBlocoFilho())
                + elemento.getFechaDelimitador() + "}";
        return str;
    }

    private String getStringArquivo(List<ElementoBloco> elementos) {
        String str = "";
        for (ElementoBloco e : elementos) {
            if (e.getTipo() != ElementoBloco.OBJETO && e.getTipo()
                    != ElementoBloco.ARR_OBJETO) {
                str += e.getNome() + ";" + e.getAbreDelimitador()
                        + e.getFechaDelimitador();
            } else {
                str += e.getNome() + ";" + e.getAbreDelimitador()
                        + getStringArquivo(e.getBlocoFilho())
                        + e.getFechaDelimitador();
            }
        }
        return str;
    }

}
