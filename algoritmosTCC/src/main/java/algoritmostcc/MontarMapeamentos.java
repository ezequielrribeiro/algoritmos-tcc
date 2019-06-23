/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmostcc;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ezequielrr
 */
public class MontarMapeamentos {

    public MontarMapeamentos() {

    }

    public List<String[]> getMapeamentos(List<String> listaCamposConsolidados,
            List<String> listaJsonPathCamposConsolidados, 
            List<String[]> listaRef2, List<List<String>> listaRef1) {
        int i = 0;
        
        List<String> fixados = new ArrayList<>();
        List<String[]> mapeamentos = new ArrayList<>();
        for(String elem : listaCamposConsolidados) {
            
            i++;
            List<String> termosEquivalentes = new ArrayList<>();
            termosEquivalentes(elem, listaRef2, termosEquivalentes, fixados);
            //fixados.addAll(termosEquivalentes);
            
            for(String termo : termosEquivalentes) {
                //if (fixados.contains(termo)) continue;
                List<String> docOrig = getDocOrigem(termo, listaRef1);
                for (String documentoOrig : docOrig) {
                    i = listaCamposConsolidados.indexOf(elem);
                    String[] dados = {listaJsonPathCamposConsolidados.get(i),
                        termo, documentoOrig};
                    mapeamentos.add(dados);
                }
            }            
        }
        System.out.println(fixados);
        return mapeamentos;
    }

    private void termosEquivalentes(String termo, List<String[]> listaRef2,
            List<String> equivalencias, List<String> fixados) {
        if (fixados.contains(termo)) return;
        //System.out.println(termo);
        for (String[] termoAtual : listaRef2) {
            if (termoAtual[0].equals(termo)) {
                if (!fixados.contains(termoAtual[1]) && !equivalencias.contains(termoAtual[1])) {
                    equivalencias.add(termoAtual[1]);
                    fixados.add(termo);
                    termosEquivalentes(termoAtual[1], listaRef2,
                            equivalencias, fixados);
                }
            } else if (termoAtual[1].equals(termo)) {
                if (!fixados.contains(termoAtual[0]) && !equivalencias.contains(termoAtual[0])) {
                    equivalencias.add(termoAtual[0]);
                    fixados.add(termo);
                    termosEquivalentes(termoAtual[0], listaRef2,
                            equivalencias, fixados);
                }
            }
        }
        
    }

    private List<String> getDocOrigem(String termo,
            List<List<String>> listaRef1) {
        List<String> res = new ArrayList<>();
        for(List<String> elem : listaRef1) {
            if(elem.size() > 0) {
                if(elem.get(0) == termo) {
                    for (int i = 1; i < elem.size(); i++) {
                        res.add(elem.get(i));
                    }
                }
            }
        }
        return res;
    }
}
