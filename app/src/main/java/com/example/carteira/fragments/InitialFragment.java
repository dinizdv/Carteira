package com.example.carteira.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.example.carteira.R;
import com.example.carteira.models.UsuarioModel;


public class InitialFragment extends Fragment {

    private TextView txt1, txt2, txtTitle;
    private Button btnAcessar;

    private LinearLayout rl1, rl2;
    private UsuarioModel usuario;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_initial, container, false);

        usuario =  (UsuarioModel) getActivity().getIntent().getSerializableExtra("Usuario");

        txtTitle = view.findViewById(R.id.titleInit);
        txtTitle.setText("Bem Vindo, " + usuario.getNome() +"!");

        txt1 = view.findViewById(R.id.nome_text_view);
        txt1.setText(usuario.getNome());

        txt2 = view.findViewById(R.id.matricula_text_view);
        txt2.setText(usuario.getMatricula());

        rl1 = view.findViewById(R.id.card_layout_qr);
        rl2 = view.findViewById(R.id.card_layout_mensagem);
        enviarEmail();

        btnAcessar = view.findViewById(R.id.edit_button);

        return view;
    }

    private void enviarEmail() {
        rl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:senai@gmail.com"));
                intent.putExtra(Intent.EXTRA_SUBJECT, "Solicitação de Nova Carteirinha");
                intent.putExtra(Intent.EXTRA_TEXT, "Olá, sou o " + usuario.getNome() + "do curso " + usuario.getCurso() + " e gostaria de solicitar uma nova carteirinha!");
                startActivity(intent);
            }
        });
    }
}