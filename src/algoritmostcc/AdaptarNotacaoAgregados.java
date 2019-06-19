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

    private final static char ESPACO = ' ';
    private final static char FIM_PALAVRA = ';';
    private final static char INI_CLASSE = '{';
    private final static char FECHA_CLASSE = '}';
    private final static char INI_ARRAY = '[';
    private final static char FECHA_ARRAY = ']';
    private final static char INI_ARRAY_OBJ = '?';
    private final static char FECHA_ARRAY_OBJ = '*';
    private final static char PALAVRA = '~';

    private final static int EST_GERAL = 0;
    private final static int EST_INI_ARR = 1;
    private final static int EST_INI_ARR_OBJ = 2;
    private final static int EST_FECHA_OBJ = 3;
    private final static int EST_FECHA_ARR_OBJ = 4;

    public final static String NADA = "%%";
    private final static int NODOS_ABERTOS = 0;
    private final static int NODOS_FECHADOS = 1;

    private boolean leituraPalavra;
    private final Stack<String> bufferPalavras;
    private int estadoLeitura;
    private char bufferSimbolos;
    private StringBuilder palavraAtual;
    private final Stack[] pilha;
    private NodoExibicao nodoExibicao;
    private NodoExibicao nodoAtual;

    public AdaptarNotacaoAgregados() {
        pilha = new Stack[2];
        pilha[NODOS_ABERTOS] = new Stack<>();
        pilha[NODOS_FECHADOS] = new Stack<>();
        nodoExibicao = null;
        palavraAtual = new StringBuilder();
        bufferPalavras = new Stack<>();
        estadoLeitura = EST_GERAL;
    }

    public void lerCaractere(char c) {

        switch (c) {
            case ESPACO:
                if (leituraPalavra) {
                    bufferSimbolos = PALAVRA;
                    palavraAtual.append(c);
                }
                break;
            case '\n':
                break;
            case FIM_PALAVRA:
                if (leituraPalavra) {
                    bufferSimbolos = FIM_PALAVRA;
                    leituraPalavra = false;
                    adaptarNotacao(FIM_PALAVRA);
                }
                break;
            case INI_CLASSE:
                if (bufferSimbolos == INI_ARRAY) {
                    adaptarNotacao(INI_ARRAY_OBJ);
                } else {
                    adaptarNotacao(INI_CLASSE);
                }
                break;
            case FECHA_CLASSE:
                bufferSimbolos = FECHA_CLASSE;
                adaptarNotacao(FECHA_CLASSE);
                break;
            case INI_ARRAY:
                bufferSimbolos = INI_ARRAY;
                break;
            case FECHA_ARRAY:
                if (bufferSimbolos != FECHA_CLASSE) {
                    adaptarNotacao(INI_ARRAY);
                    adaptarNotacao(FECHA_ARRAY);
                }
                break;
            default:
                leituraPalavra = true;
                bufferSimbolos = PALAVRA;
                palavraAtual.append(c);
                break;
        }
        //System.out.print(estadoLeitura);
    }

    private void adaptarNotacao(char simbolo) {
        //System.out.print(simbolo);
        NodoExibicao novoNodo = null;
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
                novoNodo = new NodoExibicao(bufferPalavras.pop());
                if (nodoAtual != null) {
                    nodoAtual.addFilho(novoNodo);
                    nodoAtual.addRelacionamento(NodoExibicao.ZERO_UM);
                    pilha[NODOS_ABERTOS].push(nodoAtual);
                } else {
                    novoNodo.setRaiz(true);
                }
                nodoAtual = novoNodo;
                break;
            case FECHA_CLASSE:
                if (!bufferPalavras.empty()) {
                    nodoAtual.addAtributo(bufferPalavras.pop());
                }
                if (!pilha[NODOS_ABERTOS].empty()) {
                    pilha[NODOS_FECHADOS].push(nodoAtual);
                    nodoAtual = (NodoExibicao) pilha[NODOS_ABERTOS].pop();
                } else {
                    pilha[NODOS_FECHADOS].push(nodoAtual);
                }
                break;
            case INI_ARRAY:
                novoNodo = new NodoExibicao(bufferPalavras.pop());
                novoNodo.addAtributo("att");
                nodoAtual.addFilho(novoNodo);
                nodoAtual.addRelacionamento(NodoExibicao.ZERO_MUITOS);
                break;
            case FECHA_ARRAY:
                break;
            case INI_ARRAY_OBJ:
                novoNodo = new NodoExibicao(bufferPalavras.pop());
                nodoAtual.addFilho(novoNodo);
                nodoAtual.addRelacionamento(NodoExibicao.ZERO_MUITOS);
                pilha[NODOS_ABERTOS].push(nodoAtual);
                nodoAtual = novoNodo;
                break;
            case FECHA_ARRAY_OBJ:
                pilha[NODOS_FECHADOS].push(nodoAtual);
                nodoAtual = (NodoExibicao) pilha[NODOS_ABERTOS].pop();
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
