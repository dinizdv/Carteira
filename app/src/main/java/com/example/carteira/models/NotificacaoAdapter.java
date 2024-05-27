package com.example.carteira.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.carteira.R;

import java.util.List;

public class NotificacaoAdapter extends ArrayAdapter<Notificacao> {

    public NotificacaoAdapter(Context context, List<Notificacao> notificacoes) {
        super(context, 0, notificacoes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Notificacao notificacao = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.simple_list, parent, false);
        }

        TextView textViewTitulo = convertView.findViewById(R.id.textViewTitulo);
        TextView textViewItem = convertView.findViewById(R.id.textViewItem);

        textViewTitulo.setText(notificacao.getTitulo());
        textViewItem.setText(notificacao.getMensagem());

        return convertView;
    }
}
