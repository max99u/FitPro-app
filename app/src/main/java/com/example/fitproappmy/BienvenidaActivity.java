package com.example.fitproappmy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class BienvenidaActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenida);

        Button btnContinuar = findViewById(R.id.btn_continuar);
        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pasar a la pantalla de Registro
                startActivity(new Intent(BienvenidaActivity.this, RegistroActivity.class));
            }
        });
    }
}
