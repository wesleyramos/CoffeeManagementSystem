package br.pucbr.utils;

import br.pucbr.model.dao.UsuarioDAO;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class BancoDeDados {

    public static Connection conn;
    private static UsuarioDAO usuarioDAO;

    public static void criarTabelas() {
        criarTabelaUsuario();
        criarCredito();
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

        if(file.delete())
        {
//            System.out.println("File deleted successfully");
        }
        else
        {
//            System.out.println("Failed to delete the file");
        }
    }
}
