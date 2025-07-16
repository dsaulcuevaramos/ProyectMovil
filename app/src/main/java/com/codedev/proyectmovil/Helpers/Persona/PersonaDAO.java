package com.codedev.proyectmovil.Helpers.Persona;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.codedev.proyectmovil.Helpers.DatabaseHelper;
import com.codedev.proyectmovil.Helpers.Facultad.FacultadTable;
import com.codedev.proyectmovil.Helpers.Rol.RolTable;
import com.codedev.proyectmovil.Helpers.Usuario.UsuarioTable;
import com.codedev.proyectmovil.Models.PersonaModel;
import com.codedev.proyectmovil.Models.Requests.UsuarioRequest;

import java.util.ArrayList;
import java.util.List;

public class PersonaDAO {
    private DatabaseHelper helper;

    public PersonaDAO(Context context) {
        helper = new DatabaseHelper(context);
    }

    public PersonaModel addPersona(PersonaModel p){
        try (SQLiteDatabase db = this.helper.getWritableDatabase()){
            ContentValues values = new ContentValues();
            values.put(PersonaTable.COL_CODIGO, p.getCodigo());
            values.put(PersonaTable.COL_NOMBRE, p.getNombre());
            values.put(PersonaTable.COL_APELLIDO, p.getApellido());
            values.put(PersonaTable.COL_IDFACULTAD, p.getIdFacultad());
            values.put(PersonaTable.COL_ESTADO, 1);
            long resultado = db.insert(PersonaTable.TABLE_NAME, null, values);
            if (resultado != -1) {
                p.setId((int) resultado);
                return p;
            } else {
                return null;
            }
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

    public PersonaModel getPersonaById(int id){
        try(SQLiteDatabase db = this.helper.getReadableDatabase()){
            Cursor cursor = db.rawQuery("SELECT * FROM " + PersonaTable.TABLE_NAME + " WHERE " + PersonaTable.COL_ESTADO + " = 1 AND " +
                    PersonaTable.COL_ID + " = ?",  new String[]{String.valueOf(id)});

            if (!cursor.moveToFirst()) {
                cursor.close();
                return null;
            }
            PersonaModel p = new PersonaModel();
            p.setId(cursor.getInt(cursor.getColumnIndexOrThrow(PersonaTable.COL_ID)));
            p.setCodigo(cursor.getString(cursor.getColumnIndexOrThrow(PersonaTable.COL_CODIGO)));
            p.setNombre(cursor.getString(cursor.getColumnIndexOrThrow(PersonaTable.COL_NOMBRE)));
            p.setApellido(cursor.getString(cursor.getColumnIndexOrThrow(PersonaTable.COL_APELLIDO)));
            p.setIdFacultad(cursor.getInt(cursor.getColumnIndexOrThrow(PersonaTable.COL_IDFACULTAD)));
            p.setEstado(cursor.getInt(cursor.getColumnIndexOrThrow(PersonaTable.COL_ESTADO)));
            cursor.close();
            return p;
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

    public List<PersonaModel> getAllDoccenteByFacultad(int idFacultad){
        try (SQLiteDatabase db = this.helper.getReadableDatabase()){
            List<PersonaModel> listaPersonasDocentes = new ArrayList<>();
            Cursor cursor = db.rawQuery("SELECT " +
                    "u." + UsuarioTable.COL_ID + " AS usuario_id, u." + UsuarioTable.COL_USUARIO + ", " +
                    "p." + PersonaTable.COL_NOMBRE + " AS persona_nombre, p." + PersonaTable.COL_APELLIDO + " AS persona_apellido, p." + PersonaTable.COL_CODIGO + " AS persona_codigo, " +
                    "r." + RolTable.COL_NOMBRE + " AS rol_nombre " +
                    "FROM " + UsuarioTable.TABLE_NAME + " u " +
                    "JOIN " + PersonaTable.TABLE_NAME + " p ON u.persona_id = p.id " +
                    "JOIN " + RolTable.TABLE_NAME + " r ON u." + UsuarioTable.COL_ROL_ID + " = r." + RolTable.COL_ID + " " +
                    "WHERE u." + UsuarioTable.COL_ESTADO + " = 1 AND r."+RolTable.COL_ID + " = 3 " +
                    "AND p."+PersonaTable.COL_IDFACULTAD + "= ?", new String[]{String.valueOf(idFacultad)});

            if (cursor.moveToFirst()) {
                do {
                    PersonaModel p = new PersonaModel();
                    p.setCodigo(cursor.getString(cursor.getColumnIndexOrThrow("persona_codigo")));
                    p.setNombre(cursor.getString(cursor.getColumnIndexOrThrow("persona_nombre")));
                    p.setApellido(cursor.getString(cursor.getColumnIndexOrThrow("persona_apellido")));
                    listaPersonasDocentes.add(p);
                } while (cursor.moveToNext());
            }
            cursor.close();
            return listaPersonasDocentes;
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
