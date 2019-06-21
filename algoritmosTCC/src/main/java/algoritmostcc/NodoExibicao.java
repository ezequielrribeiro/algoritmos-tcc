/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmostcc;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author ezequielrr
 */
public class NodoExibicao {
    
    public static final String ZERO_UM = "0:1";
    public static final String ZERO_MUITOS = "0:N";

    private final String nome;
    private boolean raiz;
    private final List<NodoExibicao> filhos;
    private final List<String> relacionamentos;
    private final List<String> atributos;

    public NodoExibicao(String nome) {
        this.nome = nome;
        this.relacionamentos = new ArrayList<>();
        this.filhos = new ArrayList<>();
        atributos = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public boolean isRaiz() {
        return raiz;
    }

    public List<String> getRelacionamentos() {
        return relacionamentos;
    }

    public void setRaiz(boolean raiz) {
        this.raiz = raiz;
    }
    
    public void addRelacionamento(String relacionamento) {
        this.relacionamentos.add(relacionamento);
    }
    
    public void addFilho(NodoExibicao nodo) {
        filhos.add(nodo);
    }

    public List<String> getAtributos() {
        return atributos;
    }
    
    public void addAtributo(String atributo) {
        atributos.add(atributo);
    }

    @Override
    public String toString() {
        return "NodoExibicao{" + "nome=" + nome + ", raiz=" + raiz + ", filhos=" + filhos + ", relacionamentos=" + relacionamentos + ", atributos=" + atributos + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.nome);
        hash = 53 * hash + (this.raiz ? 1 : 0);
        hash = 53 * hash + Objects.hashCode(this.filhos);
        hash = 53 * hash + Objects.hashCode(this.relacionamentos);
        hash = 53 * hash + Objects.hashCode(this.atributos);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final NodoExibicao other = (NodoExibicao) obj;
        if (this.raiz != other.raiz) {
            return false;
        }
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Objects.equals(this.filhos, other.filhos)) {
            return false;
        }
        if (!Objects.equals(this.relacionamentos, other.relacionamentos)) {
            return false;
        }
        if (!Objects.equals(this.atributos, other.atributos)) {
            return false;
        }
        return true;
    }
    
    
}
