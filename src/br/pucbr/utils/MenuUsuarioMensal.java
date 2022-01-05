package br.pucbr.utils;

import br.pucbr.controller.Console;
import br.pucbr.model.Usuario;
import br.pucbr.model.UsuarioMensal;

public class MenuUsuarioMensal {

    public static Usuario mostrar(Usuario usuarioLogado) {
        int opcao;
        if (usuarioLogado instanceof UsuarioMensal) {
            do {
                mostrarMenuUsuarioMensal(usuarioLogado);
                opcao = Console.lerInt("Escolha uma opcao:");
                System.out.println("\n\n");
                switch (opcao) {
                    case 1:
                        ComprarProduto.menuComprar(usuarioLogado);
                        break;
                    case 2:
                        AdicionarCredito.adicionarCredito(usuarioLogado);
                        break;
                    case 3:
                        System.out.println("Usuário deslogado!");
                        usuarioLogado = null;
                        break;
                    default:
                        System.out.println("Opcao invalida");
                }

            } while (opcao != 3);
        }
        return null;
    }

    private static void mostrarMenuUsuarioMensal(Usuario usuarioLogado) {
        System.out.println("===========================================");
        System.out.println("Usuário: " + usuarioLogado.getNome() + " logado");
        System.out.println("Creditos: " + usuarioLogado.getCredito().getValorTotal());
        System.out.println("===========================================");
        System.out.println("1 - Comprar produto");
        System.out.println("2 - Colocar crédito");
        System.out.println("3 - Deslogar");
    }

}
