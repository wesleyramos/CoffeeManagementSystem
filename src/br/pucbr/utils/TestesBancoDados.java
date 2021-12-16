package br.pucbr.utils;

import br.pucbr.model.*;
import br.pucbr.model.dao.*;

import java.util.Date;
import java.util.List;

public class TestesBancoDados {

    public static void testes() {

        CreditoDAO creditoDAO = new CreditoDAO();
        EstoqueDAO estoqueDAO = new EstoqueDAO();
        ItemDAO itemDAO = new ItemDAO();
        VendaDAO vendaDAO = new VendaDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        HistoricoDAO historicoDAO = new HistoricoDAO();

        try {

            for (int i = 1; i <= 10; i++) {
                Credito credito = new Credito(Double.parseDouble(String.valueOf(i)));
                creditoDAO.inserir(credito);

                Usuario usuario = new UsuarioMensal("u" + String.valueOf(i), "s" + String.valueOf(i), credito);
                usuarioDAO.inserir(usuario);

                Estoque estoque = new Estoque(10, 5);
                estoqueDAO.inserir(estoque);

                Item item = new Item("Cafe_" + i, 1 + Double.parseDouble(String.valueOf(i)), estoque);
                itemDAO.inserir(item);

                Venda venda = new Venda(new Date(), i * 1.1, item);
                vendaDAO.inserir(venda);

                Historico historico = new Historico(usuario.getId(), venda.getId(), new Date(), item.getValor());
                historicoDAO.inserir(historico);

            }

            List<Usuario> usuarios = usuarioDAO.buscarPorNome("u1");
            if (usuarios != null) {
                for (Usuario usuario : usuarios) {

                    System.out.println("Usuario ID: " + usuario.getId());
                    System.out.println("Usuario nome: " + usuario.getNome());
                    System.out.println("Usuario senha: " + usuario.getSenha());
                    System.out.println("Credito: " + usuario.getCredito().getValorTotal());

                    usuario.setSenha("s1");
                    usuario.setTipo(1);
                    usuarioDAO.alterar(usuario);

                    Credito credito = usuario.getCredito();
                    credito.setValorTotal(100d);
                    creditoDAO.alterar(credito);

                    usuarioDAO.remover(usuario.getId());
                    creditoDAO.remover(credito.getId());

                }
            }

            List<Item> itens = itemDAO.buscarPorDescricao("Cafe_22");
            for (Item item : itens) {

                System.out.println("Item ID: " + item.getId());
                System.out.println("Descricao: " + item.getDescricao());
                System.out.println("Valor: " + item.getValor());
                System.out.println("Estoque atual: " + item.getEstoque().getEstoqueAtual());
                System.out.println("Estoque Min: " + item.getEstoque().getEstoqueMinimo());

                Estoque estoque = item.getEstoque();
                estoque.setEstoqueAtual(100);
                estoque.setEstoqueMinimo(10);
                estoqueDAO.alterar(estoque);

                item.setDescricao("Cafe_22");
                item.setValor(15d);
                itemDAO.alterar(item);

                itemDAO.remover(item.getId());
                estoqueDAO.remover(estoque.getId());

            }

            List<Venda> vendas = vendaDAO.buscar(3);
            for (Venda venda : vendas) {

                System.out.println("Venda ID: " + venda.getId());
                System.out.println("Venda data: " + venda.getData());
                System.out.println("Venda total: " + venda.getTotal());

                System.out.println("Item ID: " + venda.getItem().getId());
                System.out.println("Descricao: " + venda.getItem().getDescricao());
                System.out.println("Valor: " + venda.getItem().getValor());

                System.out.println("Estoque atual: " + venda.getItem().getEstoque().getEstoqueAtual());
                System.out.println("Estoque atual: " + venda.getItem().getEstoque().getEstoqueMinimo());

                venda.setTotal(4d);
                vendaDAO.alterar(venda);

                Item item = venda.getItem();
                item.setDescricao("Cafe_222");
                itemDAO.alterar(item);

                Estoque estoque = venda.getItem().getEstoque();
                estoque.setEstoqueAtual(500);
                estoqueDAO.alterar(estoque);

                vendaDAO.remover(venda.getId());
                estoqueDAO.remover(venda.getItem().getEstoque().getId());
                itemDAO.remover(venda.getItem().getId());

            }


//            for ( int i = 1; i <= 10; i++ ){
//                creditoDAO.remover(i);
//                usuarioDAO.remover(i);
//                estoqueDAO.remover(i);
//                itemDAO.remover(i);
//                vendaDAO.remover(i);
//                historicoDAO.remover(i);
//            }

        } catch (Exception e) {
            System.err.println("Falha na comunicação com o Banco de Dados");
            System.err.println(e.getMessage());
        }

    }

    }

