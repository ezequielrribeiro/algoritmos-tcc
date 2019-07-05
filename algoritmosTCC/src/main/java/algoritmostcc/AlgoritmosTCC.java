/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmostcc;

import dao.MatrizResultadosDAO;
import dao.ListasDAO;
import dao.estruturaConsolidadaDAO;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ezequielrr
 */
public class AlgoritmosTCC {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, Exception {
        Controle c = new Controle();
        c.processoEmBlocos();
        
        
        
//        List<List<String>> listaReferencias1;
//        ListasDAO l = new ListasDAO();
//        listaReferencias1 = l.lerListaReferencias1();
//        NodoEstruturaConsolidada n;
//        estruturaConsolidadaDAO e = new estruturaConsolidadaDAO();
//        n = e.lerEstruturaConsolidada();
//        e.gravarEsquemaConceitual(n);
//        System.out.println(n);

        
        
//        CarregarDoArquivo c = new CarregarDoArquivo();
//        List<List<ElementoBloco>> estruturaUnificada;
//        List<List<String>> listaEspecialista;
//        List<List<String>> listaReferencias1;
//        List<String[]> listaReferencias2;
//        try {
//            listaReferencias1 = c.listaReferencias1("lista-referencias-1.csv");
//            listaReferencias2 = c.listaReferencias2("lista-referencias-2.csv");
//            estruturaUnificada = c.estruturaUnificadaBlocos("estrutura-unificada.csv");
//            listaEspecialista = c.listaEspecialista("lista-especialista.csv");
//
//            RemontarEstrutura r = new RemontarEstrutura();
//            r.remontarPorBlocos(estruturaUnificada, listaReferencias2, listaReferencias1, listaEspecialista);
//            System.out.println(r.getStringArquivo());
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(AlgoritmosTCC.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(AlgoritmosTCC.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
}
