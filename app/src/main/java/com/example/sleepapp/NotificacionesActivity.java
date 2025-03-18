package com.example.sleepapp;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class NotificacionesActivity extends AppCompatActivity {

    private SqlLiteHelper sqlLiteHelper;
    private TextView textViewAlarmas;
    private Button botonVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificaciones);

        sqlLiteHelper = new SqlLiteHelper(this);
        textViewAlarmas = findViewById(R.id.textViewAlarmas);
        botonVolver = findViewById(R.id.botonVolver);

        // Cargar las alarmas de la base de datos
        cargarAlarmas();

        // Configurar el botón para volver al menú principal
        botonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // Vuelve a la actividad anterior
            }
        });
    }

    private void cargarAlarmas() {
        Cursor cursor = sqlLiteHelper.obtenerAlarmas(); // Método que debes implementar en SqlLiteHelper
        StringBuilder alarmas = new StringBuilder();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Cambia los nombres de las columnas según tu base de datos
                String hora = cursor.getString(cursor.getColumnIndex("hora"));
                String dia = cursor.getString(cursor.getColumnIndex("dia"));
                alarmas.append("Alarma: ").append(hora).append(" en ").append(dia).append("\n");
            } while (cursor.moveToNext());
            cursor.close();
        } else {
            alarmas.append("No hay alarmas creadas.");
        }

        textViewAlarmas.setText(alarmas.toString());
    }
}
