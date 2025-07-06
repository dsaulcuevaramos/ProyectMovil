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

    public List<FacultadModel> getFacultad(){
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
}
