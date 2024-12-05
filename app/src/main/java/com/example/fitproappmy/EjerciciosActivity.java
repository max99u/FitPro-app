package com.example.fitproappmy;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class EjerciciosActivity extends AppCompatActivity {

    private RecyclerView rvEjercicios;
    private EjercicioAdapter ejercicioAdapter;
    private List<Ejercicio> listaEjercicios;
    private List<Ejercicio> listaEjerciciosFiltrados;
    private EditText searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicios);

        // Inicializar vistas
        rvEjercicios = findViewById(R.id.rv_ejercicios);
        searchBar = findViewById(R.id.search_bar);
        rvEjercicios.setLayoutManager(new LinearLayoutManager(this));

        // Inicializar la lista de ejercicios
        listaEjercicios = new ArrayList<>();
        listaEjerciciosFiltrados = new ArrayList<>();
        agregarEjercicios();
        listaEjerciciosFiltrados.addAll(listaEjercicios); // Al principio, todos los ejercicios están disponibles

        // Configurar el adaptador
        ejercicioAdapter = new EjercicioAdapter(listaEjerciciosFiltrados, this);
        rvEjercicios.setAdapter(ejercicioAdapter);

        // Configurar clic en los elementos de los ejercicios
        ejercicioAdapter.setOnItemClickListener(ejercicio -> {
            Intent intent = new Intent(EjerciciosActivity.this, DetalleEjercicioActivity.class);
            intent.putExtra("nombre", ejercicio.getNombre());
            intent.putExtra("descripcion", ejercicio.getDescripcion());
            startActivity(intent);
        });

        // Añadir listener a la barra de búsqueda
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No hacer nada
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filtrarEjercicios(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // No hacer nada
            }
        });

        // Configurar el BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_ejercicios);

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
                startActivity(new Intent(this, EntrenamientoActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (item.getItemId() == R.id.nav_ejercicios) {
                return true; // Ya estamos en esta actividad
            } else if (item.getItemId() == R.id.nav_ajustes) {
                startActivity(new Intent(this, AjustesActivity.class));
                overridePendingTransition(0, 0);
                return true;
            }
            return false;
        });
    }

    private void agregarEjercicios() {
        // Ejercicios de Pecho
        listaEjercicios.add(new Ejercicio("Press de Banca", "Realiza el press de banca con barra para desarrollar la parte media del pecho."));
        listaEjercicios.add(new Ejercicio("Press de Banca Inclinado", "Ejercicio ideal para trabajar la parte superior del pecho."));
        listaEjercicios.add(new Ejercicio("Aperturas con Mancuernas", "Trabaja el pectoral desde otro ángulo, enfocándose en la expansión del pecho."));

        // Ejercicios de Espalda
        listaEjercicios.add(new Ejercicio("Remo con Barra", "Remo con barra para fortalecer la espalda baja y media."));
        listaEjercicios.add(new Ejercicio("Pull-ups", "Las dominadas son un ejercicio fundamental para toda la musculatura de la espalda."));
        listaEjercicios.add(new Ejercicio("Peso Muerto", "Ejercicio compuesto que trabaja los músculos de la espalda, especialmente la zona lumbar."));

        // Ejercicios de Pierna
        listaEjercicios.add(new Ejercicio("Sentadilla", "Las sentadillas son el mejor ejercicio para desarrollar los músculos de las piernas."));
        listaEjercicios.add(new Ejercicio("Prensa de Pierna", "La prensa de pierna ayuda a fortalecer cuádriceps, glúteos e isquiotibiales."));
        listaEjercicios.add(new Ejercicio("Zancadas", "Las zancadas ayudan a fortalecer los glúteos, cuádriceps e isquiotibiales."));

        // Ejercicios de Tríceps
        listaEjercicios.add(new Ejercicio("Extensión de Tríceps", "Extensiones con mancuerna para trabajar el músculo tríceps."));
        listaEjercicios.add(new Ejercicio("Fondos de Tríceps", "Ejercicio efectivo para fortalecer el tríceps, usando tu peso corporal."));
        listaEjercicios.add(new Ejercicio("Press Francés", "Ejercicio que trabaja la cabeza larga del tríceps con una barra o mancuernas."));

        // Ejercicios de Bíceps
        listaEjercicios.add(new Ejercicio("Curl con Barra", "El curl con barra es un ejercicio básico para desarrollar los bíceps."));
        listaEjercicios.add(new Ejercicio("Curl Martillo", "El curl martillo se enfoca en el braquiorradial, además de los bíceps."));
        listaEjercicios.add(new Ejercicio("Curl Concentrado", "Ejercicio para aislar el bíceps y trabajar en su forma y definición."));

        // Ejercicios de Hombro
        listaEjercicios.add(new Ejercicio("Press Militar", "Press militar con barra para desarrollar los músculos del hombro."));
        listaEjercicios.add(new Ejercicio("Elevación Lateral", "Elevaciones laterales para trabajar el deltoides lateral."));
        listaEjercicios.add(new Ejercicio("Elevación Frontal", "Elevaciones frontales para trabajar el deltoides frontal."));

        // Ejercicios de Antebrazo
        listaEjercicios.add(new Ejercicio("Curl de Muñeca con Barra", "Curl de muñeca para trabajar los músculos del antebrazo."));
        listaEjercicios.add(new Ejercicio("Curl de Muñeca Inverso", "Trabaja los extensores del antebrazo para un desarrollo equilibrado."));
        listaEjercicios.add(new Ejercicio("Sostén con Peso Estático", "Mantén el peso durante un tiempo para mejorar la fuerza de agarre."));

        // Ejercicios de Pantorrillas
        listaEjercicios.add(new Ejercicio("Elevaciones de Talones de Pie", "Realiza elevaciones de talones para fortalecer las pantorrillas."));
        listaEjercicios.add(new Ejercicio("Elevaciones de Talones Sentado", "Este ejercicio se enfoca más en el músculo sóleo."));
        listaEjercicios.add(new Ejercicio("Saltos de Pantorrilla", "Saltos para trabajar la fuerza explosiva de las pantorrillas."));
    }

    private void filtrarEjercicios(String texto) {
        listaEjerciciosFiltrados.clear();
        if (texto.isEmpty()) {
            listaEjerciciosFiltrados.addAll(listaEjercicios);
        } else {
            String textoFiltrado = texto.toLowerCase();
            for (Ejercicio ejercicio : listaEjercicios) {
                if (ejercicio.getNombre().toLowerCase().contains(textoFiltrado)) {
                    listaEjerciciosFiltrados.add(ejercicio);
                }
            }
        }
        // Notificar al adaptador que la lista cambió
        ejercicioAdapter.notifyDataSetChanged();
    }
}
