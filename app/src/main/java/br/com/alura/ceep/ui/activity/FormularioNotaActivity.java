package br.com.alura.ceep.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import br.com.alura.ceep.R;
import br.com.alura.ceep.model.Nota;

import static br.com.alura.ceep.Constantes.NotasActivity.EXTRA_NOTA;
import static br.com.alura.ceep.Constantes.NotasActivity.EXTRA_POSICAO;
import static br.com.alura.ceep.Constantes.NotasActivity.EXTRA_POSICAO_DEFAULT_VALUE;
import static br.com.alura.ceep.Constantes.NotasActivity.RESULT_CODE_INSERIR_NOTA;

public class FormularioNotaActivity extends AppCompatActivity {

    private EditText notaTituloView;
    private EditText notaDescricaoView;
    private int posicaoSelecionada = EXTRA_POSICAO_DEFAULT_VALUE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_nota);

        recuperarCamposFormulario();

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_NOTA)) {
            posicaoSelecionada = intent.getIntExtra(EXTRA_POSICAO, EXTRA_POSICAO_DEFAULT_VALUE);

            Nota nota = (Nota) intent.getSerializableExtra(EXTRA_NOTA);
            definirValoresCamposFormulario(nota);
        }
    }

    private void definirValoresCamposFormulario(Nota nota) {
        notaTituloView.setText(nota.getTitulo());
        notaDescricaoView.setText(nota.getDescricao());
    }

    private void recuperarCamposFormulario() {
        notaTituloView = findViewById(R.id.formulario_nota_titulo);
        notaDescricaoView = findViewById(R.id.formulario_nota_descricao);
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

                Nota nota = new Nota(notaTituloView.getText().toString(), notaDescricaoView.getText().toString());
                Intent intentComNotaEPosicao = gerarIntentComNotaEPosicao(nota, posicaoSelecionada);
                setResult(RESULT_CODE_INSERIR_NOTA, intentComNotaEPosicao);

                finish();

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private Intent gerarIntentComNotaEPosicao(Nota nota, int posicao) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_NOTA, nota);
        intent.putExtra(EXTRA_POSICAO, posicao);

        return intent;
    }

}
