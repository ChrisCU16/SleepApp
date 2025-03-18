package com.example.sleepapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SqlLiteHelper extends SQLiteOpenHelper {

    private static final String NOMBRE_BASE_DATOS = "BDSleepApp";
    private static final int VERSION_BASE_DATOS = 5;

    // Nombres de las tablas
    private static final String TABLA_USUARIOS = "tusuarios";
    private static final String TABLA_RUTINA = "trutina";
    private static final String TABLA_ALARMAS = "talarma";
    private static final String TABLA_VIDEOS = "tvideos";
    // Columnas de la tabla tusuarios
    private static final String COLUMNA_DNI = "dni";
    private static final String COLUMNA_USUARIO = "usuario";
    private static final String COLUMNA_CONTRASENA = "contraseña";
    private static final String COLUMNA_NOMBRE = "nombre";
    private static final String COLUMNA_APELLIDO = "apellido";
    private static final String COLUMNA_CORREO = "correo";
    private static final String COLUMNA_FECHA_NACIMIENTO = "fechanacimiento";

    // Columnas de la tabla trutina
    private static final String COLUMNA_EDAD = "edad";
    private static final String COLUMNA_HORAS_TRABAJO = "horasTrabajo";
    private static final String COLUMNA_EJERCICIO_MINUTOS = "ejercicioMinutos";
    private static final String COLUMNA_RECREACION_MINUTOS = "recreacionMinutos";
    private static final String COLUMNA_CAFE_CONSUMO = "cafeConsumo"; // Café
    private static final String COLUMNA_TIEMPO_PANTALLA = "tiempoPantalla"; // Tiempo frente a pantallas
    private static final String COLUMNA_ESTRES = "estres"; // Nivel de estrés

    // Columnas de la tabla talarma
    private static final String COLUMNA_ID = "id"; // ID de la alarma
    private static final String COLUMNA_HORA_DESPERTAR = "horaDespertar";
    private static final String COLUMNA_TONO_ALARMA = "tonoAlarma";
    private static final String COLUMNA_DNI_USUARIO = "dniUsuario"; // Referencia al usuario
    // Columnas de la tabla tvideos
    private static final String COLUMNA_ID_VIDEO = "id_video"; // ID del video
    private static final String COLUMNA_TITULO = "titulo"; // Título del video
    private static final String COLUMNA_VIDEO_ID = "video_id"; // ID de video de YouTube


    public SqlLiteHelper(@Nullable Context context) {
        super(context, NOMBRE_BASE_DATOS, null, VERSION_BASE_DATOS);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear tabla tusuarios
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLA_USUARIOS + " (" +
                COLUMNA_DNI + " INTEGER PRIMARY KEY, " +
                COLUMNA_USUARIO + " TEXT NOT NULL, " +
                COLUMNA_CONTRASENA + " TEXT NOT NULL, " +
                COLUMNA_NOMBRE + " TEXT NOT NULL, " +
                COLUMNA_APELLIDO + " TEXT NOT NULL, " +
                COLUMNA_CORREO + " TEXT NOT NULL, " +
                COLUMNA_FECHA_NACIMIENTO + " TEXT NOT NULL)");

        // Crear tabla trutina
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLA_RUTINA + " (" +
                COLUMNA_DNI + " INTEGER, " +
                COLUMNA_EDAD + " TEXT NOT NULL, " +
                COLUMNA_HORAS_TRABAJO + " INTEGER NOT NULL, " +
                COLUMNA_EJERCICIO_MINUTOS + " INTEGER NOT NULL, " +
                COLUMNA_RECREACION_MINUTOS + " INTEGER NOT NULL, " +
                COLUMNA_CAFE_CONSUMO + " INTEGER NOT NULL, " + // Café
                COLUMNA_TIEMPO_PANTALLA + " INTEGER NOT NULL, " + // Tiempo frente a pantallas
                COLUMNA_ESTRES + " INTEGER NOT NULL, " + // Estrés
                "FOREIGN KEY (" + COLUMNA_DNI + ") REFERENCES " + TABLA_USUARIOS + "(" + COLUMNA_DNI + ") ON DELETE CASCADE)");

