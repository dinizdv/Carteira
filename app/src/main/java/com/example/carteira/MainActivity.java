package com.example.carteira;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import androidx.appcompat.app.AppCompatActivity;
import com.example.carteira.controllers.MainActivityController;
import com.example.carteira.repositories.UsuarioRepository;
import com.example.carteira.services.ApiService;

public class MainActivity extends AppCompatActivity {

    MainActivityController controller;
    ApiService apiService;
    UsuarioRepository usuarioRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        apiService = new ApiService();
        usuarioRepository = new UsuarioRepository(this);
        controller = new MainActivityController(this, apiService, usuarioRepository);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                controller.validarToken();
            }
        }, 3000);
    }
}


