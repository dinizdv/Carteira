package com.example.carteira.models;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivityModel {

    private String token;

    public MainActivityModel(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public boolean validateToken() {

        OkHttpClient client = new OkHttpClient();
        final String URL = "http://10.0.2.2:8080/login/validarToken";

        try {
            Request request = new Request.Builder()
                    .url(URL)
                    .addHeader("Authorization", "Bearer "+ getToken())
                    .build();

            Response response = client.newCall(request).execute();

            return response.isSuccessful();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
