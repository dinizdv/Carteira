package com.example.carteira.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.carteira.Login;
import com.example.carteira.R;
import com.example.carteira.ConfirmarConta;
import com.example.carteira.models.UsuarioModel;

import java.util.Base64;

public class ContaFragment extends Fragment {

    ImageView fotoPerfil;
    TextView nomeUsuario;
    UsuarioModel usuario;
    Button alterarSenha, sairConta;
    SharedPreferences sharedPreferences;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_conta, container, false);

        sharedPreferences = getActivity().getSharedPreferences("Controle_Acesso", Context.MODE_PRIVATE);

        fotoPerfil = view.findViewById(R.id.imageView3);
        nomeUsuario = view.findViewById(R.id.nome_usuario_fragment_conta);
        alterarSenha = view.findViewById(R.id.alterar_senha);
        sairConta = view.findViewById(R.id.sair_conta);

        usuario = (UsuarioModel) getActivity().getIntent().getSerializableExtra("Usuario");

        String image = getActivity().getIntent().getStringExtra("ImagemUsuario");

        Bitmap img = getFotoUsuario(image);

        fotoPerfil.setImageBitmap(img);
        nomeUsuario.setText(usuario.getNome());

        alterarSenhaActivity(this.getActivity());
        sairConta(this.getActivity());

        return view;
    }

    private void alterarSenhaActivity(Activity activity) {
        alterarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ConfirmarConta.class);
                intent.putExtra("Usuario", usuario);

                startActivity(intent);
            }
        });
    }

    private void sairConta(Activity activity) {
        sairConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences.edit().clear().apply();
                Intent intent = new Intent(activity, Login.class);

                startActivity(intent);
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