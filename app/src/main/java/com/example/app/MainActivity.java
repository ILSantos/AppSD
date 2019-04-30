package com.example.app;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.app.Adapter.Adapter;
import com.example.app.DAO.Repositorio;
import com.example.app.Model.Countries;
import com.example.app.Util.Http;
import com.example.app.Util.HttpRetro;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private Adapter adapter;
    private List<Countries> countriesList;
    private ListView listView;
    private SwipeRefreshLayout swipeRefresh;
    Repositorio db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        // seta cores
        swipeRefresh.setColorScheme(R.color.colorPrimary, R.color.colorAccent);
        swipeRefresh.setOnRefreshListener(this);

        listView = (ListView) findViewById(R.id.listView);

        countriesList = new ArrayList<Countries>();

        db = new Repositorio(getBaseContext());
        adapter = new Adapter(this, countriesList);
        getDataRetro();

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplication(), countriesList.get(position).toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getData() {
        String[] nomes = {"BRASIL", "ESTADOS UNIDOS", "FRANÇA", "ARGENTINA"};
        String[] capital= {"Brasilia", "Washington DC.", "Paris", "Buenos Aires"};

        countriesList.clear();
        for (int i = 0; i < 20; i++) {
            int nextInt = new Random().nextInt(4);
            Countries countries = new Countries(i, nomes[nextInt], "", capital[nextInt]);
            countriesList.add(countries);
        }
        adapter.notifyDataSetChanged();
    }

    // chama AsyncTask para requisicao dos countries
    public void getDataHttp() {
        CountriesTask mTask = new CountriesTask();
        mTask.execute();
    }

    @Override
    public void onRefresh() {
        getDataRetro();
    }

    class CountriesTask extends AsyncTask<Void, Void, List<Countries>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            swipeRefresh.setRefreshing(true);
        }

        @Override
        protected List<Countries> doInBackground(Void... voids) {
            return Http.carregarCountriesJson();
        }

        @Override
        protected void onPostExecute(List<Countries> countries) {
            super.onPostExecute(countries);
            if (countries != null) {
                countriesList.clear();
                countriesList.addAll(countries);
                adapter.notifyDataSetChanged();
            }
            swipeRefresh.setRefreshing(false);
        }
    }

    private void getDataRetro() {

        swipeRefresh.setRefreshing(true);

        // se tiver conexao faz get, senao pega do sqlite
        if (isConnected()) {
            HttpRetro.getCountriesClient().getCountries().enqueue(new Callback<List<Countries>>() {
                @Override
                public void onResponse(Call<List<Countries>> call, Response<List<Countries>> response) {
                    if (response.isSuccessful()) {
                        List<Countries> countriesBody = response.body();
                        countriesList.clear();

                        db.excluirAll();

                        for (Countries countries : countriesBody) {
                            System.out.println(countriesBody);
                            countriesList.add(countries);
                            db.inserir(countries);
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        System.out.println(response.errorBody());
                    }
                    swipeRefresh.setRefreshing(false);
                }

                @Override
                public void onFailure(Call<List<Countries>> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        } else {
            swipeRefresh.setRefreshing(false);
            Toast.makeText(this, "Sem Conexão, listando Countries do banco...", Toast.LENGTH_SHORT).show();
            getDataSqlite();
        }

    }

    private void getDataSqlite () {
        countriesList.clear();
        countriesList.addAll(db.listarCountries());
        adapter.notifyDataSetChanged();
    }

    public Boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm != null) {
            NetworkInfo ni = cm.getActiveNetworkInfo();
            return ni != null && ni.isConnected();
        }

        return false;
    }

}


