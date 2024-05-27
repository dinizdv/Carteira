package com.example.carteira.models;

public class Notificacao {

    private String id, mensagem, titulo;

    public Notificacao(String id, String titulo, String mensagem) {
        this.id = id;
        this.titulo = titulo;
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }

    public String getId() {
        return id;
    }

    public String getTitulo() { return titulo; }

    @Override
    public String toString() {
        return getMensagem();
    }
}
