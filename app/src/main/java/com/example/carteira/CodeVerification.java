package com.example.carteira;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.carteira.services.ApiService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Response;

public class CodeVerification extends AppCompatActivity {
    EditText emailInput, codigoInput;
    Button confirmar;
    ApiService apiService = new ApiService();

    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_verification);

        emailInput = findViewById(R.id.email_confirm_codigo);
        codigoInput = findViewById(R.id.codigo_confirm);

        confirmar = findViewById(R.id.buttonConfirmCodigo);

        token = getIntent().getStringExtra("JWToken");

        confirmCode(this);
    }

    private void confirmCode(Activity activity) {
        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailInput.getText().toString();
                String code = codigoInput.getText().toString();

                try {
                    Response response = apiService.verifyCode(email, code);
                    if (response.isSuccessful()) {

                        JSONObject json = new JSONObject(response.body().string());
                        String jwtToken = json.getString("tokenJWT");

                        Intent intent = new Intent(activity, TrocarSenha.class);
                        intent.putExtra("JWToken", jwtToken);

                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(activity, "Credenciais Inválidas", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException | JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}