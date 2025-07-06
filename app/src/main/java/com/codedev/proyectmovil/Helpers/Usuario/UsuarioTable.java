package com.codedev.proyectmovil.Helpers.Usuario;

public class UsuarioTable {
    public static final String TABLE_NAME = "Usuario";
    public static final String COL_ID = "id";
    public static final String COL_USUARIO = "usuario";
    public static final String COL_CONTRASENIA = "contrasenia";
    public static final String COL_PERSONA_ID = "persona_id";
    public static final String COL_ROL_ID = "rol_id";
    public static final String COL_ESTADO = "estado";

    public static final String SQL_CREATE = "CREATE TABLE " + TABLE_NAME + " (" +
            COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_USUARIO + " TEXT, " +
            COL_CONTRASENIA + " TEXT, " +
            COL_PERSONA_ID + " INTEGER, " +
            COL_ROL_ID + " INTEGER, " +
            COL_ESTADO + " INTEGER)";

    public static final String SQL_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME;
}
