package com.example.app.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Countries implements Serializable {

    @SerializedName("id")
    public long id;

    @SerializedName("nome")
    public String nome;

    @SerializedName("sigla")
    public String sigla;

    @SerializedName("capital")
    public String capital;

    @SerializedName("idioma")
    public String idioma;

    public  Countries (long id, String nome, String sigla, String capital, String idioma) {
        this.id = id;
        this.nome = nome;
        this.sigla = sigla;
        this.capital = capital;
        this.idioma = idioma;
    }

    @Override
    public String toString() {
        return "Countries{" +
                " nome='" + nome + '\'' +
                " sigla='" + sigla + '\'' +
                " capital='" + capital + '\''+
                " idioma='" + idioma + '\'' +
                '}';
    }

    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getSigla() {
        return sigla;
    }

    public String getCapital() {
        return capital;
    }

    public String getIdioma() {
        return idioma;
    }

}
