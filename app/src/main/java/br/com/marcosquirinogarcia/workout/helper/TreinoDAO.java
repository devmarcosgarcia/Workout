package br.com.marcosquirinogarcia.workout.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.marcosquirinogarcia.workout.model.Exercicio;
import br.com.marcosquirinogarcia.workout.model.Treino;

public class TreinoDAO implements ITreinoDAO{

    private SQLiteDatabase escreve;
    private SQLiteDatabase le;

    public TreinoDAO(Context context) {
        DbHelper db = new DbHelper(context);
        escreve = db.getWritableDatabase();
        le = db.getReadableDatabase();
    }

    @Override
    public boolean salvar(Treino treino) {
        ContentValues cv = new ContentValues();
        cv.put("nome_treino", treino.getNomeTreino());

        try {
            escreve.insert(DbHelper.TABELA_TREINO, null, cv);
        }catch (Exception e){
            Log.e("INFO", "Erro ao salvar treino " + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public boolean atualizar(Treino treino) {

        ContentValues cv = new ContentValues();
        cv.put("nome_treino", treino.getNomeTreino());


        try {
            String [] argsTreino = {treino.getId().toString()};
            escreve.update(DbHelper.TABELA_TREINO, cv, "id=?", argsTreino);

            Log.i("INFO", "Treino modificado");

        }catch (Exception e){
            Log.e("INFO", "Erro ao modificar treino " + e.getMessage());
            return false;
        }

        return true;

    }

    @Override
    public boolean deletar(Treino treino) {

        try {
            String [] args = {treino.getId().toString()};
            escreve.delete(DbHelper.TABELA_TREINO, "id=?", args);
            Log.i("INFO", "Treino excluido");
        }catch (Exception e){
            Log.e("INFO", "Erro ao excluir treino " + e.getMessage());
            return false;
        }

        return true;


    }

    @Override
    public List<Treino> listar() {

        List<Treino> treinos = new ArrayList<>();

        String sql = "SELECT * FROM " + DbHelper.TABELA_TREINO + " ;";
        Cursor c = le.rawQuery(sql, null);

        while (c.moveToNext()){

            Treino treino = new Treino();

            Long id = c.getLong(c.getColumnIndex("id"));
            String nomeTreino = c.getString(c.getColumnIndex("nome_treino"));

            treino.setId(id);
            treino.setNomeTreino(nomeTreino);

            treinos.add(treino);
        }

        return treinos;
    }
}
