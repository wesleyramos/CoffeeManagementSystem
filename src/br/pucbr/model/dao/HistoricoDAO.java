package br.pucbr.model.dao;

import br.pucbr.model.Historico;
import br.pucbr.utils.BancoDeDados;

import java.sql.*;
import java.util.List;

public class HistoricoDAO implements InterfaceDAO{

    @Override
    public Historico inserir(Object _historico) throws RuntimeException, SQLException {

        if( _historico instanceof Historico){
            Historico historico = (Historico) _historico;
            Statement statement = BancoDeDados.conectar().createStatement();

            if( statement != null ){
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
                if( genKeys.next() ){
                    historico.setId(genKeys.getInt(1));
                }

                return historico;
            }else{
                throw new SQLException();
            }

        }else{
            throw new RuntimeException();
        }

    }

    @Override
    public boolean alterar(Object _historico) throws Exception {

        if( _historico != null ){
            if( _historico instanceof Historico ){
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

    public List<Historico> buscar(Object _historico) throws Exception{

        if( _historico != null ){
            if( _historico instanceof Historico ) {
                Historico historico = (Historico) _historico;

                PreparedStatement preparedStatement = BancoDeDados.conectar().prepareStatement("SELECT * FROM historico WHERE id_usuario=?");
                preparedStatement.setInt(1, historico.getUsuarioId());

                ResultSet rs = preparedStatement.getResultSet();
                while (rs.next()){
                    System.out.println( "ID: " + rs.getInt("id") );
                    System.out.println( "Valor total: " + rs.getDouble("valor_total") );
                }

            }

        }

        return null;
    }

    @Override
    public boolean remover(Integer id) throws Exception{

        if( id != null ){
            PreparedStatement preparedStatement = BancoDeDados.conectar().prepareStatement("DELETE FROM historico WHERE id_historico=?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            return Boolean.TRUE;
        }

        return false;
    }

}
