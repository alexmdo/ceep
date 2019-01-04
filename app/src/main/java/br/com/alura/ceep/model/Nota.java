package br.com.alura.ceep.model;

import java.io.Serializable;

public class Nota implements Serializable {

    private Long id;
    private String titulo;
    private String descricao;
    private PaletaCorEnum paletaCorEnum = PaletaCorEnum.BRANCO;
    private Integer posicao;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPosicao() {
        return posicao;
    }

    public void setPosicao(Integer posicao) {
        this.posicao = posicao;
    }
}