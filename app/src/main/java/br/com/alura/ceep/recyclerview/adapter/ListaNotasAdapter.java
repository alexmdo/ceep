package br.com.alura.ceep.recyclerview.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import br.com.alura.ceep.R;
import br.com.alura.ceep.model.Nota;
import br.com.alura.ceep.recyclerview.adapter.listener.OnItemClickListener;


public class ListaNotasAdapter extends RecyclerView.Adapter<ListaNotasAdapter.NotaViewHolder> {

    private Context context;
    private List<Nota> listaNotas;
    private OnItemClickListener onItemClickListener;

    public ListaNotasAdapter(Context context, List<Nota> listaNotas) {
        this.context = context;
        this.listaNotas = listaNotas;
    }

    public void adicionar(Nota nota) {
        listaNotas.add(nota);
        notifyDataSetChanged();
    }

    public void alterar(int posicao, Nota nota) {
        listaNotas.set(posicao, nota);
        notifyItemChanged(posicao);
    }

    public void remover(int position) {
        listaNotas.remove(position);
        notifyItemRemoved(position);
    }

    public void trocar(int posicaoInicial, int posicaoFinal) {
        Collections.swap(listaNotas, posicaoInicial, posicaoFinal);
        notifyItemMoved(posicaoInicial, posicaoFinal);
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

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    class NotaViewHolder extends RecyclerView.ViewHolder {

        private TextView titulo;
        private TextView descricao;
        private Nota nota;

        public NotaViewHolder(View itemView) {
            super(itemView);

            titulo = itemView.findViewById(R.id.item_nota_titulo);
            descricao = itemView.findViewById(R.id.item_nota_descricao);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(nota, getAdapterPosition());
                }
            });
        }

        public void definirValoresNasViews(Nota nota) {
            this.nota = nota;

            titulo.setText(nota.getTitulo());
            descricao.setText(nota.getDescricao());
        }

    }
}
