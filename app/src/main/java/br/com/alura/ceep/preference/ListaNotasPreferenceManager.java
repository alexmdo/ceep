package br.com.alura.ceep.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class ListaNotasPreferenceManager {

    private static final String TAG = ListaNotasPreferenceManager.class.getCanonicalName();
    public static final String CHAVE_LAYOUT_PADRAO = "LAYOUT_PADRAO";
    public static final String PREFERENCE_FILE_NAME = "br.com.alura.ceep.ui.activity.ListaNotasActivity";
    private Context context;

    public ListaNotasPreferenceManager(Context context) {
        this.context = context;
    }

    public void salvarLayoutManager(LayoutManagerEnum layoutEnum) {
        SharedPreferences sharedPreferences = getSharedPreferences();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(CHAVE_LAYOUT_PADRAO, layoutEnum.toString());
        editor.commit();

        Log.d(TAG, "salvarLayoutManager: layout salvo -> " + layoutEnum);
    }

    public LayoutManagerEnum obterLayoutManager() {
        SharedPreferences sharedPreferences = getSharedPreferences();
        String layoutSelecionado = sharedPreferences.getString(CHAVE_LAYOUT_PADRAO, "");

        LayoutManagerEnum layoutManagerEnum = LayoutManagerEnum.LINEAR_LAYOUT;
        try {
            layoutManagerEnum = LayoutManagerEnum.valueOf(layoutSelecionado);
            Log.d(TAG, "obterLayoutManager: layout obtido -> " + layoutManagerEnum);
        } catch (Exception e) {
            Log.d(TAG, "obterLayoutManager: nenhum layout padrao foi definido anteriormente. Devolvendo padrão -> " + layoutManagerEnum);
            return layoutManagerEnum;
        }

        return layoutManagerEnum;
    }

    private SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);
    }

    public enum LayoutManagerEnum {

        LINEAR_LAYOUT, STAGGERED_GRID_LAYOUT;

    }
}