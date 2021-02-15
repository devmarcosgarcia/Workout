package br.com.marcosquirinogarcia.workout.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    public static int VERSION = 1;
    public static String NOME_DB = "DB_TAREFAS";
    public static String TABELA_TREINO = "treinos";
    public static String TABELA_EXERCICIOS = "exercicios";

    public DbHelper(@Nullable Context context) {
        super(context, NOME_DB, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sqlTabelaTreino = "CREATE TABLE IF NOT EXISTS " + TABELA_TREINO
                + " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " nome_treino TEXT NOT NULL ); ";

        String sqlTabelaExercicio = "CREATE TABLE IF NOT EXISTS " + TABELA_EXERCICIOS
                + " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " nome_exercicio TEXT NOT NULL," +
                "num_series INTEGER, " +
                "num_repeticoes INTEGER," +
                "peso INTEGER, " +
                "nome_treino TEXT NOT NULL) ;";


        try {
            db.execSQL(sqlTabelaTreino);
            Log.i("INFO DB", "Tabela treino criada");
        }catch (Exception e){
            Log.i("INFO DB", "Erro ao criar tabela treino" + e.getMessage());
        }

        try {
            db.execSQL(sqlTabelaExercicio);
            Log.i("INFO DB", "Tabela exercicio criada");
        }catch (Exception e){
            Log.i("INFO DB", "Erro ao criar tabela exercicio" + e.getMessage());
        }

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()){
            db.execSQL("PRAGMA foreign_keys = ON;");
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
