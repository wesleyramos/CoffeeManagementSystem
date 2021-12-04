package br.pucbr.model;

public class UsuarioMensal extends Usuario {

    private static Double desconto = 0.10;

    public UsuarioMensal(int id, String nome, String usuario, String senha) {
        super(id, nome, usuario, senha);
    }
}
