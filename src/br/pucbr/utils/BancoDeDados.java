package br.pucbr.utils;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class BancoDeDados {

    public static Connection conn;

    public static void criarTabelas() {
        criarTabelaUsuario();
        criarCredito();
        criarTabelaEstoque();
        criarTabelaHistorico();
        criarTabelaItem();
        criarTabelaVenda();
    }

    private static void criarCredito() {
        try {
            Statement statement = BancoDeDados.conectar().createStatement();

            if (statement != null) {
                statement.execute("CREATE TABLE IF NOT EXISTS credito( " +
                        "id_credito INTEGER NOT NULL UNIQUE" +
                        ", valor_total NUMERIC NOT NULL" +
                        ", PRIMARY KEY('id_credito' AUTOINCREMENT))");
            }
        } catch (SQLException sqlException) {
            System.err.println("Erro ao inserir credito: " + sqlException.getMessage());
        }
    }

    private static void criarTabelaUsuario() {
        try {
            Statement statement = BancoDeDados.conectar().createStatement();

            if (statement != null) {
                statement.execute("CREATE TABLE IF NOT EXISTS usuario( " +
                        "id_usuario INTEGER NOT NULL UNIQUE" +
                        ", nome TEXT NOT NULL" +
                        ", usuario TEXT NOT NULL" +
                        ", senha TEXT NOT NULL" +
                        ", id_credito INTEGER NOT NULL" +
                        ", tipo INTEGER NOT NULL" +
                        ", PRIMARY KEY('id_usuario' AUTOINCREMENT))");
            }

        } catch (SQLException sqlException) {
            System.err.println("Erro ao inserir usuario: " + sqlException.getMessage());
        }
    }

    private static void criarTabelaEstoque() {
        try {
            Statement statement = BancoDeDados.conectar().createStatement();

            if (statement != null) {
                statement.execute("CREATE TABLE IF NOT EXISTS estoque( " +
                        "id_estoque INTEGER NOT NULL UNIQUE" +
                        ", estoque_atual NUMERIC NOT NULL" +
                        ", estoque_minimo NUMERIC NOT NULL" +
                        ", PRIMARY KEY('id_estoque' AUTOINCREMENT))");
            }

        } catch (SQLException sqlException) {
            System.err.println("Erro ao criar tabela de estoque: " + sqlException.getMessage());
        }
    }

    private static void criarTabelaHistorico() {
        try {
            Statement statement = BancoDeDados.conectar().createStatement();

            if (statement != null) {
                statement.execute("CREATE TABLE IF NOT EXISTS historico( " +
                        "id_historico INTEGER NOT NULL UNIQUE" +
                        ", id_usuario INTEGER NOT NULL" +
                        ", id_venda INTEGER NOT NULL" +
                        ", data DATE NOT NULL" +
                        ", total NUMERIC NOT NULL" +
                        ", PRIMARY KEY('id_historico' AUTOINCREMENT))");
            }

        } catch (SQLException sqlException) {
            System.err.println("Erro ao criar tabela de historico: " + sqlException.getMessage());
        }
    }

    private static void criarTabelaItem() {
        try {
            Statement statement = BancoDeDados.conectar().createStatement();

            if (statement != null) {
                statement.execute("CREATE TABLE IF NOT EXISTS item( " +
                        "id_item INTEGER NOT NULL UNIQUE" +
                        ", descricao TEXT NOT NULL" +
                        ", valor NUMERIC NOT NULL" +
                        ", id_estoque INTEGER NOT NULL" +
                        ", PRIMARY KEY('id_item' AUTOINCREMENT))");
            }

        } catch (SQLException sqlException) {
            System.err.println("Erro ao criar tabela de item: " + sqlException.getMessage());
        }
    }

    private static void criarTabelaVenda() {
        try {
            Statement statement = BancoDeDados.conectar().createStatement();

            if (statement != null) {
                statement.execute("CREATE TABLE IF NOT EXISTS venda( " +
                        "id_venda INTEGER NOT NULL UNIQUE" +
                        ", data DATE NOT NULL" +
                        ", total NUMERIC NOT NULL" +
                        ", id_item INTEGER NOT NULL" +
                        ", PRIMARY KEY('id_venda' AUTOINCREMENT))");
            }

        } catch (SQLException sqlException) {
            System.err.println("Erro ao criar tabela de venda: " + sqlException.getMessage());
        }
    }

    public static Connection conectar() {

        if (conn == null) {
            try {
                conn = DriverManager.getConnection("jdbc:sqlite:cafe.db");
                System.out.println("Conexão realizada com sucesso!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return conn;
    }

    public static void droparBase() {
        File file = new File("/home/wesley/arquivos/03_PosGraduacao/trabalho/CoffeeManagementSystem/cafe.db");

        if (file.delete()) {
//            System.out.println("File deleted successfully");
        } else {
//            System.out.println("Failed to delete the file");
        }
    }
}
