package com.example.carteira.services;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ApiService {

    private final String url = "https://apicontroleacesso-1.onrender.com";
    private OkHttpClient client = new OkHttpClient().newBuilder().callTimeout(1000, TimeUnit.SECONDS).build();

    public Response getToken(String username, String password) throws IOException {

        try {
            String registerData = "{\"email\":\"" + username + "\",\"senha\":\"" + password + "\"}";

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
                    .url(url + "/login/validarToken")
                    .addHeader("Authorization", "Bearer "+ token)
                    .build();

            return client.newCall(request).execute();

        } catch (Exception e) {
            throw new RuntimeException("Falha ao validar Token!");
        }
    }

    public Response getUsuario(String id, String token) {

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

    public Bitmap getFotoUsuario(String id, String token) {

        try {
            Request request = new Request.Builder()
                    .url(url + "/usuario/imagem/" + id)
                    .get()
                    .addHeader("Authorization", "Bearer " + token)
                    .build();

            Response response = client.newCall(request).execute();

            if (!response.isSuccessful()) {
                throw new IOException("Código de resposta inesperado: " + response);
            }

            return getBitmapFromResponse(response);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Bitmap getBitmapFromResponse(Response response) throws IOException {
        if (response.body() != null) {
            byte[] bytes = response.body().bytes();
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        } else {
            return null;
        }
    }
}
