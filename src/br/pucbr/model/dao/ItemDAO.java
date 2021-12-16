package br.pucbr.model.dao;

import br.pucbr.model.Estoque;
import br.pucbr.model.Item;
import br.pucbr.model.Usuario;
import br.pucbr.utils.BancoDeDados;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO implements InterfaceDAO{

    @Override
    public Item inserir(Object _item) throws RuntimeException, SQLException {

        if( _item instanceof Item){
            Item item = (Item) _item;
            Statement statement = BancoDeDados.conectar().createStatement();

            if( statement != null ){
                statement.execute("CREATE TABLE IF NOT EXISTS item( " +
                        "id_item INTEGER NOT NULL UNIQUE" +
                        ", descricao TEXT NOT NULL" +
                        ", valor NUMERIC NOT NULL" +
                        ", id_estoque INTEGER NOT NULL" +
                        ", PRIMARY KEY('id_item' AUTOINCREMENT))");

                PreparedStatement preparedStatement = BancoDeDados.conectar()
                        .prepareStatement("INSERT INTO item(descricao, valor, id_estoque) VALUES (?,?,?)");

                preparedStatement.setString(1, item.getDescricao());
                preparedStatement.setDouble(2, item.getValor());
                preparedStatement.setInt(3, item.getEstoque().getId());
                preparedStatement.executeUpdate();

                ResultSet genKeys = preparedStatement.getGeneratedKeys();
                if( genKeys.next() ){
                    item.setId(genKeys.getInt(1));
                }

                return item;
            }else{
                throw new SQLException();
            }

        }else{
            throw new RuntimeException();
        }

    }

    @Override
    public boolean alterar(Object _item) throws Exception {

        if( _item != null ){
            if( _item instanceof Item ){
                Item item = (Item) _item;

                PreparedStatement preparedStatement = BancoDeDados.conectar()
                        .prepareStatement("UPDATE item SET descricao=?, valor=?, id_estoque=? WHERE id_item=?");

                preparedStatement.setString(1, item.getDescricao());
                preparedStatement.setDouble(2, item.getValor());
                preparedStatement.setInt(3, item.getEstoque().getId());
                preparedStatement.setInt(4, item.getId());
                preparedStatement.executeUpdate();

                return Boolean.TRUE;
            }
        }

        return false;
    }

    public List<Item> buscarPorDescricao(String descricao) throws Exception{

        if( descricao != null && !descricao.equalsIgnoreCase("") ){

                PreparedStatement preparedStatement = BancoDeDados.conectar().prepareStatement("SELECT * FROM item AS i INNER JOIN estoque AS e ON i.id_estoque = e.id_estoque WHERE descricao=?");
                preparedStatement.setString(1, descricao);

            ResultSet rs = preparedStatement.executeQuery();
            List<Item> ret = new ArrayList<Item>();
            while (rs.next()){
                Estoque estoque = new Estoque(rs.getInt("id_estoque"), rs.getDouble("estoque_atual"), rs.getDouble("estoque_minimo"));
                Item item = new Item(rs.getInt("id_item"), rs.getString("descricao"), rs.getDouble("valor"), estoque);
                ret.add(item);
            }

            return ret;
        }

        return null;
    }

    @Override
    public boolean remover(Integer id) throws Exception{

        if( id != null ){
            PreparedStatement preparedStatement = BancoDeDados.conectar().prepareStatement("DELETE FROM item WHERE id_item=?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            return Boolean.TRUE;
        }

        return false;
    }

}
