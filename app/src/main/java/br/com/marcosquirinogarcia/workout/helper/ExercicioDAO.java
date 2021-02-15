package br.com.marcosquirinogarcia.workout.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.marcosquirinogarcia.workout.model.Exercicio;

public class ExercicioDAO implements IExercicioDAO {

    private SQLiteDatabase escreve;
    private SQLiteDatabase le;

    public ExercicioDAO(Context context) {
        DbHelper db = new DbHelper(context);
        escreve = db.getWritableDatabase();
        le = db.getReadableDatabase();
    }

    @Override
    public boolean salvar(Exercicio exercicio) {

        ContentValues cv = new ContentValues();
        cv.put("nome_exercicio", exercicio.getNomeExercicio());
        cv.put("num_series", exercicio.getNumSeries());
        cv.put("num_repeticoes", exercicio.getNumRepeticoes());
        cv.put("peso", exercicio.getPeso());
        cv.put("nome_treino", exercicio.getNomeTreino());

        try {
            escreve.insert(DbHelper.TABELA_EXERCICIOS, null, cv);
        }catch (Exception e){
            Log.e("INFO", "Erro ao salvar exercicio " + e.getMessage());

        return false;
        }

        return true;
    }

    @Override
    public boolean atualizar(Exercicio exercicio) {

        ContentValues cv = new ContentValues();
        cv.put("nome_treino", exercicio.getNomeTreino());
        cv.put("nome_exercicio", exercicio.getNomeExercicio());
        cv.put("num_repeticoes", exercicio.getNumRepeticoes());
        cv.put("num_series", exercicio.getNumSeries());
        cv.put("peso", exercicio.getPeso());


        try {
            String [] args = {exercicio.getId().toString()};
            escreve.update(DbHelper.TABELA_EXERCICIOS, cv, "id=?", args);

            Log.i("INFO", "Exercicio modificado");

        }catch (Exception e){
            Log.e("INFO", "Erro ao modificar exercicio " + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public boolean deletar(Exercicio exercicio) {

        try {
            String [] args = {exercicio.getId().toString()};
            escreve.delete(DbHelper.TABELA_EXERCICIOS, "id=?", args);
            Log.i("INFO", "Exercicio excluido");
        }catch (Exception e){
            Log.e("INFO", "Erro ao excluir exercicio " + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public List<Exercicio> listar(String nomeTreinoSelecionado) {

        List<Exercicio> exercicios = new ArrayList<>();

        String sql = "SELECT * FROM exercicios WHERE exercicios.nome_treino = '" + nomeTreinoSelecionado + "' ;";

        Cursor c = le.rawQuery(sql, null);

        while (c.moveToNext()){

            Exercicio exercicio = new Exercicio();

            Long id = c.getLong(c.getColumnIndex("id"));
            String nomeTreino = c.getString(c.getColumnIndex("nome_treino"));
            String nomeExercicio = c.getString(c.getColumnIndex("nome_exercicio"));
            int numSeries = c.getInt(c.getColumnIndex("num_series"));
            int numRepeticoes = c.getInt(c.getColumnIndex("num_repeticoes"));
            int peso = c.getInt(c.getColumnIndex("peso"));

            exercicio.setId(id);
            exercicio.setNomeTreino(nomeTreino);
            exercicio.setNomeExercicio(nomeExercicio);
            exercicio.setNumSeries(numSeries);
            exercicio.setNumRepeticoes(numRepeticoes);
            exercicio.setPeso(peso);

            exercicios.add(exercicio);
        }

        return exercicios;
    }
}
