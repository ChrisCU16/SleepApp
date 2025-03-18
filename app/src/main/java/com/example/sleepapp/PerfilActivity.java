package com.example.sleepapp;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class PerfilActivity extends AppCompatActivity {

    private SqlLiteHelper sqlLiteHelper;
    private TextView textViewPerfil;
    private Button botonVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        sqlLiteHelper = new SqlLiteHelper(this);
        textViewPerfil = findViewById(R.id.textViewPerfil);
        botonVolver = findViewById(R.id.botonVolver);

        // Cargar los datos de perfil
        cargarPerfil();

        // Configurar el botón para volver al menú principal
        botonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // Vuelve a la actividad anterior
            }
        });
    }

    private void cargarPerfil() {
        Cursor cursor = sqlLiteHelper.obtenerUsuario(); // Método que debes implementar en SqlLiteHelper
        StringBuilder perfil = new StringBuilder();

        if (cursor != null && cursor.moveToFirst()) {
            String usuario = cursor.getString(cursor.getColumnIndex("usuario"));
            String nombre = cursor.getString(cursor.getColumnIndex("nombre"));
            String apellido = cursor.getString(cursor.getColumnIndex("apellido"));
            String correo = cursor.getString(cursor.getColumnIndex("correo"));

            perfil.append("Usuario: ").append(usuario).append("\n")
                    .append("Nombre: ").append(nombre).append("\n")
                    .append("Apellido: ").append(apellido).append("\n")
                    .append("Correo: ").append(correo).append("\n");

            cursor.close();
        } else {
            perfil.append("No se encontraron datos de usuario.");
        }

        textViewPerfil.setText(perfil.toString());
    }
}