// Crear tabla talarma con los nuevos atributos
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLA_ALARMAS + " (" +
                COLUMNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMNA_HORA_DESPERTAR + " TEXT NOT NULL, " +
                COLUMNA_TONO_ALARMA + " TEXT NOT NULL, " +
                "descripcion TEXT, " +
                "repeticion TEXT, " +
                "estado INTEGER DEFAULT 1, " +
                "duracion_tono INTEGER DEFAULT 30, " +
                COLUMNA_DNI_USUARIO + " INTEGER NOT NULL, " +
                "FOREIGN KEY (" + COLUMNA_DNI_USUARIO + ") REFERENCES " + TABLA_USUARIOS + "(" + COLUMNA_DNI + "))");

        // Crear tabla de videos
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLA_VIDEOS + " (" +
                COLUMNA_ID_VIDEO + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMNA_TITULO + " TEXT NOT NULL, " +
                COLUMNA_VIDEO_ID + " TEXT NOT NULL)");
        // Insertar videos de relajación
        insertarVideosDeRelajacion(db);
    }
    private void insertarVideosDeRelajacion(SQLiteDatabase db) {
        insertarVideo(db, "Video de Relajación 1", "2JfnuCe74gQ");
        insertarVideo(db, "Video de Relajación 2", "nTYYPu7sWBA");
        insertarVideo(db, "Video de Relajación 3", "XT9VEY-pra0");
    }
    private void insertarVideo(SQLiteDatabase db, String titulo, String videoId) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMNA_TITULO, titulo);
        contentValues.put(COLUMNA_VIDEO_ID, videoId);
        db.insert(TABLA_VIDEOS, null, contentValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_USUARIOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_RUTINA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_ALARMAS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_VIDEOS);
        onCreate(db);
    }

    // Método para insertar datos de rutina
    public boolean insertarRutina(int dni, String edad, int horasTrabajo, int tiempoEjercicio, int tiempoRecreacion, int consumoCafe, int tiempoPantalla, int nivelEstres) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMNA_DNI, dni);
        contentValues.put(COLUMNA_EDAD, edad);
        contentValues.put(COLUMNA_HORAS_TRABAJO, horasTrabajo);
        contentValues.put(COLUMNA_EJERCICIO_MINUTOS, tiempoEjercicio);
        contentValues.put(COLUMNA_RECREACION_MINUTOS, tiempoRecreacion);
        contentValues.put(COLUMNA_CAFE_CONSUMO, consumoCafe); // Café
        contentValues.put(COLUMNA_TIEMPO_PANTALLA, tiempoPantalla); // Tiempo frente a pantallas
        contentValues.put(COLUMNA_ESTRES, nivelEstres); // Estrés

        long resultado = db.insert(TABLA_RUTINA, null, contentValues);
        return resultado != -1;  // Devuelve verdadero si la inserción fue exitosa
    }

    // Método para insertar una nueva alarma con atributos adicionales
    public boolean insertarAlarma(String horaDespertar, String tonoAlarma, int dniUsuario,
                                  @Nullable String descripcion, @Nullable String repeticion,
                                  int estado, int duracionTono) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMNA_HORA_DESPERTAR, horaDespertar);
        contentValues.put(COLUMNA_TONO_ALARMA, tonoAlarma);
        contentValues.put(COLUMNA_DNI_USUARIO, dniUsuario);
        contentValues.put("descripcion", descripcion);
        contentValues.put("repeticion", repeticion);
        contentValues.put("estado", estado);
        contentValues.put("duracion_tono", duracionTono);

        long resultado = db.insert(TABLA_ALARMAS, null, contentValues);
        return resultado != -1; // Devuelve verdadero si la inserción fue exitosa
    }
    // Método para obtener todas las alarmas activas de un usuario
    public Cursor obtenerAlarmasActivas(int dniUsuario) {
        SQLiteDatabase db = this.getReadableDatabase();
        String consulta = "SELECT * FROM " + TABLA_ALARMAS +
                " WHERE " + COLUMNA_DNI_USUARIO + " = ? AND estado = 1";
        return db.rawQuery(consulta, new String[]{String.valueOf(dniUsuario)});
    }
    // Método para activar o desactivar una alarma
    public boolean actualizarEstadoAlarma(int idAlarma, int nuevoEstado) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("estado", nuevoEstado);
        int resultado = db.update(TABLA_ALARMAS, contentValues, COLUMNA_ID + " = ?",
                new String[]{String.valueOf(idAlarma)});
        return resultado > 0; // Devuelve verdadero si la actualización fue exitosa
    }


    // Método para obtener duración adicional de rutina
