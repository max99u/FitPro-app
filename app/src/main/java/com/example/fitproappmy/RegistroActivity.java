package com.example.fitproappmy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegistroActivity extends AppCompatActivity {

    private EditText etNombre, etEdad, etPeso;
    private RadioGroup rgGenero;
    private Button btnCrear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        // Inicializar vistas
        etNombre = findViewById(R.id.et_nombre);
        etEdad = findViewById(R.id.et_edad);
        etPeso = findViewById(R.id.et_peso);
        rgGenero = findViewById(R.id.rg_genero);
        btnCrear = findViewById(R.id.btn_crear);

        // Configurar el botón para crear el perfil
        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = etNombre.getText().toString().trim();
                String edad = etEdad.getText().toString().trim();
                String peso = etPeso.getText().toString().trim();
                String genero = "";

                // Obtener el género seleccionado
                int selectedId = rgGenero.getCheckedRadioButtonId();
                if (selectedId == R.id.rb_hombre) {
                    genero = "Hombre";
                } else if (selectedId == R.id.rb_mujer) {
                    genero = "Mujer";
                } else if (selectedId == R.id.rb_otro) {
                    genero = "Otro";
                }

                // Validación básica
                if (nombre.isEmpty() || edad.isEmpty() || peso.isEmpty() || genero.isEmpty()) {
                    Toast.makeText(RegistroActivity.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    // Guardar los datos del perfil en SharedPreferences
                    SharedPreferences sharedPreferences = getSharedPreferences("UserProfile", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("nombre", nombre);
                    editor.putString("edad", edad);
                    editor.putString("peso", peso);
                    editor.putString("genero", genero);
                    editor.apply();

                    // Navegar a la actividad principal (EntrenamientoActivity)
                    Intent intent = new Intent(RegistroActivity.this, EntrenamientoActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
