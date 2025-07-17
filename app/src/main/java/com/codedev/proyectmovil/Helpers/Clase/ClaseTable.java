package com.codedev.proyectmovil.Helpers.Clase;

public class ClaseTable {
    public static final String TABLE_NAME = "Clase";
    public static final String COL_ID = "id";
    public static final String COL_GRUPO = "grupo";
    public static final String COL_CURSO_ID = "curso_id";
    public static final String COL_PERIODO_ID = "periodo_id";
    public static final String COL_ESTADO = "estado";

    public static final String SQL_CREATE = "CREATE TABLE " + TABLE_NAME + " (" +
            COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_GRUPO + " TEXT, " +
            COL_PERIODO_ID    + " INTEGER, " +
            COL_CURSO_ID + " INTEGER, " +
            COL_ESTADO      + " INTEGER)";

    public static final String SQL_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME;
}
