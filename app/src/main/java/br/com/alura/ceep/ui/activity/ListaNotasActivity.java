package br.com.alura.ceep.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.com.alura.ceep.R;
import br.com.alura.ceep.dao.NotaDAO;
import br.com.alura.ceep.model.Nota;
import br.com.alura.ceep.recyclerview.adapter.ListaNotasAdapter;
import br.com.alura.ceep.recyclerview.adapter.OnItemClickListener;

import static br.com.alura.ceep.Constantes.NotasActivity.EXTRA_NOTA;
import static br.com.alura.ceep.Constantes.NotasActivity.REQUEST_CODE_INSERIR_NOTA;
import static br.com.alura.ceep.Constantes.NotasActivity.RESULT_CODE_INSERIR_NOTA;

public class ListaNotasActivity extends AppCompatActivity {

    private ListaNotasAdapter listaNotasAdapter;
    private List<Nota> todasNotas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_notas);

        todasNotas = obterTodasNotas();
        configurarRecyclerView(todasNotas);

        definirAcaoDaTextViewDeInserirNota();
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

        if (isResultadoDaInclusaoDeNota(requestCode, resultCode, data)) {
            inserirNotaEAtualizarAdapter(data);
        }
    }

    private void inserirNotaEAtualizarAdapter(@Nullable Intent data) {
        Nota nota = (Nota) data.getSerializableExtra(EXTRA_NOTA);

        NotaDAO notaDAO = new NotaDAO();
        notaDAO.insere(nota);

        this.listaNotasAdapter.adicionar(nota);
    }

    private boolean isResultadoDaInclusaoDeNota(int requestCode, int resultCode, @Nullable Intent data) {
        return requestCode == REQUEST_CODE_INSERIR_NOTA && resultCode == RESULT_CODE_INSERIR_NOTA && data.hasExtra(EXTRA_NOTA);
    }

    private void configurarRecyclerView(List<Nota> listaNotas) {
        RecyclerView listaNotasRecyclerView = findViewById(R.id.lista_notas_recyclerview);
        definirAdapter(listaNotas, listaNotasRecyclerView);
    }

    private void definirAdapter(List<Nota> listaNotas, RecyclerView listaNotasRecyclerView) {
        listaNotasAdapter = new ListaNotasAdapter(this, listaNotas);
        listaNotasAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick() {
                Toast.makeText(ListaNotasActivity.this, "Item clicado na activity", Toast.LENGTH_SHORT).show();
            }
        });
        listaNotasRecyclerView.setAdapter(listaNotasAdapter);
        
    }

    private List<Nota> obterTodasNotas() {
        NotaDAO notaDAO = new NotaDAO();

        return notaDAO.todos();
    }

}
