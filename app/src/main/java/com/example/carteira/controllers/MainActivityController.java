package com.example.carteira.controllers;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.carteira.Initial;
import com.example.carteira.Login;
import com.example.carteira.models.UsuarioModel;
import com.example.carteira.repositories.UsuarioRepository;
import com.example.carteira.services.ApiService;

import okhttp3.Response;

public class MainActivityController {
    private ApiService apiService;
    private Activity activity;
    private SharedPreferences sharedPreferences;
    private UsuarioRepository usuarioRepository;

    public MainActivityController(Activity activity, ApiService apiService, UsuarioRepository usuarioRepository) {
        this.apiService = apiService;
        this.activity = activity;
        this.usuarioRepository = usuarioRepository;
        this.sharedPreferences = activity.getSharedPreferences("Controle_Acesso", Context.MODE_PRIVATE);
    }

    public void validarToken() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String token = sharedPreferences.getString("JWToken", "");
                String id = sharedPreferences.getString("idUsuario", "");

                Response response = apiService.validateToken(token);

                if (!token.equals("") && response.isSuccessful()) {
                    UsuarioModel usuario = usuarioRepository.getById(id);

                    Intent intent = new Intent(activity, Initial.class);
                    intent.putExtra("Usuario", usuario);
                    intent.putExtra("ImagemUsuario", usuario.getFoto());

                    activity.startActivity(intent);
                    activity.finish();
                } else {
                    sharedPreferences.edit().remove("JWToken").remove("idUsuario").apply();

                    Intent intent = new Intent(activity, Login.class);
                    activity.startActivity(intent);

                    activity.finish();
                }
            }
        }).start();
    }
}
