package com.codedev.proyectmovil.Helpers.Alumno;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.codedev.proyectmovil.Helpers.DatabaseHelper;
import com.codedev.proyectmovil.Models.AlumnoModel;

import java.util.ArrayList;
import java.util.List;

public class AlumnoDAO {
    private DatabaseHelper helper;

    public AlumnoDAO (Context context) {helper = new DatabaseHelper(context);}

    public boolean addAlumno(AlumnoModel a){
        try(SQLiteDatabase db = this.helper.getWritableDatabase()){
            ContentValues values = new ContentValues();
            values.put(AlumnoTable.COL_CODIGO, a.getCodigo());
            values.put(AlumnoTable.COL_NOMBRE, a.getNombre());
            values.put(AlumnoTable.COL_CORREO, a.getCorreo());
            values.put(AlumnoTable.COL_IDFACTULAD, a.getIdFacultad());
            values.put(AlumnoTable.COL_ESTADO, a.getEstado());
            long resultado = db.insert(AlumnoTable.TABLE_NAME,null,values);
            return resultado != -1;
        }
    }

    public boolean updateAlumno(AlumnoModel a){
        try (SQLiteDatabase db = this.helper.getWritableDatabase()){
            ContentValues values = new ContentValues();
            values.put(AlumnoTable.COL_CODIGO, a.getCodigo());
            values.put(AlumnoTable.COL_NOMBRE, a.getNombre());
            values.put(AlumnoTable.COL_CORREO, a.getCorreo());
            values.put(AlumnoTable.COL_IDFACTULAD, a.getIdFacultad());
            values.put(AlumnoTable.COL_ESTADO, a.getEstado());
            long filas = db.update(AlumnoTable.TABLE_NAME,values,AlumnoTable.COL_ID + " = ?", new String[]{String.valueOf(a.getId())});
            return filas > 0;
        }
    }

    public boolean deleteAlumno(int id){
        try (SQLiteDatabase db = this.helper.getWritableDatabase()){
            ContentValues values = new ContentValues();
            values.put(AlumnoTable.COL_ESTADO, 0);
            long filas = db.update(AlumnoTable.TABLE_NAME, values, AlumnoTable.COL_ID + " =?", new String[]{String.valueOf(id)});
            return filas > 0;
        }
    }

    public List<AlumnoModel> getAlumnos(){
        try (SQLiteDatabase db = this.helper.getReadableDatabase()){
            List<AlumnoModel> listaUsuarios = new ArrayList<>();
            Cursor cursor = db.rawQuery("SELECT * FROM " + AlumnoTable.TABLE_NAME + " WHERE " + AlumnoTable.COL_ESTADO + " = 1", null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    AlumnoModel a = new AlumnoModel();
                    a.setId(cursor.getInt(cursor.getColumnIndexOrThrow(AlumnoTable.COL_ID)));
                    a.setCodigo(cursor.getString(cursor.getColumnIndexOrThrow(AlumnoTable.COL_CODIGO)));
                    a.setNombre(cursor.getString(cursor.getColumnIndexOrThrow(AlumnoTable.COL_NOMBRE)));
                    a.setCorreo(cursor.getString(cursor.getColumnIndexOrThrow(AlumnoTable.COL_CORREO)));
                    a.setIdFacultad(cursor.getInt(cursor.getColumnIndexOrThrow(AlumnoTable.COL_IDFACTULAD)));
                    a.setEstado(cursor.getInt(cursor.getColumnIndexOrThrow(AlumnoTable.COL_ESTADO)));
                    listaUsuarios.add(a);
                } while (cursor.moveToNext());
            }

            cursor.close();
            return listaUsuarios;
        }
    }

}
