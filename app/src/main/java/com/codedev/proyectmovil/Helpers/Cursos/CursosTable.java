package com.codedev.proyectmovil.Helpers.Cursos;

public class CursosTable {
    public static final String TABLE_NAME = "Cursos";
    public static final String COL_ID = "id";
    public static final String COL_NOMBRE = "nombre";
    public static final String COL_CODIGO = "codigo";
    public static final String COL_IDFACULTAD = "idFacultad";
    public static final String COL_ESTADO = "estado";

    public static final String SQL_CREATE = "CREATE TABLE " + TABLE_NAME + " (" +
            COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_NOMBRE      + " TEXT, " +
            COL_CODIGO      + " TEXT, " +
            COL_IDFACULTAD + " INTEGER, " +
            COL_ESTADO      + " INTEGER)";

    public static final String SQL_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME;
}
