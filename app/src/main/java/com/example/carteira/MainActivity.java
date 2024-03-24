package com.example.carteira;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;

import androidx.appcompat.app.AppCompatActivity;
import com.example.carteira.controllers.MainActivityController;

public class MainActivity extends AppCompatActivity {

    MainActivityController controller;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        sharedPreferences = getSharedPreferences("Controle_Acesso", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("JWToken", null);

        controller = new MainActivityController(token);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                if (token != null && controller.validarToken()) {
                    Intent intent = new Intent(MainActivity.this, Initial.class);
                    startActivity(intent);
                } else {
                    sharedPreferences.edit().remove("JWToken").apply();

                    Intent intent = new Intent(MainActivity.this, Login.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 3000);
    }
}


