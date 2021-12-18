package br.pucbr.model.dao;

import br.pucbr.model.Estoque;
import br.pucbr.utils.BancoDeDados;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class EstoqueDAO implements InterfaceDAO {

    @Override
    public Estoque inserir(Object _estoque) {
        if (_estoque instanceof Estoque) {
            Estoque estoque = (Estoque) _estoque;

            try {
                Statement statement = BancoDeDados.conectar().createStatement();

                if (statement != null) {
                    statement.execute("CREATE TABLE IF NOT EXISTS estoque( " +
                            "id_estoque INTEGER NOT NULL UNIQUE" +
                            ", estoque_atual NUMERIC NOT NULL" +
                            ", estoque_minimo NUMERIC NOT NULL" +
                            ", PRIMARY KEY('id_estoque' AUTOINCREMENT))");

                    PreparedStatement preparedStatement = BancoDeDados.conectar().prepareStatement("INSERT INTO estoque(estoque_atual, estoque_minimo) VALUES (?,?)");
                    preparedStatement.setDouble(1, estoque.getEstoqueAtual());
                    preparedStatement.setDouble(2, estoque.getEstoqueMinimo());
                    preparedStatement.executeUpdate();

                    ResultSet genKeys = preparedStatement.getGeneratedKeys();
                    if (genKeys.next()) {
                        estoque.setId(genKeys.getInt(1));
                    }

                    return estoque;
                }

            } catch (SQLException sqlException) {
                System.err.println("Erro ao inserir usuario: " + sqlException.getMessage());
            }

        } else {
            System.out.println("estoque de tipo incorreto: " + _estoque.getClass());
        }
        return null;
    }


    @Override
    public boolean alterar(Object _estoque) {
        try {
            if (_estoque != null) {
                if (_estoque instanceof Estoque) {
                    Estoque estoque = (Estoque) _estoque;

                    PreparedStatement preparedStatement = BancoDeDados.conectar().prepareStatement("UPDATE estoque SET estoque_atual=?, estoque_minimo=? WHERE id_estoque=?");
                    preparedStatement.setDouble(1, estoque.getEstoqueAtual());
                    preparedStatement.setDouble(2, estoque.getEstoqueMinimo());
                    preparedStatement.setInt(3, estoque.getId());
                    preparedStatement.executeUpdate();

                    return Boolean.TRUE;
                }
            }

            return false;
        } catch (Exception exc) {
            System.out.println("Error to update estoque!" + exc.getMessage());
            return false;
        }
    }

    public List<Estoque> buscar(Object _estoque) throws Exception {

        if (_estoque != null) {
            if (_estoque instanceof Estoque) {
                Estoque estoque = (Estoque) _estoque;

                PreparedStatement preparedStatement = BancoDeDados.conectar().prepareStatement("SELECT * FROM estoque WHERE estoque_atual=?");
                preparedStatement.setDouble(1, estoque.getEstoqueAtual());

                ResultSet rs = preparedStatement.getResultSet();
                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("id"));
                    System.out.println("Valor total: " + rs.getDouble("valor_total"));
                }

            }

        }

        return null;
    }

    @Override
    public boolean remover(Integer id) throws Exception {

        if (id != null) {
            PreparedStatement preparedStatement = BancoDeDados.conectar().prepareStatement("DELETE FROM estoque WHERE id_estoque=?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            return Boolean.TRUE;
        }

        return false;
    }

}
