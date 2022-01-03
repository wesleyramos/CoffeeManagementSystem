package br.pucbr.controller;

import br.pucbr.model.Usuario;
import br.pucbr.model.dao.UsuarioDAO;
import br.pucbr.utils.BancoDeDados;
import br.pucbr.utils.Login;
import br.pucbr.utils.MenuAdmin;
import br.pucbr.utils.MenuUsuarioMensal;

public class Main {

    private static UsuarioDAO usuarioDAO = new UsuarioDAO();

    private static Usuario usuarioLogado = null;

    public static void main(String[] args) {
        BancoDeDados.criarTabelas();

//        Credito credito = new Credito(0d);
//        CreditoDAO creditoDAO = new CreditoDAO();
//        usuarioDAO.inserir(new UsuarioAdmin("admin", "admin", "admin", creditoDAO.inserir(credito)));

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
                        try {
                            usuarioLogado = MenuUsuarioMensal.mostrar(usuarioLogado, null, null, null);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    case 2:
                        try {
                            usuarioLogado = MenuAdmin.mostrar(usuarioLogado, null, null, null);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                }
            }
        } while (opcao != 8);
    }

}
