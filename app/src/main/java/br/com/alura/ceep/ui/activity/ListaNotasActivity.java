package br.com.alura.ceep.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

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

        NotaDAO notaDAO = new NotaDAO();
        for (int i = 1; i <= 10000; i++)  {
            notaDAO.insere(new Nota("Titulo " + i, "Descricao " + i));
        }
        List<Nota> listaNotas = notaDAO.todos();

        RecyclerView listaNotasRecyclerView = findViewById(R.id.lista_notas_recyclerview);
        listaNotasRecyclerView.setAdapter(new ListaNotasAdapter(listaNotas));
    }
}
