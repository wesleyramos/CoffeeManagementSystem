package br.pucbr.model.dao;

import br.pucbr.model.Credito;
import br.pucbr.model.Usuario;
import br.pucbr.model.UsuarioEsporadico;
import br.pucbr.model.UsuarioMensal;
import br.pucbr.utils.BancoDeDados;
import br.pucbr.utils.HashMd5;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO implements InterfaceDAO{

    @Override
    public Usuario inserir(Object _usuario) throws RuntimeException, SQLException {

        if( _usuario instanceof Usuario){
            Usuario usuario = (Usuario) _usuario;
            Statement statement = BancoDeDados.conectar().createStatement();

            if( statement != null ){
                statement.execute("CREATE TABLE IF NOT EXISTS usuario( " +
                        "id_usuario INTEGER NOT NULL UNIQUE" +
                        ", nome TEXT NOT NULL" +
                        ", senha TEXT NOT NULL" +
                        ", id_credito INTEGER NOT NULL" +
                        ", tipo INTEGER NOT NULL" +
                        ", PRIMARY KEY('id_usuario' AUTOINCREMENT))");

                PreparedStatement preparedStatement = BancoDeDados.conectar()
                        .prepareStatement("INSERT INTO usuario(nome, senha, id_credito, tipo) VALUES (?,?,?,?)");

                preparedStatement.setString(1, usuario.getNome());
                preparedStatement.setString(2, HashMd5.gerarHashMd5(usuario.getSenha()));
                preparedStatement.setInt(3, usuario.getCredito().getId());
                preparedStatement.setInt(4, usuario.getTipo());
                preparedStatement.executeUpdate();

                ResultSet genKeys = preparedStatement.getGeneratedKeys();
                if( genKeys.next() ){
                    usuario.setId(genKeys.getInt(1));
                }

                return usuario;
            }else{
                throw new SQLException();
            }

        }else{
            throw new RuntimeException();
        }

    }

    @Override
    public boolean alterar(Object _usuario) throws Exception {

        if( _usuario != null ){
            if( _usuario instanceof Usuario ){
                Usuario usuario = (Usuario) _usuario;

                PreparedStatement preparedStatement = BancoDeDados.conectar()
                        .prepareStatement("UPDATE usuario SET nome=?, senha=?, tipo=? WHERE id_usuario=?");

                preparedStatement.setString(1, usuario.getNome());
                preparedStatement.setString(2, HashMd5.gerarHashMd5(usuario.getSenha()));
                preparedStatement.setInt(3, usuario.getTipo());
                preparedStatement.setInt(4, usuario.getId());
                preparedStatement.executeUpdate();

                return Boolean.TRUE;
            }
        }

        return false;
    }

    public List<Usuario> buscarPorNome(String _nome) throws Exception{

        if( _nome != null && !_nome.equalsIgnoreCase("")){

            PreparedStatement preparedStatement = BancoDeDados.conectar().prepareStatement("SELECT * FROM usuario AS u LEFT JOIN credito AS c ON u.id_credito = c.id_credito WHERE nome=?");
            preparedStatement.setString(1, _nome);

            ResultSet rs = preparedStatement.executeQuery();
            List<Usuario> ret = new ArrayList<Usuario>();
            while (rs.next()){

                int idUsuario = rs.getInt("id_usuario");
                String nome = rs.getString("nome");
                String senha = rs.getString("senha");
                int idCredido = rs.getInt("id_credito");
                int tipo = rs.getInt("tipo");

                int idCredito = rs.getInt("id_credito");
                double valor_total = rs.getDouble("valor_total");

                Usuario usuario = null;

                switch (tipo) {
                    case 0:
                    case 1:
                    case 2:
                        Credito credito = new Credito(idCredito, valor_total);
                        usuario = new UsuarioMensal(idUsuario, nome, senha, credito);
                        break;
                    default:
                        System.err.println("Tipo de usuario não mapeado.");
                }

                ret.add(usuario);
            }

            return ret;

            }

        return null;
    }

    @Override
    public boolean remover(Integer id) throws Exception{

        if( id != null ){
            PreparedStatement preparedStatement = BancoDeDados.conectar().prepareStatement("DELETE FROM usuario WHERE id_usuario=?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            return Boolean.TRUE;
        }

        return false;
    }

}
