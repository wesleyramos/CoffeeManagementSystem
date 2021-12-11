package br.pucbr.utils;

import br.pucbr.model.Venda;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ListaVenda {

    private List<Venda> listaVendas = new ArrayList<>();
    private int proximoIdValido = 0;


    public int adicionarVenda(Date dataCompra, Double total, int id) {
        Venda novaVenda = new Venda(proximoIdValido, dataCompra, total, id);
        listaVendas.add(novaVenda);
        proximoIdValido++;
        return novaVenda.getId();
    }
}
