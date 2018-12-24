package br.com.alura.ceep.recyclerview.helper.callback;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import br.com.alura.ceep.dao.NotaDAO;
import br.com.alura.ceep.recyclerview.adapter.ListaNotasAdapter;

public class NotaItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private ListaNotasAdapter listaNotasAdapter;

    public NotaItemTouchHelperCallback(ListaNotasAdapter listaNotasAdapter) {
        this.listaNotasAdapter = listaNotasAdapter;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int marcacoesDeslize = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        int marcacoesArrastar = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;

        return makeMovementFlags(marcacoesArrastar, marcacoesDeslize);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder source, @NonNull RecyclerView.ViewHolder target) {
        int posicaoInicial = source.getAdapterPosition();
        int posicaoFinal = target.getAdapterPosition();

        trocarNotaDePosicao(posicaoInicial, posicaoFinal);

        return true;
    }

    private void trocarNotaDePosicao(int posicaoInicial, int posicaoFinal) {
        new NotaDAO().troca(posicaoInicial, posicaoFinal);
        listaNotasAdapter.trocar(posicaoInicial, posicaoFinal);
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        int adapterPosition = viewHolder.getAdapterPosition();

        removerNota(adapterPosition);
    }

    private void removerNota(int adapterPosition) {
        new NotaDAO().remove(adapterPosition);
        listaNotasAdapter.remover(adapterPosition);
    }
}
