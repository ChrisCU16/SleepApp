package com.example.sleepapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class listardatos extends AppCompatActivity {
    ListView lstUsuarios;
    SqlLiteHelper conexion;
    ArrayList<String> listaInformacion;
    ArrayList<Usuario> listaUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_listardatos);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        lstUsuarios = findViewById(R.id.lstUsuarios);
        Button botonCerrar = findViewById(R.id.btnCerrar);
        conexion = new SqlLiteHelper(getApplicationContext());
        listarUsuarios();
        ArrayAdapter adaptador = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,listaInformacion);
        lstUsuarios.setAdapter(adaptador);
botonCerrar.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent Regrasar = new Intent(listardatos.this,MainActivity.class);
        startActivity(Regrasar);
    }
});
    }

    private void listarUsuarios() {
        SQLiteDatabase baseDeDatos = conexion.getReadableDatabase();
        Usuario usuario = null;
        listaUsuarios = new ArrayList<Usuario>();
        Cursor cursor = baseDeDatos.rawQuery("SELECT * FROM tusuarios", null);
        while (cursor.moveToNext()){
            usuario = new Usuario();
            usuario.setDni(cursor.getInt(0));
            usuario.setUsuario(cursor.getString(1));
            usuario.setContraseña(cursor.getString(2));
            usuario.setNombre(cursor.getString(3));
            usuario.setApellidos(cursor.getString(4));
            usuario.setCorreo(cursor.getString(5));
            usuario.setFechanacimiento(cursor.getString(6));

            listaUsuarios.add(usuario);
        }
        crearFormatoInformacion();
    }
    private void crearFormatoInformacion()
    {
        listaInformacion = new ArrayList<String>();
        for (int i=0; i<listaUsuarios.size();i++){
            listaInformacion.add(listaUsuarios.get(i).getDni().toString()+"-"+listaUsuarios.get(i).getUsuario()+ "-" + listaUsuarios.get(i).getContraseña()+"-"+ listaUsuarios.get(i).getNombre()+"-"+listaUsuarios.get(i).getApellidos() + "-" + listaUsuarios.get(i).getCorreo()+"-"+listaUsuarios.get(i).getFechanacimiento());
        }
    }


}