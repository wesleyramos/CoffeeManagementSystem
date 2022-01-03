package br.pucbr.utils;

import br.pucbr.controller.Console;
import br.pucbr.model.*;
import br.pucbr.model.dao.CreditoDAO;
import br.pucbr.model.dao.EstoqueDAO;
import br.pucbr.model.dao.ItemDAO;
import br.pucbr.model.dao.UsuarioDAO;

import java.util.List;

public class MenuAdmin {
    private static CreditoDAO creditoDAO = new CreditoDAO();
    private static UsuarioDAO usuarioDAO = new UsuarioDAO();

    public static Usuario mostrar(Usuario usuarioLogado, List<Item> itemList, List<Historico> historicoSistema, ListaVenda listaVendas) throws InterruptedException {
        int opcao;

        do {
            menuAdmin(usuarioLogado);
            opcao = Console.lerInt("Escolha uma opcao:");
            System.out.println("\n\n");
            switch (opcao) {
                case 1:
                    String nome = Console.lerString("Nome:");
                    String login = Console.lerString("Login:");
                    String senha = Console.lerString("Senha:");
                    int tipo = Console.lerInt("Tipo: 1 - Mensal, 2 - Admin");
                    int id_usuario = cadastrarUsuario(nome, login, senha, tipo);
                    break;
                case 2:
                    String desc = Console.lerString("Descricao:");
                    Double valor = Console.lerDouble("Valor:");
                    double estoqueAtual = Console.lerDouble("Estoque Atual:");
                    double estoqueMinimo = Console.lerDouble("Estoque mínimo:");
                    cadastrarItem(desc, valor, estoqueAtual, estoqueMinimo);
                    break;
                case 3:
                    ComprarProduto.menuComprar(usuarioLogado);
                    break;
                case 4:
                    Historico historico = new Historico();
                    historico.listarHistoricoPorUsuario(usuarioLogado.getUsuario());
                    break;
                case 5:
                    AdicionarCredito.adicionarCredito(usuarioLogado);
                    break;
                case 6:
                    System.out.println("Obrigado por utilizar nosso sistema!");
                    break;
                default:
                    System.out.println("Opcao invalida");
            }

        } while (opcao != 6);

        return null;
    }

    public static int cadastrarUsuario(String nome, String login, String senha, int tipo) {
        Credito creditoInserido = new Credito();
        Usuario usuario = null;
        creditoDAO.inserir(creditoInserido);
        switch (tipo) {
            case 1:
                usuario = new UsuarioMensal(nome, login, senha, creditoInserido);
                break;
            case 2:
                usuario = new UsuarioAdmin(nome, login, senha, creditoInserido);
                break;
            default:
                System.out.println("Tipo de usuário inválido: " + tipo);
        }
        usuarioDAO.inserir(usuario);

        return usuario.getId();
    }

    public static Integer cadastrarItem(String descricao, Double valor, Double estoqueAtual, Double estoqueMinimo) {
        Estoque estoque = new Estoque(estoqueAtual, estoqueMinimo);
        EstoqueDAO estoqueDAO = new EstoqueDAO();
        estoqueDAO.inserir(estoque);

        Item item = new Item(descricao, valor, estoque);
        ItemDAO itemDAO = new ItemDAO();
        Item inserir = itemDAO.inserir(item);

        return inserir.getId();
    }

    public static void imprimirHistorico(ListaVenda listaVendas) {
        for (Venda venda : listaVendas.getListaVendas()) {
            System.out.println(venda);
        }
    }

    private static void menuAdmin(Usuario usuario) {
        System.out.println("\n\n===========================================");
        System.out.println("Admin: " + usuario.getNome() + " logado");
        System.out.println("Creditos: " + usuario.getCredito().getValorTotal());
        System.out.println("===========================================");
        System.out.println("1 - Cadastrar usuário");
        System.out.println("2 - Cadastrar item");
        System.out.println("3 - Comprar produto");
        System.out.println("4 - Imprimir Histórico");
        System.out.println("5 - Colocar crédito");
        System.out.println("6 - Deslogar");
    }

}
