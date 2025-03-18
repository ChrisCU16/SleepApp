package com.example.sleepapp;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class ApagarAlarma extends AppCompatActivity {

    private MediaPlayer mediaPlayer; // Para reproducir el tono de alarma

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apagar_alarma);

        // Reproducir el tono de alarma recibido
        String alarmTone = getIntent().getStringExtra("ALARM_TONE");
        if (alarmTone != null) {
            mediaPlayer = MediaPlayer.create(this, Uri.parse(alarmTone));
            mediaPlayer.setLooping(true); // Repetir el tono hasta apagar
            mediaPlayer.start();
        }

        // Configurar botón para apagar la alarma
        Button btnStopAlarm = findViewById(R.id.btnStopAlarm);
        btnStopAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Detener el tono de alarma
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;
                }

                // Redirigir al menú principal
                Intent intent = new Intent(ApagarAlarma.this, MenuPrincipal.class);
                startActivity(intent);
                finish(); // Cerrar la actividad actual
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Asegurarse de liberar recursos si se cierra la actividad
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
