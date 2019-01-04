package br.com.alura.ceep.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import br.com.alura.ceep.R;
import br.com.alura.ceep.dao.NotaDAO;
import br.com.alura.ceep.model.Nota;
import br.com.alura.ceep.preference.ListaNotasPreferenceManager;
import br.com.alura.ceep.recyclerview.adapter.ListaNotasAdapter;
import br.com.alura.ceep.recyclerview.helper.callback.NotaItemTouchHelperCallback;

import static br.com.alura.ceep.Constantes.NotasActivity.EXTRA_NOTA;
import static br.com.alura.ceep.Constantes.NotasActivity.REQUEST_CODE_ALTERAR_NOTA;
import static br.com.alura.ceep.Constantes.NotasActivity.REQUEST_CODE_INSERIR_NOTA;

public class ListaNotasActivity extends AppCompatActivity {

    public static final String NOTAS_TITULO_APPBAR = "Notas";
    private final ListaNotasPreferenceManager notasPreferenceManager = new ListaNotasPreferenceManager(this);
    private ListaNotasAdapter listaNotasAdapter;
    private List<Nota> todasNotas;
    private RecyclerView listaNotasRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_notas);

        setTitle(NOTAS_TITULO_APPBAR);

        extrairViewsDoLayout();

        todasNotas = obterTodasNotas();
        configurarRecyclerView(todasNotas);

        definirAcaoDaTextViewDeInserirNota();
    }

    private void extrairViewsDoLayout() {
        listaNotasRecyclerView = findViewById(R.id.lista_notas_recyclerview);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lista_notas, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean isLinearLayoutEnabled = isLinearLayoutAtivado();

        configurarLayoutManager();

        menu.findItem(R.id.menu_item_layout_linear).setVisible(!isLinearLayoutEnabled);
        menu.findItem(R.id.menu_item_layout_grid).setVisible(isLinearLayoutEnabled);

        return super.onPrepareOptionsMenu(menu);
    }

    private boolean isLinearLayoutAtivado() {
        ListaNotasPreferenceManager.LayoutManagerEnum layoutManagerEnum = notasPreferenceManager.obterLayoutManager();

        return layoutManagerEnum == ListaNotasPreferenceManager.LayoutManagerEnum.LINEAR_LAYOUT;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_layout_linear:
                notasPreferenceManager.salvarLayoutManager(ListaNotasPreferenceManager.LayoutManagerEnum.LINEAR_LAYOUT);
                break;
            case R.id.menu_item_layout_grid:
                notasPreferenceManager.salvarLayoutManager(ListaNotasPreferenceManager.LayoutManagerEnum.STAGGERED_GRID_LAYOUT);
                break;
        }

        // necessário invocar esse método para requisitar ao sistema que o método onPrepareOptionsMenu seja invocado
        // @see https://developer.android.com/guide/topics/ui/menus#ChangingTheMenu
        invalidateOptionsMenu();

        return super.onOptionsItemSelected(item);
    }

    private void definirAcaoDaTextViewDeInserirNota() {
        TextView insereNotas = findViewById(R.id.lista_notas_insere_nota);
        insereNotas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                irParaFormularioNotaActivity();
            }
        });
    }

    private void irParaFormularioNotaActivity() {
        Intent irParaFormularioNotaActivity = new Intent(ListaNotasActivity.this, FormularioNotaActivity.class);
        startActivityForResult(irParaFormularioNotaActivity, REQUEST_CODE_INSERIR_NOTA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (isResultadoDaInclusaoDeNota(requestCode, data)) {
            if (resultCode == Activity.RESULT_OK) {
                inserirNotaEAtualizarAdapter(data);
            }
        } else if (isResultadoDaAlteracaoDeNota(requestCode, data)) {
            if (resultCode == Activity.RESULT_OK) {
                alterarNotaEAtualizarAdapter(data);
            }
        }
    }

    private void alterarNotaEAtualizarAdapter(@Nullable Intent data) {
        Nota nota = (Nota) data.getSerializableExtra(EXTRA_NOTA);

        NotaDAO notaDAO = new NotaDAO(this);
        notaDAO.altera(nota);

        this.listaNotasAdapter.alterar(nota.getPosicao(), nota);
    }

    private boolean isResultadoDaAlteracaoDeNota(int requestCode, @Nullable Intent data) {
        return data != null && requestCode == REQUEST_CODE_ALTERAR_NOTA && data.hasExtra(EXTRA_NOTA);
    }

    private void inserirNotaEAtualizarAdapter(@Nullable Intent data) {
        Nota nota = (Nota) data.getSerializableExtra(EXTRA_NOTA);

        NotaDAO notaDAO = new NotaDAO(this);
        notaDAO.insere(nota);

        this.listaNotasAdapter.adicionar(nota);
    }

    private boolean isResultadoDaInclusaoDeNota(int requestCode, @Nullable Intent data) {
        return data != null && requestCode == REQUEST_CODE_INSERIR_NOTA && data.hasExtra(EXTRA_NOTA);
    }

    private void configurarLayoutManager() {
        if (isLinearLayoutAtivado()) {
            LinearLayoutManager layout = new LinearLayoutManager(this);
            layout.setReverseLayout(true);
            layout.setStackFromEnd(true);
            listaNotasRecyclerView.setLayoutManager(layout);
        } else {
            StaggeredGridLayoutManager layout = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            layout.setReverseLayout(true);
            listaNotasRecyclerView.setLayoutManager(layout);
        }
    }

    private void configurarRecyclerView(List<Nota> listaNotas) {
        configurarLayoutManager();
        definirAdapter(listaNotas);
        configurarRecyclerViewParaResponderAEventosDeSwipe();
    }

    private void configurarRecyclerViewParaResponderAEventosDeSwipe() {
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new NotaItemTouchHelperCallback(this, listaNotasAdapter));
        itemTouchHelper.attachToRecyclerView(listaNotasRecyclerView);
    }

    private void definirAdapter(List<Nota> listaNotas) {
        listaNotasAdapter = new ListaNotasAdapter(this, listaNotas);
        listaNotasAdapter.setOnItemClickListener(new ListaNotasAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Nota nota) {
                Intent alterarNotaIntent = gerarIntentParaFormularioComNota(nota);
                startActivityForResult(alterarNotaIntent, REQUEST_CODE_ALTERAR_NOTA);
            }
        });
        listaNotasRecyclerView.setAdapter(listaNotasAdapter);

    }

    @NonNull
    private Intent gerarIntentParaFormularioComNota(Nota nota) {
        Intent intent = new Intent(ListaNotasActivity.this, FormularioNotaActivity.class);
        intent.putExtra(EXTRA_NOTA, nota);
        return intent;
    }

    private List<Nota> obterTodasNotas() {
        NotaDAO notaDAO = new NotaDAO(this);

        return notaDAO.todos();
    }

}

