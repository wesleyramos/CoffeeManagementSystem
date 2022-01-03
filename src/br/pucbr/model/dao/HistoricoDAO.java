package br.pucbr.model.dao;

import br.pucbr.model.*;
import br.pucbr.utils.BancoDeDados;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class HistoricoDAO implements InterfaceDAO {

    @Override
    public Historico inserir(Object _historico) {

        if (_historico instanceof Historico) {
            Historico historico = (Historico) _historico;
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

                    PreparedStatement preparedStatement = BancoDeDados.conectar()
                            .prepareStatement("INSERT INTO historico(id_usuario, id_venda, data, total) VALUES (?,?,?,?)");

                    preparedStatement.setDouble(1, historico.getUsuarioId());
                    preparedStatement.setDouble(2, historico.getVendaId());
                    preparedStatement.setDate(3, new java.sql.Date(historico.getData().getTime()));
                    preparedStatement.setDouble(4, historico.getTotal());
                    preparedStatement.executeUpdate();

                    ResultSet genKeys = preparedStatement.getGeneratedKeys();
                    if (genKeys.next()) {
                        historico.setId(genKeys.getInt(1));
                    }

                    return historico;
                }

            } catch (SQLException sqlException) {
                System.err.println("Erro ao inserir usuario: " + sqlException.getMessage());
            }

        } else {
            System.out.println("_item de tipo incorreto!!!");
        }
        return null;
    }

    @Override
    public boolean alterar(Object _historico) throws Exception {

        if (_historico != null) {
            if (_historico instanceof Historico) {
                Historico historico = (Historico) _historico;

                PreparedStatement preparedStatement = BancoDeDados.conectar()
                        .prepareStatement("UPDATE historico SET id_usuario=?, id_venda=?, total=? WHERE id_historico=?");

                preparedStatement.setDouble(1, historico.getUsuarioId());
                preparedStatement.setDouble(2, historico.getVendaId());
                preparedStatement.setDouble(3, historico.getTotal());
                preparedStatement.setInt(4, historico.getId());
                preparedStatement.executeUpdate();

                return Boolean.TRUE;
            }
        }

        return false;
    }

    public List<Historico> buscar(Object _historico) throws Exception {

        if (_historico != null) {
            if (_historico instanceof Historico) {
                Historico historico = (Historico) _historico;

                PreparedStatement preparedStatement = BancoDeDados.conectar().prepareStatement("SELECT * FROM historico WHERE id_usuario=?");
                preparedStatement.setInt(1, historico.getUsuarioId());

                ResultSet rs = preparedStatement.getResultSet();
                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("id"));
                    System.out.println("Valor total: " + rs.getDouble("valor_total"));
                }

            }

        }

        return null;
    }

    public List<Historico> buscarPorUsuario(String nomeUsuario) throws Exception {

        if (nomeUsuario != null && !nomeUsuario.equalsIgnoreCase("")) {

            PreparedStatement preparedStatement = BancoDeDados.conectar().prepareStatement("SELECT * FROM historico AS h " +
                    "INNER JOIN usuario AS u " +
                    "ON h.id_usuario = u.id_usuario " +
                    "INNER JOIN venda AS v " +
                    "ON h.id_venda = v.id_venda " +
                    "INNER JOIN item AS i " +
                    "ON v.id_item = i.id_item " +
                    "WHERE u.usuario = ?;");
            preparedStatement.setString(1, nomeUsuario);

            ResultSet rs = preparedStatement.executeQuery();
            List<Historico> historicos = new ArrayList<>();
            while (rs.next()) {

                Usuario usuario = null;
                if( rs.getInt("tipo") == 1 ){
                    usuario = new UsuarioMensal( rs.getInt("id_usuario"), rs.getString("nome")
                            , rs.getString("usuario"), rs.getString("senha"), null, rs.getInt("tipo") );
                }else{
                    usuario = new UsuarioAdmin( rs.getInt("id_usuario"), rs.getString("nome")
                            , rs.getString("usuario"), rs.getString("senha"), null, rs.getInt("tipo") );
                }

                Item item = new Item(rs.getInt("id_item"), rs.getString("descricao"), rs.getDouble("valor"), null);

                Venda venda = new Venda(rs.getDate("data"), rs.getDouble("total"), item);
                venda.setId(rs.getInt("id_venda"));

                Historico historico = new Historico(rs.getDate("data"), rs.getDouble("total"), usuario, venda);

                historicos.add(historico);
            }

            return historicos;

        }

        return null;
    }

    @Override
    public boolean remover(Integer id) throws Exception {

        if (id != null) {
            PreparedStatement preparedStatement = BancoDeDados.conectar().prepareStatement("DELETE FROM historico WHERE id_historico=?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            return Boolean.TRUE;
        }

        return false;
    }

}
