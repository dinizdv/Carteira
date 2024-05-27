package com.example.carteira.services;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;
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
                    .url(url + "/validarToken")
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

    public String getFotoUsuario(String id, String token) {

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

            return getBase64Image(response);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Response resetSenha(String senha, String token) throws IOException {

        try {
            String registerData = "{\"senha\":\"" + senha + "\"}";

            MediaType json = MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(registerData, json);

            Request request = new Request.Builder()
                    .url(url + "/resetpassword")
                    .post(body)
                    .addHeader("Authorization", "Bearer " + token)
                    .addHeader("content-type", "application/json; charset=utf-8")
                    .build();

            return client.newCall(request).execute();
        } catch (Exception e) {
            throw new RuntimeException("Algo deu Errado!");
        }
    }

    public Response requestCode(String email) throws IOException {

        try {
            String registerData = "{\"email\":\"" + email + "\"}";

            MediaType json = MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(registerData, json);

            Request request = new Request.Builder()
                    .url(url + "/requestcode")
                    .post(body)
                    .addHeader("content-type", "application/json; charset=utf-8")
                    .build();

            return client.newCall(request).execute();
        } catch (Exception e) {
            throw new RuntimeException("Algo deu Errado!");
        }
    }

    public Response verifyCode(String email, String code) throws IOException {

        try {
            String registerData = "{\"email\":\"" + email + "\",\"code\":\"" + code + "\"}";

            MediaType json = MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(registerData, json);

            Request request = new Request.Builder()
                    .url(url + "/verifycode")
                    .post(body)
                    .addHeader("content-type", "application/json; charset=utf-8")
                    .build();

            return client.newCall(request).execute();
        } catch (Exception e) {
            throw new RuntimeException("Algo deu Errado!");
        }
    }

    public Response validarQrCode(String id, String token) throws IOException {

        try {

            Request request = new Request.Builder()
                    .url(url + "/usuario/qrcode/" + id)
                    .get()
                    .addHeader("Authorization", "Bearer " + token)
                    .build();

            return client.newCall(request).execute();
        } catch (Exception e) {
            throw new RuntimeException("Algo deu Errado!");
        }
    }

    private String getBase64Image(Response response) throws IOException {
        if (response.body() != null) {
            byte[] bytes = response.body().bytes();
            return Base64.getEncoder().encodeToString(bytes);
        } else {
            return null;
        }
    }

}
