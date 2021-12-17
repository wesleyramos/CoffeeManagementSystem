package br.pucbr.model;

import java.util.Date;

public class Venda {

    private int id;
    private Date data;
    private double total;
    private int itemId;
    private Item item;

    public Venda(int id, Date data, Double total, int _itemId) {
        this.id = id;
        this.data = data;
        this.total = total;
        this.itemId = _itemId;
    }

    public Venda(Date data, Double total, Item _item) {
        this.id = id;
        this.data = data;
        this.total = total;
        this.item = _item;
    }

    public Venda(Date data, Double total, int itemId, Item _item) {
        this.id = id;
        this.itemId = itemId;
        this.data = data;
        this.total = total;
        this.item = _item;
    }

    public Venda(int id, Date data, Double total, Item _item) {
        this.id = id;
        this.data = data;
        this.total = total;
        this.item = _item;
    }

    @Override
    public String toString() {
        return "Venda{" +
                "id=" + id +
                ", data=" + data +
                ", total=" + total +
                ", idItem=" + itemId +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public Item getItem() {
        return item;
    }
}
