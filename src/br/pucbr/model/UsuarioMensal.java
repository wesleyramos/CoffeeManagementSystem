package br.pucbr.model;

public class UsuarioMensal extends Usuario {

    private Double desconto;

    public UsuarioMensal(int id, String nome, String usuario, String senha) {
        super(id, nome, usuario, senha);
    }
}
