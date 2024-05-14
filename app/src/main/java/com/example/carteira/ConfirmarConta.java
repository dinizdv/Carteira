package com.example.carteira;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carteira.models.UsuarioModel;
import com.example.carteira.services.ApiService;

import java.io.IOException;
import java.util.Base64;

import okhttp3.Response;

public class ConfirmarConta extends AppCompatActivity {

    ImageView img;
    EditText email, senha;
    Button btnConfirmarConta;

    ApiService apiService = new ApiService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_conta);

        img = findViewById(R.id.foto_confirm_account);
        email = findViewById(R.id.username_confirm);
        senha = findViewById(R.id.password_confirm);
        btnConfirmarConta = findViewById(R.id.buttonConfirm);

        UsuarioModel usuario = (UsuarioModel) getIntent().getSerializableExtra("Usuario");

        Bitmap image = getFotoUsuario(usuario.getFoto());
        img.setImageBitmap(image);

        confirmarConta(this, usuario);

    }

    private Bitmap getFotoUsuario(String foto) {
        byte[] byteArray = Base64.getDecoder().decode(foto);

        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        Bitmap roundedBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(roundedBitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));

        canvas.drawRoundRect(new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight()), bitmap.getWidth() / 2, bitmap.getHeight() / 2, paint);
        return roundedBitmap;
    }

    private void confirmarConta(Context context, UsuarioModel usuario) {
        btnConfirmarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailUser = email.getText().toString();
                String senhaUser = senha.getText().toString();

                try {
                    Response response = apiService.getToken(emailUser, senhaUser);
                    if (response.isSuccessful()) {
                        Intent intent = new Intent(context, TrocarSenha.class);
                        intent.putExtra("Usuario", usuario);

                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(context, "Credenciais Inválidas", Toast.LENGTH_LONG).show();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}