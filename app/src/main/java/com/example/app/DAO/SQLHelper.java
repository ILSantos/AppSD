package com.example.app.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLHelper extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "dbCountries";
    private static final int VERSAO_BANCO = 1;
    public static final String TABELA_COUNTRIES = "countries_tabela";
    public static final String COLUNA_ID = "_id";
    public static final String COLUNA_NOME= "nome";
    public static final String COLUNA_SIGLA = "sigla";
    public static final String COLUNA_CAPITAL = "capital";
    public static final String COLUNA_IDIOMA = "idioma";

    public SQLHelper(Context context) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }

    public void  onCreate (SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                " CREATE TABLE " + TABELA_COUNTRIES + " ( " +
                        COLUNA_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                        COLUNA_NOME + " TEXT, " +
                        COLUNA_SIGLA + " TEXT, " +
                        COLUNA_CAPITAL + " TEXT, " +
                        COLUNA_IDIOMA + "TEXT)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
