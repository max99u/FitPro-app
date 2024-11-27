package com.example.fitproappmy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.List;

public class DetalleEntrenamientoActivity extends AppCompatActivity {

    private TextView tvNombreEntrenamiento, tvFechaEntrenamiento, tvDetallesEntrenamiento;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_entrenamiento);

        // Configurar Toolbar con flecha de retroceso
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        // Inicializar vistas
        tvNombreEntrenamiento = findViewById(R.id.tv_nombre_entrenamiento);
        tvFechaEntrenamiento = findViewById(R.id.tv_fecha_entrenamiento);
        tvDetallesEntrenamiento = findViewById(R.id.tv_detalles_entrenamiento);

        // Inicializar base de datos
        dbHelper = new DatabaseHelper(this);

        // Obtener el ID del entrenamiento desde el intent
        int entrenamientoId = getIntent().getIntExtra("entrenamiento_id", -1);

        if (entrenamientoId != -1) {
            cargarEntrenamiento(entrenamientoId);
        } else {
            Toast.makeText(this, "Error al cargar el entrenamiento", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void cargarEntrenamiento(int entrenamientoId) {
        // Obtener entrenamiento desde la base de datos
        Entrenamiento entrenamiento = dbHelper.obtenerEntrenamientoPorId(entrenamientoId);

        if (entrenamiento != null) {
            // Mostrar nombre y fecha del entrenamiento
            tvNombreEntrenamiento.setText(entrenamiento.getNombre());
            tvFechaEntrenamiento.setText(entrenamiento.getFecha());

            // Obtener y mostrar los ejercicios asociados a este entrenamiento
            List<Entrenamiento> listaEjercicios = dbHelper.obtenerEjerciciosPorEntrenamiento(entrenamientoId);
            if (listaEjercicios.isEmpty()) {
                tvDetallesEntrenamiento.setText("No hay ejercicios guardados para este entrenamiento.");
            } else {
                // Crear una cadena con los detalles de los ejercicios
                StringBuilder detalles = new StringBuilder();
                for (Entrenamiento ejercicio : listaEjercicios) {
                    detalles.append("Ejercicio: ").append(ejercicio.getNombre()).append("\n");
                    detalles.append("Repeticiones: ").append(ejercicio.getRepeticiones()).append("\n");
                    detalles.append("Peso: ").append(ejercicio.getPeso()).append(" kg\n\n");
                }
                tvDetallesEntrenamiento.setText(detalles.toString());
            }
        } else {
            Toast.makeText(this, "Entrenamiento no encontrado", Toast.LENGTH_SHORT).show();
            finish(); // Cierra la actividad si no se encuentra el entrenamiento
        }
    }

}
