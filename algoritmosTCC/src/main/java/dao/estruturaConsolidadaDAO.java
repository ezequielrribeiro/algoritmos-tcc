/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import aux.ParserEstruturaConsolidada;
import model.ElementoBloco;
import model.NodoEstruturaConsolidada;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 *
 * @author ezequielrr
 */
public class estruturaConsolidadaDAO {
    
    public NodoEstruturaConsolidada lerEstruturaConsolidada() 
            throws FileNotFoundException, IOException, Exception {
        String nomeArquivo = "estrutura-consolidada.txt";
        ParserEstruturaConsolidada notacoes = new ParserEstruturaConsolidada();
        FileReader arq = new FileReader(nomeArquivo);
        BufferedReader lerArq = new BufferedReader(arq);
        int c;

        while ((c = lerArq.read()) != -1) {
            notacoes.lerCaractere((char)c);
        }
        lerArq.close();
        arq.close();
        return notacoes.getNodoExibicao();
    }

    /**
     * Grava a estrutura consolidada a partir de uma estrutura remontada
     * @param elementos
     * @throws java.io.IOException
     */
    public void gravarEstruturaConsolidada(List<ElementoBloco> elementos) throws IOException {
        FileWriter arq = new FileWriter("estrutura-consolidada.txt");
        PrintWriter gravarArq = new PrintWriter(arq);
        gravarArq.println(getStringEstruturaConsolidada(elementos));
        gravarArq.close();
        arq.close();
    }

    /**
     * Grava o esquema conceitual a partir da estrutura consolidada
     */
    public void gravarEsquemaConceitual(NodoEstruturaConsolidada estrutura) 
            throws IOException {
        FileWriter arq = new FileWriter("esquema-conceitual.txt");
        PrintWriter gravarArq = new PrintWriter(arq);
        gravarArq.println(estrutura.toString());
        gravarArq.close();
        arq.close();        
    }
    
    /**
     * Método auxiliar usado para tornar a lista remontada em uma string
     * de uma estrutura consolidada
     */
    private String getStringEstruturaConsolidada(List<ElementoBloco> elementos) {
        String str = "";
        for (ElementoBloco e : elementos) {
            if (e.getTipo() != ElementoBloco.OBJETO && e.getTipo()
                    != ElementoBloco.ARR_OBJETO) {
                str += e.getNome() + ";" + e.getAbreDelimitador()
                        + e.getFechaDelimitador();
            } else {
                str += e.getNome() + ";" + e.getAbreDelimitador()
                        + getStringEstruturaConsolidada(e.getBlocoFilho())
                        + e.getFechaDelimitador();
            }
        }
        return str;
    }
}
