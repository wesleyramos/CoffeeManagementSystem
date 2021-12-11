package br.pucbr.controller;

import br.pucbr.model.Credito;
import br.pucbr.model.Estoque;
import br.pucbr.model.Historico;
import br.pucbr.model.Item;
import br.pucbr.model.Usuario;
import br.pucbr.model.UsuarioAdmin;
import br.pucbr.model.UsuarioMensal;
import br.pucbr.utils.ListaVenda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Maquina {

    private static List<Usuario> usuarioList = Arrays.asList(
            new UsuarioMensal(2, "wesley", "wesley", "wesley", new Credito(0d)),
            new UsuarioAdmin(3, "admin", "admin", "admin", new Credito(0d))
    );
    private static List<Item> itemList = Arrays.asList(new Item(1, "café extra forte", 4d, new Estoque(3, 1)));
    private static List<Historico> historicoSistema = new ArrayList<>();
    private static ListaVenda vendas = new ListaVenda();

}
