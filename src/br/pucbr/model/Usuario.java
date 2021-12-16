package br.pucbr.model;

public abstract class Usuario {

    private int id;
    private String nome;
    private String usuario;
    private String senha;
    private Credito credito;
    private int tipo; //TODO Enum para o tipo

    public Usuario(int id, String nome, String usuario, String senha, Credito credito) {
        this.id = id;
        this.nome = nome;
        this.usuario = usuario;
        this.senha = senha;
        this.credito = credito;
    }

    public Usuario(int id, String nome, String usuario, String senha, Credito credito, int tipo) {
        this.id = id;
        this.nome = nome;
        this.usuario = usuario;
        this.senha = senha;
        this.credito = credito;
        this.tipo = tipo;
    }

    public Usuario(String _nome, String _usuario, String _senha, Credito _credito, int _tipo) {
        this.nome = _nome;
        this.senha = _senha;
        this.credito = _credito;
        this.tipo = _tipo;
        this.usuario = _usuario;
    }

    public Usuario(String _nome, String _senha, Credito _credito, int _tipo) {
        this.nome = _nome;
        this.senha = _senha;
        this.credito = _credito;
        this.tipo = _tipo;
    }

    public Usuario(int id, String nome, String senha, Credito credito) {
        this.id = id;
        this.nome = nome;
        this.senha = senha;
        this.credito = credito;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Credito getCredito() {
        return credito;
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

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", usuario='" + usuario + '\'' +
                ", senha='" + senha + '\'' +
                ", credito=" + credito +
                ", tipo=" + tipo +
                '}';
    }

    public void comprar() {

    }

}
