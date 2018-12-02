package br.com.alura.ceep.recyclerview.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.alura.ceep.R;
import br.com.alura.ceep.model.Nota;


public class ListaNotasAdapter extends RecyclerView.Adapter<ListaNotasAdapter.NotaViewHolder> {

    private Context context;
    private List<Nota> listaNotas;

    public ListaNotasAdapter(Context context, List<Nota> listaNotas) {
        this.context = context;
        this.listaNotas = listaNotas;
    }

    @NonNull
    @Override
    public NotaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int posicao) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.item_nota, viewGroup, false);

        return new NotaViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull NotaViewHolder viewHolder, int posicao) {
        Nota nota = listaNotas.get(posicao);
        viewHolder.definirValoresNasViews(nota);
    }

    @Override
    public int getItemCount() {
        return this.listaNotas.size();
    }

    class NotaViewHolder extends RecyclerView.ViewHolder {

        private TextView titulo;
        private TextView descricao;

        public NotaViewHolder(View layout) {
            super(layout);

            titulo = layout.findViewById(R.id.item_nota_titulo);
            descricao = layout.findViewById(R.id.item_nota_descricao);
        }

        public void definirValoresNasViews(Nota nota) {
            titulo.setText(nota.getTitulo());
            descricao.setText(nota.getDescricao());
        }

    }
}
