package br.com.alura.ceep.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import br.com.alura.ceep.R;
import br.com.alura.ceep.dao.NotaDAO;
import br.com.alura.ceep.model.Nota;
import br.com.alura.ceep.recyclerview.adapter.ListaNotasAdapter;
import br.com.alura.ceep.recyclerview.adapter.listener.OnItemClickListener;
import br.com.alura.ceep.recyclerview.helper.callback.NotaItemTouchHelperCallback;

import static br.com.alura.ceep.Constantes.NotasActivity.EXTRA_NOTA;
import static br.com.alura.ceep.Constantes.NotasActivity.EXTRA_POSICAO;
import static br.com.alura.ceep.Constantes.NotasActivity.EXTRA_POSICAO_DEFAULT_VALUE;
import static br.com.alura.ceep.Constantes.NotasActivity.REQUEST_CODE_ALTERAR_NOTA;
import static br.com.alura.ceep.Constantes.NotasActivity.REQUEST_CODE_INSERIR_NOTA;

public class ListaNotasActivity extends AppCompatActivity {

    public static final String NOTAS_TITULO_APPBAR = "Notas";
    private ListaNotasAdapter listaNotasAdapter;
    private List<Nota> todasNotas;
    boolean isLinearLayoutEnabled = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_notas);

        setTitle(NOTAS_TITULO_APPBAR);

        todasNotas = obterTodasNotas();
        configurarRecyclerView(todasNotas);

        definirAcaoDaTextViewDeInserirNota();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lista_notas, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.menu_item_layout_linear).setVisible(isLinearLayoutEnabled);
        menu.findItem(R.id.menu_item_layout_grid).setVisible(!isLinearLayoutEnabled);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_layout_linear:
                isLinearLayoutEnabled = false;
                break;
            case R.id.menu_item_layout_grid:
                isLinearLayoutEnabled = true;
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
        int posicao = data.getIntExtra(EXTRA_POSICAO, EXTRA_POSICAO_DEFAULT_VALUE);
        Nota nota = (Nota) data.getSerializableExtra(EXTRA_NOTA);

        NotaDAO notaDAO = new NotaDAO();
        notaDAO.altera(posicao, nota);

        this.listaNotasAdapter.alterar(posicao, nota);
    }

    private boolean isResultadoDaAlteracaoDeNota(int requestCode, @Nullable Intent data) {
        return data != null && requestCode == REQUEST_CODE_ALTERAR_NOTA && data.hasExtra(EXTRA_NOTA) && data.hasExtra(EXTRA_POSICAO);
    }

    private void inserirNotaEAtualizarAdapter(@Nullable Intent data) {
        Nota nota = (Nota) data.getSerializableExtra(EXTRA_NOTA);

        NotaDAO notaDAO = new NotaDAO();
        notaDAO.insere(nota);

        this.listaNotasAdapter.adicionar(nota);
    }

    private boolean isResultadoDaInclusaoDeNota(int requestCode, @Nullable Intent data) {
        return data != null && requestCode == REQUEST_CODE_INSERIR_NOTA && data.hasExtra(EXTRA_NOTA);
    }

    private void configurarRecyclerView(List<Nota> listaNotas) {
        RecyclerView listaNotasRecyclerView = findViewById(R.id.lista_notas_recyclerview);

        definirAdapter(listaNotas, listaNotasRecyclerView);
        configurarRecyclerViewParaResponderAEventosDeSwipe(listaNotasRecyclerView);
    }

    private void configurarRecyclerViewParaResponderAEventosDeSwipe(RecyclerView listaNotasRecyclerView) {
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new NotaItemTouchHelperCallback(listaNotasAdapter));
        itemTouchHelper.attachToRecyclerView(listaNotasRecyclerView);
    }

    private void definirAdapter(List<Nota> listaNotas, RecyclerView listaNotasRecyclerView) {
        listaNotasAdapter = new ListaNotasAdapter(this, listaNotas);
        listaNotasAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(Nota nota, int posicao) {
                Intent alterarNotaIntent = gerarIntentParaFormularioComNotaEPosicao(nota, posicao);
                startActivityForResult(alterarNotaIntent, REQUEST_CODE_ALTERAR_NOTA);
            }
        });
        listaNotasRecyclerView.setAdapter(listaNotasAdapter);

    }

    @NonNull
    private Intent gerarIntentParaFormularioComNotaEPosicao(Nota nota, int posicao) {
        Intent intent = new Intent(ListaNotasActivity.this, FormularioNotaActivity.class);
        intent.putExtra(EXTRA_NOTA, nota);
        intent.putExtra(EXTRA_POSICAO, posicao);
        return intent;
    }

    private List<Nota> obterTodasNotas() {
        NotaDAO notaDAO = new NotaDAO();

        return notaDAO.todos();
    }

}
