package com.example.carteira.particles;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.carteira.R;

public class Particles extends View {

    private Paint paint;
    private float[] particlesX;
    private float[] particlesY;
    private int numParticles = 100;
    private float particleSpeed = 1f; // Velocidade das partículas
    private Handler handler;
    private Runnable invalidateRunnable;

    public Particles(Context context) {
        super(context);
        init();
        startInvalidating();
    }

    public Particles(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        startInvalidating();
    }

    public Particles(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        startInvalidating();
    }

    private void init() {
        paint = new Paint();
//        paint.setColor(getResources().getColor(R.color.particle_color)); // Defina a cor das partículas
        paint.setColor(ContextCompat.getColor(getContext(), R.color.gray)); // Define a c
        handler = new Handler();
        invalidateRunnable = new Runnable() {
            @Override
            public void run() {
                invalidate();
                handler.postDelayed(this, 100); // Redesenha a cada 100 milissegundos
            }
        };
    }

    private void startInvalidating() {
        handler.post(invalidateRunnable);
    }

    private void stopInvalidating() {
        handler.removeCallbacks(invalidateRunnable);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        particlesX = new float[numParticles];
        particlesY = new float[numParticles];

        // Inicialize as posições das partículas aleatoriamente em toda a tela
        for (int i = 0; i < numParticles; i++) {
            particlesX[i] = (float) (Math.random() * w);
            particlesY[i] = (float) (Math.random() * h);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // Desenhe as partículas primeiro
        for (int i = 0; i < numParticles; i++) {
            canvas.drawCircle(particlesX[i], particlesY[i], 5, paint); // Desenhe uma partícula como um círculo
        }

        // Atualize as posições das partículas
        updateParticles();
    }

    private void updateParticles() {
        // Atualize as posições das partículas de forma aleatória
        for (int i = 0; i < numParticles; i++) {
            particlesX[i] += (Math.random() * 2 - 1) * particleSpeed; // Variação aleatória na posição X
            particlesY[i] += (Math.random() * 2 - 1) * particleSpeed; // Variação aleatória na posição Y

            // Se a partícula sair da tela, coloque-a de volta na borda oposta
            if (particlesX[i] < 0) particlesX[i] = getWidth();
            if (particlesX[i] > getWidth()) particlesX[i] = 0;
            if (particlesY[i] < 0) particlesY[i] = getHeight();
            if (particlesY[i] > getHeight()) particlesY[i] = 0;
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopInvalidating();
    }
}
