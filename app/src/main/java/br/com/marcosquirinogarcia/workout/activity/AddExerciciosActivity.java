package br.com.marcosquirinogarcia.workout.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import br.com.marcosquirinogarcia.workout.R;
import br.com.marcosquirinogarcia.workout.helper.ExercicioDAO;
import br.com.marcosquirinogarcia.workout.model.Exercicio;
import br.com.marcosquirinogarcia.workout.model.Treino;

public class AddExerciciosActivity extends AppCompatActivity {

    private TextView textViewTreinoSelecionado;
    private TextView textViewTituloPagina;
    private Treino treinoSelecionado;
    private Exercicio exercicioSelecionado;
    private Button buttonAddExercicio;

    private TextInputEditText editTextNomeExercicio;
    private TextInputEditText editTextNumSeries;
    private TextInputEditText editTextNumRepeticoes;
    private TextInputEditText editTextPeso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercicios);

        textViewTreinoSelecionado = findViewById(R.id.textView_nome_treino_selecionado);
        textViewTituloPagina = findViewById(R.id.textViewAddExercicio);

        editTextNomeExercicio = findViewById(R.id.editText_nome_exercicio);
        editTextNumSeries = findViewById(R.id.editText_num_series);
        editTextNumRepeticoes = findViewById(R.id.editText_num_repeticoes);
        editTextPeso = findViewById(R.id.editText_qtd_peso);
        buttonAddExercicio = findViewById(R.id.button_add_exercicio);

        //Recupera treino
        treinoSelecionado = (Treino) getIntent().getSerializableExtra("treinoSelecionado");

        //Configura treino na caixa de texto
        if (treinoSelecionado != null) {
            textViewTreinoSelecionado.setText(treinoSelecionado.getNomeTreino());
        }


        //Recupera exercicio para edição
        exercicioSelecionado = (Exercicio) getIntent().getSerializableExtra("exercicioSelecionado");

        if (exercicioSelecionado != null){
            textViewTituloPagina.setText("Editar Exercicio");
            textViewTreinoSelecionado.setVisibility(View.INVISIBLE);
            buttonAddExercicio.setText(R.string.button_Salvar);

            editTextNomeExercicio.setText(exercicioSelecionado.getNomeExercicio());
            editTextNumSeries.setText(String.valueOf(exercicioSelecionado.getNumSeries()));
           editTextNumRepeticoes.setText(String.valueOf(exercicioSelecionado.getNumRepeticoes()));
            editTextPeso.setText(String.valueOf(exercicioSelecionado.getPeso()));


        }


        buttonAddExercicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ExercicioDAO exercicioDAO = new ExercicioDAO(getApplicationContext());

                String nomeExercicio = editTextNomeExercicio.getText().toString();

                if (exercicioSelecionado != null){
                    //edicao do exercicio

                    if (!nomeExercicio.isEmpty()){

                        Exercicio exercicio = new Exercicio();

                        exercicio.setId(exercicioSelecionado.getId());
                        exercicio.setNomeExercicio(editTextNomeExercicio.getText().toString());
                        exercicio.setNomeTreino(exercicioSelecionado.getNomeTreino());
                        exercicio.setNumSeries((int) Integer.parseInt(editTextNumSeries.getText().toString()));
                        exercicio.setNumRepeticoes((int) Integer.parseInt(editTextNumRepeticoes.getText().toString()));
                        exercicio.setPeso((int) Integer.parseInt(editTextPeso.getText().toString()));

                        //atualiza o banco de dados
                        if (exercicioDAO.atualizar(exercicio)){
                            finish();
                            Toast.makeText(AddExerciciosActivity.this, "Exercicio atualizado!", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(AddExerciciosActivity.this, "Não foi possivel atualizar exercicio!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }else {
                    //salvar novo exercicio
                    if (!nomeExercicio.isEmpty()){

                        Exercicio exercicio = new Exercicio();

                        exercicio.setNomeExercicio(editTextNomeExercicio.getText().toString());
                        exercicio.setNomeTreino(textViewTreinoSelecionado.getText().toString());
                        exercicio.setNumSeries((int) Integer.parseInt(editTextNumSeries.getText().toString()));
                        exercicio.setNumRepeticoes((int) Integer.parseInt(editTextNumRepeticoes.getText().toString()));
                        exercicio.setPeso((int) Integer.parseInt(editTextPeso.getText().toString()));

                        if (exercicioDAO.salvar(exercicio)){
                            finish();
                            Toast.makeText(AddExerciciosActivity.this, "Exercicio salvo!", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(AddExerciciosActivity.this, "Não foi possivel salvar exercicio!", Toast.LENGTH_SHORT).show();
                        }

                    }
                }

            }
        });
    }
}