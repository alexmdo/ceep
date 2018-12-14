package br.com.alura.ceep.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import br.com.alura.ceep.R;
import br.com.alura.ceep.model.Nota;

import static br.com.alura.ceep.Constantes.NotasActivity.EXTRA_NOTA;
import static br.com.alura.ceep.Constantes.NotasActivity.RESULT_CODE_INSERIR_NOTA;

public class FormularioNotaActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_nota);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_formulario_nota_salva, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_salvar_notas:
                Nota nota = instanciarNovaNota();

                inserirNotaEDevolverParaIntent(nota);

                finish();

                break;

        }

        return super.onOptionsItemSelected(item);
    }

    private void inserirNotaEDevolverParaIntent(Nota nota) {
        Intent voltarParaListaNotasActivity = new Intent();
        voltarParaListaNotasActivity.putExtra(EXTRA_NOTA, nota);

        setResult(RESULT_CODE_INSERIR_NOTA, voltarParaListaNotasActivity);
    }

    @NonNull
    private Nota instanciarNovaNota() {
        EditText notaTitulo = findViewById(R.id.formulario_nota_titulo);
        EditText notaDescricao = findViewById(R.id.formulario_nota_descricao);

        return new Nota(notaTitulo.getText().toString(), notaDescricao.getText().toString());
    }
}
