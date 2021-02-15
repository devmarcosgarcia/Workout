package br.com.marcosquirinogarcia.workout.model;

import java.io.Serializable;

public class Exercicio implements Serializable {

    private Long id;
    private String nomeExercicio;
    private String nomeTreino;
    private int numSeries;
    private int numRepeticoes;
    private int peso;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeExercicio() {
        return nomeExercicio;
    }

    public void setNomeExercicio(String nomeExercicio) {
        this.nomeExercicio = nomeExercicio;
    }

    public String getNomeTreino() {
        return nomeTreino;
    }

    public void setNomeTreino(String nomeTreino) {
        this.nomeTreino = nomeTreino;
    }

    public int getNumSeries() {
        return numSeries;
    }

    public void setNumSeries(int numSeries) {
        this.numSeries = numSeries;
    }

    public int getNumRepeticoes() {
        return numRepeticoes;
    }

    public void setNumRepeticoes(int numRepeticoes) {
        this.numRepeticoes = numRepeticoes;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }
}
