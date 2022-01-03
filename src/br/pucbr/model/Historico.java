package br.pucbr.model;

import java.util.Date;

public class Historico {

    private int id;
    private int usuarioId;
    private int vendaId;
    private Date data;
    private double total;

    private Usuario usuario;
    private Venda venda;

    public Historico(int _usuarioId, int _vendaId, Date data, double _total) {
        this.usuarioId = _usuarioId;
        this.vendaId = _vendaId;
        this.data = data;
        this.total = _total;
    }

    public Historico(Date data, double total, Usuario _usuario, Venda venda) {
        this.data = data;
        this.total = total;
        this.usuario = _usuario;
        this.usuarioId = _usuario.getId();
        this.venda = venda;
        this.vendaId = venda.getId();
    }

    @Override
    public String toString() {
        return "Historico{" +
                "id=" + id +
                ", usuarioId=" + usuarioId +
                ", vendaId=" + vendaId +
                ", data=" + data +
                ", total=" + total +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getVendaId() {
        return vendaId;
    }

    public void setVendaId(int vendaId) {
        this.vendaId = vendaId;
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

    public Usuario getUsuario() { return usuario; }

    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public Venda getVenda() { return venda; }

    public void setVenda(Venda venda) { this.venda = venda; }
}
