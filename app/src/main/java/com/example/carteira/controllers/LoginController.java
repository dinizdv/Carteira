package com.example.carteira.controllers;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.example.carteira.Initial;
import com.example.carteira.models.LoginModel;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import okhttp3.Response;

public class LoginController {
    private LoginModel loginModel;
    private SharedPreferences sharedPreferences;
    private Context context;

    public LoginController(Context context) {
        this.context = context;
        loginModel = new LoginModel();
        sharedPreferences = context.getSharedPreferences("Controle_Acesso", Context.MODE_PRIVATE);
    }

    public void loginUsuario(String username, String password) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = loginModel.login(username, password);
                    String sResponse = response.body().string();
                    JSONObject json = new JSONObject(sResponse);

                    if (response.isSuccessful()) {
                        String jwtToken = json.getString("tokenJWT");

                        SharedPreferences.Editor editor = sharedPreferences.edit().putString("JWToken", jwtToken);
                        editor.apply();

                        irParaInitialActivity();
                    } else {
                       String statusError = json.getString("error");
                       Log.i("APP", statusError);
                       mostrarToast("Credenciais Inválidas");
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void irParaInitialActivity() {
        Intent intent = new Intent(context, Initial.class);
        context.startActivity(intent);
        ((Activity) context).finish();
    }

    private void mostrarToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
