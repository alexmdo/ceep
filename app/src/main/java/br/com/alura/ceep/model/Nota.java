package br.com.alura.ceep.model;

import java.io.Serializable;

public class Nota implements Serializable {

    private String titulo;
    private String descricao;
    private PaletaCorEnum paletaCorEnum = PaletaCorEnum.BRANCO;

    public Nota() {}

    public Nota(String titulo, String descricao) {
        this.titulo = titulo;
        this.descricao = descricao;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public PaletaCorEnum getPaletaCorEnum() {
        return paletaCorEnum;
    }

    public void setPaletaCorEnum(PaletaCorEnum paletaCorEnum) {
        this.paletaCorEnum = paletaCorEnum;
    }
}