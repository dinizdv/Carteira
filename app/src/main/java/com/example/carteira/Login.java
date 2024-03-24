package com.example.carteira;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carteira.controllers.LoginController;

public class Login extends AppCompatActivity {

    EditText etUser, etPassword;
    Button btnLogin;
    LoginController loginController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        etUser = findViewById(R.id.username);
        etPassword = findViewById(R.id.password);

        btnLogin = findViewById(R.id.buttonLogin);

        loginController = new LoginController(this);

        login();

    }

    private void login() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUser.getText().toString();
                String password = etPassword.getText().toString();

                loginController.loginUsuario(username, password);

                Intent intent = new Intent(Login.this, Initial.class);
                startActivity(intent);
            }
        });
    }
}
