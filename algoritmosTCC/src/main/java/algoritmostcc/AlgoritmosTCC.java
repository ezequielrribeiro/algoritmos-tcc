/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmostcc;

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
    public static void main(String[] args) {
        CarregarDoArquivo c = new CarregarDoArquivo();
        List<List<String>> lr2;
        try {
            lr2 = c.listaReferencias1("lista-referencias-1.csv");
            for(List<String> e : lr2)
                System.out.println(e);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AlgoritmosTCC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
