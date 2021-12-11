package br.pucbr.controller;

import br.pucbr.model.Credito;
import br.pucbr.model.Estoque;
import br.pucbr.model.Historico;
import br.pucbr.model.Item;
import br.pucbr.model.UsuarioMensal;
import br.pucbr.utils.ComprarProduto;
import br.pucbr.utils.ListaVenda;
import br.pucbr.utils.MenuAdmin;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainTest {

    @Test
    public void tentarComprarSemSaldo() {
        Item itemCafe = new Item(1, "café extra forte", 4d, new Estoque(3, 1));
        UsuarioMensal usuarioWesley = new UsuarioMensal(2, "wesley", "wesley", "wesley", new Credito(0d));
        List<Historico> historicoSistema = new ArrayList<>();
        ListaVenda vendas = new ListaVenda();
        ComprarProduto.pagarViaCredito(usuarioWesley, itemCafe, vendas, historicoSistema);
    }

    @Test
    public void usuarioComSaldoIgualAoItemComprado() {
        Item itemCafe = new Item(1, "café extra forte", 4d, new Estoque(3, 1));
        UsuarioMensal usuarioWesley = new UsuarioMensal(2, "wesley", "wesley", "wesley", new Credito(4d));
        List<Historico> historicoSistema = new ArrayList<>();
        ListaVenda vendas = new ListaVenda();
        ComprarProduto.pagarViaCredito(usuarioWesley, itemCafe, vendas, historicoSistema);
        MenuAdmin.imprimirHistorico(vendas);
    }

}