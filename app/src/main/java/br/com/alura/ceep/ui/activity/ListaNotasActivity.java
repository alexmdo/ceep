package br.com.alura.ceep.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.List;

import br.com.alura.ceep.R;
import br.com.alura.ceep.dao.NotaDAO;
import br.com.alura.ceep.model.Nota;
import br.com.alura.ceep.ui.adapter.ListaNotasAdapter;

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

        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(new ListaNotasAdapter(this, listaNotas));
    }
}
