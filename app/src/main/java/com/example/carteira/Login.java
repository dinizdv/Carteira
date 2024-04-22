package com.example.carteira;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carteira.controllers.LoginController;
import com.example.carteira.repositories.UsuarioRepository;
import com.example.carteira.services.ApiService;

public class Login extends AppCompatActivity {

    EditText etUser, etPassword;
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

        btnLogin = findViewById(R.id.buttonLogin);

        apiService = new ApiService();
        usuarioRepository = new UsuarioRepository(this);

        loginController = new LoginController(this, apiService, usuarioRepository);

        login();

    }

    private void login() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUser.getText().toString();
                String password = etPassword.getText().toString();

                loginController.login(username, password);
            }
        });
    }
}
