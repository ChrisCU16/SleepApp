package com.example.sleepapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

public class MenuPrincipal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        ImageButton botonRutina = findViewById(R.id.btnRutina);
        ImageButton botonAlarma = findViewById(R.id.botonMostrarAlarma);
        ImageButton botonEstadistica = findViewById(R.id.btnEstadistica);
        ImageButton botonVideos = findViewById(R.id.btnVideos);

        ImageButton botonPerfil = findViewById(R.id.perfil);
        ImageButton botonSalir = findViewById(R.id.btnSalir);

        botonRutina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent botonRutina = new Intent(MenuPrincipal.this, registrorutina.class);
                startActivity(botonRutina);
            }
        });

        botonAlarma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent botonAlarma = new Intent(MenuPrincipal.this, RegistrarAlarma.class);
                startActivity(botonAlarma);
            }
        });

        botonEstadistica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent botonEstadistica = new Intent(MenuPrincipal.this, estadisticas.class);
                startActivity(botonEstadistica);
            }
        });

        botonVideos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent botonVideos = new Intent(MenuPrincipal.this, VideosActivity.class);
                startActivity(botonVideos);
            }
        });

        // Añadir el click para el botón de perfil
        botonPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent botonPerfil = new Intent(MenuPrincipal.this, PerfilActivity.class);
                startActivity(botonPerfil);
            }
        });

        // Añadir el click para el botón de salir
        botonSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent botonSalir = new Intent(MenuPrincipal.this, MainActivity.class);
                startActivity(botonSalir);
            }
        });
    }
}
