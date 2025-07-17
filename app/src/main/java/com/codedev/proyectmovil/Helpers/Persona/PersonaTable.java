package com.codedev.proyectmovil.Helpers.Persona;

public class PersonaTable {
    public static final String TABLE_NAME = "Persona";
    public static final String COL_ID = "id";
    public static final String COL_CODIGO = "codigo";
    public static final String COL_NOMBRE = "nombre";
    public static final String COL_APELLIDO = "apellido";
    public static final String COL_IDFACULTAD = "facultad_id";
    public static final String COL_ESTADO = "estado";

    public static final String SQL_CREATE = "CREATE TABLE " + TABLE_NAME + " (" +
            COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_CODIGO      + " TEXT, " +
            COL_NOMBRE      + " TEXT, " +
            COL_APELLIDO    + " TEXT, " +
            COL_IDFACULTAD  + " INTEGER, " +
            COL_ESTADO      + " INTEGER)";

    public static final String SQL_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME;

}
