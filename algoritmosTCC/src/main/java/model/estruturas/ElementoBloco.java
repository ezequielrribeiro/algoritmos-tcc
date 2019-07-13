/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.estruturas;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author ezequielrr
 */
public class ElementoBloco {

    public final static int ATRIBUTO = 0;
    public final static int OBJETO = 1;
    public final static int ARR_OBJETO = 2;
    public final static int ARRAY = 3;

    private String nome;
    private int tipo;
    private List<ElementoBloco> blocoFilho;

    public ElementoBloco() {
        this.nome = "";
        this.tipo = ATRIBUTO;
        blocoFilho = new ArrayList<>(0);
    }

    public ElementoBloco(String nome, int tipo) {
        this.nome = nome;
        this.tipo = tipo;
        this.blocoFilho = new ArrayList<>(0);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public List<ElementoBloco> getBlocoFilho() {
        return blocoFilho;
    }

    public void setBlocoFilho(List<ElementoBloco> blocoFilho) {
        this.blocoFilho = blocoFilho;
    }

    public void addBlocoFilho(ElementoBloco elem) {
        this.blocoFilho.add(elem);
    }

    public String getAbreDelimitador() {
        switch (this.tipo) {
            case ElementoBloco.ARRAY:
                return "[";
            case ElementoBloco.ARR_OBJETO:
                return "[{";
            case ElementoBloco.ATRIBUTO:
                return "";
            case ElementoBloco.OBJETO:
                return "{";
            default:
                return null;
        }
    }

    public String getFechaDelimitador() {
        switch (this.tipo) {
            case ElementoBloco.ARRAY:
                return "]";
            case ElementoBloco.ARR_OBJETO:
                return "}]";
            case ElementoBloco.ATRIBUTO:
                return "";
            case ElementoBloco.OBJETO:
                return "}";
            default:
                return null;
        }
    }

    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(this.nome);
        hash = 47 * hash + this.tipo;
        hash = 47 * hash + Objects.hashCode(this.blocoFilho);
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
        final ElementoBloco other = (ElementoBloco) obj;
        if (this.tipo != other.tipo) {
            return false;
        }
        if(this.nome.equalsIgnoreCase(other.getNome())) {
            return true;
        }
        if (!Objects.equals(this.blocoFilho, other.blocoFilho)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "{" + "nome=" + nome + ", tipo=" + tipo + ", blocoFilho=\n\t" + blocoFilho + '}';
    }
}
