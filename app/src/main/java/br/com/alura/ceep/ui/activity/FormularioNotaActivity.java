package br.com.alura.ceep.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.Arrays;
import java.util.List;

import br.com.alura.ceep.R;
import br.com.alura.ceep.model.Nota;
import br.com.alura.ceep.recyclerview.adapter.ListaPaletaCorAdapter;

import static br.com.alura.ceep.Constantes.NotasActivity.EXTRA_NOTA;
import static br.com.alura.ceep.Constantes.NotasActivity.EXTRA_POSICAO;
import static br.com.alura.ceep.Constantes.NotasActivity.EXTRA_POSICAO_DEFAULT_VALUE;

public class FormularioNotaActivity extends AppCompatActivity {

    public static final String INSERE_NOTA_APPBAR_TITLE = "Insere nota";
    public static final String ALTERA_NOTA_APPBAR_TITLE = "Altera nota";
    private EditText notaTituloView;
    private EditText notaDescricaoView;
    private int posicaoSelecionada = EXTRA_POSICAO_DEFAULT_VALUE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_nota);

        setTitle(INSERE_NOTA_APPBAR_TITLE);

        recuperarCamposFormulario();

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_NOTA)) {
            setTitle(ALTERA_NOTA_APPBAR_TITLE);

            posicaoSelecionada = intent.getIntExtra(EXTRA_POSICAO, EXTRA_POSICAO_DEFAULT_VALUE);

            Nota nota = (Nota) intent.getSerializableExtra(EXTRA_NOTA);
            definirValoresCamposFormulario(nota);
        }

        List<ListaPaletaCorAdapter.PaletaCorEnum> listaPaletaCor = Arrays.asList(ListaPaletaCorAdapter.PaletaCorEnum.values());
        configurarRecyclerView(listaPaletaCor);
    }

    private void configurarRecyclerView(List<ListaPaletaCorAdapter.PaletaCorEnum> listaPaletaCor) {
        RecyclerView paletaCorRecyclerView = findViewById(R.id.formulario_nota_paleta_cor_recyclerview);
        configurarAdapter(listaPaletaCor, paletaCorRecyclerView);
    }

    private void configurarAdapter(List<ListaPaletaCorAdapter.PaletaCorEnum> listaPaletaCor, RecyclerView paletaCorRecyclerView) {
        ListaPaletaCorAdapter listaPaletaCorAdapter = new ListaPaletaCorAdapter(this, listaPaletaCor);
        paletaCorRecyclerView.setAdapter(listaPaletaCorAdapter);
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
                setResult(Activity.RESULT_OK, intentComNotaEPosicao);

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
