package com.example.fitproappmy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Constantes para la base de datos y tablas
    private static final String DATABASE_NAME = "fitproapp.db";
    private static final int DATABASE_VERSION = 2;

    // Tabla para los entrenamientos
    private static final String TABLE_ENTRENAMIENTOS = "Entrenamientos";
    private static final String COLUMN_ENTRENAMIENTO_ID = "id";
    private static final String COLUMN_ENTRENAMIENTO_NOMBRE = "nombre";
    private static final String COLUMN_ENTRENAMIENTO_FECHA = "fecha";

    // Tabla para los ejercicios
    private static final String TABLE_EJERCICIOS = "Ejercicios";
    private static final String COLUMN_EJERCICIO_ID = "id";
    private static final String COLUMN_EJERCICIO_ENTRENAMIENTO_ID = "entrenamiento_id";
    private static final String COLUMN_EJERCICIO_NOMBRE = "nombre";
    private static final String COLUMN_EJERCICIO_REPETICIONES = "repeticiones";
    private static final String COLUMN_EJERCICIO_PESO = "peso";

    // Constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear tabla para entrenamientos
        String createTrainingsTable = "CREATE TABLE " + TABLE_ENTRENAMIENTOS + " (" +
                COLUMN_ENTRENAMIENTO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ENTRENAMIENTO_NOMBRE + " TEXT, " +
                COLUMN_ENTRENAMIENTO_FECHA + " TEXT)";
        db.execSQL(createTrainingsTable);

        // Crear tabla para ejercicios
        String createExercisesTable = "CREATE TABLE " + TABLE_EJERCICIOS + " (" +
                COLUMN_EJERCICIO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_EJERCICIO_ENTRENAMIENTO_ID + " INTEGER, " +
                COLUMN_EJERCICIO_NOMBRE + " TEXT, " +
                COLUMN_EJERCICIO_REPETICIONES + " INTEGER, " +
                COLUMN_EJERCICIO_PESO + " REAL, " +
                "FOREIGN KEY(" + COLUMN_EJERCICIO_ENTRENAMIENTO_ID + ") REFERENCES " +
                TABLE_ENTRENAMIENTOS + "(" + COLUMN_ENTRENAMIENTO_ID + "))";
        db.execSQL(createExercisesTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EJERCICIOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ENTRENAMIENTOS);
        onCreate(db);
    }

    // **Métodos para entrenamientos**
    public long insertarEntrenamiento(String nombre, String fecha) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ENTRENAMIENTO_NOMBRE, nombre);
        values.put(COLUMN_ENTRENAMIENTO_FECHA, fecha);
        return db.insert(TABLE_ENTRENAMIENTOS, null, values); // Devuelve el ID del entrenamiento
    }

    public List<Entrenamiento> obtenerEntrenamientos() {
        List<Entrenamiento> listaEntrenamientos = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ENTRENAMIENTOS, null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                listaEntrenamientos.add(new Entrenamiento(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ENTRENAMIENTO_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ENTRENAMIENTO_NOMBRE)),
                        0, 0.0,
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ENTRENAMIENTO_FECHA))
                ));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return listaEntrenamientos;
    }

    public Entrenamiento obtenerEntrenamientoPorId(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ENTRENAMIENTOS, null, COLUMN_ENTRENAMIENTO_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            Entrenamiento entrenamiento = new Entrenamiento(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ENTRENAMIENTO_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ENTRENAMIENTO_NOMBRE)),
                    0, 0.0,
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ENTRENAMIENTO_FECHA))
            );
            cursor.close();
            return entrenamiento;
        }
        return null;
    }

    // **Métodos para ejercicios**
    public long insertarEjercicio(int entrenamientoId, String nombre, int repeticiones, double peso) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EJERCICIO_ENTRENAMIENTO_ID, entrenamientoId);
        values.put(COLUMN_EJERCICIO_NOMBRE, nombre);
        values.put(COLUMN_EJERCICIO_REPETICIONES, repeticiones);
        values.put(COLUMN_EJERCICIO_PESO, peso);
        return db.insert(TABLE_EJERCICIOS, null, values); // Devuelve el ID del ejercicio
    }

    // Eliminar entrenamiento y sus ejercicios
    public void eliminarEntrenamiento(int entrenamientoId) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Eliminar los ejercicios asociados primero
        db.delete(TABLE_EJERCICIOS, COLUMN_EJERCICIO_ENTRENAMIENTO_ID + "=?", new String[]{String.valueOf(entrenamientoId)});
        // Luego eliminar el entrenamiento
        db.delete(TABLE_ENTRENAMIENTOS, COLUMN_ENTRENAMIENTO_ID + "=?", new String[]{String.valueOf(entrenamientoId)});
    }

    public List<Entrenamiento> obtenerEjerciciosPorEntrenamiento(int entrenamientoId) {
        List<Entrenamiento> listaEjercicios = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_EJERCICIOS, null,
                COLUMN_EJERCICIO_ENTRENAMIENTO_ID + "=?",
                new String[]{String.valueOf(entrenamientoId)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                listaEjercicios.add(new Entrenamiento(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_EJERCICIO_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EJERCICIO_NOMBRE)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_EJERCICIO_REPETICIONES)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_EJERCICIO_PESO)),
                        null
                ));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return listaEjercicios;
    }

}

