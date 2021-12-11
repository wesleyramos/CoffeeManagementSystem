package br.pucbr.model;

public class Estoque {
    private int estoqueAtual;
    private int estoqueMinimo;

    public Estoque(int estoqueAtual, int estoqueMinimo) {
        this.estoqueAtual = estoqueAtual;
        this.estoqueMinimo = estoqueMinimo;
    }

    public int getEstoqueAtual() {
        return estoqueAtual;
    }

    public int getEstoqueMinimo() {
        return estoqueMinimo;
    }

    public void venderItem() {
        this.estoqueAtual--;
    }

}
