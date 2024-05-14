package com.example.carteira;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.carteira.services.ApiService;

import java.io.IOException;

import okhttp3.Response;

public class TrocarSenha extends AppCompatActivity {

    EditText trocaSenha, trocaSenhaNovamente;
    Button btnEnviar;

    ApiService apiService = new ApiService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trocar_senha);

        SharedPreferences sharedPreferences = getSharedPreferences("Controle_Acesso", Context.MODE_PRIVATE);

        trocaSenha = findViewById(R.id.troca_senha);
        trocaSenhaNovamente = findViewById(R.id.troca_senha_novamente);

        btnEnviar = findViewById(R.id.buttonEnviar);

        String token = sharedPreferences.getString("JWToken", "");

        enviarTrocaSenha(this, token);
    }

    private void enviarTrocaSenha(Context context, String token) {
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String senha =  trocaSenha.getText().toString();
                String senhaNovamente = trocaSenhaNovamente.getText().toString();

                if (senha.equals(senhaNovamente)) {
                    try {
                        Response response = apiService.resetSenha(senha, token);
                        if (response.isSuccessful()) {
                            Toast.makeText(context, "Sucesso ao trocar Senha", Toast.LENGTH_LONG);
                            finish();
                        } else {
                            Toast.makeText(context, "Houve um Erro ao trocar Senha", Toast.LENGTH_LONG);
                            finish();
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    Toast.makeText(context, "As Senhas não Correspondem", Toast.LENGTH_LONG);
                }

            }
        });
    }
}