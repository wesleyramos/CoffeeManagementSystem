package br.pucbr.model;

import java.util.Date;

public class Venda {

    private int id;
    private Date data;
    private Double total;
    private int idItem;

    public Venda(int id, Date data, Double total, int idItem) {
        this.id = id;
        this.data = data;
        this.total = total;
        this.idItem = idItem;
    }

    public int getId() {
        return id;
    }


}
