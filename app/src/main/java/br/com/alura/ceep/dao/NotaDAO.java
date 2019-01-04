package br.com.alura.ceep.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import br.com.alura.ceep.model.Nota;
import br.com.alura.ceep.model.PaletaCorEnum;

public class NotaDAO extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Ceep";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "Notas";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITULO = "titulo";
    public static final String COLUMN_DESCRICAO = "descricao";
    public static final String COLUMN_COR = "cor";
    public static final String COLUMN_POSICAO = "posicao";

    private final static ArrayList<Nota> notas = new ArrayList<>();
    private static final String TAG = NotaDAO.class.getName();

    public NotaDAO(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String ddlCriacaoTabela = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITULO + " TEXT NOT NULL, " +
                COLUMN_DESCRICAO + " TEXT NOT NULL, " +
                COLUMN_COR + " TEXT NOT NULL DEFAULT 'BRANCO', " +
                COLUMN_POSICAO + " INT NOT NULL DEFAULT 0);";
        db.execSQL(ddlCriacaoTabela);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TBD
    }

    public List<Nota> todos() {
        SQLiteDatabase db = getReadableDatabase();

        String queryTodasNotas = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + COLUMN_POSICAO;
        Cursor cursor = db.rawQuery(queryTodasNotas, null);

        List<Nota> listaTodasNotas = obterListaNotasDoCursor(cursor);

        notas.clear();
        notas.addAll(listaTodasNotas);

        return (List<Nota>) notas.clone();
    }

    public void insere(Nota nota) {
        SQLiteDatabase db = getWritableDatabase();
        definirPosicao(nota);

        long idGerado = db.insert(TABLE_NAME, null, obterContentValueDaNota(nota));
        nota.setId(idGerado);

        notas.addAll(Arrays.asList(nota));
    }

    private boolean existeNotaCadastrada() {
        String queryExisteNota = "SELECT COUNT(*) FROM " + TABLE_NAME;
        Cursor cursor = getReadableDatabase().rawQuery(queryExisteNota, null);

        return (cursor.moveToNext() && cursor.getInt(0) > 0);
    }

    private void definirPosicao(Nota nota) {
        if (!existeNotaCadastrada()) {
            nota.setPosicao(0);
        } else {
            int ultimaPosicaoUtilizada = obterUltimaPosicaoUtilizada();
            nota.setPosicao(ultimaPosicaoUtilizada + 1);
        }

        Log.d(TAG, "definirPosicao: posicao definida -> " + nota.getPosicao());
    }

    private int obterUltimaPosicaoUtilizada() {
        int ultimaPosicaoUtilizada = -1;

        String queryUltimaPosicaoUtilizada = "SELECT MAX(" + COLUMN_POSICAO + ") FROM " + TABLE_NAME;
        Cursor cursor = getReadableDatabase().rawQuery(queryUltimaPosicaoUtilizada, null);
        if (cursor.moveToNext()) {
            ultimaPosicaoUtilizada = cursor.getInt(0);
        }
        return ultimaPosicaoUtilizada;
    }

    public void altera(Nota nota) {
        SQLiteDatabase db = getWritableDatabase();
        db.update(TABLE_NAME, obterContentValueDaNota(nota), "id = ?", new String[]{nota.getId().toString()});

        notas.set(nota.getPosicao(), nota);
    }

    public void remove(Nota nota) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, "id = ?", new String[]{nota.getId().toString()});

        Integer posicao = nota.getPosicao();
        notas.remove(posicao);

        decrementarPosicaoDasNotasDePosicaoSuperior(posicao);
    }

    private void decrementarPosicaoDasNotasDePosicaoSuperior(Integer posicao) {
        List<Nota> listaNotas = obterNotasSuperiorAPosicao(posicao);
        for (Nota n :
                listaNotas) {
            n.setPosicao(n.getPosicao() - 1);
            altera(n);
        }
    }

    private List<Nota> obterNotasSuperiorAPosicao(Integer posicao) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, COLUMN_POSICAO + " > ?", new String[]{posicao.toString()}, null, null, COLUMN_POSICAO + " DESC");

        return obterListaNotasDoCursor(cursor);
    }

    public void troca(Nota notaPosicaoInicio, Nota notaPosicaoFim) {
        Integer posicaoInicial = notaPosicaoInicio.getPosicao();
        Integer posicaoFinal = notaPosicaoFim.getPosicao();

        notaPosicaoInicio.setPosicao(posicaoFinal);
        notaPosicaoFim.setPosicao(posicaoInicial);

        altera(notaPosicaoInicio);
        altera(notaPosicaoFim);

        Collections.swap(notas, posicaoInicial, posicaoFinal);
    }

    public Nota obterPorPosicao(Integer posicao) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(true, TABLE_NAME, null, COLUMN_POSICAO + "= ?", new String[]{posicao.toString()}, null, null, null, null);

        List<Nota> notas = obterListaNotasDoCursor(cursor);
        if (!notas.isEmpty()) {
            return notas.get(0);
        } else {
            return null;
        }
    }

    private ContentValues obterContentValueDaNota(Nota nota) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TITULO, nota.getTitulo());
        contentValues.put(COLUMN_DESCRICAO, nota.getDescricao());
        contentValues.put(COLUMN_COR, nota.getPaletaCorEnum().toString());
        contentValues.put(COLUMN_POSICAO, nota.getPosicao());

        return contentValues;
    }

    @NonNull
    private List<Nota> obterListaNotasDoCursor(Cursor cursor) {
        List<Nota> listaTodasNotas = new ArrayList<>();
        while (cursor.moveToNext()) {
            Nota nota = new Nota();
            nota.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
            nota.setTitulo(cursor.getString(cursor.getColumnIndex(COLUMN_TITULO)));
            nota.setDescricao(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRICAO)));
            nota.setPaletaCorEnum(PaletaCorEnum.valueOf(cursor.getString(cursor.getColumnIndex(COLUMN_COR))));
            nota.setPosicao(cursor.getInt(cursor.getColumnIndex(COLUMN_POSICAO)));

            listaTodasNotas.add(nota);
        }

        return listaTodasNotas;
    }

}
