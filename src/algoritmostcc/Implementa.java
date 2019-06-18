/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmostcc;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ezequielrr
 */
public class Implementa {
    //private List<MatrizBloco> termosConsolidados;

    public void consolidaEstruturaBloco(int[][] matrizBloco, String[] palavrasConsolidadas) {
        for(String p : palavrasConsolidadas) {
            System.out.println("Palavra consolidada:" + p);
        }

        List<String> aRemover = new ArrayList<>();
        List<String[]> equivalencias = new ArrayList<>();
        // Caminhamento em diagonal (acima da diagonal principal)
        // Movimentação das colunas
        for (int j = 0; j < matrizBloco.length; j++) {
            if (j == 0) {
                continue;
            }
            String[] equivalencia = new String[2];
            // Movimentação das linhas
            for (int i = j - 1; i >= 0; i--) {
                System.out.println(" coluna: " + j + "linha: " + i + 
                        " tam matriz: " + matrizBloco.length);
                if (matrizBloco[i][j] == 1) {
                    System.out.println("palavra linha:" + palavrasConsolidadas[i] +
                            " palavra coluna: " + palavrasConsolidadas[j]);
                    equivalencia[0] = palavrasConsolidadas[i];
                    equivalencia[1] = palavrasConsolidadas[j];
                    // Adiciona as equivalências a lista de equivalencias
                    equivalencias.add(equivalencia);
                    /* Adiciona a palavra da coluna a uma lista de palavras a
                     * serem excluídas da lista de palavras consolidadas
                     * posteriormente */
                    aRemover.add(palavrasConsolidadas[j]);
                }
            }
        }
        System.out.println("Matriz Bloco:" + matrizBloco.toString());
        System.out.println("Palavras Consolidadas:" + palavrasConsolidadas.toString());
        System.out.println("Lista de Equivalências 2:" + equivalencias.toString());
        System.out.println("Palavras a Remover:" + aRemover.toString());
    }
    
    public void remontarEstrutura() {
        
    }

}
