package com.example.sleepapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class GraficoBarras extends View {
    private Paint paint = new Paint();
    private Paint textPaint = new Paint(); // Paint para el texto
    private float[] datos = {6.5f, 7.4f, 8.5f, 8.0f, 7.5f, 6.5f, 9.3f}; // Nuevos datos

    public GraficoBarras(Context context) {
        super(context);
        init();
    }

    public GraficoBarras(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GraficoBarras(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);

        // Configuración del paint para el texto
        textPaint.setColor(Color.BLACK); // Color del texto
        textPaint.setTextSize(40); // Tamaño del texto
        textPaint.setTextAlign(Paint.Align.CENTER); // Alinear el texto al centro
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int ancho = getWidth();
        int alto = getHeight();
        int numBarras = datos.length;

        float barraAncho = (float) ancho / (numBarras * 2);

        // Calcular la altura máxima para normalizar las barras
        float maxDataValue = 10f; // Asumiendo que el valor máximo es 10

        for (int i = 0; i < numBarras; i++) {
            float barraAltura = (datos[i] / maxDataValue) * alto; // Normaliza para el rango 0-10
            float left = (i * 2 * barraAncho) + barraAncho / 2;
            float right = left + barraAncho;
            float top = alto - barraAltura;
            float bottom = alto;

            canvas.drawRect(left, top, right, bottom, paint);

            // Dibujar el texto sobre la barra
            float textX = (left + right) / 2; // Posición X centrada
            float textY = top - 10; // Posición Y justo encima de la barra
            canvas.drawText(String.valueOf(datos[i]), textX, textY, textPaint);
        }
    }

    public void setDatos(float[] nuevosDatos) {
        this.datos = nuevosDatos;
        invalidate(); // Redibuja la vista con los nuevos datos
    }
}
