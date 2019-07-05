/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmostcc;

import model.ElementoBloco;
import aux.InfoJSON;
import dao.ListasDAO;
import dao.estruturaConsolidadaDAO;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ezequielrr
 */
public class RemontarEstrutura {

    private final Queue<List<ElementoBloco>> filaBlocos;

    public RemontarEstrutura() {
        this.filaBlocos = new LinkedList<>();
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
        estruturaConsolidadaDAO est = new estruturaConsolidadaDAO();
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
