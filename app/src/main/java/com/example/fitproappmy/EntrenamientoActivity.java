package com.example.fitproappmy;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class EntrenamientoActivity extends AppCompatActivity {

    private EditText etNombre, etRepeticiones, etPeso;
    private Button btnEmpezar, btnGuardarEjercicio, btnGuardarEntrenamiento;
    private LinearLayout formularioEntrenamiento, layoutPlantillas;
    private RecyclerView rvEntrenamientos;

    private Button btnPlantillaBrazo, btnPlantillaPecho, btnPlantillaPierna, btnPlantillaHombro;

    private DatabaseHelper dbHelper;
    private EntrenamientoAdapter adapter;
    private List<Entrenamiento> listaEjercicios;

    private Toolbar toolbar; // Toolbar para manejar la flecha de retroceso
    private String nombreEntrenamiento = "Entrenamiento Personalizado"; // Nombre por defecto del entrenamiento
    private boolean vieneDeHistorial = false; // Variable para indicar si viene del historial

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrenamiento);

        // Configurar Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Revisar si la actividad fue iniciada desde el historial
        if (getIntent().hasExtra("viene_de_historial")) {
            vieneDeHistorial = getIntent().getBooleanExtra("viene_de_historial", false);
        }

        // Configurar el título de la Toolbar según de dónde venga
        if (vieneDeHistorial) {
            getSupportActionBar().setTitle("Historial");
        } else {
            getSupportActionBar().setTitle(nombreEntrenamiento);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Habilitar flecha de retroceso

        // Inicializar vistas
        btnEmpezar = findViewById(R.id.btn_empezar_entrenamiento);
        formularioEntrenamiento = findViewById(R.id.formulario_entrenamiento);
        layoutPlantillas = findViewById(R.id.layout_plantillas);
        etNombre = findViewById(R.id.et_nombre);
        etRepeticiones = findViewById(R.id.et_repeticiones);
        etPeso = findViewById(R.id.et_peso);
        btnGuardarEjercicio = findViewById(R.id.btn_guardar_ejercicio);
        btnGuardarEntrenamiento = findViewById(R.id.btn_guardar_entrenamiento);
        rvEntrenamientos = findViewById(R.id.rv_entrenamientos);

        btnPlantillaBrazo = findViewById(R.id.btn_plantilla_brazo);
        btnPlantillaPecho = findViewById(R.id.btn_plantilla_pecho);
        btnPlantillaPierna = findViewById(R.id.btn_plantilla_pierna);
        btnPlantillaHombro = findViewById(R.id.btn_plantilla_hombro);

        // Configurar RecyclerView
        rvEntrenamientos.setLayoutManager(new LinearLayoutManager(this));
        listaEjercicios = new ArrayList<>();
        adapter = new EntrenamientoAdapter(listaEjercicios, this);
        rvEntrenamientos.setAdapter(adapter);

        // Configurar base de datos
        dbHelper = new DatabaseHelper(this);

        // Mostrar formulario vacío al hacer clic en "Empezar entrenamiento"
        btnEmpezar.setOnClickListener(v -> {
            iniciarEntrenamientoDesdeCero();
            nombreEntrenamiento = "Entrenamiento Personalizado";
            activarFlechaRetroceso(true);
            getSupportActionBar().setTitle("Entrenamiento"); // Cambiar el título de la toolbar
        });

        // Guardar ejercicio manualmente
        btnGuardarEjercicio.setOnClickListener(v -> guardarEjercicio());

        // Guardar entrenamiento completo
        btnGuardarEntrenamiento.setOnClickListener(v -> guardarEntrenamiento());

        // Configurar botones de plantillas
        btnPlantillaBrazo.setOnClickListener(v -> {
            cargarPlantilla("brazo");
            nombreEntrenamiento = "Entrenamiento de Brazos";
            activarFlechaRetroceso(true);
            getSupportActionBar().setTitle("Entrenamiento de Brazos"); // Cambiar título
        });
        btnPlantillaPecho.setOnClickListener(v -> {
            cargarPlantilla("pecho");
            nombreEntrenamiento = "Entrenamiento de Pecho";
            activarFlechaRetroceso(true);
            getSupportActionBar().setTitle("Entrenamiento de Pecho"); // Cambiar título
        });
        btnPlantillaPierna.setOnClickListener(v -> {
            cargarPlantilla("pierna");
            nombreEntrenamiento = "Entrenamiento de Piernas";
            activarFlechaRetroceso(true);
            getSupportActionBar().setTitle("Entrenamiento de Piernas"); // Cambiar título
        });
        btnPlantillaHombro.setOnClickListener(v -> {
            cargarPlantilla("hombro");
            nombreEntrenamiento = "Entrenamiento de Hombros";
            activarFlechaRetroceso(true);
            getSupportActionBar().setTitle("Entrenamiento de Hombros"); // Cambiar título
        });

        // Configurar BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_entrenar);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_perfil) {
                startActivity(new Intent(this, PerfilActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (item.getItemId() == R.id.nav_historial) {
                startActivity(new Intent(this, HistorialActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (item.getItemId() == R.id.nav_entrenar) {
                return true; // Ya estamos aquí
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

    private void activarFlechaRetroceso(boolean activar) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(activar);
    }

    private void iniciarEntrenamientoDesdeCero() {
        btnEmpezar.setVisibility(View.GONE); // Oculta el botón inicial
        layoutPlantillas.setVisibility(View.GONE); // Oculta las plantillas
        formularioEntrenamiento.setVisibility(View.VISIBLE); // Muestra el formulario
        btnGuardarEntrenamiento.setVisibility(View.VISIBLE); // Asegura que el botón esté visible
        listaEjercicios.clear(); // Limpia cualquier ejercicio previo
        adapter.notifyDataSetChanged(); // Actualiza la vista
    }

    private void cargarPlantilla(String tipo) {
        iniciarEntrenamientoDesdeCero(); // Limpia y prepara el entrenamiento
        formularioEntrenamiento.setVisibility(View.VISIBLE);
        btnGuardarEjercicio.setVisibility(View.VISIBLE); // Desactivar guardar ejercicio para plantillas
        btnGuardarEntrenamiento.setVisibility(View.VISIBLE); // Asegurar que siempre se muestra

        switch (tipo) {
            case "brazo":
                listaEjercicios.add(new Entrenamiento(0, "Curl de bíceps", 0, 0, "0r / 0kg"));
                listaEjercicios.add(new Entrenamiento(0, "Curl inclinado", 0, 0, "0r / 0kg"));
                listaEjercicios.add(new Entrenamiento(0, "Tríceps posterior", 0, 0, "0r / 0kg"));
                listaEjercicios.add(new Entrenamiento(0, "Tríceps anterior", 0, 0, "0r / 0kg"));
                break;
            case "pecho":
                listaEjercicios.add(new Entrenamiento(0, "Press banca", 0, 0.0, null));
                listaEjercicios.add(new Entrenamiento(0, "Press banca inclinado", 0, 0.0, null));
                listaEjercicios.add(new Entrenamiento(0, "Fondos", 0, 0.0, null)); // Fondos con peso corporal
                listaEjercicios.add(new Entrenamiento(0, "Aperturas", 0, 0.0, null));
                break;
            case "pierna":
                listaEjercicios.add(new Entrenamiento(0, "Sentadilla", 0, 0.0, null));
                listaEjercicios.add(new Entrenamiento(0, "Prensa", 0, 0.0, null));
                listaEjercicios.add(new Entrenamiento(0, "Sentadilla hack", 0, 0.0, null));
                listaEjercicios.add(new Entrenamiento(0, "Extensión de cuádriceps", 0, 0.0, null));
                break;
            case "hombro":
                listaEjercicios.add(new Entrenamiento(0, "Press militar", 0, 0.0, null));
                listaEjercicios.add(new Entrenamiento(0, "Elevaciones laterales", 0, 0.0, null));
                listaEjercicios.add(new Entrenamiento(0, "Elevaciones frontales", 0, 0.0, null));
                listaEjercicios.add(new Entrenamiento(0, "Elevaciones al mentón", 0, 0.0, null));
                break;
        }

        layoutPlantillas.setVisibility(View.GONE); // Oculta las plantillas
        adapter.notifyDataSetChanged(); // Actualiza la lista de ejercicios
    }

    // Método para guardar un ejercicio
    private void guardarEjercicio() {
        String nombre = etNombre.getText().toString();
        String repeticiones = etRepeticiones.getText().toString();
        String peso = etPeso.getText().toString();

        if (TextUtils.isEmpty(nombre) || TextUtils.isEmpty(repeticiones) || TextUtils.isEmpty(peso)) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        Entrenamiento ejercicio = new Entrenamiento(0, nombre, Integer.parseInt(repeticiones), Double.parseDouble(peso), "");
        listaEjercicios.add(ejercicio);
        adapter.notifyDataSetChanged();

        etNombre.setText("");
        etRepeticiones.setText("");
        etPeso.setText("");
    }

    // Método para guardar un entrenamiento
    private void guardarEntrenamiento() {
        // Insertar entrenamiento en la base de datos con el nombre adecuado
        long entrenamientoId = dbHelper.insertarEntrenamiento(nombreEntrenamiento, "Hoy");

        if (entrenamientoId != -1) {
            // Guardar todos los ejercicios asociados a este entrenamiento
            for (Entrenamiento ejercicio : listaEjercicios) {
                dbHelper.insertarEjercicio((int) entrenamientoId,
                        ejercicio.getNombre(),
                        ejercicio.getRepeticiones(),
                        ejercicio.getPeso());
            }
            Toast.makeText(this, "Entrenamiento guardado con éxito", Toast.LENGTH_SHORT).show();
            listaEjercicios.clear();
            adapter.notifyDataSetChanged();

            // Redirigir a la actividad de historial
            startActivity(new Intent(this, HistorialActivity.class));
            finish();
        } else {
            Toast.makeText(this, "Error al guardar el entrenamiento", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (vieneDeHistorial) {
                Intent intent = new Intent(this, HistorialActivity.class);
                startActivity(intent);
                finish(); // Finaliza la actividad actual para que vuelva a la anterior
            } else {
                regresarAPantallaPrincipal();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void regresarAPantallaPrincipal() {
        formularioEntrenamiento.setVisibility(View.GONE);
        layoutPlantillas.setVisibility(View.VISIBLE);
        btnEmpezar.setVisibility(View.VISIBLE);
        activarFlechaRetroceso(false); // Ocultar la flecha
    }
}
