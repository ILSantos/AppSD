package com.example.app.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Countries implements Serializable {


    @SerializedName("name")
    public String name;

    @SerializedName("alpha3Code")
    public String alpha3Code;

    @SerializedName("capital")
    public String capital;


    public  Countries (String name, String alpha3Code, String capital) {
        this.name = name;
        this.alpha3Code = alpha3Code;
        this.capital = capital;
    }

    @Override
    public String toString() {
        return "Countries{" +
                " name='" + name + '\'' +
                " alpha3Code='" + alpha3Code + '\'' +
                " capital='" + capital + '\''+
                '}';
    }


    public String getNome() {
        return name;
    }

    public String getSigla() {
        return alpha3Code;
    }

    public String getCapital() {
        return capital;
    }

}
