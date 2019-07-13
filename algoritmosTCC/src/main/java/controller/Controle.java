/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.ConsolidarEstrutura;
import model.RemontarEstrutura;
import model.MontarMapeamentos;
import model.AdaptarNotacaoAgregados;
import java.io.IOException;

/**
 *
 * @author ezequielrr
 */
public class Controle {

    public void executarProcesso(int modo) throws Exception {
        if (modo == 1) {
            this.processoEmBlocos();
        } else if (modo == 2) {
            this.processoUnicoBloco();
        } else {
            throw new Exception("Você pode apenas selecionar um modo "
                    + "(1 = blocos, 2 = único bloco)");
        }
    }

    /**
     * Executa o processo de extração do esquema em blocos
     */
    private void processoEmBlocos() throws IOException, Exception {
        ConsolidarEstrutura cons = new ConsolidarEstrutura();
        RemontarEstrutura rem = new RemontarEstrutura();
        MontarMapeamentos mm = new MontarMapeamentos();
        AdaptarNotacaoAgregados an = new AdaptarNotacaoAgregados();
        //Consolidar Estrutura
        cons.consolidarEstruturaBlocos();

        // Remontar Estrutura
        rem.remontarPorBlocos();
        // Carregar Estrutura Consolidada

        //Gerar mapeamentos
        mm.montarMapeamentos();

        //Adaptar à notação de agregados (esquema conceitual)
        an.adaptarNotacaoAgregados();

    }

    /** Executa o processo de extração de esquema como um único bloco,
     *  comparando todos os elementos de todos os graus de hierarquia
     *  entre si*/
    public void processoUnicoBloco() throws IOException, Exception {
        ConsolidarEstrutura cons = new ConsolidarEstrutura();
        RemontarEstrutura rem = new RemontarEstrutura();
        MontarMapeamentos mm = new MontarMapeamentos();
        AdaptarNotacaoAgregados an = new AdaptarNotacaoAgregados();
        //Consolidar Estrutura
        cons.consolidarEstruturaBlocos();

        // Remontar Estrutura
        rem.remontarUnicoBloco();

        //Gerar mapeamentos
        mm.montarMapeamentos();

        //Adaptar à notação de agregados (esquema conceitual)
        an.adaptarNotacaoAgregados();
    }
}
