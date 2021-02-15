package br.com.marcosquirinogarcia.workout.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import br.com.marcosquirinogarcia.workout.R;
import br.com.marcosquirinogarcia.workout.helper.TreinoDAO;
import br.com.marcosquirinogarcia.workout.model.Treino;

public class AddTreinoActivity extends AppCompatActivity {

    private TextInputEditText textAdicionarTreino;
    private Treino treinoAtual;
    private TextView textViewTitulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_treino);

        Toolbar toolbar = findViewById(R.id.toolbar_treinos);
        toolbar.setTitle(R.string.toobar_title_treinos);
        setSupportActionBar(toolbar);

        textAdicionarTreino = findViewById(R.id.editTextNomeTreino);
        textViewTitulo = findViewById(R.id.textView_titulo_pagina_add_edit_treino);

        //Recupera treino para edição
        treinoAtual = (Treino) getIntent().getSerializableExtra("treinoSelecionado");

        //Configura treino na caixa de texto
        if (treinoAtual != null) {

            textViewTitulo.setText("Alterar nome treino:");

            textAdicionarTreino.setText(treinoAtual.getNomeTreino());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_add_edit_treino, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.menu_salvar_treino) {

            TreinoDAO treinoDAO = new TreinoDAO(getApplicationContext());

            String nomeTreino = textAdicionarTreino.getText().toString();

            if (treinoAtual != null) { // treino para edicao

                if (!nomeTreino.isEmpty()){

                    Treino treino = new Treino();
                    treino.setNomeTreino(nomeTreino);
                    treino.setId(treinoAtual.getId());

                    //atualiza no banco de dados
                    if (treinoDAO.atualizar(treino)) {
                        finish();
                        Toast.makeText(this, "Treino modificado!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Erro ao modificar treino", Toast.LENGTH_SHORT).show();
                    }

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }

            } else { // salvar treino

                //Validação do treino
                if (!nomeTreino.isEmpty()) {

                    Treino treino = new Treino();
                    treino.setNomeTreino(nomeTreino);

                    if (treinoDAO.salvar(treino)) {
                        finish();
                        Toast.makeText(this, "Treino salvo!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Erro ao salvar treino", Toast.LENGTH_SHORT).show();

                    }
                }

            }

        }
        return super.onOptionsItemSelected(item);
    }
}
