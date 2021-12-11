package br.pucbr.model;

public abstract class Usuario {

    private int id;
    private String nome;
    private String usuario;
    private String senha;
    private Credito credito;

    public int getId() {
        return id;
    }

    public Credito getCredito() {
        return credito;
    }

    public Usuario(int id, String nome, String usuario, String senha, Credito credito) {
        this.id = id;
        this.nome = nome;
        this.usuario = usuario;
        this.senha = senha;
        this.credito = credito;
    }

    public String getNome() {
        return nome;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getSenha() {
        return senha;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", usuario='" + usuario + '\'' +
                ", senha='" + senha + '\'' +
                '}';
    }

    public void comprar() {

    }

}
