package br.com.marcosquirinogarcia.workout.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import br.com.marcosquirinogarcia.workout.R;
import br.com.marcosquirinogarcia.workout.adapter.TreinoAdapter;
import br.com.marcosquirinogarcia.workout.helper.ExercicioDAO;
import br.com.marcosquirinogarcia.workout.helper.RecyclerItemClickListener;
import br.com.marcosquirinogarcia.workout.helper.TreinoDAO;
import br.com.marcosquirinogarcia.workout.model.Treino;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fabAdicionarTreino;
    private RecyclerView recyclerViewTreinos;
    private TreinoAdapter treinoAdapter;
    private List<Treino> listTreinos = new ArrayList<>();
    private Treino treinoSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);

        // FAB add Treino
        fabAdicionarTreino = findViewById(R.id.fab_add_treinos);
        fabAdicionarTreino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddTreinoActivity.class);
                startActivity(intent);
            }
        });

        // RecyclerView
        recyclerViewTreinos = findViewById(R.id.recyclerView_treinos);

        //Evento de click RecyclerView
        recyclerViewTreinos.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(),
                recyclerViewTreinos, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //Recupera treino para edição
                Treino treinoSelecionado = listTreinos.get(position);

                //Envia treino selecionado para a tela de adicionar treino para edição
                Intent intent = new Intent(MainActivity.this, TreinoActivity.class);
                intent.putExtra("treinoSelecionado", treinoSelecionado);
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);

                treinoSelecionado = listTreinos.get(position);

                //Título e mensagem do alertDialog
                dialog.setTitle("Confirmar Exclusão");
                dialog.setMessage("Deseja excluir o treino " + treinoSelecionado.getNomeTreino() + " ?");

                dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        TreinoDAO treinoDAO = new TreinoDAO(getApplicationContext());

                        if (treinoDAO.deletar(treinoSelecionado)){
                            Toast.makeText(MainActivity.this, "treino excluido", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(MainActivity.this, "Erro ao excluir treino", Toast.LENGTH_SHORT).show();
                        }

                        carregarListaTreinos();

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
        }));

    }

    public void carregarListaTreinos(){

        //Listar tarefas
        TreinoDAO treinoDAO = new TreinoDAO(getApplicationContext());
        listTreinos = treinoDAO.listar();

        //Configuração adapter
         treinoAdapter = new TreinoAdapter(listTreinos); // recebe lista

        //Configuração recyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewTreinos.setLayoutManager(layoutManager);
        recyclerViewTreinos.setHasFixedSize(true);
        recyclerViewTreinos.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        recyclerViewTreinos.setAdapter(treinoAdapter);

    }

    @Override
    protected void onStart() {
        carregarListaTreinos();
        super.onStart();
    }
}