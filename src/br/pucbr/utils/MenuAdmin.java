package br.pucbr.utils;

import br.pucbr.controller.Console;
import br.pucbr.model.Historico;
import br.pucbr.model.Item;
import br.pucbr.model.Usuario;
import br.pucbr.model.UsuarioMensal;
import br.pucbr.model.Venda;

import java.util.List;

public class MenuAdmin {

    public static Usuario mostrar(Usuario usuarioLogado, List<Item> itemList, List<Historico> historicoSistema, ListaVenda listaVendas) throws InterruptedException {
        int opcao;
        if (usuarioLogado instanceof UsuarioMensal) {
            do {
                menuAdmin(usuarioLogado);
                opcao = Console.lerInt("Escolha uma opcao:");
                System.out.println("\n\n");
                switch (opcao) {
                    case 1:
                        ComprarProduto.execute(itemList, usuarioLogado, historicoSistema, listaVendas);
                        break;
                    case 2:
                        System.out.println("Em contrução, volte mais tarde!");
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
        }
        return null;
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
        System.out.println("1 - Comprar produto");
        System.out.println("2 - Colocar crédito");
        System.out.println("3 - Imprimir Histórico");
        System.out.println("4 - Cadastrar usuário");
        System.out.println("5 - Cadastrar item");
        System.out.println("6 - Atualizar estoque");
        System.out.println("7 - Deslogar");
    }

}
