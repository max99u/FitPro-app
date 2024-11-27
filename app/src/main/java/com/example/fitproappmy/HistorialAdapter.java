package com.example.fitproappmy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HistorialAdapter extends RecyclerView.Adapter<HistorialAdapter.HistorialViewHolder> {

    private List<Entrenamiento> listaEntrenamientos;
    private Context context; // Agregado para manejar el contexto
    private OnItemClickListener onItemClickListener;
    private OnDeleteClickListener onDeleteClickListener;

    // Constructor actualizado para incluir el contexto
    public HistorialAdapter(List<Entrenamiento> listaEntrenamientos, Context context) {
        this.listaEntrenamientos = listaEntrenamientos;
        this.context = context;
    }

    // Interfaz para manejar clics en los elementos del historial
    public interface OnItemClickListener {
        void onItemClick(Entrenamiento entrenamiento);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    // Interfaz para manejar la acción de eliminar
    public interface OnDeleteClickListener {
        void onDeleteClick(Entrenamiento entrenamiento);
    }

    public void setOnDeleteClickListener(OnDeleteClickListener listener) {
        this.onDeleteClickListener = listener;
    }

    @Override
    public HistorialViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_historial, parent, false); // Usa el contexto
        return new HistorialViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HistorialViewHolder holder, int position) {
        Entrenamiento entrenamiento = listaEntrenamientos.get(position);
        holder.bind(entrenamiento, onItemClickListener, onDeleteClickListener);
    }

    @Override
    public int getItemCount() {
        return listaEntrenamientos.size();
    }

    public static class HistorialViewHolder extends RecyclerView.ViewHolder {

        private TextView tvNombreEntrenamiento;
        private TextView tvFechaEntrenamiento;
        private Button btnEliminarEntrenamiento;

        public HistorialViewHolder(View itemView) {
            super(itemView);
            tvNombreEntrenamiento = itemView.findViewById(R.id.tv_nombre_entrenamiento);
            tvFechaEntrenamiento = itemView.findViewById(R.id.tv_fecha_entrenamiento);
            btnEliminarEntrenamiento = itemView.findViewById(R.id.btn_eliminar_entrenamiento);
        }

        public void bind(final Entrenamiento entrenamiento, final OnItemClickListener listener, final OnDeleteClickListener deleteListener) {
            tvNombreEntrenamiento.setText(entrenamiento.getNombre());
            tvFechaEntrenamiento.setText(entrenamiento.getFecha());

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClick(entrenamiento);
                }
            });

            // Listener para eliminar el entrenamiento
            btnEliminarEntrenamiento.setOnClickListener(v -> {
                if (deleteListener != null) {
                    deleteListener.onDeleteClick(entrenamiento);
                }
            });
        }
    }
}
