package br.pucbr.utils;

import br.pucbr.controller.Console;
import br.pucbr.model.Historico;
import br.pucbr.model.Item;
import br.pucbr.model.Usuario;

import java.util.Date;
import java.util.List;

public class ComprarProduto {

    public static boolean execute(List<Item> itemList, Usuario usuarioLogado, List<Historico> historicoSistema, ListaVenda listaVendas) throws InterruptedException {
        System.out.println("Itens na máquina:");
        System.out.println("0 voltar ao menu anterior.");
        for (Item item : itemList) {
            System.out.println(item.getId() + " " + item.getDescricao() + ": R$ " + item.getValor());
        }
        int idProduto = Console.lerInt("Digite o código do produto: ");
        if (idProduto == 0) {
            System.out.println("Compra cancelada.");
            return false;
        }
        return comprarProdutoComId(itemList, idProduto, usuarioLogado, listaVendas, historicoSistema);
    }

    private static boolean comprarProdutoComId(List<Item> itemList, int idProduto, Usuario usuarioLogado, ListaVenda listaVendas, List<Historico> historicoSistema) throws InterruptedException {
        int i = 0;
        for (i = 0; i < itemList.size(); i++) {
            Item item = itemList.get(i);
            if (idProduto == item.getId()) {
                if (existeItemNoEstoque(item)) {
                    int formaPagamento = Console.lerInt("Digite 1 para debitar do crédito e 2 para pagar via maquininha: ");
                    if (formaPagamento == 1) {
                        return pagarViaCredito(usuarioLogado, item, listaVendas, historicoSistema);

                    } else if (formaPagamento == 2) {
                        pagarViaMaquininha(usuarioLogado, item, listaVendas, historicoSistema);

                    } else {
                        System.out.println("Forma de pagamento inválido");
                        return false;
                    }
                }
            }
        }
        if (i == itemList.size()) {
            System.out.println("Produto não encontrado: " + idProduto);
        }
        return false;
    }

    public static boolean pagarViaMaquininha(Usuario usuarioLogado, Item item, ListaVenda listaVendas, List<Historico> historicoSistema) throws InterruptedException {
        System.out.println("Total da compra: ");
        Console.lerInt("Digite a senha: ");
        System.out.println("Validando pagamento");
        Thread.sleep(2000);
        System.out.print(".");
        Thread.sleep(2000);
        System.out.print(".");
        Thread.sleep(2000);
        System.out.print(".");
        realizarCompra(item, usuarioLogado, listaVendas, historicoSistema);
        return true;
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

    private static boolean existeItemNoEstoque(Item item) {
        if (item.getEstoque().getEstoqueAtual() > 0) {
            return true;
        }

        return false;
    }

    private static void realizarCompra(Item item, Usuario usuario, ListaVenda listaVendas, List<Historico> historicoSistema) {
        Date dataCompra = new Date();
        item.getEstoque().venderItem();
        usuario.getCredito().pagarCompra(item.getValor());
        int idVenda = listaVendas.adicionarVenda(dataCompra, item.getValor(), item.getId());
        historicoSistema.add(new Historico(usuario.getId(), idVenda, dataCompra));
        System.out.println("\nCompra realizada com sucesso!!!");
    }


}
