package br.pucbr.utils;

import br.pucbr.controller.Console;
import br.pucbr.model.Estoque;
import br.pucbr.model.Historico;
import br.pucbr.model.Item;
import br.pucbr.model.Usuario;
import br.pucbr.model.Venda;
import br.pucbr.model.dao.EstoqueDAO;
import br.pucbr.model.dao.HistoricoDAO;
import br.pucbr.model.dao.ItemDAO;
import br.pucbr.model.dao.VendaDAO;

import java.util.Date;
import java.util.List;

public class ComprarProduto {

//    public static boolean menuComprar(List<Item> itemList, Usuario usuarioLogado,
//                                      List<Historico> historicoSistema, ListaVenda listaVendas)
//            throws InterruptedException {
//        System.out.println("Itens na máquina:");
//        System.out.println("0 voltar ao menu anterior.");
//        for (Item item : itemList) {
//            System.out.println(item.getId() + " " + item.getDescricao() + ": R$ " + item.getValor());
//        }
//        int idProduto = Console.lerInt("Digite o código do produto: ");
//        if (idProduto == 0) {
//            System.out.println("Compra cancelada.");
//            return false;
//        }
//        return comprarProduto(itemList, idProduto, usuarioLogado, listaVendas, historicoSistema);
//    }


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
                    mostrarMenuFormaPagamento(item, usuarioLogado);
//                    return comprarProduto(item, usuarioLogado);
                } else {
                    System.out.println("Estoque zerado");
                }
            }
        }

        System.out.println("Produto inválido");
        return false;
    }

    private static void mostrarMenuFormaPagamento(Item item, Usuario usuarioLogado) {
        int formaPagamento = Console.lerInt("Digite 1 para debitar do crédito e 2 para pagar via maquininha: ");
        if (formaPagamento == 1) {
//            return pagarViaCredito(usuarioLogado, item);

        } else if (formaPagamento == 2) {
            pagarViaMaquininha(usuarioLogado, item);

        } else {
            System.out.println("Forma de pagamento inválido");
        }
    }

    public static Historico comprarProduto(Item item, Usuario usuarioLogado) {
        EstoqueDAO estoqueDAO = new EstoqueDAO();
        Estoque estoque = item.getEstoque();
        estoque.setEstoqueAtual(estoque.getEstoqueAtual() - 1);
        estoqueDAO.alterar(estoque);
        Date data = new Date();
        VendaDAO vendaDAO = new VendaDAO();
        Venda venda = vendaDAO.inserir(new Venda(new Date(), item.getValor(), item.getId(), item));
        HistoricoDAO historicoDAO = new HistoricoDAO();
        Historico historico = historicoDAO.inserir(new Historico(usuarioLogado.getId(), venda.getId(), data, item.getValor()));
        return historico;
    }

    public static void pagarViaMaquininha(Usuario usuarioLogado, Item item) {
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
        } catch (Exception exc) {
            System.out.println("Error to pay with machine: " + exc.getMessage());
        }
    }

    public static boolean pagarViaCredito(Usuario usuarioLogado, Item item, ListaVenda listaVendas, List<Historico> historicoSistema) {
        if (usuarioLogado.getCredito().getValorTotal() >= item.getValor()) {
            realizarCompra(item, usuarioLogado, listaVendas, historicoSistema);
            System.out.println("Debitado do crédito, sobrou: R$ " + usuarioLogado.getCredito().getValorTotal());
            return true;
        } else {
            System.out.println("Saldo insuficiente!!!");
            return false;
        }
    }

    private static void realizarCompra(Item item, Usuario usuario, ListaVenda listaVendas, List<Historico> historicoSistema) {
        Date dataCompra = new Date();
        item.getEstoque().venderItem();
        usuario.getCredito().pagarCompra(item.getValor());
        int idVenda = listaVendas.adicionarVenda(dataCompra, item.getValor(), item.getId());
        historicoSistema.add(new Historico(usuario.getId(), idVenda, dataCompra, 0d));
        System.out.println("\nCompra realizada com sucesso!!!");
    }


}
