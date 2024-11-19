package com.example.fitproappmy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.TextView;

import com.example.fitproappmy.ui.PerfilFragment;
import com.example.fitproappmy.ui.HistorialFragment;
import com.example.fitproappmy.ui.EntrenamientoFragment;
import com.example.fitproappmy.ui.EjerciciosFragment;
import com.example.fitproappmy.ui.AjustesFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private TextView titleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Referencia al TextView del título
        titleText = findViewById(R.id.title_text);

        // Referencia al BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Establece el fragmento inicial
        loadFragment(new PerfilFragment(), "Perfil");

        // Listener para manejar la selección de elementos en el BottomNavigationView
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            String title = "";

            // Cambia el fragmento según el ítem seleccionado
            if (item.getItemId() == R.id.nav_historial) {
                selectedFragment = new HistorialFragment();
                title = "Historial";
            } else if (item.getItemId() == R.id.nav_entrenar) {
                selectedFragment = new EntrenamientoFragment();
                title = "Entrenar";
            } else if (item.getItemId() == R.id.nav_ejercicios) {
                selectedFragment = new EjerciciosFragment();
                title = "Ejercicios";
            } else if (item.getItemId() == R.id.nav_ajustes) {
                selectedFragment = new AjustesFragment();
                title = "Ajustes";
            } else if (item.getItemId() == R.id.nav_perfil) {
                selectedFragment = new PerfilFragment();
                title = "Perfil";
            }

            // Carga el fragmento seleccionado
            if (selectedFragment != null) {
                loadFragment(selectedFragment, title);
            }
            return true;
        });
    }

    /**
     * Método para cargar un fragmento en el contenedor y actualizar el título
     */
    private void loadFragment(Fragment fragment, String title) {
        // Cambia el fragmento
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();

        // Actualiza el título
        titleText.setText(title);
    }
}
