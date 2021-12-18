package br.pucbr.controller;

import br.pucbr.model.Credito;
import br.pucbr.model.Usuario;
import br.pucbr.model.UsuarioAdmin;
import br.pucbr.model.dao.UsuarioDAO;
import br.pucbr.utils.BancoDeDados;
import br.pucbr.utils.Login;

public class Main {

    private static UsuarioDAO usuarioDAO = new UsuarioDAO();

    private static Usuario usuarioLogado = null;

    public static void main(String[] args) {
        BancoDeDados.criarTabelas();

        usuarioDAO.inserir(new UsuarioAdmin("admin", "admin", "admin", new Credito(0d)));

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
                    case 0:
//                        usuarioLogado = MenuUsuarioMensal.mostrar(usuarioLogado, itemList, historicoSistema, vendas);
                    case 1:
//                        usuarioLogado = MenuAdmin.mostrar(usuarioLogado, itemList, historicoSistema, vendas);
                }
            }
        } while (opcao != 8);
    }

}
