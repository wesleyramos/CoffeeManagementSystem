package br.pucbr.model;

public class Item {
    private int id;
    private String descricao;
    private Double valor;
    private Estoque estoque;

    public Item(int id, String descricao, Double valor, Estoque estoque) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.estoque = estoque;
    }

    public Item( String descricao, Double valor, Estoque estoque) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.estoque = estoque;
    }

    public Estoque getEstoque() {
        return estoque;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) { this.id = id; }

    public String getDescricao() {
        return descricao;
    }

    public Double getValor() {
        return valor;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public void setEstoque(Estoque estoque) {
        this.estoque = estoque;
    }
}
