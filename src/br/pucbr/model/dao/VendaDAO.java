package br.pucbr.model.dao;

import br.pucbr.model.Estoque;
import br.pucbr.model.Item;
import br.pucbr.model.Venda;
import br.pucbr.utils.BancoDeDados;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class VendaDAO implements InterfaceDAO {

    @Override
    public Venda inserir(Object _venda) {

        if (_venda instanceof Venda) {
            Venda venda = (Venda) _venda;
            try {
                Statement statement = BancoDeDados.conectar().createStatement();

                if (statement != null) {
                    statement.execute("CREATE TABLE IF NOT EXISTS venda( " +
                            "id_venda INTEGER NOT NULL UNIQUE" +
                            ", data DATE NOT NULL" +
                            ", total NUMERIC NOT NULL" +
                            ", id_item INTEGER NOT NULL" +
                            ", PRIMARY KEY('id_venda' AUTOINCREMENT))");

                    PreparedStatement preparedStatement = BancoDeDados.conectar()
                            .prepareStatement("INSERT INTO venda(data, total, id_item) VALUES (?,?,?)");

                    preparedStatement.setDate(1, new java.sql.Date(venda.getData().getTime()));
                    preparedStatement.setDouble(2, venda.getTotal());
                    preparedStatement.setInt(3, venda.getItem().getId());
                    preparedStatement.executeUpdate();

                    ResultSet genKeys = preparedStatement.getGeneratedKeys();
                    if (genKeys.next()) {
                        venda.setId(genKeys.getInt(1));
                    }

                    return venda;
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
    public boolean alterar(Object _venda) throws Exception {

        if (_venda != null) {
            if (_venda instanceof Venda) {
                Venda venda = (Venda) _venda;

                PreparedStatement preparedStatement = BancoDeDados.conectar()
                        .prepareStatement("UPDATE venda SET data=?, total=?, id_item=? WHERE id_venda=?");

                preparedStatement.setDate(1, (java.sql.Date) venda.getData());
                preparedStatement.setDouble(2, venda.getTotal());
                preparedStatement.setInt(3, venda.getItem().getId());
                preparedStatement.setInt(4, venda.getId());
                preparedStatement.executeUpdate();

                return Boolean.TRUE;
            }
        }

        return false;
    }

    public List<Venda> buscar(int idVenda) throws Exception {
        PreparedStatement preparedStatement = BancoDeDados.conectar().prepareStatement("SELECT * FROM venda AS v " +
                "INNER JOIN item AS i " +
                "ON v.id_item = i.id_item " +
                "INNER JOIN estoque AS e " +
                "ON i.id_estoque = e.id_estoque " +
                "WHERE id_venda=?");

        preparedStatement.setInt(1, idVenda);

        ResultSet rs = preparedStatement.executeQuery();
        List<Venda> ret = new ArrayList<Venda>();
        while (rs.next()) {
            Estoque estoque = new Estoque(rs.getInt("id_estoque"), rs.getDouble("estoque_atual"), rs.getDouble("estoque_minimo"));
            Item item = new Item(rs.getInt("id_item"), rs.getString("descricao"), rs.getDouble("valor"), estoque);
            Venda venda = new Venda(rs.getInt("id_venda"), rs.getDate("data"), rs.getDouble("total"), item);
            ret.add(venda);
        }

        return ret;
    }

    @Override
    public boolean remover(Integer id) throws Exception {

        if (id != null) {
            PreparedStatement preparedStatement = BancoDeDados.conectar().prepareStatement("DELETE FROM venda WHERE id_venda=?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            return Boolean.TRUE;
        }

        return false;
    }

}
