package br.pucbr.controller;

import br.pucbr.model.Credito;
import br.pucbr.model.Item;
import br.pucbr.model.Usuario;
import br.pucbr.model.UsuarioMensal;

import java.util.Arrays;
import java.util.List;

public class Main {

    private static List<Usuario> usuarioList = Arrays.asList(new UsuarioMensal(2, "wesley", "wesley", "wesley", new Credito(0d)));
    private static List<Item> itemList = Arrays.asList(new Item(1, "café extra forte", 4d));

    private static int proximoIdUsuario = 3;

    private static Usuario usuarioLogado = null;

    public static void main(String[] args) {
        int opcao = 8;
        do {
            if (null == usuarioLogado) {
                mostrarMenu();
                opcao = Console.lerInt("Escolha uma opcao:");
                System.out.println("\n\n");
                switch (opcao) {
                    case 1:
                        usuarioLogado = efetuarLogin();
                        if (usuarioLogado.equals(null)) {
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
                do {
                    mostrarMenuUsuarioMensal();
                    opcao = Console.lerInt("Escolha uma opcao:");
                    System.out.println("\n\n");
                    switch (opcao) {
                        case 1:
                            comprarProduto();
                            break;
                        case 3:
                            cadastrarUsuario();
                            break;

                        case 8:
                            System.out.println("Fim");
                            break;

                        default:
                            System.out.println("Opcao invalida");
                    }

                } while (opcao != 8);
            }
        } while (opcao != 8);

    }

    private static void comprarProduto() {
        System.out.println("Itens na máquina:");
        double valorAPagar = 0;
        for (Item item : itemList) {
            System.out.println(item.getId() + " " + item.getDescricao() + ": R$ " + item.getValor());
        }
        int idProduto = Console.lerInt("Digite o código do produto: ");
        int i = 0;
        for (i = 0; i < itemList.size(); i++) {
            Item item = itemList.get(i);
            if (idProduto == item.getId()) {
                valorAPagar += item.getValor();
                int formaPagamento = Console.lerInt("Digite 1 para debitar do crédito e 2 para pagar via maquininha: ");
                if (formaPagamento == 1) {
                    if(usuarioLogado.getCredito().getValorTotal() >= valorAPagar) {
                        usuarioLogado.getCredito().pagarCompra(valorAPagar);
                        System.out.println("Debitado do crédito, sobrou: R$ " + usuarioLogado.getCredito().getValorTotal());
                    } else {
                        System.out.println("Saldo insuficiente!!!");
                        break;
                    }
                }
            }
        }
        if(i == itemList.size()) {
            System.out.println("Produto não encontrado: " + idProduto);
        }

    }

    private static void mostrarItens() {

    }

    private static void mostrarMenuUsuarioMensal() {
        System.out.println("\n\n===========================================");
        System.out.println("Usuário: " + usuarioLogado.getNome() + " logado");
        System.out.println("===========================================");
        System.out.println("1 - Comprar produto");
        System.out.println("2 - Colocar crédito");
        System.out.println("8 - Sair");
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
