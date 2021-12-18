package br.pucbr.controller;

import br.pucbr.model.Credito;
import br.pucbr.model.Historico;
import br.pucbr.model.Item;
import br.pucbr.model.Usuario;
import br.pucbr.model.UsuarioAdmin;
import br.pucbr.model.Venda;
import br.pucbr.model.dao.ItemDAO;
import br.pucbr.model.dao.UsuarioDAO;
import br.pucbr.model.dao.VendaDAO;
import br.pucbr.utils.BancoDeDados;
import br.pucbr.utils.ComprarProduto;
import br.pucbr.utils.Login;
import br.pucbr.utils.MenuAdmin;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@TestMethodOrder(MethodOrderer.MethodName.class)
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
    public void test01_LoginFail() {
        Usuario usuario = Login.efetuarLogin("admin", "admin");
        assertNull(usuario);
    }

    @Test
    public void test02_LoginSucesso() {
        UsuarioAdmin admin = new UsuarioAdmin("admin", "admin", "admin", new Credito(0d));
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        usuarioDAO.inserir(admin);

        Usuario usuario = Login.efetuarLogin("admin", "admin");


        assertEquals("admin", usuario.getUsuario());
    }

    @Test
    public void test03_CadastrarUsuarioWesley() {
        int idUsuario = MenuAdmin.cadastrarUsuario("wesley", "wesley", "wesley", 0);
        assertNotNull(idUsuario);
    }

    @Test
    public void test04_CadastrarProduto() {
        Integer id_item = MenuAdmin.cadastrarItem("desc1", 4d, 1d, 1d);
        assertNotNull(id_item);
    }


    @Test
    public void test05_ComprarComMaquininha() {
        Usuario usuario = Login.efetuarLogin("admin", "admin");
        ItemDAO itemDAO = new ItemDAO();
        List<Item> itens = itemDAO.buscarPorDescricao("desc1");
        Historico historico = ComprarProduto.comprarProduto(itens.get(0), usuario);
        System.out.println(historico);
        assertEquals(1, historico.getId());
        assertEquals(1, historico.getUsuarioId());
        assertEquals(1, historico.getVendaId());
        assertEquals(4d, historico.getTotal());
        VendaDAO vendaDAO = new VendaDAO();
        List<Venda> vendas = vendaDAO.buscar(historico.getVendaId());
        Venda venda = vendas.get(0);
        System.out.println("venda: " + venda);
        assertEquals(4d, venda.getTotal());

//        assertNotNull(id_venda);
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