package br.com.alura.ceep.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import br.com.alura.ceep.R;
import br.com.alura.ceep.dao.NotaDAO;
import br.com.alura.ceep.model.Nota;

public class FormularioNotaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_nota);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_formulario_nota_salva, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_salvar_notas:
                EditText notaTitulo = findViewById(R.id.formulario_nota_titulo);
                EditText notaDescricao = findViewById(R.id.formulario_nota_descricao);

                Nota nota = new Nota(notaTitulo.getText().toString(), notaDescricao.getText().toString());

                NotaDAO notaDAO = new NotaDAO();
                notaDAO.insere(nota);

                finish();

                break;

        }

        return super.onOptionsItemSelected(item);
    }
}
