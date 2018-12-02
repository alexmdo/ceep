package br.com.alura.ceep.recyclerview.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.alura.ceep.R;
import br.com.alura.ceep.model.Nota;

public class ListaNotasAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<Nota> listaNotas;

    public ListaNotasAdapter(Context context, List<Nota> listaNotas) {
        this.context = context;
        this.listaNotas = listaNotas;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int posicao) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.item_nota, viewGroup, false);

        return new NotaViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int posicao) {
        Nota nota = listaNotas.get(posicao);

        View layout = viewHolder.itemView;

        TextView titulo = layout.findViewById(R.id.item_nota_titulo);
        titulo.setText(nota.getTitulo());

        TextView descricao = layout.findViewById(R.id.item_nota_descricao);
        descricao.setText(nota.getDescricao());
    }

    @Override
    public int getItemCount() {
        return this.listaNotas.size();
    }


    private class NotaViewHolder extends RecyclerView.ViewHolder {

        public NotaViewHolder(View layout) {
            super(layout);
        }

    }
}
