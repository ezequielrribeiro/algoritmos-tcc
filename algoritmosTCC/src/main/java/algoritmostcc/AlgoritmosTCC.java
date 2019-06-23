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
        // TODO code application logic here

        /*Implementa impl = new Implementa();

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

        impl.consolidaEstruturaBloco(matrizBloco, palavrasConsolidadas);*/
        AdaptarNotacaoAgregados adp = new AdaptarNotacaoAgregados();

        MontarMapeamentos m = new MontarMapeamentos();

        String[] eq1 = {"in_reply_to_user_id_str", "in_reply_to_user_id"};
        String[] eq2 = {"place", "spot"};
        String[] eq3 = {"spot", "um_teste"};
        String[] eq4 = {"cara", "um_teste"};
        List<String[]> listaRef2 = new ArrayList<>();
        listaRef2.add(eq1);
        listaRef2.add(eq2);
        listaRef2.add(eq3);
        listaRef2.add(eq4);

        List<List<String>> listaRef1 = new ArrayList<>();

        List<String> ref1 = new ArrayList<>();
        ref1.add("in_reply_to_user_id_str");
        ref1.add("doc1");
        ref1.add("doc2");
        ref1.add("doc4");

        List<String> ref2 = new ArrayList<>();
        ref2.add("in_reply_to_user_id");
        ref2.add("doc1");
        ref2.add("doc3");

        List<String> ref3 = new ArrayList<>();
        ref3.add("place");
        ref3.add("doc1");
        ref3.add("doc3");

        List<String> ref4 = new ArrayList<>();
        ref4.add("spot");
        ref4.add("doc3");

        List<String> ref5 = new ArrayList<>();
        ref5.add("place");
        ref5.add("doc1");

        List<String> ref6 = new ArrayList<>();
        ref6.add("um_teste");
        ref6.add("doc6");

        List<String> ref7 = new ArrayList<>();
        ref7.add("cara");
        ref7.add("docx");

        listaRef1.add(ref1);
        listaRef1.add(ref2);
        listaRef1.add(ref3);
        listaRef1.add(ref4);
        listaRef1.add(ref5);
        listaRef1.add(ref6);
        listaRef1.add(ref7);

        try {
            FileReader fr = new FileReader("estr-unif.txt");
            int i;
            while ((i = fr.read()) != -1) {
                //System.out.print((char) i);
                adp.lerCaractere((char) i);
            }

            List<String[]> um_teste = m.getMapeamentos(adp.getListaNomes(),
                    adp.getListaJsonPath(), listaRef2, listaRef1);
            for (String[] teste : um_teste) {
                System.out.println("(0): " + teste[0] + " (1): " + teste[1] + " (2): " + teste[2]);
            }

        } catch (FileNotFoundException ex) {
            System.out.println("Arquivo não encontrado");
        } catch (IOException ex) {
            Logger.getLogger(AlgoritmosTCC.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            //System.out.println(ex.getMessage());
            Logger.getLogger(AlgoritmosTCC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
