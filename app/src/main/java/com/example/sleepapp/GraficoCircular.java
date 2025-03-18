package com.example.sleepapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.Random;


public class GraficoCircular extends View {

    private float[] datos = new float[]{};
    private Paint paint = new Paint();

    public GraficoCircular(Context context) {
        super(context);
    }

    public GraficoCircular(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GraficoCircular(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setDatos(float[] datos, int[] coloresCircular) {
        this.datos = datos;
        invalidate(); // Redibuja el gráfico con los nuevos datos
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (datos != null && datos.length > 0) {
            float total = 0;
            for (float dato : datos) {
                total += dato;
            }

            // Dibujar gráfico circular
            float startAngle = 0;
            RectF rectF = new RectF(100, 100, 500, 500);

            for (float dato : datos) {
                float sweepAngle = (dato / total) * 360;
                paint.setColor(getRandomColor());
                canvas.drawArc(rectF, startAngle, sweepAngle, true, paint);
                startAngle += sweepAngle;
            }
        }
    }

    private int getRandomColor() {
        Random random = new Random();
        return Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }
}
