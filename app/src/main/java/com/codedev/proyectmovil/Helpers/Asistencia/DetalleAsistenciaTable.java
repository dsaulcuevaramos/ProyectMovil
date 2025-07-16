package com.codedev.proyectmovil.Helpers.Asistencia;

public class DetalleAsistenciaTable {

    public static final String TABLE_NAME = "Detalle_asistencia";
    public static final String COL_ID = "id";
    public static final String COL_ASISTENCIA_ID = "asistencia_id";
    public static final String COL_DETALLECLASE_ID= "detaleclase_id";
    public static final String COL_ASISTENCIA_VALOR= "asistencia_valor";
    public static final String COL_OBSERVACION= "observacion";
    public static final String COL_ESTADO= "estado";


    public static final String SQL_CREATE = "CREATE TABLE " + TABLE_NAME + " (" +
            COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_ASISTENCIA_ID + " INTEGER, " +
            COL_DETALLECLASE_ID + " INTEGER, " +
            COL_ASISTENCIA_VALOR + " INTEGER, " +
            COL_OBSERVACION + " TEXT, " +
            COL_ESTADO + " INTEGER)";


    public static final String SQL_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME;

}
