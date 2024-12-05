package com.example.fitproappmy;

public class Logro {
    private String titulo;
    private String descripcion;
    private int imageResource;

    public Logro(String titulo, String descripcion, int imageResource) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.imageResource = imageResource;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getImageResource() {
        return imageResource;
    }
}
