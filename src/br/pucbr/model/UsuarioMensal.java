package br.pucbr.model;

public class UsuarioMensal extends Usuario {

    private static Double desconto = 0.10;

    public UsuarioMensal(int id, String nome, String usuario, String senha, Credito credito) {
        super(id, nome, usuario, senha, credito);
    }

    public UsuarioMensal(String nome, String senha, Credito credito) {
        super(nome, senha, credito, 1);
    }

    public UsuarioMensal(String nome, String usuario, String senha, Credito credito) {
        super(nome, usuario, senha, credito, 1);
    }

    public UsuarioMensal(int id, String nome, String senha, Credito credito) {
        super(id, nome, senha, credito);
    }

    public UsuarioMensal(int id, String nome, String usuario, String senha, Credito credito, int tipo) {
        super(id, nome, usuario, senha, credito, tipo);

    }

    public static Double getDesconto() {
        return desconto;
    }

}
