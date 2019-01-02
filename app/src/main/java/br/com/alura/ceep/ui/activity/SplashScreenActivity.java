package br.com.alura.ceep.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.com.alura.ceep.R;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                irParaListaNotaActivity();
            }
        }, 2000);
    }

    private void irParaListaNotaActivity() {
        Intent listaNotaActivityIntent = new Intent(this, ListaNotasActivity.class);
        startActivity(listaNotaActivityIntent);

        finish();

    }
}
