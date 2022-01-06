package br.pucbr.controller;

import br.pucbr.model.Historico;
import br.pucbr.model.Item;
import br.pucbr.model.Usuario;
import br.pucbr.model.dao.HistoricoDAO;
import br.pucbr.model.dao.ItemDAO;
import br.pucbr.utils.AdicionarCredito;
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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.MethodName.class)
public class MainTest {

    @BeforeAll
    public static void setupDB() {
        BancoDeDados.droparBase();
        BancoDeDados.criarTabelas();
    }

    @AfterAll
    public static void dropDB() {
        BancoDeDados.droparBase();
    }

    @Test
    public void test01_LoginFail() {
        Usuario usuario = Login.efetuarLogin("admin1", "admin1");
        assertNull(usuario);
    }

    @Test
    public void test02_LoginAdminComSucesso() {
        Usuario usuario = Login.efetuarLogin("admin", "admin");
        assertEquals("admin", usuario.getUsuario());
    }

    @Test
    public void test03_LoginMensalComSucesso() {
        Usuario usuario = Login.efetuarLogin("usuario", "usuario");
        assertEquals("usuario", usuario.getUsuario());
    }

    @Test
    public void test04_CadastrarUsuarioDeTipoInexistente() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            MenuAdmin.cadastrarUsuario("wesley", "wesley", "wesley", 0);
        });

        String expectedMessage = "Tipo de usuario invalido: 0";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void test05_CadastrarUsuarioMensalWesley() {
        int idUsuario = MenuAdmin.cadastrarUsuario("wesley", "wesley", "wesley", 1);
        assertNotNull(idUsuario);
    }

    @Test
    public void test06_CadastrarUsuarioAdminTeste123() {
        int idUsuario = MenuAdmin.cadastrarUsuario("teste123", "teste123", "teste123", 2);
        assertNotNull(idUsuario);
    }

    @Test
    public void test07_CadastrarProduto() {
        Integer id_item = MenuAdmin.cadastrarItem("desc1", 4d, 1d, 1d);
        assertNotNull(id_item);
    }

    @Test
    public void test08_TentarComprarProdutoQueNãoExiste() {
        Usuario usuario = Login.efetuarLogin("admin", "admin");
        boolean result = ComprarProduto.tentarComprar(-1, usuario);
        assertFalse(result);
    }

    @Test
    public void test09_TentarComprarProdutoSemEstoque() {
        Integer id_item = MenuAdmin.cadastrarItem("produto0estoque", 4d, 0d, 1d);
        assertNotNull(id_item);
        Usuario usuario = Login.efetuarLogin("admin", "admin");
        boolean result = ComprarProduto.tentarComprar(id_item, usuario);
        assertFalse(result);
    }

    @Test
    public void test10_PagarViaCreditoSemSaldo() {
        Integer id_item = MenuAdmin.cadastrarItem("cafe fraco", 40000d, 10d, 1d);
        ItemDAO itemDAO = new ItemDAO();
        List<Item> itens = itemDAO.buscarPorDescricao("cafe fraco");
        assertNotNull(id_item);
        Usuario usuario = Login.efetuarLogin("admin", "admin");
        boolean result = ComprarProduto.pagarViaCredito(usuario, itens.get(0));
        assertFalse(result);
    }

    @Test
    public void test11_PagarViaCreditoComSaldo() {
        Integer id_item = MenuAdmin.cadastrarItem("cafe tipo 1", 2d, 10d, 1d);
        ItemDAO itemDAO = new ItemDAO();
        List<Item> itens = itemDAO.buscarPorDescricao("cafe tipo 1");
        assertNotNull(id_item);
        Usuario usuario = Login.efetuarLogin("admin", "admin");
        AdicionarCredito.adicionarCredito(2, usuario);
        boolean result = ComprarProduto.pagarViaCredito(usuario, itens.get(0));
        assertTrue(result);
    }

    @Test
    public void test12_PagarViaCreditoComprarDuasVezesUmItemQueCustaTodoOSaldoInicial() {
        Integer id_item = MenuAdmin.cadastrarItem("cafe tipo 2", 2d, 10d, 1d);
        ItemDAO itemDAO = new ItemDAO();
        List<Item> itens = itemDAO.buscarPorDescricao("cafe tipo 2");
        assertNotNull(id_item);
        Usuario usuario = Login.efetuarLogin("admin", "admin");
        AdicionarCredito.adicionarCredito(2, usuario);
        ComprarProduto.pagarViaCredito(usuario, itens.get(0));
        boolean result = ComprarProduto.pagarViaCredito(usuario, itens.get(0));
        assertFalse(result);
    }

    @Test
    public void test13_PagarViaCreditoComSaldoDuasVezes() throws Exception {
        Integer id_item = MenuAdmin.cadastrarItem("cafe tipo 5", 2d, 10d, 1d);
        ItemDAO itemDAO = new ItemDAO();
        List<Item> itens = itemDAO.buscarPorDescricao("cafe tipo 5");
        assertNotNull(id_item);
        Usuario usuario = Login.efetuarLogin("admin", "admin");
        AdicionarCredito.adicionarCredito(100, usuario);
        ComprarProduto.pagarViaCredito(usuario, itens.get(0));
        boolean result = ComprarProduto.pagarViaCredito(usuario, itens.get(0));
        assertTrue(result);
    }

    @Test
    public void test14_ComprarProduto() throws Exception {
        MenuAdmin.cadastrarUsuario("usuario_teste_historico", "usuario_teste_historico", "usuario_teste_historico", 1);
        Integer id_item = MenuAdmin.cadastrarItem("cafe tipo 3", 2d, 10d, 1d);
        ItemDAO itemDAO = new ItemDAO();
        List<Item> itens = itemDAO.buscarPorDescricao("cafe tipo 3");
        Item itemComprado = itens.get(0);
        assertNotNull(id_item);
        Usuario usuario = Login.efetuarLogin("usuario_teste_historico", "usuario_teste_historico");
        AdicionarCredito.adicionarCredito(200, usuario);
        ComprarProduto.pagarViaCredito(usuario, itemComprado);
        ComprarProduto.pagarViaCredito(usuario, itemComprado);
        ComprarProduto.pagarViaCredito(usuario, itemComprado);
        assertEquals(itemComprado.getEstoque().getEstoqueAtual(), 7);
        HistoricoDAO historicoDAO = new HistoricoDAO();
        List<Historico> historicos = historicoDAO.buscarPorUsuario(usuario.getNome());
        assertEquals(3, historicos.size());
        assertEquals(usuario.getId(), historicos.get(0).getUsuario().getId());
        assertEquals(usuario.getId(), historicos.get(1).getUsuario().getId());
        assertEquals(usuario.getId(), historicos.get(2).getUsuario().getId());
        assertEquals(itemComprado.getValor(), historicos.get(0).getTotal());
        assertEquals(itemComprado.getValor(), historicos.get(1).getTotal());
        assertEquals(itemComprado.getValor(), historicos.get(2).getTotal());
    }


}