package br.com.marcosquirinogarcia.workout.model;

import java.io.Serializable;

public class Treino implements Serializable {

    private Long id;
    private String nomeTreino;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeTreino() {
        return nomeTreino;
    }

    public void setNomeTreino(String nomeTreino) {
        this.nomeTreino = nomeTreino;
    }
}
