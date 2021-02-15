package br.com.marcosquirinogarcia.workout.adapter;

import android.content.Intent;
import android.os.TestLooperManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.marcosquirinogarcia.workout.R;
import br.com.marcosquirinogarcia.workout.model.Exercicio;

public class ExercicioAdapter extends RecyclerView.Adapter<ExercicioAdapter.MyViewHolder> {

    private List<Exercicio> listaExercicios;

    public ExercicioAdapter(List<Exercicio> lista){
        this.listaExercicios = lista;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_exercicio_adapter, parent, false);

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Exercicio exercicio = listaExercicios.get(position);
        holder.nomeExercicio.setText(exercicio.getNomeExercicio());
        holder.numSeries.setText(String.valueOf(exercicio.getNumSeries()));
        holder.numRepeticoes.setText(String.valueOf(exercicio.getNumRepeticoes()));
        holder.qtdPeso.setText(String.valueOf(exercicio.getPeso()));
    }

    @Override
    public int getItemCount() {return this.listaExercicios.size(); }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView nomeExercicio;
        TextView numSeries;
        TextView numRepeticoes;
        TextView qtdPeso;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nomeExercicio = itemView.findViewById(R.id.textView_nome_exercicio);
            numSeries = itemView.findViewById(R.id.textView_num_series);
            numRepeticoes = itemView.findViewById(R.id.textView_num_repeticoes);
            qtdPeso = itemView.findViewById(R.id.textView_qtd_peso);
        }
    }
}
