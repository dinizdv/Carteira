package com.example.carteira;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.carteira.fragments.ContaFragment;
import com.example.carteira.fragments.DuvidasFragment;
import com.example.carteira.fragments.InitialFragment;
import com.example.carteira.fragments.QrcodeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class Initial extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    InitialFragment initialFragment = new InitialFragment();
    DuvidasFragment duvidasFragment = new DuvidasFragment();
    QrcodeFragment qrcodeFragment = new QrcodeFragment();
    ContaFragment contaFragment = new ContaFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, initialFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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
}
