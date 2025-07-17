package com.codedev.proyectmovil.Helpers.Periodo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.codedev.proyectmovil.Helpers.DatabaseHelper;
import com.codedev.proyectmovil.Models.PeriodoModel;

import java.util.ArrayList;
import java.util.List;

public class PeriodoDAO {

    private DatabaseHelper helper;

    public PeriodoDAO(Context context) {
        helper = new DatabaseHelper(context);
    }

    public PeriodoModel addPeriodo(PeriodoModel p) {
        try (SQLiteDatabase db = this.helper.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put(PeriodoTable.COL_CODIGO, p.getCodigo());
            values.put(PeriodoTable.COL_NOMBRE, p.getNombre());
            values.put(PeriodoTable.COL_ACTIVO, p.getActivo());
            values.put(PeriodoTable.COL_ESTADO, 1);
            long resultado = db.insert(PeriodoTable.TABLE_NAME, null, values);
            if (resultado != -1) {
                p.setId((int) resultado);
                return p;
            } else {
                return null;
            }
        }
    }

    public boolean updatePeriodo(PeriodoModel p) {
        try (SQLiteDatabase db = this.helper.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put(PeriodoTable.COL_CODIGO, p.getCodigo());
            values.put(PeriodoTable.COL_NOMBRE, p.getNombre());
            values.put(PeriodoTable.COL_ACTIVO, p.getActivo());
            values.put(PeriodoTable.COL_ESTADO, p.getEstado());
            long filas = db.update(PeriodoTable.TABLE_NAME, values, PeriodoTable.COL_ID + " = ?", new String[]{String.valueOf(p.getId())});
            return filas > 0;
        }
    }

    public boolean deletePeriodo(int id) {
        try (SQLiteDatabase db = this.helper.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put(PeriodoTable.COL_ESTADO, 0);
            long filas = db.update(PeriodoTable.TABLE_NAME, values, PeriodoTable.COL_ID + " =?", new String[]{String.valueOf(id)});
            return filas > 0;
        }
    }

    public PeriodoModel getPeriodoActivo() {
        try (SQLiteDatabase db = this.helper.getReadableDatabase();
             Cursor cursor = db.rawQuery("SELECT * FROM " + PeriodoTable.TABLE_NAME +
                     " WHERE " + PeriodoTable.COL_ESTADO + " = 1 AND " + PeriodoTable.COL_ACTIVO + " = 1 LIMIT 1", null)) {

            if (cursor.moveToFirst()) {
                PeriodoModel periodo = new PeriodoModel();
                periodo.setId(cursor.getInt(cursor.getColumnIndexOrThrow(PeriodoTable.COL_ID)));
                periodo.setCodigo(cursor.getString(cursor.getColumnIndexOrThrow(PeriodoTable.COL_CODIGO)));
                periodo.setNombre(cursor.getString(cursor.getColumnIndexOrThrow(PeriodoTable.COL_NOMBRE)));
                periodo.setActivo(cursor.getInt(cursor.getColumnIndexOrThrow(PeriodoTable.COL_ACTIVO)));
                periodo.setEstado(cursor.getInt(cursor.getColumnIndexOrThrow(PeriodoTable.COL_ESTADO)));
                return periodo;
            } else {
                return null;
            }
        }
    }

    public PeriodoModel getPeriodoById(int id) {
        try (SQLiteDatabase db = this.helper.getReadableDatabase()) {
            PeriodoModel p = null;
            Cursor cursor = db.rawQuery("SELECT * FROM " + PeriodoTable.TABLE_NAME + " WHERE " + PeriodoTable.COL_ESTADO + " =1 AND "+ PeriodoTable.COL_ID + " =?",
                    new String[]{String.valueOf(id)});
            if (cursor.moveToFirst()) {
                p = new PeriodoModel();
                p.setId(cursor.getInt(cursor.getColumnIndexOrThrow(PeriodoTable.COL_ID)));
                p.setCodigo(cursor.getString(cursor.getColumnIndexOrThrow(PeriodoTable.COL_CODIGO)));
                p.setNombre(cursor.getString(cursor.getColumnIndexOrThrow(PeriodoTable.COL_NOMBRE)));
                p.setActivo(cursor.getInt(cursor.getColumnIndexOrThrow(PeriodoTable.COL_ACTIVO)));
                p.setEstado(cursor.getInt(cursor.getColumnIndexOrThrow(PeriodoTable.COL_ESTADO)));
            }

            cursor.close();
            return p;
        }
    }

    public List<PeriodoModel> getAllPeriodos() {
        try (SQLiteDatabase db = this.helper.getReadableDatabase()) {
            List<PeriodoModel> lista = new ArrayList<>();

            Cursor cursor = db.rawQuery("SELECT * FROM " + PeriodoTable.TABLE_NAME + " WHERE " + PeriodoTable.COL_ESTADO + " =1", null);
            if (cursor.moveToFirst()) {
                do {
                    PeriodoModel p = new PeriodoModel();
                    p.setId(cursor.getInt(cursor.getColumnIndexOrThrow(PeriodoTable.COL_ID)));
                    p.setCodigo(cursor.getString(cursor.getColumnIndexOrThrow(PeriodoTable.COL_CODIGO)));
                    p.setNombre(cursor.getString(cursor.getColumnIndexOrThrow(PeriodoTable.COL_NOMBRE)));
                    p.setActivo(cursor.getInt(cursor.getColumnIndexOrThrow(PeriodoTable.COL_ACTIVO)));
                    p.setEstado(cursor.getInt(cursor.getColumnIndexOrThrow(PeriodoTable.COL_ESTADO)));
                    lista.add(p);
                } while (cursor.moveToNext());
            }

            cursor.close();
            return lista;
        }
    }
}
