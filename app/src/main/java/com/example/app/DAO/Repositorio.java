package com.example.app.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.app.Model.Countries;

import java.util.ArrayList;
import java.util.List;

public class Repositorio {

    private SQLHelper helper;
    private SQLiteDatabase db;

    public Repositorio(Context context) {
        helper = new SQLHelper(context);
    }

    public long inserir (Countries countries) {
        db = helper.getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(SQLHelper.COLUNA_NOME, countries.name);
        cv.put(SQLHelper.COLUNA_SIGLA, countries.alpha3Code);
        cv.put(SQLHelper.COLUNA_CAPITAL, countries.capital);

        long id = db.insert(SQLHelper.TABELA_COUNTRIES, null, cv);

//        if (id != -1) {
//            countries.id = id;
//        }

        db.close();
        return id;
    }

    public void excluirAll() {
        db = helper.getWritableDatabase();
        db.delete(SQLHelper.TABELA_COUNTRIES, null, null);
        db.close();
    }

    public List<Countries> listarCountries() {
        String sql = " SELECT * FROM " + SQLHelper.TABELA_COUNTRIES;
        db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        List<Countries> list = new ArrayList<>();

        while (cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndex(SQLHelper.COLUNA_ID));
            String nome = cursor.getString(cursor.getColumnIndex(SQLHelper.COLUNA_NOME));
            String sigla = cursor.getString(cursor.getColumnIndex(SQLHelper.COLUNA_SIGLA));
            String capital = cursor.getString(cursor.getColumnIndex(SQLHelper.COLUNA_CAPITAL));

            Countries countries = new Countries(id, nome, sigla, capital);
            list.add(countries);
        }
        cursor.close();
        return list;
    }


}
