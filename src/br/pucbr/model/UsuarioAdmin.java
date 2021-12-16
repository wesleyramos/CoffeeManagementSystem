package br.pucbr.model;

public class UsuarioAdmin extends Usuario {

    public UsuarioAdmin(int id, String nome, String usuario, String senha, Credito credito) {
        super(id, nome, usuario, senha, credito);
    }

    public UsuarioAdmin(String nome, String usuario, String senha, Credito credito) {
        super(nome, usuario, senha, credito, 1);
    }

    public UsuarioAdmin(int id, String nome, String usuario, String senha, Credito credito, int tipo) {
        super(id, nome, usuario, senha, credito, tipo);

    }

}
