/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmostcc;

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
    private List<ElementoBloco> blocoPrincipal;

    public RemontarEstrutura() {
        this.filaBlocos = new LinkedList<>();
        this.blocoPrincipal = null;
    }

    public void remontarPorBlocos(List<List<ElementoBloco>> blocosCamposConsolidados,
            List<String[]> listaRef2, List<List<String>> listaRef1) throws IOException {

        int i = 0;
        for (i = blocosCamposConsolidados.size() - 1; i >= 0; i--) {
            List<ElementoBloco> elementos = blocosCamposConsolidados.get(i);
            for (int j = elementos.size() - 1; j >= 0; j--) {
                ElementoBloco eb = elementos.get(j);
                atualizaElementoBloco(eb, listaRef1);
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
        this.blocoPrincipal = filaBlocos.poll();
    }

    private void atualizaElementoBloco(ElementoBloco elem,
            List<List<String>> listaRef1) throws IOException {
        int tipoElemento = this.getTipoBloco(elem.getNome(), listaRef1);
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

    public String getStringArquivo() {
        return this.getStringArquivo(blocoPrincipal);
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
