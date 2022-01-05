package br.pucbr.controller;

import br.pucbr.model.Usuario;
import br.pucbr.utils.BancoDeDados;
import br.pucbr.utils.Login;
import br.pucbr.utils.MenuAdmin;
import br.pucbr.utils.MenuUsuarioMensal;

public class Main {

    private static Usuario usuarioLogado = null;

    public static void main(String[] args) throws InterruptedException {
        BancoDeDados.criarTabelas();

        int opcao = 0;
        do {
            if (null == usuarioLogado) {
                String usuario = Console.lerString("Digite o nome do usuario: ");
                String senha = Console.lerString("Digite a senha do usuario: ");
                usuarioLogado = Login.efetuarLogin(usuario, senha);
                if (null == usuarioLogado) {
                    System.out.println("\n\n===========================================");
                    System.out.println("Verifique usuário e senha, dados incorretos!!!");
                }
            } else {
                switch (usuarioLogado.getTipo()) {
                    case 1:
                        usuarioLogado = MenuUsuarioMensal.mostrar(usuarioLogado);
                        break;
                    case 2:
                        usuarioLogado = MenuAdmin.mostrar(usuarioLogado);
                        break;
                }
            }
        } while (opcao != 8);
    }

}
