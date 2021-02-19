package br.com.marcosquirinogarcia.workout.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

    private EditText editTextNomeExercicio;
    private TextView TextNumSeries;
    private TextView TextNumRepeticoes;
    private TextView TextPeso;

    private Button btnRemoveSeries;
    private Button btnAddSeries;
    private Button btnRemoveRepeticoes;
    private Button btnAddRepeticoes;
    private Button btnRemovePeso;
    private Button btnAddPeso;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercicios);

        textViewTreinoSelecionado = findViewById(R.id.textView_nome_treino_selecionado);
        textViewTituloPagina = findViewById(R.id.textViewAddExercicio);

        editTextNomeExercicio = findViewById(R.id.edit_nome_exercicio);
        TextNumSeries = findViewById(R.id.tv_num_series);
        TextNumRepeticoes = findViewById(R.id.tv_num_repeticoes);
        TextPeso = findViewById(R.id.tv_qtd_peso);
        buttonAddExercicio = findViewById(R.id.btn_add_exercicio);
        btnRemoveSeries = findViewById(R.id.btn_remove_series);
        btnAddSeries = findViewById(R.id.btn_add_series);
        btnRemoveRepeticoes = findViewById(R.id.btn_remove_repeticoes);
        btnAddRepeticoes = findViewById(R.id.btn_add_repeticoes);
        btnRemovePeso = findViewById(R.id.btn_remove_peso);
        btnAddPeso = findViewById(R.id.btn_add_peso);

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
            textViewTreinoSelecionado.setVisibility(View.GONE);
            buttonAddExercicio.setText(R.string.button_Salvar);

            editTextNomeExercicio.setText(exercicioSelecionado.getNomeExercicio());
            TextNumSeries.setText(String.valueOf(exercicioSelecionado.getNumSeries()));
           TextNumRepeticoes.setText(String.valueOf(exercicioSelecionado.getNumRepeticoes()));
            TextPeso.setText(String.valueOf(exercicioSelecionado.getPeso()));


        }


        btnRemoveSeries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int numSeriesAtual = Integer.parseInt(TextNumSeries.getText().toString());

                if (numSeriesAtual > 0){
                    numSeriesAtual --;
                }
                TextNumSeries.setText(String.valueOf(numSeriesAtual));
            }
        });

        btnAddSeries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int numSeriesAtual = Integer.parseInt(TextNumSeries.getText().toString());

                if (numSeriesAtual <= 100){
                    numSeriesAtual ++;
                }
                TextNumSeries.setText(String.valueOf(numSeriesAtual));
            }
        });

        btnRemoveRepeticoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int numRepeticoesAtual = Integer.parseInt(TextNumRepeticoes.getText().toString());

                if (numRepeticoesAtual > 0){
                    numRepeticoesAtual --;
                }
                TextNumRepeticoes.setText(String.valueOf(numRepeticoesAtual));
            }
        });

        btnAddRepeticoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int numRepeticoesAtual = Integer.parseInt(TextNumRepeticoes.getText().toString());

                if (numRepeticoesAtual <= 100){
                    numRepeticoesAtual ++;
                }
                TextNumRepeticoes.setText(String.valueOf(numRepeticoesAtual));
            }
        });

        btnRemovePeso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pesoAtual = Integer.parseInt(TextPeso.getText().toString());

                if (pesoAtual > 0){
                    pesoAtual --;
                }
                TextPeso.setText(String.valueOf(pesoAtual));
            }
        });

        btnAddPeso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pesoAtual = Integer.parseInt(TextPeso.getText().toString());

                if (pesoAtual <= 100){
                    pesoAtual ++;
                }
                TextPeso.setText(String.valueOf(pesoAtual));
            }
        });


        buttonAddExercicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ExercicioDAO exercicioDAO = new ExercicioDAO(getApplicationContext());

                String nomeExercicio = editTextNomeExercicio.getText().toString();
                String numSeries = TextNumSeries.getText().toString();
                String numRepeticoes = TextNumRepeticoes.getText().toString();
                String peso = TextPeso.getText().toString();

                if (exercicioSelecionado != null){
                    //edicao do exercicio

                    if (!nomeExercicio.isEmpty() && !numSeries.isEmpty() && !numRepeticoes.isEmpty() && !peso.isEmpty() ){

                        Exercicio exercicio = new Exercicio();

                        exercicio.setId(exercicioSelecionado.getId());
                        exercicio.setNomeExercicio(editTextNomeExercicio.getText().toString());
                        exercicio.setNomeTreino(exercicioSelecionado.getNomeTreino());
                        exercicio.setNumSeries((int) Integer.parseInt(TextNumSeries.getText().toString()));
                        exercicio.setNumRepeticoes((int) Integer.parseInt(TextNumRepeticoes.getText().toString()));
                        exercicio.setPeso((int) Integer.parseInt(TextPeso.getText().toString()));

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
                    if (!nomeExercicio.isEmpty() && !numSeries.isEmpty() && !numRepeticoes.isEmpty() && !peso.isEmpty() ){

                        Exercicio exercicio = new Exercicio();

                        exercicio.setNomeExercicio(editTextNomeExercicio.getText().toString());
                        exercicio.setNomeTreino(textViewTreinoSelecionado.getText().toString());
                        exercicio.setNumSeries((int) Integer.parseInt(TextNumSeries.getText().toString()));
                        exercicio.setNumRepeticoes((int) Integer.parseInt(TextNumRepeticoes.getText().toString()));
                        exercicio.setPeso((int) Integer.parseInt(TextPeso.getText().toString()));

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