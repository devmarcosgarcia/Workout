package br.com.marcosquirinogarcia.workout.helper;

import java.util.List;

import br.com.marcosquirinogarcia.workout.model.Treino;

public interface ITreinoDAO {

    public boolean salvar (Treino treino);
    public boolean atualizar (Treino treino);
    public boolean deletar (Treino treino);
    public List<Treino> listar();
}
