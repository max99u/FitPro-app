package com.example.fitproappmy;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class DetalleEjercicioActivity extends AppCompatActivity {

    private TextView tvNombreEjercicio, tvDescripcionEjercicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_ejercicio);

        // Configurar Toolbar con flecha de retroceso
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        // Inicializar vistas
        tvNombreEjercicio = findViewById(R.id.tv_nombre_ejercicio);
        tvDescripcionEjercicio = findViewById(R.id.tv_descripcion_ejercicio);

        // Obtener datos del intent
        String nombre = getIntent().getStringExtra("nombre");
        String descripcion = getIntent().getStringExtra("descripcion");

        // Mostrar los datos en los TextViews
        tvNombreEjercicio.setText(nombre);
        tvDescripcionEjercicio.setText(descripcion);
    }
}
