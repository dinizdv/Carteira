package com.example.carteira.models;

public class Notificacao {

    private String id;
    private String mensagem;

    public Notificacao(String id, String mensagem) {
        this.id = id;
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }

    public String getId() {
        return id;
    }
}
