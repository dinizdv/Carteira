package com.example.carteira.controllers;

import com.example.carteira.models.MainActivityModel;

public class MainActivityController {
    private MainActivityModel mainActivityModel;

    public MainActivityController(String token) {
        this.mainActivityModel = new MainActivityModel(token);
    }

    public boolean validarToken() {
        return mainActivityModel.validateToken();
    }
}
