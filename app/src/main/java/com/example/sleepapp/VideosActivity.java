package com.example.sleepapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class VideosActivity extends AppCompatActivity {

    private WebView webViewVideos;
    private Button botonVolverMenu;
    private Button botonSiguienteVideo;
    private Button botonVideoAnterior; // Nuevo botón
    private SqlLiteHelper sqlLiteHelper;
    private Cursor cursor;
    private int currentIndex = 0; // Índice del video actual

    // Definición de las columnas
    private static final String COLUMNA_VIDEO_ID = "video_id"; // ID de video de YouTube

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);

        sqlLiteHelper = new SqlLiteHelper(this);
        webViewVideos = findViewById(R.id.webViewVideos);
        botonVolverMenu = findViewById(R.id.boton_volver_menu);
        botonSiguienteVideo = findViewById(R.id.boton_siguiente_video);
        botonVideoAnterior = findViewById(R.id.boton_video_anterior); // Inicializa el nuevo botón

        // Configurar el WebView
        webViewVideos.setWebViewClient(new WebViewClient());
        WebSettings webSettings = webViewVideos.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Cargar un video de la base de datos
        cargarVideo(currentIndex);

        // Configurar el botón para volver al menú principal
        botonVolverMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VideosActivity.this, MenuPrincipal.class);
                startActivity(intent);
                finish();
            }
        });

        // Configurar el botón para el siguiente video
        botonSiguienteVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarSiguienteVideo();
            }
        });

        // Configurar el botón para el video anterior
        botonVideoAnterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarVideoAnterior();
            }
        });
    }

    private void cargarVideo(int index) {
        cursor = sqlLiteHelper.obtenerVideos();
        if (cursor != null && cursor.moveToFirst()) {
            if (index >= cursor.getCount()) {
                index = 0; // Reiniciar al primer video si se pasa del límite
            } else if (index < 0) {
                index = cursor.getCount() - 1; // Reiniciar al último video si se pasa del límite negativo
            }
            cursor.moveToPosition(index); // Mover al índice deseado

            String videoId = cursor.getString(cursor.getColumnIndex(COLUMNA_VIDEO_ID));
            String url = "https://www.youtube.com/embed/" + videoId;
            webViewVideos.loadUrl(url);
            currentIndex = index; // Actualizar el índice actual
        }
    }

    private void cargarSiguienteVideo() {
        cargarVideo(currentIndex + 1); // Cargar el siguiente video
    }

    private void cargarVideoAnterior() {
        cargarVideo(currentIndex - 1); // Cargar el video anterior
    }

    @Override
    public void onBackPressed() {
        if (webViewVideos.canGoBack()) {
            webViewVideos.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cursor != null) {
            cursor.close(); // Cerrar el cursor si se está usando
        }
    }
}