// Método para obtener duración adicional de rutina basada en el último registro
    public int obtenerDuracionAdicionalDeRutina(int dni) {
        int horasAdicionales = 0; // Variable para almacenar las horas adicionales
        SQLiteDatabase db = this.getReadableDatabase();

        // Consulta para obtener el último registro de rutina del usuario
        String consulta = "SELECT " +
                COLUMNA_CAFE_CONSUMO + ", " +
                COLUMNA_TIEMPO_PANTALLA + ", " +
                COLUMNA_ESTRES +
                " FROM " + TABLA_RUTINA +
                " WHERE " + COLUMNA_DNI + " = ? " +
                " ORDER BY ROWID DESC LIMIT 1"; // Obtener el último registro

        Cursor cursor = db.rawQuery(consulta, new String[]{String.valueOf(dni)});

        if (cursor.moveToFirst()) {
            int consumoCafe = cursor.getInt(cursor.getColumnIndex(COLUMNA_CAFE_CONSUMO));
            int tiempoPantalla = cursor.getInt(cursor.getColumnIndex(COLUMNA_TIEMPO_PANTALLA));
            int nivelEstres = cursor.getInt(cursor.getColumnIndex(COLUMNA_ESTRES));

            // Lógica para determinar las horas adicionales
            if (consumoCafe > 2) {
                horasAdicionales += 1; // Aumentar una hora
            }
            if (tiempoPantalla > 3) { // Condición ajustable
                horasAdicionales += 1; // Aumentar otra hora
            }
            if (nivelEstres > 5) {
                horasAdicionales += 1; // Aumentar otra hora
            }
        }

        cursor.close();
        return horasAdicionales; // Retorna la duración adicional
    }



    // Método para sugerir la hora de dormir
    public String sugerirHoraDormir(String horaDespertar, int horasAdicionales) {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        String horaSugerida = "";

        try {
            Date fechaDespertar = sdf.parse(horaDespertar);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fechaDespertar);

            // Sumar 8 horas de sueño y las horas adicionales
            calendar.add(Calendar.HOUR_OF_DAY, 8 + horasAdicionales);

            // Obtener la nueva hora sugerida
            horaSugerida = sdf.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return horaSugerida; // Devuelve la hora sugerida para dormir
    }
    public Cursor obtenerRutinaPorDni(int dni) {
        SQLiteDatabase db = this.getReadableDatabase();
        String consulta = "SELECT horasTrabajo, ejercicioMinutos, recreacionMinutos, cafeConsumo, tiempoPantalla, estres FROM trutina WHERE dni = ?";
        return db.rawQuery(consulta, new String[]{String.valueOf(dni)});
    }
    public Cursor obtenerUltimasSieteAlarmas(int dniUsuario) {
        SQLiteDatabase db = this.getReadableDatabase();
        String consulta = "SELECT * FROM " + TABLA_ALARMAS + " WHERE " + COLUMNA_DNI_USUARIO + " = ? ORDER BY " + COLUMNA_ID + " DESC LIMIT 7";
        return db.rawQuery(consulta, new String[]{String.valueOf(dniUsuario)});
    }

    // Método para obtener todos los videos
    public Cursor obtenerVideos() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLA_VIDEOS, null);
    }
    public Cursor obtenerAlarmas() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLA_ALARMAS, null, null, null, null, null, null); // Cambiado aquí
    }


    public Cursor obtenerUsuario() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLA_USUARIOS, null, null, null, null, null, null);
    }

}

