package br.pucbr.model;

public class UsuarioMensal extends Usuario {

    private static Double desconto = 0.10;




    public static Double getDesconto() {
        return desconto;
    }

    public UsuarioMensal(int id, String nome, String usuario, String senha, Credito credito) {
        super(id, nome, usuario, senha, credito);
    }
}
