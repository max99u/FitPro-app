package com.example.fitproappmy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EjercicioAdapter extends RecyclerView.Adapter<EjercicioAdapter.EjercicioViewHolder> {

    private List<Ejercicio> listaEjercicios;
    private Context context;
    private OnItemClickListener onItemClickListener; // Listener para el clic en el ejercicio

    // Constructor del adaptador
    public EjercicioAdapter(List<Ejercicio> listaEjercicios, Context context) {
        this.listaEjercicios = listaEjercicios;
        this.context = context;
    }

    // Interfaz para manejar clics en los elementos del listado
    public interface OnItemClickListener {
        void onItemClick(Ejercicio ejercicio);
    }

    // Método para configurar el listener desde EjerciciosActivity
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public EjercicioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_ejercicio, parent, false);
        return new EjercicioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EjercicioViewHolder holder, int position) {
        Ejercicio ejercicio = listaEjercicios.get(position);
        holder.bind(ejercicio, onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return listaEjercicios.size();
    }

    public static class EjercicioViewHolder extends RecyclerView.ViewHolder {

        private TextView tvNombreEjercicio;

        public EjercicioViewHolder(View itemView) {
            super(itemView);
            tvNombreEjercicio = itemView.findViewById(R.id.tv_nombre_ejercicio);
        }

        public void bind(final Ejercicio ejercicio, final OnItemClickListener listener) {
            // Asignar nombre del ejercicio al TextView
            tvNombreEjercicio.setText(ejercicio.getNombre());

            // Configurar el clic
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClick(ejercicio);
                }
            });
        }
    }
}
