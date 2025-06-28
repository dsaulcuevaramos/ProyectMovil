package com.codedev.proyectmovil.Helpers.Persona;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.codedev.proyectmovil.Helpers.DatabaseHelper;
import com.codedev.proyectmovil.Models.PersonaModel;

import java.util.ArrayList;
import java.util.List;

public class PersonaDAO {
    private DatabaseHelper helper;

    public PersonaDAO(Context context) {
        helper = new DatabaseHelper(context);
    }

    public boolean addPersona(PersonaModel p){
        try (SQLiteDatabase db = this.helper.getWritableDatabase()){
            ContentValues values = new ContentValues();
            values.put(PersonaTable.COL_CODIGO, p.getCodigo());
            values.put(PersonaTable.COL_NOMBRE, p.getNombre());
            values.put(PersonaTable.COL_APELLIDO, p.getApellido());
            values.put(PersonaTable.COL_IDFACULTAD, p.getIdFacultad());
            values.put(PersonaTable.COL_ESTADO, 1);
            long resultado = db.insert(PersonaTable.TABLE_NAME, null, values);
            return resultado != -1;
        }
    }

    public boolean updatePersona(PersonaModel p){
        try (SQLiteDatabase db = this.helper.getWritableDatabase()){
            ContentValues values = new ContentValues();
            values.put(PersonaTable.COL_CODIGO, p.getCodigo());
            values.put(PersonaTable.COL_NOMBRE, p.getNombre());
            values.put(PersonaTable.COL_APELLIDO, p.getApellido());
            values.put(PersonaTable.COL_IDFACULTAD, p.getIdFacultad());
            values.put(PersonaTable.COL_ESTADO, p.getEstado());
            long resultado = db.update(PersonaTable.TABLE_NAME, values, PersonaTable.COL_ID + "=?", new String[]{String.valueOf(p.getId())});
            return resultado != -1;
        }
    }

    public boolean deletePersona(int id){
        try(SQLiteDatabase db = this.helper.getWritableDatabase()){
            ContentValues values = new ContentValues();
            values.put(PersonaTable.COL_ESTADO,0);
            long filas = db.update(PersonaTable.TABLE_NAME,values, PersonaTable.COL_ID + " =?", new String[]{String.valueOf(id)});
            return filas>0;
        }
    }

    public List<PersonaModel> getPersonas(){
        try (SQLiteDatabase db = this.helper.getReadableDatabase()){
            List<PersonaModel> listaPersonas = new ArrayList<>();
            Cursor cursor = db.rawQuery("SELECT * FROM " + PersonaTable.TABLE_NAME + " WHERE " + PersonaTable.COL_ESTADO + " = 1", null);

            if (cursor.moveToFirst()) {
                do {
                    PersonaModel p = new PersonaModel();
                    p.setId(cursor.getInt(cursor.getColumnIndexOrThrow(PersonaTable.COL_ID)));
                    p.setCodigo(cursor.getString(cursor.getColumnIndexOrThrow(PersonaTable.COL_CODIGO)));
                    p.setNombre(cursor.getString(cursor.getColumnIndexOrThrow(PersonaTable.COL_NOMBRE)));
                    p.setApellido(cursor.getString(cursor.getColumnIndexOrThrow(PersonaTable.COL_APELLIDO)));
                    p.setIdFacultad(cursor.getInt(cursor.getColumnIndexOrThrow(PersonaTable.COL_IDFACULTAD)));
                    p.setEstado(cursor.getInt(cursor.getColumnIndexOrThrow(PersonaTable.COL_ESTADO)));
                    listaPersonas.add(p);
                } while (cursor.moveToNext());
            }
            cursor.close();
            return listaPersonas;
        }
    }

    public List<PersonaModel> getBusquedaPersonas(String valor){
        try (SQLiteDatabase db = this.helper.getReadableDatabase()){
            List<PersonaModel> listaPersonas = new ArrayList<>();
            Cursor cursor = db.rawQuery("SELECT * FROM " + PersonaTable.TABLE_NAME + " WHERE " + PersonaTable.COL_ESTADO + " = 1 AND (" +
                            PersonaTable.COL_NOMBRE + " LIKE ? OR " +
                            PersonaTable.COL_APELLIDO + " LIKE ?)",
                    new String[]{"%" + valor + "%", "%" + valor + "%"});

            if (cursor.moveToFirst()) {
                do {
                    PersonaModel p = new PersonaModel();
                    p.setId(cursor.getInt(cursor.getColumnIndexOrThrow(PersonaTable.COL_ID)));
                    p.setCodigo(cursor.getString(cursor.getColumnIndexOrThrow(PersonaTable.COL_CODIGO)));
                    p.setNombre(cursor.getString(cursor.getColumnIndexOrThrow(PersonaTable.COL_NOMBRE)));
                    p.setApellido(cursor.getString(cursor.getColumnIndexOrThrow(PersonaTable.COL_APELLIDO)));
                    p.setIdFacultad(cursor.getInt(cursor.getColumnIndexOrThrow(PersonaTable.COL_IDFACULTAD)));
                    p.setEstado(cursor.getInt(cursor.getColumnIndexOrThrow(PersonaTable.COL_ESTADO)));
                    listaPersonas.add(p);
                } while (cursor.moveToNext());
            }
            cursor.close();
            return listaPersonas;
        }
    }


}
