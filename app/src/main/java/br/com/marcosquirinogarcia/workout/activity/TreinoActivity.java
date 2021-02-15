package br.com.marcosquirinogarcia.workout.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.marcosquirinogarcia.workout.R;
import br.com.marcosquirinogarcia.workout.adapter.ExercicioAdapter;
import br.com.marcosquirinogarcia.workout.adapter.TreinoAdapter;
import br.com.marcosquirinogarcia.workout.helper.ExercicioDAO;
import br.com.marcosquirinogarcia.workout.helper.RecyclerItemClickListener;
import br.com.marcosquirinogarcia.workout.helper.TreinoDAO;
import br.com.marcosquirinogarcia.workout.model.Exercicio;
import br.com.marcosquirinogarcia.workout.model.Treino;

public class TreinoActivity extends AppCompatActivity {

    private TextView textViewNomeTreino;
    private Treino treinoAtual;
    private RecyclerView recyclerViewExercicios;
    private ExercicioAdapter exercicioAdapter;
    private Exercicio exercicioSelecionado;
    private List<Exercicio> listExercicios = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treino);

        Toolbar toolbar = findViewById(R.id.toolbar_treino_selecionado);
        toolbar.setTitle(R.string.toobar_title_treinos);
        setSupportActionBar(toolbar);

        textViewNomeTreino = findViewById(R.id.textView_treinos_nome_treino);

        //Recupera treino selecionado
        treinoAtual = (Treino) getIntent().getSerializableExtra("treinoSelecionado");


        //Configura treino na caixa de texto
        if (treinoAtual != null) {
            textViewNomeTreino.setText(treinoAtual.getNomeTreino());
        }

        recyclerViewExercicios = findViewById(R.id.recyclerViewExercicios);

        recyclerViewExercicios.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), recyclerViewExercicios,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                                //Recupera exercicio para edição
                                Exercicio exercicioSelecionado = listExercicios.get(position);

                                //Envia exercicio selecionado para a tela de adicionar exercicio para edição
                                Intent intent = new Intent(TreinoActivity.this, AddExerciciosActivity.class);
                                intent.putExtra("exercicioSelecionado", exercicioSelecionado);
                                startActivity(intent);
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                                AlertDialog.Builder dialog = new AlertDialog.Builder(TreinoActivity.this);

                                exercicioSelecionado = listExercicios.get(position);

                                //Título e mensagem do alertDialog
                                dialog.setTitle("Confirmar Exclusão");
                                dialog.setMessage("Deseja excluir o exercicio " + exercicioSelecionado.getNomeExercicio() + " ?");

                                dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        ExercicioDAO exercicioDAO = new ExercicioDAO(getApplicationContext());

                                        if (exercicioDAO.deletar(exercicioSelecionado)){
                                            Toast.makeText(TreinoActivity.this, "exercicio excluido", Toast.LENGTH_SHORT).show();
                                        }else {
                                            Toast.makeText(TreinoActivity.this, "Erro ao excluir exercicio", Toast.LENGTH_SHORT).show();
                                        }

                                       carregarListaExercicios();

                                    }
                                });

                                dialog.setNegativeButton("Não", null); // nada será feito, por isso foi usado null

                                //Exibe a dialog
                                dialog.create();
                                dialog.show();
                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        })
        );



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_treino_selecionado, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_editar_treino:

                //Recupera treino para edição
                Treino treinoSelecionado = treinoAtual;

                //Envia treino selecionado para a tela de adicionar treino para edição
                Intent intent = new Intent(TreinoActivity.this, AddTreinoActivity.class);
                intent.putExtra("treinoSelecionado", treinoSelecionado);
                startActivity(intent);

                break;

            case R.id.menu_deletar_treino:

                AlertDialog.Builder dialog = new AlertDialog.Builder(TreinoActivity.this);

                //Título e mensagem do alertDialog
                dialog.setTitle("Confirmar Exclusão");
                dialog.setMessage("Deseja excluir a tarefa " + treinoAtual.getNomeTreino() + " ?");

                dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        TreinoDAO treinoDAO = new TreinoDAO(getApplicationContext());

                        if (treinoDAO.deletar(treinoAtual)){
                            Toast.makeText(TreinoActivity.this, "Treino excluido", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(TreinoActivity.this, "Erro ao excluir treino", Toast.LENGTH_SHORT).show();
                        }

                        Intent intent = new Intent(TreinoActivity.this, MainActivity.class);
                        startActivity(intent);

                    }
                });

                dialog.setNegativeButton("Não", null); // nada será feito, por isso foi usado null

                //Exibe a dialog
                dialog.create();
                dialog.show();

                break;

            case R.id.menu_adicionar_exercicios:

                treinoSelecionado = treinoAtual;

                Intent intentTreino = new Intent(TreinoActivity.this, AddExerciciosActivity.class);
                intentTreino.putExtra("treinoSelecionado", treinoSelecionado);
                startActivity(intentTreino);

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void carregarListaExercicios(){

        // Listar exercicios
        ExercicioDAO exercicioDAO = new ExercicioDAO(getApplicationContext());
        listExercicios = exercicioDAO.listar(treinoAtual.getNomeTreino());

        // Configurações adapter
        exercicioAdapter = new ExercicioAdapter(listExercicios);

        // Configuração recyclerView

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewExercicios.setLayoutManager(layoutManager);
        recyclerViewExercicios.setHasFixedSize(true);
        recyclerViewExercicios.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        recyclerViewExercicios.setAdapter(exercicioAdapter);

    }


    @Override
    protected void onStart() {
        carregarListaExercicios();
        super.onStart();
    }
}