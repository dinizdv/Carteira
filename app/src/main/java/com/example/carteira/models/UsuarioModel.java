package com.example.carteira.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

public class UsuarioModel implements Serializable {
    private Long id;
    private String nome, matricula, curso, cpf, email, nivel, foto, role;
    private LocalDate data_nascimento;

    public UsuarioModel(Long id, LocalDate data_nascimento, String nome, String matricula, String curso, String cpf, String email, String foto, String nivel, String role) {
        this.id = id;
        this.data_nascimento = data_nascimento;
        this.nome = nome;
        this.matricula = matricula;
        this.curso = curso;
        this.cpf = cpf;
        this.email = email;
        this.foto = foto;
        this.nivel = nivel;
        this.role = role;
    }

    public UsuarioModel(LocalDate data_nascimento, String nome, String matricula, String curso, String cpf, String email, String foto, String nivel, String role) {
        this.data_nascimento = data_nascimento;
        this.nome = nome;
        this.matricula = matricula;
        this.curso = curso;
        this.cpf = cpf;
        this.email = email;
        this.foto = foto;
        this.nivel = nivel;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getMatricula() {
        return matricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCurso() {
        return curso;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFotoBytes() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getNivel() {
        return nivel;
    }

    public String getRole() {
        return role;
    }

    public LocalDate getData_nascimento() {
        return data_nascimento;
    }

    @Override
    public String toString() {
        return "UsuarioModel{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", matricula='" + matricula + '\'' +
                ", curso='" + curso + '\'' +
                ", cpf='" + cpf + '\'' +
                ", email='" + email + '\'' +
                ", nivel='" + nivel + '\'' +
                ", role='" + role + '\'' +
                ", data_nascimento=" + data_nascimento +
                '}';
    }
}
