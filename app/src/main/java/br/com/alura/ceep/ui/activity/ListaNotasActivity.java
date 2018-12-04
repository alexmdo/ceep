package br.com.alura.ceep.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import br.com.alura.ceep.R;
import br.com.alura.ceep.dao.NotaDAO;
import br.com.alura.ceep.model.Nota;
import br.com.alura.ceep.recyclerview.adapter.ListaNotasAdapter;

public class ListaNotasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_notas);

        List<Nota> listaNotas = mockarNotas();
        configurarRecyclerView(listaNotas);

        TextView insereNotas = findViewById(R.id.lista_notas_insere_nota);
        insereNotas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent irParaFormularioNotaActivity = new Intent(ListaNotasActivity.this, FormularioNotaActivity.class);
                startActivity(irParaFormularioNotaActivity);
            }
        });


    }

    private void configurarRecyclerView(List<Nota> listaNotas) {
        RecyclerView listaNotasRecyclerView = findViewById(R.id.lista_notas_recyclerview);
        definirAdapter(listaNotas, listaNotasRecyclerView);
    }

    private void definirAdapter(List<Nota> listaNotas, RecyclerView listaNotasRecyclerView) {
        listaNotasRecyclerView.setAdapter(new ListaNotasAdapter(this, listaNotas));
    }

    private List<Nota> mockarNotas() {
        NotaDAO notaDAO = new NotaDAO();
        inserirNotasAleatorias(notaDAO);

        return notaDAO.todos();
    }

    private void inserirNotasAleatorias(NotaDAO notaDAO) {
        for (int i = 1; i <= 10000; i++)  {
            notaDAO.insere(new Nota("Titulo " + i, "Descricao " + i));
        }
    }
}
