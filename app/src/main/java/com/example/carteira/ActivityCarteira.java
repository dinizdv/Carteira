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
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.carteira.fragments.InitialFragment;
import com.example.carteira.models.UsuarioModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Base64;


public class ActivityCarteira extends AppCompatActivity {

    Button btnbackcard;
    TextView nome, matricula, cpf, dataNascimento, curso;
    ImageView fotoUser;
    UsuarioModel usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carteira);

        usuario = (UsuarioModel) getIntent().getSerializableExtra("Usuario");

        btnbackcard = findViewById(R.id.back_button);

        fotoUser = findViewById(R.id.fotoUser);

        nome = findViewById(R.id.nomeUser);
        matricula = findViewById(R.id.matriculaUser);
        cpf = findViewById(R.id.cpfUser);
        dataNascimento = findViewById(R.id.dataUser);
        curso = findViewById(R.id.cursoUser);

        Bitmap image = getFotoUsuario(usuario.getFoto());
        fotoUser.setImageBitmap(image);

        nome.setText(usuario.getNome());
        matricula.setText(usuario.getMatricula());
        cpf.setText(usuario.getCpf());
        dataNascimento.setText(usuario.getData_nascimento().toString());
        try {
            JSONObject json = new JSONObject(usuario.getCurso());
            curso.setText(json.getString("nome"));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        voltarHome();
    }

    private void voltarHome () {
        btnbackcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
}