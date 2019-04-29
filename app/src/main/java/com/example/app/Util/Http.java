package com.example.app.Util;

import com.example.app.Model.Countries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Http {
    public static final String COUNTRIES_URL_JSON = "https://restcountries.eu/rest/v1/all/";

    private static HttpURLConnection connectar (String urlArquivo) throws IOException {
        final int SEGUNDOS = 1000;
        URL url = new URL(urlArquivo);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setReadTimeout(10 *  SEGUNDOS);
        connection.setConnectTimeout(15 * SEGUNDOS);
        connection.setRequestMethod("GET");
        connection.setDoInput(true);
        connection.setDoOutput(false);
        connection.connect();

        return connection;
    }

    public static List<Countries> carregarCountriesJson() {
        try {
            HttpURLConnection conexao = connectar(COUNTRIES_URL_JSON);
            int resposta = conexao.getResponseCode();
            if (resposta == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = conexao.getInputStream();
                JSONArray jsonArray = new JSONArray(bytesParaString(inputStream));
                return lerJsonCountries(jsonArray);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static List<Countries> lerJsonCountries(JSONArray jsonArray) throws JSONException {
        List<Countries> listaDeCountries = new ArrayList<Countries>();
//        JSONArray jsonCountries = jsonArray.getJSONArray("countries");
        for (int j = 0; j < jsonArray.length(); j++) {
            JSONObject unidade = jsonArray.getJSONObject(j);
            Countries countries = new Countries(
                    unidade.getLong("id"),
                    unidade.getString("nome"),
                    unidade.getString("sigla"),
                    unidade.getString("capital"),
                    unidade.getString("idioma")
            );
            listaDeCountries.add(countries);
        }

        return listaDeCountries;
    }

    private static String bytesParaString(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        // o bufferzao vai armazenar todos os bytes lidos
        ByteArrayOutputStream bufferzao = new ByteArrayOutputStream();
        // precisamos saber quantos bytes foram lidos
        int bytesLidos;
        // vamos lendo de 1kb por vez
        while ((bytesLidos = inputStream.read(buffer)) != -1) {
            // copiando a quantidade de bytes  lidos do buffer para o bufferzao
            bufferzao.write(buffer, 0, bytesLidos);
        }

        return  new String(bufferzao.toByteArray(), "UTF-8");
    }

}
