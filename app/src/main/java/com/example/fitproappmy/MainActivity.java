package com.example.fitproappmy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Verificar si el usuario ya está registrado
        SharedPreferences sharedPreferences = getSharedPreferences("UserProfile", Context.MODE_PRIVATE);
        boolean isRegistered = sharedPreferences.getBoolean("isRegistered", false);

        if (!isRegistered) {
            // Si el usuario no está registrado, mostrar la pantalla de bienvenida
            Intent intent = new Intent(MainActivity.this, BienvenidaActivity.class);
            startActivity(intent);
            finish(); // Finalizar MainActivity para no permitir regresar con el botón de atrás
            return;
        }

        // Si el usuario está registrado, continuar con la actividad principal
        setContentView(R.layout.activity_main);

        // Configurar el BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Establecer el ítem seleccionado en "Entrenar"
        bottomNavigationView.setSelectedItemId(R.id.nav_entrenar);

        // Configurar el Listener para cambiar entre actividades usando if-else en lugar de switch
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_perfil) {
                startActivity(new Intent(this, PerfilActivity.class));
                overridePendingTransition(0, 0); // Sin animación
                return true;
            } else if (itemId == R.id.nav_historial) {
                startActivity(new Intent(this, HistorialActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (itemId == R.id.nav_entrenar) {
                return true; // Ya estamos en esta actividad
            } else if (itemId == R.id.nav_ejercicios) {
                startActivity(new Intent(this, EjerciciosActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (itemId == R.id.nav_ajustes) {
                startActivity(new Intent(this, AjustesActivity.class));
                overridePendingTransition(0, 0);
                return true;
            }
            return false;
        });
    }
}
