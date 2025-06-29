package com.codedev.proyectmovil.Helpers.Usuario;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.codedev.proyectmovil.Models.ProfesorModel;

import java.util.ArrayList;
import java.util.List;

public class ProfesorDAO {

    private DatabaseHelper helper;

    public ProfesorDAO(Context context) {
        helper = new DatabaseHelper(context);
    }

    public boolean addProfesor(ProfesorModel u){
        try (SQLiteDatabase db = this.helper.getWritableDatabase()){
            ContentValues values = new ContentValues();
            values.put(ProfesorTable.COL_CODIGO, u.getCodigo());
            values.put(ProfesorTable.COL_NOMBRE, u.getNombre());
            values.put(ProfesorTable.COL_CORREO, u.getCorreo());
            values.put(ProfesorTable.COL_IDFACULTAD, 1);
            values.put(ProfesorTable.COL_ESTADO, 1);
            long resultado = db.insert(ProfesorTable.TABLE_NAME, null, values);
            return resultado != -1;
        }
    }

    public boolean updateProfesor(ProfesorModel u){
        try (SQLiteDatabase db = this.helper.getWritableDatabase()){
            ContentValues values = new ContentValues();
            values.put(ProfesorTable.COL_CODIGO, u.getCodigo());
            values.put(ProfesorTable.COL_NOMBRE, u.getNombre());
            values.put(ProfesorTable.COL_CORREO, u.getCorreo());
            values.put(ProfesorTable.COL_IDFACULTAD, 1);
            values.put(ProfesorTable.COL_ESTADO, 1);
            long filas = db.update(ProfesorTable.TABLE_NAME, values, ProfesorTable.COL_ID + " = ?", new String[]{String.valueOf(u.getId())});
            return filas > 0;
        }
    }

    public boolean deleteProfesor(int id){
        try (SQLiteDatabase db = this.helper.getWritableDatabase()){
            ContentValues values = new ContentValues();
            values.put(ProfesorTable.COL_ESTADO, 0);
            long filas = db.update(ProfesorTable.TABLE_NAME, values, ProfesorTable.COL_ID + " =?", new String[]{String.valueOf(id)});
            return filas > 0;
        }
    }

    public List<ProfesorModel> getProfesor(){
        try (SQLiteDatabase db = this.helper.getReadableDatabase()){
            List<ProfesorModel> listaProfesores = new ArrayList<>();
            Cursor cursor = db.rawQuery("SELECT * FROM " + ProfesorTable.TABLE_NAME + " WHERE " + ProfesorTable.COL_ESTADO + " = 1", null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    ProfesorModel p = new ProfesorModel();
                    p.setId(cursor.getString(cursor.getColumnIndexOrThrow(ProfesorTable.COL_ID)));
                    p.setCodigo(cursor.getString(cursor.getColumnIndexOrThrow(ProfesorTable.COL_CODIGO)));
                    p.setNombre(cursor.getString(cursor.getColumnIndexOrThrow(ProfesorTable.COL_NOMBRE)));
                    p.setCorreo(cursor.getString(cursor.getColumnIndexOrThrow(ProfesorTable.COL_CORREO)));
                    p.setIdfacultad(cursor.getString(cursor.getColumnIndexOrThrow(ProfesorTable.COL_IDFACULTAD)));
                    p.setEstado(cursor.getString(cursor.getColumnIndexOrThrow(ProfesorTable.COL_ESTADO)));
                    listaProfesores.add(p);
                } while (cursor.moveToNext());
            }

            cursor.close();
            return listaProfesores;
        }
    }
}
