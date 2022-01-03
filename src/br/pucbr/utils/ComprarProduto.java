package br.pucbr.utils;

import br.pucbr.controller.Console;
import br.pucbr.model.*;
import br.pucbr.model.dao.*;

import java.util.Date;
import java.util.List;

public class ComprarProduto {

    public static boolean menuComprar(Usuario usuarioLogado) {
        System.out.println("Itens na máquina:");
        System.out.println("0 voltar ao menu anterior.");
        ItemDAO itemDAO = new ItemDAO();
        List<Item> listItens = itemDAO.listar();
        for (Item item : listItens) {
            System.out.println(item.getId() + " " + item.getDescricao() + ": R$ " + item.getValor());
        }

        int idProdutoEscolhido = Console.lerInt("Digite o código do produto: ");
        if (idProdutoEscolhido == 0) {
            System.out.println("Compra cancelada.");
            return false;
        }

        for (Item item : listItens) {
            if (item.getId() == idProdutoEscolhido) {
                if (item.getEstoque().getEstoqueAtual() > 0) {

                    Venda venda = new Venda(new Date(), item.getValor(), item);
                    Historico historico = new Historico(venda.getData(), item.getValor(), usuarioLogado, venda);

                    if (mostrarMenuFormaPagamento(historico)) {
                        VendaDAO vendaDAO = new VendaDAO();
                        venda = vendaDAO.inserir(venda);
                        historico.setVenda(venda);
                        HistoricoDAO historicoDAO = new HistoricoDAO();
                        historicoDAO.inserir(historico);
                        return true;
                    }

                } else {
                    System.out.println("Item " + item.getDescricao() + " em falta.");
                }
            }
        }

        System.out.println("Produto inválido");
        return false;
    }

    private static boolean mostrarMenuFormaPagamento(Historico historico) {
        int formaPagamento = Console.lerInt("Forma de pagamento: \n 1 para debitar do crédito \n 2 para pagar via cartão: ");

        if (formaPagamento == 1) {

            if (historico.getUsuario().getCredito().getValorTotal() < historico.getVenda().getItem().getValor()) {
                System.out.println("Saldo insuficiente!!!");
                try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
                mostrarMenuFormaPagamento(historico);
            }

            return pagarViaCredito(historico.getUsuario(), historico.getVenda().getItem());
        } else if (formaPagamento == 2) {
            return pagarViaMaquininha(historico.getUsuario(), historico.getVenda().getItem());
        }

        return false;
    }

    public static Historico comprarProduto(Item item, Usuario usuarioLogado) {
        atualizarEstoque(item);
        Date data = new Date();
        VendaDAO vendaDAO = new VendaDAO();
        Venda venda = vendaDAO.inserir(new Venda(new Date(), item.getValor(), item.getId(), item));
        HistoricoDAO historicoDAO = new HistoricoDAO();
        Historico historico = historicoDAO.inserir(new Historico(usuarioLogado.getId(), venda.getId(), data, item.getValor()));
        return historico;
    }

    private static void atualizarEstoque(Item item) {
        EstoqueDAO estoqueDAO = new EstoqueDAO();
        Estoque estoque = item.getEstoque();
        estoque.setEstoqueAtual(estoque.getEstoqueAtual() - 1);
        estoqueDAO.alterar(estoque);
    }

    public static boolean pagarViaMaquininha(Usuario usuarioLogado, Item item) {
        try {
            System.out.println("Total da compra: ");
            Console.lerInt("Digite a senha: ");
            System.out.println("Validando pagamento");
            Thread.sleep(2000);
            System.out.print(".");
            Thread.sleep(2000);
            System.out.print(".");
            Thread.sleep(2000);
            System.out.print(".");
            comprarProduto(item, usuarioLogado);
            return true;
        } catch (Exception exc) {
            System.out.println("Error to pay with machine: " + exc.getMessage());
        }

        return false;
    }

    public static boolean pagarViaCredito(Usuario usuarioLogado, Item item) {
        if (usuarioLogado.getCredito().getValorTotal() >= item.getValor()) {
            usuarioLogado.getCredito().pagarCompra(item.getValor());

            try {
                CreditoDAO creditoDAO = new CreditoDAO();
                creditoDAO.alterar(usuarioLogado.getCredito());
            } catch (Exception e) {
                System.err.println("Falha ao descontar o credito. " + e.getMessage());
            }

            atualizarEstoque(item);

            System.out.println("Credito debitado: " + item.getValor());
            System.out.println("Saldo: R$ " + usuarioLogado.getCredito().getValorTotal());
            return true;
        } else {
            System.out.println("Saldo insuficiente!!!");
            return false;
        }
    }

}
