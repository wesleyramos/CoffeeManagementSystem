package br.pucbr.model;

import java.util.Date;

public class Historico {

    private int usuario_id;
    private int venda_id;
    private Date data;

    public Historico(int usuario_id, int venda_id, Date data) {
        this.usuario_id = usuario_id;
        this.venda_id = venda_id;
        this.data = data;
    }
}
