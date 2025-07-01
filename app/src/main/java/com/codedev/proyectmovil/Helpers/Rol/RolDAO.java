package com.codedev.proyectmovil.Helpers.Rol;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.codedev.proyectmovil.Helpers.DatabaseHelper;
import com.codedev.proyectmovil.Models.RolModel;

import java.util.ArrayList;
import java.util.List;

public class RolDAO {
    private DatabaseHelper helper;

    public RolDAO(Context context) {
        helper = new DatabaseHelper(context);
    }

    public List<RolModel> getRol(){
        try (SQLiteDatabase db = this.helper.getReadableDatabase()){
            List<RolModel> listaRoles = new ArrayList<>();
            Cursor cursor = db.rawQuery("SELECT * FROM " + RolTable.TABLE_NAME + " WHERE " + RolTable.COL_ESTADO + " = 1", null);

            if (cursor.moveToFirst()) {
                do {
                    RolModel r = new RolModel();
                    r.setId(cursor.getInt(cursor.getColumnIndexOrThrow(RolTable.COL_ID)));
                    r.setNombre(cursor.getString(cursor.getColumnIndexOrThrow(RolTable.COL_NOMBRE)));
                    r.setEstado(cursor.getInt(cursor.getColumnIndexOrThrow(RolTable.COL_ESTADO)));
                    listaRoles.add(r);
                } while (cursor.moveToNext());
            }

            cursor.close();
            return listaRoles;
        }
    }
}
