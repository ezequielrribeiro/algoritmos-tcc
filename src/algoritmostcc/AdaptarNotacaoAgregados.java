/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmostcc;

import java.util.Stack;

/**
 *
 * @author ezequielrr
 */
public class AdaptarNotacaoAgregados {

    public final static char ESPACO = ' ';
    public final static char FIM_PALAVRA = ';';
    public final static char INI_CLASSE = '{';
    public final static char FECHA_CLASSE = '}';
    public final static char INI_ARRAY = '[';
    public final static char FECHA_ARRAY = ']';
    public final static String NADA = "%%";
    private final int NODOS_ABERTOS = 0;
    private final int NODOS_FECHADOS = 1;

    private boolean leituraPalavra;
    private Stack<String> bufferPalavras;
    private StringBuilder palavraAtual;
    private Stack[] pilha;
    private NodoExibicao nodoExibicao;
    private NodoExibicao nodoAtual;
    
    public AdaptarNotacaoAgregados() {
        pilha = new Stack[2];
        pilha[NODOS_ABERTOS] = new Stack<NodoExibicao>();
        pilha[NODOS_FECHADOS] = new Stack<NodoExibicao>();
        nodoExibicao = null;
        palavraAtual = new StringBuilder();
        bufferPalavras = new Stack<>();
    }

    public void lerCaractere(char c) {

        switch (c) {
            case ESPACO:
                if (this.leituraPalavra) {
                    this.palavraAtual.append(c);
                }
                break;
            case FIM_PALAVRA:
                if (this.leituraPalavra) {
                    this.leituraPalavra = false;
                    this.adaptarNotacao(FIM_PALAVRA);
                }
                break;
            case INI_CLASSE:
                this.adaptarNotacao(INI_CLASSE);
                break;
            case FECHA_CLASSE:
                this.adaptarNotacao(FECHA_CLASSE);
                break;
            case INI_ARRAY:
                this.adaptarNotacao(INI_ARRAY);
                break;
            case FECHA_ARRAY:
                this.adaptarNotacao(FECHA_ARRAY);
                break;
            default:
                this.leituraPalavra = true;
                this.palavraAtual.append(c);
                break;
        }
    }

    private void adaptarNotacao(char simbolo) {
        switch (simbolo) {
            case FIM_PALAVRA:
                if (bufferPalavras.empty()) {
                    bufferPalavras.push(palavraAtual.toString());
                } else {
                    nodoAtual.addAtributo(bufferPalavras.pop());
                    bufferPalavras.push(palavraAtual.toString());
                }
                palavraAtual = new StringBuilder();
                break;
            case INI_CLASSE:
                NodoExibicao novoNodo = new NodoExibicao(bufferPalavras.pop());
                if(nodoAtual != null) {
                    nodoAtual.addFilho(novoNodo);
                    nodoAtual.addRelacionamento(NodoExibicao.ZERO_UM);
                    pilha[NODOS_ABERTOS].push(nodoAtual);
                } else {
                    novoNodo.setRaiz(true);
                }
                nodoAtual = novoNodo;
                break;
            case FECHA_CLASSE:
                if(!bufferPalavras.empty()) {
                    nodoAtual.addAtributo(bufferPalavras.pop());
                }
                if(!pilha[NODOS_ABERTOS].empty()) {
                    pilha[NODOS_FECHADOS].push(nodoAtual);
                    nodoAtual = (NodoExibicao) pilha[NODOS_ABERTOS].pop();
                } else {
                    pilha[NODOS_FECHADOS].push(nodoAtual);
                }
                break;
            case INI_ARRAY:
                break;
            case FECHA_ARRAY:
                break;
            default:
                break;
        }
    }

    public NodoExibicao getNodoExibicao() {
        nodoExibicao = (NodoExibicao) pilha[NODOS_FECHADOS].pop();
        return this.nodoExibicao;
    }

}
