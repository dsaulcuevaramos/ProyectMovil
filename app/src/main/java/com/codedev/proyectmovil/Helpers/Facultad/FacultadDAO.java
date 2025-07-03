package com.codedev.proyectmovil.Helpers.Facultad;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.codedev.proyectmovil.Helpers.DatabaseHelper;
import com.codedev.proyectmovil.Models.FacultadModel;

import java.util.ArrayList;
import java.util.List;

public class FacultadDAO {
    private DatabaseHelper helper;

    public FacultadDAO(Context context) {
        helper = new DatabaseHelper(context);
    }

    public List<FacultadModel> getFacultad() {
        try (SQLiteDatabase db = this.helper.getReadableDatabase()) {
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

    public boolean addFacultad(FacultadModel facultadModel) {
        try (SQLiteDatabase db = helper.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put(FacultadTable.COL_CODIGO, facultadModel.getCodigo());
            values.put(FacultadTable.COL_NOMBRE, facultadModel.getNombre());
            values.put(FacultadTable.COL_ESTADO, 1);
            long resultado = db.insert(FacultadTable.TABLE_NAME, null, values);
            return resultado != -1;
        }
    }

    public boolean updateFacultad(FacultadModel facultadModel) {
        try (SQLiteDatabase db = helper.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put(FacultadTable.COL_CODIGO, facultadModel.getCodigo());
            values.put(FacultadTable.COL_NOMBRE, facultadModel.getNombre());
            values.put(FacultadTable.COL_ESTADO, facultadModel.getEstado());
            int filas = db.update(FacultadTable.TABLE_NAME, values,
                    FacultadTable.COL_ID + " = ?",
                    new String[]{String.valueOf(facultadModel.getId())});
            return filas > 0;
        }
    }

    public boolean deleteFacultad(int id) {
        try (SQLiteDatabase db = helper.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put(FacultadTable.COL_ESTADO, 0);
            int filas = db.update(FacultadTable.TABLE_NAME, values,
                    FacultadTable.COL_ID + " = ?",
                    new String[]{String.valueOf(id)});
            return filas > 0;
        }
    }

    public List<FacultadModel> getFacultades() {
        List<FacultadModel> listaFacultades = new ArrayList<>();
        try (SQLiteDatabase db = helper.getReadableDatabase()) {
            Cursor cursor = db.rawQuery(
                    "SELECT * FROM " + FacultadTable.TABLE_NAME +
                            " WHERE " + FacultadTable.COL_ESTADO + " = 1", null);

            if (cursor.moveToFirst()) {
                do {
                    FacultadModel facultad = new FacultadModel();
                    facultad.setId(cursor.getInt(cursor.getColumnIndexOrThrow(FacultadTable.COL_ID)));
                    facultad.setCodigo(cursor.getString(cursor.getColumnIndexOrThrow(FacultadTable.COL_CODIGO)));
                    facultad.setNombre(cursor.getString(cursor.getColumnIndexOrThrow(FacultadTable.COL_NOMBRE)));
                    facultad.setEstado(cursor.getInt(cursor.getColumnIndexOrThrow(FacultadTable.COL_ESTADO)));
                    listaFacultades.add(facultad);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return listaFacultades;
    }

    public FacultadModel getFacultadById(int id) {
        try (SQLiteDatabase db = helper.getReadableDatabase()) {
            Cursor cursor = db.query(FacultadTable.TABLE_NAME, null,
                    FacultadTable.COL_ID + " = ? AND " + FacultadTable.COL_ESTADO + " = 1",
                    new String[]{String.valueOf(id)}, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                FacultadModel facultad = new FacultadModel();
                facultad.setId(cursor.getInt(cursor.getColumnIndexOrThrow(FacultadTable.COL_ID)));
                facultad.setCodigo(cursor.getString(cursor.getColumnIndexOrThrow(FacultadTable.COL_CODIGO)));
                facultad.setNombre(cursor.getString(cursor.getColumnIndexOrThrow(FacultadTable.COL_NOMBRE)));
                facultad.setEstado(cursor.getInt(cursor.getColumnIndexOrThrow(FacultadTable.COL_ESTADO)));
                cursor.close();
                return facultad;
            }
            if (cursor != null) cursor.close();
        }
        return null;
    }

}
