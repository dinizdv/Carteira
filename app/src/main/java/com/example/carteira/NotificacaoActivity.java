package com.example.carteira;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class NotificacaoActivity extends AppCompatActivity {

    Button btnVoltar;
    ArrayAdapter<String> adapter;
    ArrayList<String> arrayList;
    ListView listView;
    String token, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificacao);

        SharedPreferences sharedPreferences = getSharedPreferences("Controle_Acesso", Context.MODE_PRIVATE);

        token = sharedPreferences.getString("JWToken", "");
        id = sharedPreferences.getString("id", "");

        btnVoltar = findViewById(R.id.back_button);
        listView = findViewById(R.id.listView);

        arrayList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, 0, arrayList);
        listView.setAdapter(adapter);


        voltar();
    }

    private void getNotificacoes() {

    }

    private void voltar() {
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}