/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmostcc;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
    public static void main(String[] args) {
        // TODO code application logic here

        Implementa impl = new Implementa();

        int[][] matrizBloco = new int[][]{
            {1, 0, 0, 0, 0, 0},
            {0, 1, 0, 0, 0, 0},
            {0, 0, 1, 1, 0, 0},
            {0, 0, 0, 1, 0, 0},
            {0, 0, 0, 0, 1, 1},
            {0, 0, 0, 0, 0, 1}
        };
        String[] palavrasConsolidadas = new String[]{"created_at", "at_created",
            "entity", "entities", "place", "spot"};

        impl.consolidaEstruturaBloco(matrizBloco, palavrasConsolidadas);
        
        AdaptarNotacaoAgregados adp = new AdaptarNotacaoAgregados();
        
        try {
            FileReader fr = new FileReader("estr-unif.txt");
            int i; 
            while ((i=fr.read()) != -1) {
                //System.out.print((char) i);
                adp.lerCaractere((char) i);
            }
            System.out.println(adp.getNodoExibicao());
        } catch (FileNotFoundException ex) {
            System.out.println("Arquivo não encontrado");
        } catch (IOException ex) {
            Logger.getLogger(AlgoritmosTCC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
