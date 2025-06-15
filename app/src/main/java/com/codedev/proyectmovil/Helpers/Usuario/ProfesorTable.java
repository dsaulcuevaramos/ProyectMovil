package com.codedev.proyectmovil.Helpers.Usuario;

public class ProfesorTable {

    public static final String TABLE_NAME = "profesor";
    public static final String COL_ID = "id";
    public static final String COL_CODIGO = "codigo";

    public static final String COL_NOMBRE = "nombre";
    public static final String COL_CORREO = "correo";
    public static final String COL_IDFACULTAD = "idfacultad";
    public static final String COL_ESTADO = "estado";


    public static final String SQL_CREATE = "CREATE TABLE " + TABLE_NAME + " (" +
            COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_CODIGO          + " TEXT, " +
            COL_NOMBRE          + " TEXT, " +
            COL_CORREO          + " TEXT, " +
            COL_IDFACULTAD          + " TEXT, " +
            COL_ESTADO      + " TEXT)";

    public static final String SQL_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME;
}
