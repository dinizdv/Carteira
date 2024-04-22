package com.example.carteira.controllers;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import com.example.carteira.Initial;
import com.example.carteira.Login;
import com.example.carteira.services.ApiService;

import okhttp3.Response;

public class MainActivityController {
    private ApiService apiService;
    private Activity activity;
    private SharedPreferences sharedPreferences;

    public MainActivityController(Activity activity, ApiService apiService) {
        this.apiService = apiService;
        this.activity = activity;
        this.sharedPreferences = activity.getSharedPreferences("Controle_Acesso", Context.MODE_PRIVATE);
    }

    public void validarToken() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String token = sharedPreferences.getString("JWToken", "");

                Response response = apiService.validateToken(null);

                if (!token.equals("") && response.isSuccessful()) {
                    Intent intent = new Intent(activity, Initial.class);
                    activity.startActivity(intent);
                } else {
                    sharedPreferences.edit().remove("JWToken").apply();

                    Intent intent = new Intent(activity, Login.class);
                    activity.startActivity(intent);
                    activity.finish();
                }
            }
        }).start();
    }
}
