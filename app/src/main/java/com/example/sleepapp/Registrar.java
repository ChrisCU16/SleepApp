package com.example.sleepapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import android.database.sqlite.SQLiteDatabase;

public class Registrar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        EditText dni = findViewById(R.id.etDni);
        EditText usuario = findViewById(R.id.etUsuario);
        EditText contraseña = findViewById(R.id.etContraseña);
        EditText nombre = findViewById(R.id.etNombre);
        EditText apellido = findViewById(R.id.etApellido);
        EditText correo = findViewById(R.id.etCorreo);
        EditText fechanacimiento = findViewById(R.id.etFechanacimiento);
        Button botonRegistrarse = findViewById(R.id.btnRegRegistrar);
        Button botonCancelar = findViewById(R.id.btnRegCancelar);

        botonRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dniStr = dni.getText().toString();
                String usuarioStr = usuario.getText().toString();
                String contraseñaStr = contraseña.getText().toString();
                String nombreStr = nombre.getText().toString();
                String apellidoStr = apellido.getText().toString();
                String correoStr = correo.getText().toString();
                String fechanacimientoStr = fechanacimiento.getText().toString();

                // Validación de los campos
                if (TextUtils.isEmpty(dniStr) || dniStr.length() != 8 || !TextUtils.isDigitsOnly(dniStr)) {
                    Toast.makeText(getApplicationContext(), "DNI debe ser de 8 dígitos numéricos", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(contraseñaStr) || contraseñaStr.length() < 6) {
                    Toast.makeText(getApplicationContext(), "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(usuarioStr) || TextUtils.isEmpty(nombreStr) || TextUtils.isEmpty(apellidoStr)
                        || TextUtils.isEmpty(correoStr) || TextUtils.isEmpty(fechanacimientoStr)) {
                    Toast.makeText(getApplicationContext(), "Todos los campos deben estar llenos", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Insertar en la base de datos si las validaciones son correctas
                try {
                    SQLiteOpenHelper conexion = new SqlLiteHelper(getApplicationContext());
                    SQLiteDatabase baseDeDatos = conexion.getWritableDatabase();
                    ContentValues valores = new ContentValues();
                    valores.put("dni", Integer.valueOf(dniStr));
                    valores.put("usuario", usuarioStr);
                    valores.put("contraseña", contraseñaStr);
                    valores.put("nombre", nombreStr);
                    valores.put("apellido", apellidoStr);
                    valores.put("correo", correoStr);
                    valores.put("fechanacimiento", fechanacimientoStr);

                    long resultado = baseDeDatos.insert("tusuarios", null, valores);
                    if (resultado != -1) {
                        Toast.makeText(getApplicationContext(), "Se guardó correctamente", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Error al guardar el registro", Toast.LENGTH_SHORT).show();
                    }

                } catch (SQLException e) {
                    Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                // Redirigir a la pantalla principal
                Intent registroCorrecto = new Intent(Registrar.this, MainActivity.class);
                startActivity(registroCorrecto);
            }
        });

        botonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ventanaLogin = new Intent(Registrar.this, MainActivity.class);
                startActivity(ventanaLogin);
            }
        });

    }
}
