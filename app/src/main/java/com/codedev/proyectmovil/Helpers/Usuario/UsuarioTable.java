package com.codedev.proyectmovil.Helpers.Usuario;

public class UsuarioTable {
    public static final String TABLE_NAME = "Usuario";
    public static final String COL_ID = "id";
    public static final String COL_NOMBRE = "nombre";
    public static final String COL_CORREO = "correo";
    public static final String COL_CONTRASENIA = "contrasenia";
    public static final String COL_ESTADO = "estado";

    public static final String SQL_CREATE = "CREATE TABLE " + TABLE_NAME + " (" +
            COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_NOMBRE      + " TEXT, " +
            COL_CORREO      + " TEXT, " +
            COL_CONTRASENIA + " TEXT, " +
            COL_ESTADO      + " INTEGER)";

    public static final String SQL_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME;
}
