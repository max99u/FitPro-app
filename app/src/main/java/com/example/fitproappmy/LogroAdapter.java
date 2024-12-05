package com.example.fitproappmy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LogroAdapter extends RecyclerView.Adapter<LogroAdapter.LogroViewHolder> {

    private List<Logro> listaLogros;
    private Context context;

    public LogroAdapter(List<Logro> listaLogros, Context context) {
        this.listaLogros = listaLogros;
        this.context = context;
    }

    @NonNull
    @Override
    public LogroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_logro, parent, false);
        return new LogroViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LogroViewHolder holder, int position) {
        Logro logro = listaLogros.get(position);
        holder.bind(logro);
    }

    @Override
    public int getItemCount() {
        return listaLogros.size();
    }

    public static class LogroViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgLogro;
        private TextView tvTituloLogro;
        private TextView tvDescripcionLogro;

        public LogroViewHolder(@NonNull View itemView) {
            super(itemView);
            imgLogro = itemView.findViewById(R.id.img_logro);
            tvTituloLogro = itemView.findViewById(R.id.tv_titulo_logro);
            tvDescripcionLogro = itemView.findViewById(R.id.tv_descripcion_logro);
        }

        public void bind(Logro logro) {
            tvTituloLogro.setText(logro.getTitulo());
            tvDescripcionLogro.setText(logro.getDescripcion());
            imgLogro.setImageResource(logro.getImageResource());
        }
    }
}
