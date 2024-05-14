package com.example.carteira.fragments;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.example.carteira.R;
import com.example.carteira.models.UsuarioModel;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class QrcodeFragment extends Fragment {

    private Button btnGenerateQR;
    private ImageView imageViewQR;
    UsuarioModel usuario;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qrcode, container, false);

        usuario = (UsuarioModel) getActivity().getIntent().getSerializableExtra("Usuario");

        btnGenerateQR = view.findViewById(R.id.btnGenerateQR);
        imageViewQR = view.findViewById(R.id.imageViewQR);

        btnGenerateQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateQRCode(usuario.getMatricula());
            }
        });

        return view;
    }

    private void generateQRCode(String text) {
        try {
            Writer writer = new MultiFormatWriter();
            BitMatrix bitMatrix = writer.encode(text, BarcodeFormat.QR_CODE, 1000, 1000);

            Bitmap bitmap = Bitmap.createBitmap(1000, 1000, Bitmap.Config.ARGB_8888);
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
}