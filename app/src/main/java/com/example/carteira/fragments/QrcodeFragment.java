package com.example.carteira.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
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

import com.example.carteira.R;
import com.example.carteira.models.UsuarioModel;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.Base64;

public class QrcodeFragment extends Fragment {

    private TextView txtView;
    private ImageView imageViewQR, fotoPerfil;
    UsuarioModel usuario;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qrcode, container, false);

        usuario = (UsuarioModel) getActivity().getIntent().getSerializableExtra("Usuario");
        String image = getActivity().getIntent().getStringExtra("ImagemUsuario");

        imageViewQR = view.findViewById(R.id.imageViewQR);
        fotoPerfil = view.findViewById(R.id.imageViewFotoQrCode);
        txtView = view.findViewById(R.id.nome_usuario_fragment_qrcode);

        generateQRCode(usuario.getMatricula());

        Bitmap imagem = getFotoUsuario(image);
        fotoPerfil.setImageBitmap(imagem);
        txtView.setText(usuario.getNome());

        return view;
    }

    private void generateQRCode(String text) {
        try {
            Writer writer = new MultiFormatWriter();
            BitMatrix bitMatrix = writer.encode(text, BarcodeFormat.QR_CODE, 900, 900);

            Bitmap bitmap = Bitmap.createBitmap(900, 900, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            canvas.drawColor(Color.TRANSPARENT);

            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            int smallerDimension = Math.min(width, height);
            for (int x = 0; x < smallerDimension; x++) {
                for (int y = 0; y < smallerDimension; y++) {
                    if (bitMatrix.get(x, y)) {
                        bitmap.setPixel(x, y, Color.BLACK);
                    }
                }
            }

            imageViewQR.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
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