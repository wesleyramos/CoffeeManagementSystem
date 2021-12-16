package br.pucbr.model;

public class Estoque {

    private int id;
    private double estoqueAtual;
    private double estoqueMinimo;

    public Estoque(double estoqueAtual, double estoqueMinimo) {
        this.estoqueAtual = estoqueAtual;
        this.estoqueMinimo = estoqueMinimo;
    }

    public Estoque(int _id, double _estoqueAtual, double _estoqueMinimo) {
        this.id = _id;
        this.estoqueAtual = _estoqueAtual;
        this.estoqueMinimo = _estoqueMinimo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) { this.id = id; }

    public double getEstoqueAtual() {
        return estoqueAtual;
    }

    public double getEstoqueMinimo() {
        return estoqueMinimo;
    }

    public void venderItem() {
        this.estoqueAtual--;
    }

    public void setEstoqueAtual(double estoqueAtual) {
        this.estoqueAtual = estoqueAtual;
    }

    public void setEstoqueMinimo(double estoqueMinimo) {
        this.estoqueMinimo = estoqueMinimo;
    }
}
