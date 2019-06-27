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
            List<String[]> listaRef2, List<List<String>> listaRef1) {

        int i = 0;
        for (i = blocosCamposConsolidados.size() - 1; i >= 0; i--) {
            List<ElementoBloco> elementos = blocosCamposConsolidados.get(i);
            for (ElementoBloco eb : elementos) {
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
            List<List<String>> listaRef1) {

        for (List<String> referencia : listaRef1) {
            if (referencia.get(0).equals(elem.getNome())) {
                try {
                    InfoJSON info = new InfoJSON(referencia.get(1));
                    int tipo = info.getTipoElemento(elem.getNome());
                    switch (tipo) {
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
                    }
                } catch (FileNotFoundException ex) {
                    System.out.println("Arquivo referenciado na lista de"
                            + " referências 1 não encontrado");
                } catch (IOException ex) {
                    Logger.getLogger(RemontarEstrutura.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
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
