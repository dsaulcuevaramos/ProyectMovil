package com.codedev.proyectmovil.Helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.codedev.proyectmovil.Helpers.Cursos.CursosTable;
import com.codedev.proyectmovil.Helpers.Usuario.UsuarioTable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Personas.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(UsuarioTable.SQL_CREATE);
        db.execSQL(CursosTable.SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(UsuarioTable.SQL_DROP);
        db.execSQL(CursosTable.SQL_DROP);
        onCreate(db);
    }
}
