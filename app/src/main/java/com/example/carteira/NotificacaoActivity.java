package com.example.carteira;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.carteira.models.Notificacao;
import com.example.carteira.models.NotificacaoAdapter;
import com.example.carteira.services.ApiService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Response;

public class NotificacaoActivity extends AppCompatActivity {

    Button btnVoltar;
    ArrayAdapter<Notificacao> adapter;
    ArrayList<Notificacao> arrayList;
    ListView listView;
    String token, id;
    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificacao);

        SharedPreferences sharedPreferences = getSharedPreferences("Controle_Acesso", Context.MODE_PRIVATE);

        token = sharedPreferences.getString("JWToken", "");
        id = sharedPreferences.getString("id", "");

        apiService = new ApiService();

        btnVoltar = findViewById(R.id.back_button);
        listView = findViewById(R.id.listView);

        arrayList = new ArrayList<>();
        adapter = new NotificacaoAdapter(this,  arrayList);
        listView.setAdapter(adapter);

        getNotificacoes();
        voltar();
    }

    private void getNotificacoes() {
        try {
            Response response = apiService.getNotiUsuario(id, token);
            if (response.isSuccessful()) {
                String jsonData = response.body().string();

                JSONArray jsonArray = new JSONArray(jsonData);
                arrayList.clear();

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String mensagem = jsonObject.getString("mensagem");
                    String id = jsonObject.getString("id");
                    String titulo;

                    int index = mensagem.indexOf(":");
                    if (index != -1) {
                        titulo = mensagem.substring(0, index);
                        mensagem = mensagem.substring(index + 1).trim();
                    } else {
                        titulo = "Sem Título";
                    }

                    Notificacao notificacao = new Notificacao(id, titulo, mensagem);
                    arrayList.add(notificacao);
                }
                runOnUiThread(() -> adapter.notifyDataSetChanged());
            }
        } catch (JSONException | IOException e) {
            runOnUiThread(() -> Toast.makeText(NotificacaoActivity.this, "Erro ao Processar as Notificações", Toast.LENGTH_LONG).show());
            finish();
            throw new RuntimeException(e);
        }

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