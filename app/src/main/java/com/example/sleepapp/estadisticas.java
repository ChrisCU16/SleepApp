package com.example.sleepapp;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class estadisticas extends AppCompatActivity {

    private GraficoBarras graficoBarras;
    private GraficoCircular graficoCircular;
    private Button botonVolverMenu;
    private SqlLiteHelper sqlLiteHelper;
    private int dniUsuario = 73024009; // Puedes obtener el DNI real según tu lógica de usuario

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadisticas);

        // Inicializar componentes
        graficoBarras = findViewById(R.id.graficoBarras);

        botonVolverMenu = findViewById(R.id.boton_volver_menu);
        sqlLiteHelper = new SqlLiteHelper(this);

        // Obtener los datos de la rutina de la base de datos
        Cursor cursor = sqlLiteHelper.obtenerRutinaPorDni(dniUsuario);
        if (cursor.moveToFirst()) {
            int horasTrabajo = cursor.getInt(cursor.getColumnIndex("horasTrabajo"));
            int ejercicioMinutos = cursor.getInt(cursor.getColumnIndex("ejercicioMinutos"));
            int recreacionMinutos = cursor.getInt(cursor.getColumnIndex("recreacionMinutos"));
            int tiempoPantalla = cursor.getInt(cursor.getColumnIndex("tiempoPantalla"));

            // Actualizar gráfico de barras
            // Aquí puedes implementar lógica para graficar las estadísticas semanales, si aplica.

            // Calcular porcentajes para el gráfico circular
            int totalTiempo = horasTrabajo + ejercicioMinutos + recreacionMinutos + tiempoPantalla;
            if (totalTiempo > 0) {
                float porcentajeTrabajo = (horasTrabajo / (float) totalTiempo) * 100;
                float porcentajeEjercicio = (ejercicioMinutos / (float) totalTiempo) * 100;
                float porcentajeRecreacion = (recreacionMinutos / (float) totalTiempo) * 100;
                float porcentajePantalla = (tiempoPantalla / (float) totalTiempo) * 100;


            }
        }

        // Botón para volver al menú principal
        botonVolverMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(estadisticas.this, MenuPrincipal.class);
                startActivity(intent);
                finish(); // Cerrar la actividad actual
            }
        });
    }
}
