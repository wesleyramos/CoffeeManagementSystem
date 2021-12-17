package br.pucbr.model;

public class Credito {

    private int id;
    private Double valorTotal;

    public Credito(int _id, Double _valorTotal) {
        this.id = _id;
        this.valorTotal = _valorTotal;
    }

    public Credito(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Credito() {
        this.valorTotal = 0d;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public void inserirCredito() {

    }

    public void pagarCompra(double valorPago) {
        valorTotal -= valorPago;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Credito{" +
                "id=" + id +
                ", valorTotal=" + valorTotal +
                '}';
    }
}
