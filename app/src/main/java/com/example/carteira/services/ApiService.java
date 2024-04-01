package com.example.carteira.services;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ApiService {

    private final String url = "http://10.0.2.2:8080";
    private OkHttpClient client = new OkHttpClient();

    public Response getToken(String username, String password) throws IOException {

        try {
            String registerData = "{\"matricula\":\"" + username + "\",\"senha\":\"" + password + "\"}";

            MediaType json = MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(registerData, json);

            Request request = new Request.Builder()
                    .url(url + "/login")
                    .post(body)
                    .addHeader("content-type", "application/json; charset=utf-8")
                    .build();

            return client.newCall(request).execute();
        } catch (Exception e) {
            throw new RuntimeException("Algo deu Errado!");
        }
    }

    public Response validateToken(String token) {

        try {
            Request request = new Request.Builder()
                    .url(url + "/validarToken")
                    .addHeader("Authorization", "Bearer "+ token)
                    .build();

            return client.newCall(request).execute();

        } catch (Exception e) {
            throw new RuntimeException("Falha ao validar Token!");
        }
    }

    public Response getUsuario(String id, String token) throws IOException {

        try {
            Request request = new Request.Builder()
                    .url(url + "/usuario/" + id)
                    .get()
                    .addHeader("content-type", "application/json; charset=utf-8")
                    .addHeader("Authorization", "Bearer " + token)
                    .build();

            return client.newCall(request).execute();
        } catch (Exception e) {
            throw new RuntimeException("Falha ao Encontrar o Usuário no Servidor!");
        }
    }
}
