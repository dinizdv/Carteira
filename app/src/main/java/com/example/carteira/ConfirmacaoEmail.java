package com.example.carteira;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.carteira.services.ApiService;

import java.io.IOException;

import okhttp3.Response;

public class ConfirmacaoEmail extends AppCompatActivity {

    ImageView fotoPerfil;
    EditText emailInput;
    Button confirmar, confirmDialog;
    Dialog dialog;
    SharedPreferences sharedPreferences;
    ApiService apiService = new ApiService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmacao_email);
        sharedPreferences = getSharedPreferences("Controle_Acesso", Context.MODE_PRIVATE);

        fotoPerfil = findViewById(R.id.foto_confirm_email);
        emailInput = findViewById(R.id.email_confirm);
        confirmar = findViewById(R.id.buttonConfirmEmail);
        dialog = new Dialog(this);

        confirm(this);

    }

    private void confirm(Activity activity) {
        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailInput.getText().toString();
                String token = sharedPreferences.getString("JWToken", "");

                try {
                    Response response = apiService.requestCode(email, token);
                    if (response.isSuccessful()) {
                        dialog.setContentView(R.layout.confirm_email);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                        confirmDialog = dialog.findViewById(R.id.confirmDialog);
                        confirmDialog.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                Intent intent = new Intent(activity, CodeVerification.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                    } else {
                        Toast.makeText(activity, "Credencial Inválida", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}