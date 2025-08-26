package br.edu.ifpb.ifitspring.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import jakarta.persistence.*;

import br.edu.ifpb.ifitspring.enums.Role;

@Entity
public class Usuario {
    @Id @GeneratedValue
    private Long id;

    private String nome;
    private String email;
    private String senha;

    @Enumerated(EnumType.STRING)
    private Role role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Role getRole() {
        return role;
    }
}
