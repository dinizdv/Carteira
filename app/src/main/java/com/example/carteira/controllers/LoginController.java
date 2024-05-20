package com.example.carteira.controllers;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;
import com.example.carteira.Initial;
import com.example.carteira.models.UsuarioModel;
import com.example.carteira.repositories.UsuarioRepository;
import com.example.carteira.services.ApiService;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;

import okhttp3.FormBody;
import okhttp3.Response;

public class LoginController {
    private Activity activity;
    private ApiService apiService;
    private UsuarioRepository usuarioRepository;
    private SharedPreferences sharedPreferences;


    public LoginController(Activity activity, ApiService apiService, UsuarioRepository usuarioRepository) {
        this.apiService = apiService;
        this.activity = activity;
        this.usuarioRepository = usuarioRepository;
        this.sharedPreferences = activity.getSharedPreferences("Controle_Acesso", Context.MODE_PRIVATE);
    }

    public void login(String username, String password) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = apiService.getToken(username, password);
                    if (response.isSuccessful()) {
                        processarTokenJWT(response);
                    } else {
                        mostrarToast("Credenciais Inválidas");
                    }
                } catch (IOException e) {
                    mostrarToast("Erro de conexão com o servidor");
                } catch (JSONException e) {
                    mostrarToast("Erro ao processar resposta");
                }
            }
        }).start();
    }

    private void processarTokenJWT(Response response) throws IOException, JSONException {
        String sResponse = response.body().string();

        JSONObject json = new JSONObject(sResponse);
        String jwtToken = json.getString("tokenJWT");
        String idUsuario = json.getString("id");

        String nome = json.getString("nome");
        UsuarioModel usuario = usuarioRepository.getByNome(nome);

        salvarTokenJWT(jwtToken);

        if (usuario != null) {
            irParaInitialActivity(usuario, null);
        } else {
            processarUsuario(idUsuario, jwtToken);
        }
    }

    private void salvarTokenJWT(String jwtToken) {
        SharedPreferences.Editor editor = sharedPreferences.edit().putString("JWToken", jwtToken);
        editor.apply();
    }

    private void processarUsuario(String idUsuario, String jwtToken) throws IOException, JSONException {

        Response userResponse = apiService.getUsuario(idUsuario, jwtToken);

        if (userResponse.isSuccessful()) {
            String body = userResponse.body().string();
            JSONObject jsonu = new JSONObject(body);

            UsuarioModel usuarioModel = extrairUsuarioModel(jsonu);

            String image = apiService.getFotoUsuario(idUsuario, jwtToken);
                if (image != null) {
                    adicionarFotoNoBanco(usuarioModel, image);
                    irParaInitialActivity(usuarioModel, image);
                } else {
                    mostrarToast("Falha ao obter imagem do usuário");
                }
        } else {
            mostrarToast("Erro ao obter informações do usuário");
        }
    }

    private UsuarioModel extrairUsuarioModel(JSONObject jsonu) throws JSONException {
        String nome = jsonu.getString("nome");
        LocalDate data_nascimento = LocalDate.parse(jsonu.getString("dataNascimento"));
        String matricula = jsonu.getString("matricula");
        String curso = jsonu.getString("curso");
        String email = jsonu.getString("email");
        String cpf = jsonu.getString("cpf");
        String nivel = jsonu.getString("nivel");

        return new UsuarioModel(data_nascimento, nome, matricula, curso, cpf, email, null, nivel, "ROLE_USER");
    }

    private void adicionarUsuarioNoBanco(UsuarioModel usuarioModel) {
        Long id = usuarioRepository.adicionarUsuario(usuarioModel);

        if (id != -1) {
            UsuarioModel user = usuarioRepository.getByNome(usuarioModel.getNome());

            SharedPreferences.Editor editor = sharedPreferences.edit().putString("idUsuario", user.getId().toString());
            editor.apply();

        } else {
            mostrarToast("Tente Novamente!");
        }
    }

    private void irParaInitialActivity(UsuarioModel usuario, String foto) {

        Intent intent = new Intent(activity, Initial.class);
        intent.putExtra("Usuario", usuario);

        intent.putExtra("ImagemUsuario", foto);

        activity.startActivity(intent);
        activity.finish();
    }

    private void adicionarFotoNoBanco(UsuarioModel usuario, String foto) {

        usuario.setFoto(foto);

        adicionarUsuarioNoBanco(usuario);
        irParaInitialActivity(usuario, foto);
    }


    private void mostrarToast(String message) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
            }
        });
    }


}
