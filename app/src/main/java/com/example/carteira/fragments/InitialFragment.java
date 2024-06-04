package com.example.carteira.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

import com.example.carteira.ActivityCarteira;
import com.example.carteira.R;
import com.example.carteira.models.UsuarioModel;

import org.json.JSONException;
import org.json.JSONObject;


public class InitialFragment extends Fragment {


    private TextView txt1, txt2, txtTitle;
    private Button btnAcessar;

    private LinearLayout rl2;
    private UsuarioModel usuario;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_initial, container, false);

        usuario = (UsuarioModel) getActivity().getIntent().getSerializableExtra("Usuario");

        txtTitle = view.findViewById(R.id.titleInit);
        txtTitle.setText("Bem Vindo (a), " + usuario.getNome() +"!");

        txt1 = view.findViewById(R.id.nome_text_view);
        txt1.setText(usuario.getNome());

        txt2 = view.findViewById(R.id.matricula_text_view);
        txt2.setText(usuario.getMatricula());

        rl2 = view.findViewById(R.id.card_layout_mensagem);
        enviarEmail();

        btnAcessar = view.findViewById(R.id.edit_button);
        abrirCarteirinha(this.getContext());

        return view;
    }

    private void enviarEmail() {
        rl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomeCurso;
                try {
                    JSONObject jsonObject = new JSONObject(usuario.getCurso());
                    nomeCurso = jsonObject.getString("nome");
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                Intent selectorIntent = new Intent(Intent.ACTION_SENDTO);
                selectorIntent.setData(Uri.parse("mailto:"));

                final Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"senai@gmail.com"});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Solicitação de Nova Carteirinha!");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Olá, sou o " + usuario.getNome() + " do curso " + nomeCurso + " e gostaria de solicitar uma nova carteirinha!");
                emailIntent.setSelector(selectorIntent);

                startActivity(Intent.createChooser(emailIntent, "teste"));
            }
        });
    }

    private void abrirCarteirinha(Context context) {
        btnAcessar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ActivityCarteira.class);
                intent.putExtra("Usuario", usuario);
                startActivity(intent);
            }
        });
    }
}