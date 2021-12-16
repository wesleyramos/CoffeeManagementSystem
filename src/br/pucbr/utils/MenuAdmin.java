package br.pucbr.utils;

import br.pucbr.controller.Console;
import br.pucbr.model.Credito;
import br.pucbr.model.Historico;
import br.pucbr.model.Item;
import br.pucbr.model.Usuario;
import br.pucbr.model.UsuarioAdmin;
import br.pucbr.model.UsuarioMensal;
import br.pucbr.model.Venda;
import br.pucbr.model.dao.CreditoDAO;
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
                    cadastrarUsuario(nome, login, senha, tipo);
                case 2:
                    ComprarProduto.execute(itemList, usuarioLogado, historicoSistema, listaVendas);
                    break;
                case 3:
                    imprimirHistorico(listaVendas);
                    break;
                case 4:
                    System.out.println("Em contrução, volte mais tarde!");
                    break;
                case 5:
                    System.out.println("Em contrução, volte mais tarde!");
                    break;
                case 6:
                    System.out.println("Em contrução, volte mais tarde!");
                    break;
                case 7:
                    System.out.println("Usuário deslogado!");
                    break;
                default:
                    System.out.println("Opcao invalida");
            }

        } while (opcao != 3);

        return null;
    }

    private static void cadastrarUsuario(String nome, String login, String senha, int tipo) {
        Credito creditoInserido = null;
        Usuario usuario = null;
        creditoInserido = new Credito();
        creditoDAO.inserir(creditoInserido);
        switch (tipo) {
            case 0:
                usuario = new UsuarioMensal(nome, login, senha, creditoInserido);
                break;
            case 1:
                usuario = new UsuarioAdmin(nome, login, senha, creditoInserido);
                break;
            default:
                System.out.println("Tipo de usuário inválido: " + tipo);
        }
        usuarioDAO.inserir(usuario);
    }

    public static void imprimirHistorico(ListaVenda listaVendas) {
        for (Venda venda : listaVendas.getListaVendas()) {
            System.out.println(venda);
        }
    }

    private static void menuAdmin(Usuario usuario) {
        System.out.println("\n\n===========================================");
        System.out.println("Admin: " + usuario.getNome() + " logado");
        System.out.println("===========================================");
        System.out.println("1 - Cadastrar usuário");
        System.out.println("2 - Comprar produto");
        System.out.println("3 - Colocar crédito");
        System.out.println("4 - Imprimir Histórico");
        System.out.println("5 - Cadastrar item");
        System.out.println("6 - Atualizar estoque");
        System.out.println("7 - Deslogar");
    }

}
