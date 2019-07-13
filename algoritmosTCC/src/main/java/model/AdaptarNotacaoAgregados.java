/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import model.dao.EstruturaConsolidadaDAO;
import model.estruturas.NodoEstruturaConsolidada;

/**
 *
 * @author ezequielrr
 */
public class AdaptarNotacaoAgregados {

    public AdaptarNotacaoAgregados() {
    }
    
    public void adaptarNotacaoAgregados() throws Exception {
        // Carrega os artefatos de entrada
        EstruturaConsolidadaDAO ecd = new EstruturaConsolidadaDAO();
        NodoEstruturaConsolidada n = ecd.lerEstruturaConsolidada();

        // Grava esquema conceitual
        ecd.gravarEsquemaConceitual(n);
    }
    
}
