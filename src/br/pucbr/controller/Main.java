package br.pucbr.controller;

public class Main {

    public static void main(String[] args) {
        int opcao = 8;

        do {
            mostrarMenu();
            opcao = Console.lerInt("Escolha uma opcao:");
            System.out.println("\n\n");

            switch (opcao) {
                case 1:
                    cult.efetuarLogin();
                    logado = true;
                    break;
                case 2:
                    cult.cadastrarCliente();
                    break;

                case 8:
                    System.out.println("Fim");
                    break;

                default:
                    System.out.println("Opcao invalida");
            }
            Console.lerString("");

        } while (opcao != 8);
    }

    public static void mostrarMenu() {
        System.out.println("\n\n===========================================");
        System.out.println("Coffee Management System - No coffee. No Job.");
        System.out.println("===========================================");
        System.out.println("1 - Entrar com login");
        System.out.println("2 - Entrar sem login");
        System.out.println("8 - Sair");
    }
}
