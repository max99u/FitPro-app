package com.max.fitproapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private TextView pageTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Habilitar EdgeToEdge para el diseño
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Configurar los padding para las barras del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Configurar el título de la página
        pageTitle = findViewById(R.id.page_title);

        // Configurar el BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Establecer el fragmento inicial y el título
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment()) // Corregido: eliminar 'new Fragment()'
                    .commit();
            pageTitle.setText("Perfil"); // Establece el título inicial
        }

        // Listener para cambiar entre fragmentos
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            // Cambiar fragmento según el ítem seleccionado
            if (item.getItemId() == R.id.nav_home) {
                selectedFragment = new HomeFragment();
                pageTitle.setText("Perfil");
            } else if (item.getItemId() == R.id.nav_history) {
                selectedFragment = new HistorialFragment();
                pageTitle.setText("Historial");
            } else if (item.getItemId() == R.id.nav_register) {
                selectedFragment = new EntrenamientoFragment();
                pageTitle.setText("Entrenamiento");
            } else if (item.getItemId() == R.id.nav_progress) {
                selectedFragment = new EjerciciosFragment();
                pageTitle.setText("Ejercicios");
            } else if (item.getItemId() == R.id.nav_settings) {
                selectedFragment = new AjustesFragment();
                pageTitle.setText("Ajustes");
            }

            // Reemplazar el fragmento seleccionado
            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();
            }

            return true;
        });
    }
}
