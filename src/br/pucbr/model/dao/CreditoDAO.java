package br.pucbr.model.dao;

import br.pucbr.model.*;
import br.pucbr.utils.BancoDeDados;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CreditoDAO implements InterfaceDAO {

    @Override
    public Credito inserir(Object _credito) throws RuntimeException, SQLException {

        if( _credito instanceof Credito ){
            Credito credito = (Credito) _credito;
            Statement statement = BancoDeDados.conectar().createStatement();

            if( statement != null ){
                statement.execute("CREATE TABLE IF NOT EXISTS credito( " +
                        "id_credito INTEGER NOT NULL UNIQUE" +
                        ", valor_total NUMERIC NOT NULL" +
                        ", PRIMARY KEY('id_credito' AUTOINCREMENT))");

                PreparedStatement preparedStatement = BancoDeDados.conectar().prepareStatement("INSERT INTO credito(valor_total) VALUES (?)");
                preparedStatement.setDouble(1, credito.getValorTotal());
                preparedStatement.executeUpdate();

                ResultSet genKeys = preparedStatement.getGeneratedKeys();
                if( genKeys.next() ){
                    credito.setId(genKeys.getInt(1));
                }

                return credito;
            }else{
                throw new SQLException();
            }

        }else{
            throw new RuntimeException();
        }

    }

    @Override
    public boolean alterar(Object object) throws Exception {

        if( object != null ){
            if( object instanceof Credito ){
                Credito credito = (Credito) object;

                PreparedStatement preparedStatement = BancoDeDados.conectar().prepareStatement("UPDATE credito SET valor_total=? WHERE id_credito=?");
                preparedStatement.setDouble(1, credito.getValorTotal());
                preparedStatement.setInt(2, credito.getId());
                preparedStatement.executeUpdate();
                preparedStatement.close();
                return Boolean.TRUE;
            }
        }

        return false;
    }

    public List<Credito> buscar(Object object) throws Exception{

        if( object != null ){
            if( object instanceof Credito ) {
                Credito credito = (Credito) object;

                PreparedStatement preparedStatement = BancoDeDados.conectar().prepareStatement("SELECT * FROM credito WHERE id_credito=?");
                preparedStatement.setInt(1, credito.getId());

                ResultSet rs = preparedStatement.executeQuery();
                List<Credito> ret = new ArrayList<Credito>();
                while (rs.next()){
                    ret.add( new Credito(rs.getInt("id"), rs.getDouble("valor_total")) );
                }

                return ret;
            }

        }

        return null;
    }

    @Override
    public boolean remover(Integer id) throws Exception{

        if( id != null ){
            PreparedStatement preparedStatement = BancoDeDados.conectar().prepareStatement("DELETE FROM credito WHERE id_credito=?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            return Boolean.TRUE;
        }

        return false;
    }

}
