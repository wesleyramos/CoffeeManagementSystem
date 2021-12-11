package br.pucbr.utils;

import br.pucbr.controller.Console;
import br.pucbr.model.Historico;
import br.pucbr.model.Item;
import br.pucbr.model.Usuario;

import java.util.Date;
import java.util.List;

public class MenuUsuarioMensal {

    public static int mostrar(Usuario usuarioLogado, List<Item> itemList, List<Historico> historicoSistema, ListaVenda listaVendas) throws InterruptedException {
        int opcao;
        do {
            mostrarMenuUsuarioMensal(usuarioLogado);
            opcao = Console.lerInt("Escolha uma opcao:");
            System.out.println("\n\n");
            switch (opcao) {
                case 1:
                    comprarProduto(itemList, usuarioLogado, historicoSistema, listaVendas);
                    break;
                case 8:
                    System.out.println("Fim");
                    break;

                default:
                    System.out.println("Opcao invalida");
            }

        } while (opcao != 8);
        return opcao;
    }

    private static void mostrarMenuUsuarioMensal(Usuario usuarioLogado) {
        System.out.println("\n\n===========================================");
        System.out.println("Usuário: " + usuarioLogado.getNome() + " logado");
        System.out.println("===========================================");
        System.out.println("1 - Comprar produto");
        System.out.println("2 - Colocar crédito");
        System.out.println("8 - Sair");
    }

    private static void comprarProduto(List<Item> itemList, Usuario usuarioLogado, List<Historico> historicoSistema, ListaVenda listaVendas) throws InterruptedException {
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
                if (existeItemNoEstoque(item)) {
                    valorAPagar += item.getValor();
                    int formaPagamento = Console.lerInt("Digite 1 para debitar do crédito e 2 para pagar via maquininha: ");
                    if (formaPagamento == 1) {
                        if (usuarioLogado.getCredito().getValorTotal() >= valorAPagar) {
                            realizarCompra(item, usuarioLogado, valorAPagar, listaVendas, historicoSistema);
                            System.out.println("Debitado do crédito, sobrou: R$ " + usuarioLogado.getCredito().getValorTotal());
                        } else {
                            System.out.println("Saldo insuficiente!!!");
                            break;
                        }
                    } else if (formaPagamento == 2) {
                        System.out.println("Total da compra: ");
                        System.out.println("Digite a senha: ");
                        System.out.println("Validando pagamento");
                        Thread.sleep(2000);
                        System.out.print(".");
                        Thread.sleep(2000);
                        System.out.print(".");
                        Thread.sleep(2000);
                        System.out.print(".");
                        realizarCompra(item, usuarioLogado, valorAPagar, listaVendas, historicoSistema);
                    } else {

                    }
                }
            }
        }
        if (i == itemList.size()) {
            System.out.println("Produto não encontrado: " + idProduto);
        }

    }

    private static void realizarCompra(Item item, Usuario usuario, double valorAPagar, ListaVenda listaVendas, List<Historico> historicoSistema) {
        Date dataCompra = new Date();
        item.getEstoque().venderItem();
        usuario.getCredito().pagarCompra(valorAPagar);
        int idVenda = listaVendas.adicionarVenda(dataCompra, item.getValor(), item.getId());
        historicoSistema.add(new Historico(usuario.getId(), idVenda, dataCompra));
    }

    private static boolean existeItemNoEstoque(Item item) {
        if (item.getEstoque().getEstoqueAtual() > 0) {
            return true;
        }

        return false;
    }

}
