package com.example.app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.app.Model.Countries;
import com.example.app.R;

import java.util.List;

public class Adapter extends ArrayAdapter<Countries> {

    public Adapter (Context context, List<Countries> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent) {
        Countries countries = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_country, parent, false);
        }

        TextView textViewNome = (TextView) convertView.findViewById(R.id.nome);
        TextView textViewCapital = (TextView) convertView.findViewById(R.id.capital);

        textViewNome.setText(countries.nome);
        textViewCapital.setText(countries.sigla);

        return convertView;
    }
}
