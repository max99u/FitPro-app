package com.example.fitproappmy;

public class Entrenamiento {

    private int id;
    private String nombre;
    private int repeticiones;
    private double peso;
    private String fecha;

    // Constructor
    public Entrenamiento(int id, String nombre, int repeticiones, double peso, String fecha) {
        this.id = id;
        this.nombre = nombre;
        this.repeticiones = repeticiones;
        this.peso = peso;
        this.fecha = fecha;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getRepeticiones() {
        return repeticiones;
    }

    public void setRepeticiones(int repeticiones) {
        this.repeticiones = repeticiones;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
