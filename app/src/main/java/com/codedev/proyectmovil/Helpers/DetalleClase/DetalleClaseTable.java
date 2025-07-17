package com.codedev.proyectmovil.Helpers.DetalleClase;

public class DetalleClaseTable {
    public static final String TABLE_NAME = "Detalle_clase";
    public static final String COL_ID = "id";
    public static final String COL_ESTADO_ASISTENCIA = "estado_asistencia";
    public static final String COL_CLASE_ID = "clase_id";
    public static final String COL_PERSONA_ID = "persona_id";
    public static final String COL_ESTADO = "estado";

    public static final String SQL_CREATE = "CREATE TABLE " + TABLE_NAME + " (" +
            COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_ESTADO_ASISTENCIA + " INTEGER, " +
            COL_PERSONA_ID + " INTEGER, " +
            COL_CLASE_ID + " INTEGER, " +
            COL_ESTADO      + " INTEGER)";

    public static final String SQL_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME;
}
