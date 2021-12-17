package br.pucbr.controller;

import br.pucbr.model.Credito;
import br.pucbr.model.Usuario;
import br.pucbr.model.UsuarioAdmin;
import br.pucbr.model.dao.ItemDAO;
import br.pucbr.model.dao.UsuarioDAO;
import br.pucbr.utils.BancoDeDados;
import br.pucbr.utils.Login;
import br.pucbr.utils.MenuAdmin;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class MainTest {

    @BeforeAll
    public static void setupDB() {
        BancoDeDados.criarTabelas();
    }

    @AfterAll
    public static void dropDB() {
        BancoDeDados.droparBase();
    }

    @Test
    public void testeLoginFail() {
        Usuario usuario = Login.efetuarLogin("admin", "admin");
        assertNull(usuario);
    }

    @Test
    public void testeLoginSucesso() {
        UsuarioAdmin admin = new UsuarioAdmin("admin", "admin", "admin", new Credito(0d));
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        usuarioDAO.inserir(admin);

        Usuario usuario = Login.efetuarLogin("admin", "admin");


        assertEquals("admin", usuario.getUsuario());
    }

    @Test
    public void testeCadastrarUsuarioWesley() {
        int idUsuario = MenuAdmin.cadastrarUsuario("wesley", "wesley", "wesley", 0);
        assertNotNull(idUsuario);
    }

    @Test
    public void testeCadastrarProduto() {
        Integer id_item = MenuAdmin.cadastrarItem("desc1", 4d, 1d, 1d);
        assertNotNull(id_item);
    }


    @Test
    public void testeComprarComMaquininha() {
        ItemDAO item = new ItemDAO();
        //        ComprarProduto.comprarProduto(1)
        //        assertNotNull(idUsuario);
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