package br.com.marcosquirinogarcia.workout.helper;

import java.util.List;

import br.com.marcosquirinogarcia.workout.model.Exercicio;

public interface IExercicioDAO {

    public boolean salvar(Exercicio exercicio);

    public boolean atualizar(Exercicio exercicio);

    public boolean deletar(Exercicio exercicio);

    public List<Exercicio> listar(String nomeTreinoSelecionado);
}

