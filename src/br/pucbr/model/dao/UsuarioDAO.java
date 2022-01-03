package br.pucbr.model.dao;

import br.pucbr.model.Credito;
import br.pucbr.model.Usuario;
import br.pucbr.model.UsuarioAdmin;
import br.pucbr.model.UsuarioMensal;
import br.pucbr.utils.BancoDeDados;
import br.pucbr.utils.HashMd5;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO implements InterfaceDAO {


    @Override
    public Usuario inserir(Object _usuario) {

        if (_usuario instanceof Usuario) {
            Usuario usuario = (Usuario) _usuario;
            try {
                Statement statement = BancoDeDados.conectar().createStatement();

                if (statement != null) {
                    PreparedStatement preparedStatement = BancoDeDados.conectar()
                            .prepareStatement("INSERT INTO usuario(nome, usuario, senha, id_credito, tipo) VALUES (?,?,?,?,?)");

                    preparedStatement.setString(1, usuario.getNome());
                    preparedStatement.setString(2, usuario.getUsuario());
                    preparedStatement.setString(3, HashMd5.gerarHashMd5(usuario.getSenha()));
                    preparedStatement.setInt(4, usuario.getCredito().getId());
                    preparedStatement.setInt(5, usuario.getTipo());
                    preparedStatement.executeUpdate();

                    ResultSet genKeys = preparedStatement.getGeneratedKeys();
                    if (genKeys.next()) {
                        usuario.setId(genKeys.getInt(1));
                    }
                    System.out.println("usuário cadastrado com sucesso!");
                    return usuario;
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
    public boolean alterar(Object _usuario) throws Exception {

        if (_usuario != null) {
            if (_usuario instanceof Usuario) {
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

    public List<Usuario> buscarPorNome(String _nome) throws Exception {

        if (_nome != null && !_nome.equalsIgnoreCase("")) {

            PreparedStatement preparedStatement = BancoDeDados.conectar().prepareStatement("SELECT * FROM usuario AS u LEFT JOIN credito AS c ON u.id_credito = c.id_credito WHERE nome=?");
            preparedStatement.setString(1, _nome);

            ResultSet rs = preparedStatement.executeQuery();
            List<Usuario> ret = new ArrayList<Usuario>();
            while (rs.next()) {

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

    public Usuario buscarPorUsuario(String _usuario) {
        if (_usuario != null && !_usuario.equalsIgnoreCase("")) {
            try {
                PreparedStatement preparedStatement = BancoDeDados.conectar().prepareStatement("SELECT * FROM usuario AS u LEFT JOIN credito AS c ON u.id_credito = c.id_credito WHERE usuario=?");
                preparedStatement.setString(1, _usuario);

                ResultSet rs = preparedStatement.executeQuery();
                Usuario usuario = null;
                while (rs.next()) {
                    if (null != rs) {
                        int idUsuario = rs.getInt("id_usuario");
                        String nome = rs.getString("nome");
                        String login = rs.getString("usuario");
                        String senha = rs.getString("senha");
                        int tipo = rs.getInt("tipo");
                        int idCredito = rs.getInt("id_credito");
                        double valorTotal = rs.getDouble("valor_total");

                        Credito credito = new Credito(idCredito, valorTotal);
                        switch (tipo) {
                            case 1:
                                usuario = new UsuarioMensal(idUsuario, nome, login, senha, credito, tipo);
                                break;
                            case 2:
                                usuario = new UsuarioAdmin(idUsuario, nome, login, senha, credito, tipo);
                                break;
                            default:
                                System.err.println("Tipo de usuario não mapeado.");
                        }
                    }
                }
                return usuario;
            } catch (SQLException sqlException) {
                System.err.println("Erro ao inserir usuario123: " + sqlException.getMessage());
                return null;
            }
        }
        return null;
    }

    @Override
    public boolean remover(Integer id) throws Exception {

        if (id != null) {
            PreparedStatement preparedStatement = BancoDeDados.conectar().prepareStatement("DELETE FROM usuario WHERE id_usuario=?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            return Boolean.TRUE;
        }

        return false;
    }


}
