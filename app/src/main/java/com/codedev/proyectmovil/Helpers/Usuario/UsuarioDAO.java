package com.codedev.proyectmovil.Helpers.Usuario;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.codedev.proyectmovil.Helpers.DatabaseHelper;
import com.codedev.proyectmovil.Models.UsuarioModel;

import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    private DatabaseHelper helper;

    public UsuarioDAO(Context context) {
        helper = new DatabaseHelper(context);
    }

    public boolean addUsuario(UsuarioModel u){
        try (SQLiteDatabase db = this.helper.getWritableDatabase()){
            ContentValues values = new ContentValues();
            values.put(UsuarioTable.COL_NOMBRE, u.getNombre());
            values.put(UsuarioTable.COL_CORREO, u.getCorreo());
            values.put(UsuarioTable.COL_CONTRASENIA, u.getContrasenia());
            values.put(UsuarioTable.COL_ESTADO, 1);
            long resultado = db.insert(UsuarioTable.TABLE_NAME, null, values);
            return resultado != -1;
        }
    }

    public boolean updateUsuario(UsuarioModel u){
        try (SQLiteDatabase db = this.helper.getWritableDatabase()){
            ContentValues values = new ContentValues();
            values.put(UsuarioTable.COL_NOMBRE, u.getNombre());
            values.put(UsuarioTable.COL_CORREO, u.getCorreo());
            values.put(UsuarioTable.COL_CONTRASENIA, u.getContrasenia());
            values.put(UsuarioTable.COL_ESTADO, u.getEstado());
            long filas = db.update(UsuarioTable.TABLE_NAME, values, UsuarioTable.COL_ID + " = ?", new String[]{String.valueOf(u.getId())});
            return filas > 0;
        }
    }

    public boolean deleteUsuario(int id){
        try (SQLiteDatabase db = this.helper.getWritableDatabase()){
            ContentValues values = new ContentValues();
            values.put(UsuarioTable.COL_ESTADO, 0);
            long filas = db.update(UsuarioTable.TABLE_NAME, values, UsuarioTable.COL_ID + " =?", new String[]{String.valueOf(id)});
            return filas > 0;
        }
    }

    public List<UsuarioModel> getUsuarios(){
        try (SQLiteDatabase db = this.helper.getReadableDatabase()){
            List<UsuarioModel> listaUsuarios = new ArrayList<>();
            Cursor cursor = db.rawQuery("SELECT * FROM " + UsuarioTable.TABLE_NAME + " WHERE " + UsuarioTable.COL_ESTADO + " = 1", null);

            if (cursor.moveToFirst()) {
                do {
                    UsuarioModel u = new UsuarioModel();
                    u.setId(cursor.getInt(cursor.getColumnIndexOrThrow(UsuarioTable.COL_ID)));
                    u.setNombre(cursor.getString(cursor.getColumnIndexOrThrow(UsuarioTable.COL_NOMBRE)));
                    u.setCorreo(cursor.getString(cursor.getColumnIndexOrThrow(UsuarioTable.COL_CORREO)));
                    u.setContrasenia(cursor.getString(cursor.getColumnIndexOrThrow(UsuarioTable.COL_CONTRASENIA)));
                    u.setEstado(cursor.getInt(cursor.getColumnIndexOrThrow(UsuarioTable.COL_ESTADO)));
                    listaUsuarios.add(u);
                } while (cursor.moveToNext());
            }

            cursor.close();
            return listaUsuarios;
        }
    }

    public List<UsuarioModel> getBusquedaUsuarios(String valor){
        try (SQLiteDatabase db = this.helper.getReadableDatabase()){
            List<UsuarioModel> listaUsuarios = new ArrayList<>();
            Cursor cursor = db.rawQuery("SELECT * FROM " + UsuarioTable.TABLE_NAME + " WHERE " + UsuarioTable.COL_ESTADO + " = 1 AND (" +
                    UsuarioTable.COL_NOMBRE + " LIKE ? OR " +
                    UsuarioTable.COL_CORREO + " LIKE ?)",
                    new String[]{"%" + valor + "%", "%" + valor + "%"});

            if (cursor.moveToFirst()) {
                do {
                    UsuarioModel u = new UsuarioModel();
                    u.setId(cursor.getInt(cursor.getColumnIndexOrThrow(UsuarioTable.COL_ID)));
                    u.setNombre(cursor.getString(cursor.getColumnIndexOrThrow(UsuarioTable.COL_NOMBRE)));
                    u.setCorreo(cursor.getString(cursor.getColumnIndexOrThrow(UsuarioTable.COL_CORREO)));
                    u.setContrasenia(cursor.getString(cursor.getColumnIndexOrThrow(UsuarioTable.COL_CONTRASENIA)));
                    u.setEstado(cursor.getInt(cursor.getColumnIndexOrThrow(UsuarioTable.COL_ESTADO)));
                    listaUsuarios.add(u);
                } while (cursor.moveToNext());
            }

            cursor.close();
            return listaUsuarios;
        }
    }

}
