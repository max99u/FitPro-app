package com.example.fitproappmy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class HistorialActivity extends AppCompatActivity {

    private RecyclerView rvHistorial;
    private HistorialAdapter historialAdapter;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);

        // Inicializar vistas
        rvHistorial = findViewById(R.id.rv_historial);
        dbHelper = new DatabaseHelper(this);

        // Configurar RecyclerView
        rvHistorial.setLayoutManager(new LinearLayoutManager(this));
        cargarEntrenamientos();

        // Configurar el BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_historial);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_perfil) {
                startActivity(new Intent(this, PerfilActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (item.getItemId() == R.id.nav_historial) {
                return true; // Ya estamos en esta actividad
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

    /**
     * Cargar los entrenamientos desde la base de datos y mostrarlos en el RecyclerView.
     */
    private void cargarEntrenamientos() {
        List<Entrenamiento> listaEntrenamientos = dbHelper.obtenerEntrenamientos();

        if (listaEntrenamientos.isEmpty()) {
            Toast.makeText(this, "No hay entrenamientos guardados", Toast.LENGTH_SHORT).show();
        } else {
            historialAdapter = new HistorialAdapter(listaEntrenamientos, this);
            rvHistorial.setAdapter(historialAdapter);

            // Configurar clic en los elementos del historial
            historialAdapter.setOnItemClickListener(entrenamiento -> {
                Intent intent = new Intent(HistorialActivity.this, DetalleEntrenamientoActivity.class);
                intent.putExtra("entrenamiento_id", entrenamiento.getId());
                intent.putExtra("entrenamiento_nombre", entrenamiento.getNombre());
                startActivity(intent);
            });

            // Configurar eliminación de entrenamientos
            historialAdapter.setOnDeleteClickListener(entrenamiento -> {
                eliminarEntrenamiento(entrenamiento);
            });
        }
    }

    /**
     * Eliminar un entrenamiento de la base de datos y actualizar la vista.
     */
    private void eliminarEntrenamiento(Entrenamiento entrenamiento) {
        dbHelper.eliminarEntrenamiento(entrenamiento.getId());
        cargarEntrenamientos(); // Volver a cargar los entrenamientos después de eliminar uno
    }
}
