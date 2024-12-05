package com.example.fitproappmy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class PerfilActivity extends AppCompatActivity {

    private TextView tvNombre, tvEdad, tvPeso;
    private RecyclerView rvLogros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        // Inicializar TextViews
        tvNombre = findViewById(R.id.tv_nombre);
        tvEdad = findViewById(R.id.tv_edad);
        tvPeso = findViewById(R.id.tv_peso);

        // Inicializar RecyclerView para logros
        rvLogros = findViewById(R.id.rv_logros);
        rvLogros.setLayoutManager(new LinearLayoutManager(this));

        // Recuperar los datos del perfil desde SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserProfile", MODE_PRIVATE);
        String nombre = sharedPreferences.getString("nombre", "N/A");
        String edad = sharedPreferences.getString("edad", "N/A");
        String peso = sharedPreferences.getString("peso", "N/A");

        // Mostrar los datos en los TextViews
        tvNombre.setText("Nombre: " + nombre);
        tvEdad.setText("Edad: " + edad + " años");
        tvPeso.setText("Peso: " + peso + " kg");

        // Crear lista de logros y agregar algunos ejemplos
        List<Logro> listaLogros = new ArrayList<>();
        listaLogros.add(new Logro("Primer Entrenamiento", "Completaste tu primer entrenamiento", R.drawable.logro1));
        listaLogros.add(new Logro("Cinco Días Seguidos", "Entrenaste cinco días seguidos", R.drawable.logro2));
        listaLogros.add(new Logro("Diez Ejercicios Realizados", "Realizaste diez ejercicios diferentes", R.drawable.logro3));
        listaLogros.add(new Logro("Diez Entrenamientos Completados", "Completaste diez entrenamientos", R.drawable.logro4));
        listaLogros.add(new Logro("Mes de Consistencia", "Entrenaste consistentemente durante un mes", R.drawable.logro5));

        // Configurar el adaptador para los logros y asignarlo al RecyclerView
        LogroAdapter logroAdapter = new LogroAdapter(listaLogros, this);
        rvLogros.setAdapter(logroAdapter);

        // Configurar el BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Establecer el ítem seleccionado
        bottomNavigationView.setSelectedItemId(R.id.nav_perfil);

        // Configurar el Listener para cambiar entre actividades
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_perfil) {
                return true; // Ya estamos en esta actividad
            } else if (item.getItemId() == R.id.nav_historial) {
                startActivity(new Intent(this, HistorialActivity.class));
                overridePendingTransition(0, 0); // Sin animación
                return true;
            } else if (item.getItemId() == R.id.nav_entrenar) {
                startActivity(new Intent(this, EntrenamientoActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (item.getItemId() == R.id.nav_ejercicios) {
                startActivity(new Intent(this, EjerciciosActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (item.getItemId() == R.id.nav_ajustes) {
                startActivity(new Intent(this, AjustesActivity.class));
                overridePendingTransition(0, 0);
                return true;
            }
            return false;
        });
    }
}
