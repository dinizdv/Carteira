package com.example.carteira;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carteira.controllers.LoginController;
import com.example.carteira.repositories.UsuarioRepository;
import com.example.carteira.services.ApiService;

public class Login extends AppCompatActivity {

    EditText etUser, etPassword;
    ProgressBar progressBar;
    TextView esqueciSenha;
    Button btnLogin;
    LoginController loginController;
    ApiService apiService;
    UsuarioRepository usuarioRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        etUser = findViewById(R.id.username);
        etPassword = findViewById(R.id.password);
        esqueciSenha = findViewById(R.id.forgetYourPassword);

        progressBar = findViewById(R.id.progress_bar);

        btnLogin = findViewById(R.id.buttonLogin);

        apiService = new ApiService();
        usuarioRepository = new UsuarioRepository(this);

        loginController = new LoginController(this, apiService, usuarioRepository);

        login();
        esqueciSenha(this);

    }

    private void login() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String username = etUser.getText().toString();
                String password = etPassword.getText().toString();

                loginController.login(username, password);
            }
        });
    }

    private void esqueciSenha(Context context) {
        esqueciSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ConfirmacaoEmail.class);
                startActivity(intent);
            }
        });
    }
}
