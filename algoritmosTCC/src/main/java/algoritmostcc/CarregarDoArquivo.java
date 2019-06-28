/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmostcc;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Classe auxiliar para carregar os artefatos utilizados durante o processo de
 * etração de esquemas a partir de fontes de dados JSON
 *
 * @author ezequielrr
 */
public class CarregarDoArquivo {

    public CarregarDoArquivo() {
    }

    public List<List<ElementoBloco>> estruturaUnificadaBlocos(String arquivo)
            throws FileNotFoundException {
        List<List<ElementoBloco>> estruturaUnificada = new ArrayList<>();
        List<ElementoBloco> l = new ArrayList<>();
        Scanner scanner = new Scanner(new FileReader(arquivo))
                .useDelimiter("\\n");

        while (scanner.hasNext()) {
            String strTemp = scanner.next();
            String[] expl = strTemp.split(";");
            l = new ArrayList<>();
            for (String s : expl) {
                ElementoBloco tmp = new ElementoBloco();
                tmp.setNome(s.trim());
                l.add(tmp);
            }
            estruturaUnificada.add(l);
        }

        return estruturaUnificada;
    }

    public List<List<String>> listaReferencias1(String arquivo)
            throws FileNotFoundException {
        List<List<String>> listaReferencias = new ArrayList<>();
        List<String> l = new ArrayList<>();
        Scanner scanner = new Scanner(new FileReader(arquivo))
                .useDelimiter("\\n");

        while (scanner.hasNext()) {
            String temp = scanner.next();
            String[] expl = temp.split(";");
            l = new ArrayList<>();
            for (String s : expl) {
                l.add(s.trim());
            }
            listaReferencias.add(l);
        }
        return listaReferencias;
    }

    public List<String[]> listaReferencias2(String arquivo)
            throws FileNotFoundException {
        List<String[]> listaReferencias = new ArrayList<>();
        Scanner scanner = new Scanner(new FileReader(arquivo))
                .useDelimiter("\\;|\\n");

        while (scanner.hasNext()) {
            String nomeCampo = scanner.next();
            String nomeEquiv = scanner.next();
            String[] equivalencias = new String[2];
            equivalencias[0] = nomeCampo.trim();
            equivalencias[1] = nomeEquiv.trim();
            listaReferencias.add(equivalencias);
        }
        return listaReferencias;
    }

}
