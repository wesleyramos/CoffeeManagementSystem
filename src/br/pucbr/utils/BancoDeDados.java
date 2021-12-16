package br.pucbr.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BancoDeDados {

    public static Connection conn;

    public static Connection conectar(){

        if( conn == null ){
            try {
                conn = DriverManager.getConnection("jdbc:sqlite:cafe.db");
                System.out.println("Conexão realizada com sucesso!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return conn;
    }

}
