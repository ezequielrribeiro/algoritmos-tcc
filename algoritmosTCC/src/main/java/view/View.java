/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.Controle;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.RemontarEstrutura;
import model.aux.InfoJSON;

/**
 * Classe para visualização do software. Conta apenas com a entrada do usuário
 * para optar entre os modos em blocos ou único bloco.
 * @author ezequielrr
 */
public class View {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        Controle c = new Controle();
        
        
        Scanner s = new Scanner(System.in);

        System.out.println("\n\n\nEXTRAÇÃO DE ESQUEMAS A PARTIR DE ARQUIVOS JSON\n\n\n");
        System.out.println("Escolha o modo de operação (1 = em blocos;"
                + " 2 = único bloco)\n\n");
        int modo = s.nextInt();
        try {
            c.executarProcesso(modo);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("Extração de esquemas a partir de arquivos JSON"
                + " executada. Verifique os arquivos de saída gerados.");
    }
}
