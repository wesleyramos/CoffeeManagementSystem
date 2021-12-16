package br.pucbr.controller;

import br.pucbr.model.Credito;
import br.pucbr.model.Usuario;
import br.pucbr.model.UsuarioAdmin;
import br.pucbr.model.dao.UsuarioDAO;
import br.pucbr.utils.BancoDeDados;
import br.pucbr.utils.Login;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class MainTest {

    @Test
    public void testeLoginFail() {
        BancoDeDados.criarTabelas();

        Usuario usuario = Login.efetuarLogin("admin", "admin");
        assertNull(usuario);

    }

    @Test
    public void testeLoginSucesso() {
        BancoDeDados.criarTabelas();

        UsuarioAdmin admin = new UsuarioAdmin("admin", "admin", "admin", new Credito(0d));
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        usuarioDAO.inserir(admin);

        Usuario usuario = Login.efetuarLogin("admin", "admin");
        assertEquals("login sucesso!", "admin", usuario.getUsuario());

    }

//    @Test
//    public void tentarComprarSemSaldo() {
//        Item itemCafe = new Item(1, "café extra forte", 4d, new Estoque(3, 1));
//        ItemDAO itemDAO = new ItemDAO();
//        itemDAO.inserir(itemCafe);
//        UsuarioMensal usuarioWesley = new UsuarioMensal(2, "wesley", "wesley", "wesley", new Credito(0d));
//        UsuarioDAO usuarioDAO = new UsuarioDAO();
//        usuarioDAO.inserir(usuarioWesley);
//        List<Historico> historicoSistema = new ArrayList<>();
//        HistoricoDAO historicoDAO = new HistoricoDAO();
//
//        ListaVenda vendas = new ListaVenda();
//        ComprarProduto.pagarViaCredito(usuarioWesley, itemCafe, vendas, historicoSistema);
//    }

//    @Test
//    public void usuarioComSaldoIgualAoItemComprado() {
//        Item itemCafe = new Item(1, "café extra forte", 4d, new Estoque(3, 1));
//        UsuarioMensal usuarioWesley = new UsuarioMensal(2, "wesley", "wesley", "wesley", new Credito(4d));
//        List<Historico> historicoSistema = new ArrayList<>();
//        ListaVenda vendas = new ListaVenda();
//        ComprarProduto.pagarViaCredito(usuarioWesley, itemCafe, vendas, historicoSistema);
//        MenuAdmin.imprimirHistorico(vendas);
//    }

}