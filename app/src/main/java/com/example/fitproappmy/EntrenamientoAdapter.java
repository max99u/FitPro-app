package com.example.fitproappmy;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EntrenamientoAdapter extends RecyclerView.Adapter<EntrenamientoAdapter.ViewHolder> {

    private List<Entrenamiento> ejercicios;
    private Context context;

    public EntrenamientoAdapter(List<Entrenamiento> ejercicios, Context context) {
        this.ejercicios = ejercicios;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflar el diseño desde item_entrenamiento.xml
        View view = LayoutInflater.from(context).inflate(R.layout.item_entrenamiento, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Entrenamiento ejercicio = ejercicios.get(position);

        // Configurar los campos con los datos actuales
        holder.tvNombre.setText(ejercicio.getNombre());
        holder.etRepeticiones.setText(String.valueOf(ejercicio.getRepeticiones()));
        holder.etPeso.setText(String.valueOf(ejercicio.getPeso()));

        // Listener para cambios en repeticiones
        holder.etRepeticiones.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    ejercicio.setRepeticiones(Integer.parseInt(s.toString()));
                } catch (NumberFormatException e) {
                    ejercicio.setRepeticiones(0); // Valor por defecto
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Listener para cambios en peso
        holder.etPeso.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    ejercicio.setPeso(Double.parseDouble(s.toString()));
                } catch (NumberFormatException e) {
                    ejercicio.setPeso(0.0); // Valor por defecto
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    @Override
    public int getItemCount() {
        return ejercicios.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre;
        EditText etRepeticiones, etPeso;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tv_nombre_ejercicio);
            etRepeticiones = itemView.findViewById(R.id.et_repeticiones);
            etPeso = itemView.findViewById(R.id.et_peso);
        }
    }
}
