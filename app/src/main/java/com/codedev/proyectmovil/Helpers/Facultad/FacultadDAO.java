package com.codedev.proyectmovil.Helpers.Facultad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.codedev.proyectmovil.Helpers.DatabaseHelper;
import com.codedev.proyectmovil.Models.FacultadModel;
import com.codedev.proyectmovil.Models.PersonaModel;

import java.util.ArrayList;
import java.util.List;

public class FacultadDAO {
    private DatabaseHelper helper;

    public FacultadDAO(Context context) {
        helper = new DatabaseHelper(context);
    }

    public List<FacultadModel> getAllFacultad(){
        try (SQLiteDatabase db = this.helper.getReadableDatabase()){
            List<FacultadModel> listaFacultades = new ArrayList<>();
            Cursor cursor = db.rawQuery("SELECT * FROM " + FacultadTable.TABLE_NAME + " WHERE " + FacultadTable.COL_ESTADO + " = 1", null);

            if (cursor.moveToFirst()) {
                do {
                    FacultadModel f = new FacultadModel();
                    f.setId(cursor.getInt(cursor.getColumnIndexOrThrow(FacultadTable.COL_ID)));
                    f.setCodigo(cursor.getString(cursor.getColumnIndexOrThrow(FacultadTable.COL_CODIGO)));
                    f.setNombre(cursor.getString(cursor.getColumnIndexOrThrow(FacultadTable.COL_NOMBRE)));
                    f.setEstado(cursor.getInt(cursor.getColumnIndexOrThrow(FacultadTable.COL_ESTADO)));
                    listaFacultades.add(f);
                } while (cursor.moveToNext());
            }

            cursor.close();
            return listaFacultades;
        }
    }

    public FacultadModel addFacultad(FacultadModel f){
        try (SQLiteDatabase db = this.helper.getWritableDatabase()){
            ContentValues values = new ContentValues();
            values.put(FacultadTable.COL_CODIGO, f.getCodigo());
            values.put(FacultadTable.COL_NOMBRE, f.getNombre());
            values.put(FacultadTable.COL_ESTADO, 1);
            long resultado = db.insert(FacultadTable.TABLE_NAME, null, values);
            if (resultado != -1) {
                f.setId((int) resultado);
                return f;
            } else {
                return null;
            }
        }
    }

    public boolean updateFacultad(FacultadModel f){
        try (SQLiteDatabase db = this.helper.getWritableDatabase()){
            ContentValues values = new ContentValues();
            values.put(FacultadTable.COL_CODIGO, f.getCodigo());
            values.put(FacultadTable.COL_NOMBRE, f.getNombre());
            values.put(FacultadTable.COL_ESTADO, f.getEstado());
            long resultado = db.update(FacultadTable.TABLE_NAME, values, FacultadTable.COL_ID + "=?", new String[]{String.valueOf(f.getId())});
            return resultado != -1;
        }
    }

    public boolean deleteFacultad(int id){
        try(SQLiteDatabase db = this.helper.getWritableDatabase()){
            ContentValues values = new ContentValues();
            values.put(FacultadTable.COL_ESTADO,0);
            long filas = db.update(FacultadTable.TABLE_NAME,values, FacultadTable.COL_ID + " =?", new String[]{String.valueOf(id)});
            return filas>0;
        }
    }

    public FacultadModel getFacultadById(int id) {
        try (SQLiteDatabase db = helper.getReadableDatabase()) {
            Cursor c = db.rawQuery(
                    "SELECT * FROM " + FacultadTable.TABLE_NAME +
                            " WHERE " + FacultadTable.COL_ESTADO + " = 1 AND " +
                            FacultadTable.COL_ID + " = ?",
                    new String[]{ String.valueOf(id) }
            );
            if (!c.moveToFirst()) {
                c.close();
                return null;
            }
            FacultadModel f = new FacultadModel();
            f.setId(c.getInt(c.getColumnIndexOrThrow(FacultadTable.COL_ID)));
            f.setCodigo(c.getString(c.getColumnIndexOrThrow(FacultadTable.COL_CODIGO)));
            f.setNombre(c.getString(c.getColumnIndexOrThrow(FacultadTable.COL_NOMBRE)));
            f.setEstado(c.getInt(c.getColumnIndexOrThrow(FacultadTable.COL_ESTADO)));
            c.close();
            return f;
        }
    }

}
