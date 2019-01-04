package br.com.alura.ceep.recyclerview.helper.callback;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import br.com.alura.ceep.dao.NotaDAO;
import br.com.alura.ceep.model.Nota;
import br.com.alura.ceep.recyclerview.adapter.ListaNotasAdapter;

public class NotaItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private Context context;
    private ListaNotasAdapter listaNotasAdapter;

    public NotaItemTouchHelperCallback(Context context, ListaNotasAdapter listaNotasAdapter) {
        this.context = context;
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
        NotaDAO dao = new NotaDAO(context);
        Nota notaPosicaoInicial = dao.obterPorPosicao(posicaoInicial);
        Nota notaPosicaoFinal = dao.obterPorPosicao(posicaoFinal);

        dao.troca(notaPosicaoInicial, notaPosicaoFinal);
        listaNotasAdapter.trocar(posicaoInicial, posicaoFinal);
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        int adapterPosition = viewHolder.getAdapterPosition();

        removerNota(adapterPosition);
    }

    private void removerNota(int adapterPosition) {
        NotaDAO dao = new NotaDAO(context);
        Nota nota = dao.obterPorPosicao(adapterPosition);

        dao.remove(nota);
        listaNotasAdapter.remover(adapterPosition);
    }
}
