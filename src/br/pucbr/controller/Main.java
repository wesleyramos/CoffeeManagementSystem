package br.pucbr.controller;

import br.pucbr.model.Credito;
import br.pucbr.model.Estoque;
import br.pucbr.model.Historico;
import br.pucbr.model.Item;
import br.pucbr.model.Usuario;
import br.pucbr.model.UsuarioMensal;
import br.pucbr.utils.ListaVenda;
import br.pucbr.utils.MenuUsuarioMensal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    private static List<Usuario> usuarioList = Arrays.asList(new UsuarioMensal(2, "wesley", "wesley", "wesley", new Credito(0d)));
    private static List<Item> itemList = Arrays.asList(new Item(1, "café extra forte", 4d, new Estoque(3, 1)));
    private static List<Historico> historicoSistema = new ArrayList<>();
    private static ListaVenda vendas = new ListaVenda();

    private static int proximoIdUsuario = 3;

    private static Usuario usuarioLogado = null;

    public static void main(String[] args) throws InterruptedException {
        int opcao = 8;
        do {
            if (null == usuarioLogado) {
                mostrarMenu();
                opcao = Console.lerInt("Escolha uma opcao:");
                System.out.println("\n\n");
                switch (opcao) {
                    case 1:
                        usuarioLogado = efetuarLogin();
                        if (null == usuarioLogado) {
                            System.out.println("\n\n===========================================");
                            System.out.println("Verifique usuário e senha, dados incorretos!!!");
                        }
                        break;
                    case 8:
                        System.out.println("Fim");
                        break;

                    default:
                        System.out.println("Opcao invalida");
                }

            } else {
                opcao = MenuUsuarioMensal.mostrar(usuarioLogado, itemList, historicoSistema, vendas);
               /* do {
                    MenuUsuarioMensal.mostrar();
                    mostrarMenuUsuarioMensal();
                    opcao = Console.lerInt("Escolha uma opcao:");
                    System.out.println("\n\n");
                    switch (opcao) {
                        case 1:
                            comprarProduto();
                            break;
//                        case 3:
//                            cadastrarUsuario();
//                            break;

                        case 8:
                            System.out.println("Fim");
                            break;

                        default:
                            System.out.println("Opcao invalida");
                    }

                } while (opcao != 8);*/
            }
        } while (opcao != 8);

    }


    private static void mostrarItens() {

    }


    private static Usuario efetuarLogin() {
        String usuario = Console.lerString("Digite o nome do usuario: ");
        String senha = Console.lerString("Digite a senha do usuario: ");
        for (Usuario usuarioVerificado : usuarioList) {
            if (usuarioVerificado.getUsuario().equals(usuario)) {
                if (usuarioVerificado.getSenha().equals(senha)) {
                    return usuarioVerificado;
                }
            } else {
                return null;
            }
        }
        return null;
    }

    public static void mostrarMenu() {
        System.out.println("\n\n===========================================");
        System.out.println("Coffee Management System - No coffee. No Job.");
        System.out.println("===========================================");
        System.out.println("1 - Entrar com login");
        System.out.println("2 - Entrar sem login");
        System.out.println("8 - Sair");
    }

    private static void cadastrarUsuario() {
        String nome = Console.lerString("Digite o nome do usuario: ");
        String usuario = Console.lerString("Digite o usuario: ");
        String senha = Console.lerString("Digite a senha do usuario: ");
        UsuarioMensal usuarioMensal = new UsuarioMensal(proximoIdUsuario, nome, usuario, senha, new Credito(0d));
        proximoIdUsuario++;
        usuarioList.add(usuarioMensal);
        mostrarUsuariosMensais();
    }

    private static void mostrarUsuariosMensais() {
        usuarioList.forEach(usuarioMensal -> {
            System.out.println(usuarioMensal.toString());
        });
    }
}
