package br.com.marcosquirinogarcia.workout.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.marcosquirinogarcia.workout.R;
import br.com.marcosquirinogarcia.workout.model.Treino;

public class TreinoAdapter extends RecyclerView.Adapter<TreinoAdapter.MyViewHolder> {

    private List<Treino> listaTreinos;

    public TreinoAdapter(List<Treino> listTreinos) {
        this.listaTreinos = listTreinos;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemListaTreinos = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_treino_adapter, parent, false);

        return new MyViewHolder(itemListaTreinos);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Treino treino = listaTreinos.get(position);
        holder.treino.setText(treino.getNomeTreino());

    }

    @Override
    public int getItemCount() {return this.listaTreinos.size(); }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView treino;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            treino = itemView.findViewById(R.id.textView_nome_treino);
        }
    }
}
