package com.example.sleepapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText dni = findViewById(R.id.User); // El campo usuario ahora será dni
        EditText contraseña = findViewById(R.id.Pass);
        Button botonIniciarSesion = findViewById(R.id.btnInicio);
        Button botonRegistrarse = findViewById(R.id.btnRegistro);
        Button botonVerCuentas = findViewById(R.id.btnVerCuentas);

        botonIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteOpenHelper conexion = new SqlLiteHelper(getApplicationContext());
                SQLiteDatabase baseDeDatos = conexion.getReadableDatabase();

                String dniIngresado = dni.getText().toString();
                String contraseñaIngresada = contraseña.getText().toString();

                // Consultar si el DNI y la contraseña coinciden
                String query = "SELECT * FROM tusuarios WHERE dni = ? AND contraseña = ?";
                Cursor cursor = baseDeDatos.rawQuery(query, new String[]{dniIngresado, contraseñaIngresada});

                if (cursor.moveToFirst()) {
                    // Inicio de sesión exitoso
                    Toast.makeText(getApplicationContext(), "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();

                    // Guardar el DNI del usuario actual para futuras consultas
                    String dniUsuarioActual = cursor.getString(cursor.getColumnIndex("dni"));
                    Intent ventanaPrincipal = new Intent(MainActivity.this, MenuPrincipal.class);
                    ventanaPrincipal.putExtra("dniUsuarioActual", dniUsuarioActual); // Pasar el DNI a la siguiente actividad
                    startActivity(ventanaPrincipal);
                } else {
                    // Usuario o contraseña incorrectos
                    Toast.makeText(getApplicationContext(), "DNI o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }

                cursor.close();
                baseDeDatos.close();
            }
        });

        botonRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ir a la pantalla de registro
                Intent ventanaRegistrar = new Intent(MainActivity.this, Registrar.class);
                startActivity(ventanaRegistrar);
            }
        });

        botonVerCuentas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Ir a la pantalla de cuentas
                Intent ventanaCuentas = new Intent(MainActivity.this, listardatos.class);
                startActivity(ventanaCuentas);
            }
        });
    }
}
