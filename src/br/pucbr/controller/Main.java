package br.pucbr.controller;

import br.pucbr.model.UsuarioMensal;

import java.util.ArrayList;
import java.util.List;

public class Main {

    static List<UsuarioMensal> usuarioMensalList = new ArrayList<>();
    private static int ultimoIdUsuario = 2;

    public static void main(String[] args) {
        int opcao = 8;

        do {
            mostrarMenu();
            opcao = Console.lerInt("Escolha uma opcao:");
            System.out.println("\n\n");

            switch (opcao) {
                case 1:
                    mostrarMenuLogin();
                    break;
                case 2:
//                    cult.cadastrarCliente();
                    break;
                case 3:
//                    cadastrarUsuario(
                    break;

                case 8:
                    System.out.println("Fim");
                    break;

                default:
                    System.out.println("Opcao invalida");
            }

        } while (opcao != 8);
    }

    private static void efetuarLogin() {
        mostrarMenu();
    }

    public static void mostrarMenu() {
        System.out.println("\n\n===========================================");
        System.out.println("Coffee Management System - No coffee. No Job.");
        System.out.println("===========================================");
        System.out.println("1 - Entrar com login");
        System.out.println("2 - Entrar sem login");
        System.out.println("3 - Cadastrar novo usuário");
        System.out.println("8 - Sair");
    }

    public static void mostrarMenuLogin() {
        int opcao;
        System.out.println("\n\n===========================================");
        System.out.println("1 - Entrar com usuario");
        System.out.println("2 - Cadastrar novo usuário");
        System.out.println("8 - Sair");
        opcao = Console.lerInt("Escolha uma opcao:");
        switch (opcao) {
            case 1:
                efetuarLogin();
                break;
            case 2:
                cadastrarUsuario();
                break;
            case 8:
                System.out.println("Fim");
                break;

            default:
                System.out.println("Opcao invalida");
        }
    }

    private static void cadastrarUsuario() {
        String nome = Console.lerString("Digite o nome do usuario: ");
        String usuario = Console.lerString("Digite o usuario: ");
        String senha = Console.lerString("Digite a senha do usuario: ");
        UsuarioMensal usuarioMensal = new UsuarioMensal(ultimoIdUsuario, nome, usuario, senha);
        ultimoIdUsuario++;
        usuarioMensalList.add(usuarioMensal);
        mostrarUsuariosMensais();
    }

    private static void mostrarUsuariosMensais() {
        usuarioMensalList.forEach(usuarioMensal -> {
            System.out.println(usuarioMensal.toString());
        });
    }
}
