package br.com.alura.ceep.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.com.alura.ceep.R;
import br.com.alura.ceep.preference.SplashScreenPreferenceManager;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        long tempoSplashScreen = definirTempoAberturaSplashScreen();
        irParaListaNotaActivity(tempoSplashScreen);
    }

    private void irParaListaNotaActivity(long tempoSplashScreen) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                irParaListaNotaActivity();
            }
        }, tempoSplashScreen);
    }

    private long definirTempoAberturaSplashScreen() {
        long tempoSplashScreen;
        SplashScreenPreferenceManager splashScreenPreferenceManager = new SplashScreenPreferenceManager(this);

        if (splashScreenPreferenceManager.isPrimeiroAcesso()) {
            splashScreenPreferenceManager.definirPrimeiroAcesso();
            return tempoSplashScreen = 2000;
        } else {
            return tempoSplashScreen = 500;
        }

    }

    private void irParaListaNotaActivity() {
        Intent listaNotaActivityIntent = new Intent(this, ListaNotasActivity.class);
        startActivity(listaNotaActivityIntent);

        finish();

    }
}
