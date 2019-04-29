package com.example.app.Util;

import com.example.app.Model.Countries;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class HttpRetro {
    private static final String BASE_URL = "https://restcountries.eu/rest/v1/all";

    // Inicializa Retrofit
    public static CountriesInterface getCountriesClient() {
        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return restAdapter.create(CountriesInterface.class);
    }

    // Interface com metodos de requisicao
    public interface CountriesInterface {
        @GET()
        Call<List<Countries>> getCountries();
    }

}
