package com.example.carteira.controllers;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;
import com.example.carteira.Initial;
import com.example.carteira.services.ApiService;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import okhttp3.Response;

public class LoginController {
    private Activity activity;
    private ApiService apiService;
    private SharedPreferences sharedPreferences;

    String idUsuario;

    public LoginController(Activity activity, ApiService apiService) {
        this.apiService = apiService;
        this.activity = activity;
        this.sharedPreferences = activity.getSharedPreferences("Controle_Acesso", Context.MODE_PRIVATE);
    }

    public void login(String username, String password) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = apiService.getToken(username, password);

                    if (response.isSuccessful()) {
                        String sResponse = response.body().string();
                        JSONObject json = new JSONObject(sResponse);

                        String jwtToken = json.getString("tokenJWT");
                        idUsuario = json.getString("id");

                        SharedPreferences.Editor editor = sharedPreferences.edit().putString("JWToken", jwtToken);
                        editor.apply();

                        apiService.getUsuario(idUsuario, jwtToken);

                        irParaInitialActivity();

                    } else {
                        mostrarToast("Credenciais Inválidas");
                    }
                } catch (IOException | JSONException e) {
                    throw new RuntimeException("Erro ao realizar o Login");
                }
            }
        });
    }

    private void irParaInitialActivity() {
        Intent intent = new Intent(activity, Initial.class);
        activity.startActivity(intent);
        activity.finish();
    }

    private void mostrarToast(String message) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}
