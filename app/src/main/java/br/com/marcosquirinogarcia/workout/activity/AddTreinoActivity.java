package br.com.marcosquirinogarcia.workout.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;

import br.com.marcosquirinogarcia.workout.R;
import br.com.marcosquirinogarcia.workout.helper.TreinoDAO;
import br.com.marcosquirinogarcia.workout.model.Treino;

public class AddTreinoActivity extends AppCompatActivity {

    private TextInputEditText textAdicionarTreino;
    private Treino treinoAtual;
    private TextView textViewTitulo;
    private Button buttonSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_treino);

        Toolbar toolbar = findViewById(R.id.toolbar_treinos);
        toolbar.setTitle(R.string.toobar_title_treinos);
        setSupportActionBar(toolbar);

        textAdicionarTreino = findViewById(R.id.editTextNomeTreino);
        textViewTitulo = findViewById(R.id.textView_titulo_pagina_add_edit_treino);
        buttonSalvar = findViewById(R.id.button_add_treino_salvar);

        //Recupera treino para edição
        treinoAtual = (Treino) getIntent().getSerializableExtra("treinoSelecionado");

        //Configura treino na caixa de texto
        if (treinoAtual != null) {

            textViewTitulo.setText("Alterar nome treino:");

            textAdicionarTreino.setText(treinoAtual.getNomeTreino());
        }

        buttonSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TreinoDAO treinoDAO = new TreinoDAO(getApplicationContext());

                String nomeTreino = textAdicionarTreino.getText().toString();

                if (treinoAtual != null) { // treino para edicao

                    if (!nomeTreino.isEmpty()) {

                        Treino treino = new Treino();
                        treino.setNomeTreino(nomeTreino);
                        treino.setId(treinoAtual.getId());

                        //atualiza no banco de dados
                        if (treinoDAO.atualizar(treino)) {
                            finish();
                            Toast.makeText(AddTreinoActivity.this, "Treino modificado!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AddTreinoActivity.this, "Erro ao modificar treino", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(AddTreinoActivity.this, "Treino salvo!", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(AddTreinoActivity.this, "Erro ao salvar treino", Toast.LENGTH_SHORT).show();

                        }
                    }

                }


            }
        });
    }
}
