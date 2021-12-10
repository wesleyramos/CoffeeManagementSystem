package br.pucbr.model;

public class Item {
    private int id;
    private String descricao;
    private Double valor;

    public Item(int id, String descricao, Double valor) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
    }

    public int getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public Double getValor() {
        return valor;
    }
}
