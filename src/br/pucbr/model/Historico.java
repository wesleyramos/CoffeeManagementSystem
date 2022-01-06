package br.pucbr.model;

import br.pucbr.model.dao.HistoricoDAO;

import java.util.Date;
import java.util.List;

public class Historico {

    private int id;
    private int usuarioId;
    private int vendaId;
    private Date data;
    private double total;

    private Usuario usuario;
    private Venda venda;

    public Historico(){

    }

    public Historico(int _usuarioId, int _vendaId, Date data, double _total) {
        this.usuarioId = _usuarioId;
        this.vendaId = _vendaId;
        this.data = data;
        this.total = _total;
    }

    public Historico(Date data, double total, Usuario _usuario, Venda _venda) {
        this.data = data;
        this.total = total;
        this.usuario = _usuario;
        this.usuarioId = _usuario.getId();
        this.venda = _venda;
        this.vendaId = _venda.getId();
    }

    public Historico(int id, java.sql.Date data, double total, Usuario usuario, Venda venda) {
        this.id = id;
        this.data = data;
        this.total = total;
        this.usuario = usuario;
        this.usuarioId = usuario.getId();
        this.venda = venda;
        this.vendaId = venda.getId();
    }

    public void listarHistoricoPorUsuario(String usuario) {
        HistoricoDAO historicoDAO = new HistoricoDAO();
        try {
            List<Historico> historicos = historicoDAO.buscarPorUsuario(usuario);
            if (historicos != null && historicos.size() > 0) {
                String sep = "          ";
                System.out.println("Historico do usuario " + usuario);
                System.out.println("Data venda" + sep + "Item" + sep + "Total");
                for (Historico historico : historicos) {
                    System.out.println(historico.getData() + sep + historico.getVenda().getItem().getDescricao() + sep + historico.getTotal());
                }

            } else {
                System.out.println("Usuario " + usuario + " sem historico.");
            }

        } catch (Exception e) {
            System.err.println("Erro ao listar historico do usuario " + usuario + ". " + e.getMessage());
        }
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }
}
