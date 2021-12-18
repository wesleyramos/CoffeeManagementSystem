package br.pucbr.utils;

import br.pucbr.model.Usuario;
import br.pucbr.model.dao.UsuarioDAO;

public class Login {

    private static UsuarioDAO usuarioDAO = new UsuarioDAO();
    public static Usuario efetuarLogin(String usuario, String senha) {

        Usuario usuarioVerificado = usuarioDAO.buscarPorUsuario(usuario);
        if (null == usuarioVerificado) {
            System.out.println("Usuário não encontrado!");
        } else {
            if (usuarioVerificado.getSenha().equals(HashMd5.gerarHashMd5(senha))) {
                System.out.println("Login com sucesso!");
                return usuarioVerificado;
            }
        }
        return null;
    }

}
