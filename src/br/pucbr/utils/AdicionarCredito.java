package br.pucbr.utils;

import br.pucbr.controller.Console;
import br.pucbr.model.Credito;
import br.pucbr.model.Usuario;
import br.pucbr.model.dao.CreditoDAO;

public class AdicionarCredito {

    public static void adicionarCredito(Usuario usuario) {
        mostrarMenuCreditoAdicional(usuario);
        double valor = Console.lerInt("Informe o valor do credito adicional:");

        try {
            CreditoDAO creditoDAO = new CreditoDAO();
            Credito credito = usuario.getCredito();
            credito.inserirCredito(valor);
            creditoDAO.alterar(usuario.getCredito());
            System.out.println("Credito adicionado com sucesso.");
        } catch (Exception e) {
            System.err.println("Erro ao adicionar credito: " + e.getMessage());
        }

    }

    private static void mostrarMenuCreditoAdicional(Usuario usuarioLogado) {

        System.out.println("\n\n===========================================");
        System.out.println("Usuário: " + usuarioLogado.getNome() + " logado");

        if (usuarioLogado.getCredito() != null) {
            System.out.println("SALDO ATUAL: " + usuarioLogado.getCredito().getValorTotal());
        } else {
            System.out.println("SALDO ATUAL: 0");
        }

        System.out.println("===========================================");
    }

}
