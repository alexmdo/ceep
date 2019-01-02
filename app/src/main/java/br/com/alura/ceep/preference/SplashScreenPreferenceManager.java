package br.com.alura.ceep.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SplashScreenPreferenceManager {

    private static final String TAG = SplashScreenPreferenceManager.class.getName();
    public static final String PREFERENCE_FILE_NAME = "br.com.alura.ceep.ui.activity.SplashScreenPreferenceManager";
    public static final String CHAVE_PRIMEIRO_ACESSO = "PRIMEIRO_ACESSO";
    private Context context;

    public SplashScreenPreferenceManager(Context context) {
        this.context = context;
    }

    public void definirPrimeiroAcesso() {
        SharedPreferences sharedPreferences = getSharedPreferences();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(CHAVE_PRIMEIRO_ACESSO, false);
        editor.commit();

        Log.d(TAG, "definirPrimeiroAcesso: primeiro acesso definido com sucesso");
    }

    public boolean isPrimeiroAcesso() {
        SharedPreferences sharedPreferences = getSharedPreferences();

        boolean isPrimeiroAcesso = sharedPreferences.getBoolean(CHAVE_PRIMEIRO_ACESSO, true);

        Log.d(TAG, "isPrimeiroAcesso: primeiro acesso? -> " + isPrimeiroAcesso);

        return isPrimeiroAcesso;
    }

    private SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);
    }

}
