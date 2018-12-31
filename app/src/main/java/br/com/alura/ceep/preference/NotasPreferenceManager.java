package br.com.alura.ceep.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import br.com.alura.ceep.ui.activity.ListaNotasActivity;

public class NotasPreferenceManager {

    public static final String CHAVE_LAYOUT_SELECIONADO_PREFERENCE = "CHAVE_LAYOUT_SELECIONADO_PREFERENCE";
    private static final String TAG = NotasPreferenceManager.class.getCanonicalName();
    private Context context;

    public NotasPreferenceManager(Context context) {
        this.context = context;
    }

    public void salvarLayoutPadrao(LayoutManagerEnum layoutEnum) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("br.com.alura.ceep.ui.activity.ListaNotasActivity", ListaNotasActivity.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(CHAVE_LAYOUT_SELECIONADO_PREFERENCE, layoutEnum.toString());
        edit.commit();

        Log.d(TAG, "salvarLayoutPadrao: layout salvo -> " + layoutEnum);
    }

    public LayoutManagerEnum obterLayoutPadrao() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("br.com.alura.ceep.ui.activity.ListaNotasActivity", ListaNotasActivity.MODE_PRIVATE);
        String layoutSelecionado = sharedPreferences.getString(CHAVE_LAYOUT_SELECIONADO_PREFERENCE, "");

        LayoutManagerEnum layoutManagerEnum = LayoutManagerEnum.LINEAR_LAYOUT;
        try {
            layoutManagerEnum = LayoutManagerEnum.valueOf(layoutSelecionado);
            Log.d(TAG, "obterLayoutPadrao: layout obtido -> " + layoutManagerEnum);
        } catch (Exception e) {
            Log.d(TAG, "obterLayoutPadrao: nenhum layout padrao foi definido anteriormente. Devolvendo padrão -> " + layoutManagerEnum);
            return layoutManagerEnum;
        }

        return layoutManagerEnum;
    }

    public enum LayoutManagerEnum {

        LINEAR_LAYOUT, STAGGERED_GRID_LAYOUT;

    }
}