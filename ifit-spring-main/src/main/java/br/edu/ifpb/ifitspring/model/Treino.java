package br.edu.ifpb.ifitspring.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Treino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeTreino;

    @ElementCollection
    private List<Long> exercicios;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private LocalDate dataICriacao;

    // Getters e Setters
    public Long getId() { return id; }

    public String getNomeTreino() { return nomeTreino; }

    public void setNomeTreino(String nomeTreino) { this.nomeTreino = nomeTreino; }

    public List<Long> getExercicios() { return exercicios; }

    public void setExercicios(List<Long> exercicios) { this.exercicios = exercicios; }

    public Usuario getUsuario() { return usuario; }

    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public LocalDate getDataICriacao() { return dataICriacao; }

    public void setDataICriacao(LocalDate dataICriacao) { this.dataICriacao = dataICriacao; }
}
