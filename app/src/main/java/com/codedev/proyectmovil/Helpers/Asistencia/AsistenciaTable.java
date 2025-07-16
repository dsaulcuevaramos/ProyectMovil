package com.codedev.proyectmovil.Helpers.Asistencia;

public class AsistenciaTable {

    public static final String TABLE_NAME = "Asistencia";
    public static final String COL_ID = "id";
    public static final String COL_FECHA = "fecha";
    public static final String COL_ACTIVO = "activo";
    public static final String COL_ESTADO = "estado";

    public static final String SQL_CREATE = "CREATE TABLE " + TABLE_NAME + " (" +
            COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_FECHA + " TEXT, " +
            COL_ACTIVO + " INTEGER, " +
            COL_ESTADO      + " INTEGER)";

    public static final String SQL_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME;

}
