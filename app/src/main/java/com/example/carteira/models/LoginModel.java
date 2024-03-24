package com.example.carteira.models;
import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginModel {
    private OkHttpClient client;

    public LoginModel() {
        client = new OkHttpClient();
    }

    public Response login(String username, String password) throws IOException {
        String url = "http://10.0.2.2:8080/login";

        String registerData = "{\"matricula\":\"" + username + "\",\"senha\":\"" + password + "\"}";

        MediaType json = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(registerData, json);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("content-type", "application/json; charset=utf-8")
                .build();

        return client.newCall(request).execute();
    }
}
