package com.codedev.proyectmovil.Helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.codedev.proyectmovil.Helpers.Clase.ClaseTable;
import com.codedev.proyectmovil.Helpers.Cursos.CursosTable;
import com.codedev.proyectmovil.Helpers.DetalleClase.DetalleClaseTable;
import com.codedev.proyectmovil.Helpers.Facultad.FacultadTable;
import com.codedev.proyectmovil.Helpers.Periodo.PeriodoTable;
import com.codedev.proyectmovil.Helpers.Persona.PersonaTable;
import com.codedev.proyectmovil.Helpers.Rol.RolTable;
import com.codedev.proyectmovil.Helpers.Usuario.UsuarioTable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Personas.db";
    private static final int DATABASE_VERSION = 12;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(FacultadTable.SQL_CREATE);
        db.execSQL(RolTable.SQL_CREATE);
        db.execSQL(PersonaTable.SQL_CREATE);
        db.execSQL(UsuarioTable.SQL_CREATE);
        db.execSQL(CursosTable.SQL_CREATE);
        db.execSQL(PeriodoTable.SQL_CREATE);
        db.execSQL(ClaseTable.SQL_CREATE);
        db.execSQL(DetalleClaseTable.SQL_CREATE);
        insertarFacultadesIniciales(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(FacultadTable.SQL_DROP);
        db.execSQL(RolTable.SQL_DROP);
        db.execSQL(PersonaTable.SQL_DROP);
        db.execSQL(UsuarioTable.SQL_DROP);
        db.execSQL(CursosTable.SQL_DROP);
        db.execSQL(PeriodoTable.SQL_DROP);
        db.execSQL(ClaseTable.SQL_DROP);
        db.execSQL(DetalleClaseTable.SQL_DROP);
        onCreate(db);
    }

    private void insertarFacultadesIniciales(SQLiteDatabase db) {
        db.execSQL("INSERT INTO " + FacultadTable.TABLE_NAME + " (" +
                FacultadTable.COL_CODIGO + ", " +
                FacultadTable.COL_NOMBRE + ", " +
                FacultadTable.COL_ESTADO + ") VALUES ('F001', 'Facultad de Ingeniería', 1)");

        db.execSQL("INSERT INTO " + FacultadTable.TABLE_NAME + " (" +

                FacultadTable.COL_CODIGO + ", " +
                FacultadTable.COL_NOMBRE + ", " +
                FacultadTable.COL_ESTADO + ") VALUES ('F002', 'Facultad de Medicina', 1)");

        db.execSQL("INSERT INTO " + RolTable.TABLE_NAME + " (" +
                RolTable.COL_ID + ", " +
                RolTable.COL_NOMBRE + ", " +
                RolTable.COL_ESTADO + ") VALUES (1, 'Administrador', 1)");
        db.execSQL("INSERT INTO " + RolTable.TABLE_NAME + " (" +
                RolTable.COL_ID + ", " +
                RolTable.COL_NOMBRE + ", " +
                RolTable.COL_ESTADO + ") VALUES (2, 'Alumno', 1)");
        db.execSQL("INSERT INTO " + RolTable.TABLE_NAME + " (" +
                RolTable.COL_ID + ", " +
                RolTable.COL_NOMBRE + ", " +
                RolTable.COL_ESTADO + ") VALUES (3, 'Profesor', 1)");
    }
}

