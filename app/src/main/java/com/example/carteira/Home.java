package com.example.carteira;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Home extends AppCompatActivity {

    EditText etUser, etPassword;
    Button btnLogin;
    OkHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        etUser = findViewById(R.id.username);
        etPassword = findViewById(R.id.password);

        client = new OkHttpClient();
        btnLogin = findViewById(R.id.buttonLogin);

        login();

    }

    private void sendRequest(String url) {
        new Thread(new Runnable() {
            final String registerData = "{\"matricula\":\"" + etUser.getText().toString() + "\",\"password\":\"" + etPassword.getText().toString() + "\"}";
            @Override
            public void run() {
                try {
                    MediaType JSON = MediaType.parse("application/json; charset=utf-8");

                    RequestBody body = RequestBody.create(JSON, registerData);

                    Request request = new Request.Builder()
                            .url(url)
                            .post(body)
                            .addHeader("content-type", "application/json; charset=utf-8")
                            .build();

                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        String result = response.body().string();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.d("App",result);
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void login() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://10.0.2.2:8080/login";
                sendRequest(url);

                // abrir tela de fragmentos
                Intent intent = new Intent(Home.this, Initial.class);
                startActivity(intent);
            }
        });
    }
}