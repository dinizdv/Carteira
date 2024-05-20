package com.example.carteira;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.carteira.fragments.ContaFragment;
import com.example.carteira.fragments.DuvidasFragment;
import com.example.carteira.fragments.InitialFragment;
import com.example.carteira.fragments.QrcodeFragment;
import com.example.carteira.services.ApiService;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Response;

public class Initial extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    DuvidasFragment duvidasFragment = new DuvidasFragment();
    QrcodeFragment qrcodeFragment = new QrcodeFragment();
    ContaFragment contaFragment = new ContaFragment();
    ApiService apiService = new ApiService();
    Bundle bundle = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);
        SharedPreferences sharedPreferences = getSharedPreferences("Controle_Acesso", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("JWToken", "");
        String id = sharedPreferences.getString("idUsuario", "");

        InitialFragment initialFragment = new InitialFragment();
        validateQrcode(id, token);

        qrcodeFragment.setArguments(bundle);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, initialFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.inicio) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, initialFragment).commit();
                    return true;
                } else if (id == R.id.duvidas) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, duvidasFragment).commit();
                    return true;
                } else if (id == R.id.qrcode) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, qrcodeFragment).commit();
                    return true;
                } else if (id == R.id.conta) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, contaFragment).commit();
                    return true;
                }
                return false;
            }
        });
    }

    private void validateQrcode(String id, String token) {
        try {
            Response response = apiService.validarQrCode(id, token);
            if (response.isSuccessful()) {
                String body = response.body().string();
                JSONObject json = new JSONObject(body);

                String validade = json.getString("validade_hoje");

                bundle.putString("validadeQRCode", validade);
            }
        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }
    }

}
