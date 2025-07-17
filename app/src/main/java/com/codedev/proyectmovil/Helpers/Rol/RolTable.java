package com.codedev.proyectmovil.Helpers.Rol;

public class RolTable {
    public static final String TABLE_NAME = "Rol";
    public static final String COL_ID = "id";
    public static final String COL_NOMBRE = "nombre";
    public static final String COL_ESTADO = "estado";

    public static final String SQL_CREATE = "CREATE TABLE " + TABLE_NAME + " (" +
            COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_NOMBRE + " TEXT, " +
            COL_ESTADO + " INTEGER)";

    public static final String SQL_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME;
    public static final String SQL_INSERT = "DROP TABLE IF EXISTS " + TABLE_NAME;
}
