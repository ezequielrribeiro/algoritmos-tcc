/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmostcc;

import java.io.IOException;

/**
 *
 * @author ezequielrr
 */
public class Controle {

    /**
     * Executa o processo de extração do esquema em blocos
     */
    public void processoEmBlocos() throws IOException, Exception {
        ConsolidarEstrutura cons = new ConsolidarEstrutura();
        RemontarEstrutura rem = new RemontarEstrutura();
        MontarMapeamentos mm = new MontarMapeamentos();
        //Consolidar Estrutura
        cons.consolidarEstruturaBlocos();

        // Remontar Estrutura
        rem.remontarPorBlocos();
        // Carregar Estrutura Consolidada
        
        //Gerar mapeamentos
        mm.montarMapeamentos();
        
        //Adaptar à notação de agregados (esquema conceitual)

    }

    public void processoUnicoBloco() {

    }
}
