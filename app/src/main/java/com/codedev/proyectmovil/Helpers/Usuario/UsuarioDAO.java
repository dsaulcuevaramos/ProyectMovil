package com.codedev.proyectmovil.Helpers.Usuario;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.codedev.proyectmovil.Helpers.DatabaseHelper;
import com.codedev.proyectmovil.Helpers.Facultad.FacultadDAO;
import com.codedev.proyectmovil.Helpers.Facultad.FacultadTable;
import com.codedev.proyectmovil.Helpers.Persona.PersonaTable;
import com.codedev.proyectmovil.Helpers.Rol.RolDAO;
import com.codedev.proyectmovil.Helpers.Rol.RolTable;
import com.codedev.proyectmovil.Models.FacultadModel;
import com.codedev.proyectmovil.Models.Requests.UsuarioRequest;
import com.codedev.proyectmovil.Models.RolModel;
import com.codedev.proyectmovil.Models.UsuarioModel;

import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    private DatabaseHelper helper;

    public UsuarioDAO(Context context) {
        helper = new DatabaseHelper(context);
    }

    public UsuarioModel addUsuario(UsuarioModel u) {
        try (SQLiteDatabase db = this.helper.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put(UsuarioTable.COL_USUARIO, u.getUsuario());
            values.put(UsuarioTable.COL_CONTRASENIA, u.getContrasenia());
            values.put(UsuarioTable.COL_PERSONA_ID, u.getPersona_id());
            values.put(UsuarioTable.COL_ROL_ID, u.getRol_id());
            values.put(UsuarioTable.COL_ESTADO, 1);
            long resultado = db.insert(UsuarioTable.TABLE_NAME, null, values);
            if (resultado != -1) {
                u.setId((int) resultado);
                return u;
            } else {
                return null;
            }
        }
    }

    public boolean updateUsuario(UsuarioModel u) {
        try (SQLiteDatabase db = this.helper.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put(UsuarioTable.COL_USUARIO, u.getUsuario());
            values.put(UsuarioTable.COL_CONTRASENIA, u.getContrasenia());
            values.put(UsuarioTable.COL_PERSONA_ID, u.getPersona_id());
            values.put(UsuarioTable.COL_ROL_ID, u.getRol_id());
            values.put(UsuarioTable.COL_ESTADO, u.getEstado());
            long filas = db.update(UsuarioTable.TABLE_NAME, values, UsuarioTable.COL_ID + " = ?", new String[]{String.valueOf(u.getId())});
            return filas > 0;
        }
    }

    public boolean deleteUsuario(int id) {
        try (SQLiteDatabase db = this.helper.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put(UsuarioTable.COL_ESTADO, 0);
            long filas = db.update(UsuarioTable.TABLE_NAME, values, UsuarioTable.COL_ID + " =?", new String[]{String.valueOf(id)});
            return filas > 0;
        }
    }

    public boolean recoverUsuario(int id) {
        try (SQLiteDatabase db = this.helper.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put(UsuarioTable.COL_ESTADO, 1);
            long filas = db.update(UsuarioTable.TABLE_NAME, values, UsuarioTable.COL_ID + " =?", new String[]{String.valueOf(id)});
            return filas > 0;
        }
    }

    public UsuarioModel getUsuarioById(int id) {
        try (SQLiteDatabase db = this.helper.getReadableDatabase()) {
            UsuarioModel u = null;
            Cursor cursor = db.rawQuery("SELECT * FROM " + UsuarioTable.TABLE_NAME + " WHERE " + UsuarioTable.COL_ESTADO + " = 1 AND " +
                    UsuarioTable.COL_ID + " = ?", new String[]{String.valueOf(id)});
            if (cursor.moveToFirst()) {
                u = new UsuarioModel();
                u.setId(cursor.getInt(cursor.getColumnIndexOrThrow(UsuarioTable.COL_ID)));
                u.setUsuario(cursor.getString(cursor.getColumnIndexOrThrow(UsuarioTable.COL_USUARIO)));
                u.setContrasenia(cursor.getString(cursor.getColumnIndexOrThrow(UsuarioTable.COL_CONTRASENIA)));
                u.setPersona_id(cursor.getInt(cursor.getColumnIndexOrThrow(UsuarioTable.COL_PERSONA_ID)));
                u.setRol_id(cursor.getInt(cursor.getColumnIndexOrThrow(UsuarioTable.COL_ROL_ID)));
                u.setEstado(cursor.getInt(cursor.getColumnIndexOrThrow(UsuarioTable.COL_ESTADO)));
            }
            cursor.close();
            return u;
        }
    }

    public UsuarioRequest getUsuarioByAuthentication(String usuario, String password) {
        try (SQLiteDatabase db = this.helper.getReadableDatabase()) {
            UsuarioRequest u = null;

            Cursor cursor = db.rawQuery("SELECT u.*, p." + PersonaTable.COL_IDFACULTAD + " AS idFacultad FROM " + UsuarioTable.TABLE_NAME + " u " +
                    "JOIN " + PersonaTable.TABLE_NAME + " p ON u.persona_id = p.id " + " WHERE " + UsuarioTable.COL_USUARIO + " = ? AND " +
                    UsuarioTable.COL_CONTRASENIA + " = ? AND u." + UsuarioTable.COL_ESTADO + " = 1", new String[]{usuario, password});
            if (cursor.moveToFirst()) {
                u = new UsuarioRequest();
                u.setIdUsuario(cursor.getInt(cursor.getColumnIndexOrThrow(UsuarioTable.COL_ID)));
                u.setUsuario(cursor.getString(cursor.getColumnIndexOrThrow(UsuarioTable.COL_USUARIO)));
                u.setIdFacultad(cursor.getInt(cursor.getColumnIndexOrThrow("idFacultad")));
                u.setIdRol(cursor.getInt(cursor.getColumnIndexOrThrow(UsuarioTable.COL_ROL_ID)));
                u.setEstado(cursor.getInt(cursor.getColumnIndexOrThrow(UsuarioTable.COL_ESTADO)));
            }
            cursor.close();
            return u;
        }
    }

    public UsuarioRequest getUsuarioCompletoById(int id) {
        try (SQLiteDatabase db = this.helper.getReadableDatabase()) {
            List<UsuarioRequest> listaUsuarios = new ArrayList<>();
            Cursor cursor = db.rawQuery("SELECT " +
                    "u." + UsuarioTable.COL_ID + " AS usuario_id, u." + UsuarioTable.COL_USUARIO + ", " +
                    "p." + PersonaTable.COL_NOMBRE + " AS persona_nombre, p." + PersonaTable.COL_APELLIDO + " AS persona_apellido, p." + PersonaTable.COL_CODIGO + " AS persona_codigo, " +
                    "r." + RolTable.COL_NOMBRE + " AS rol_nombre, " +
                    "f." + FacultadTable.COL_NOMBRE + " AS facultad_nombre " +
                    "FROM " + UsuarioTable.TABLE_NAME + " u " +
                    "JOIN " + PersonaTable.TABLE_NAME + " p ON u.persona_id = p.id " +
                    "JOIN " + RolTable.TABLE_NAME + " r ON u." + UsuarioTable.COL_ROL_ID + " = r." + RolTable.COL_ID + " " +
                    "JOIN " + FacultadTable.TABLE_NAME + " f ON p." + PersonaTable.COL_IDFACULTAD + " = f." + FacultadTable.COL_ID + " " +
                    "WHERE u.estado = 1 AND u." + UsuarioTable.COL_ID + " = ?", new String[]{String.valueOf(id)});
            UsuarioRequest uc = null;

            if (cursor.moveToFirst()) {
                uc = new UsuarioRequest();
                uc.setIdUsuario(cursor.getInt(cursor.getColumnIndexOrThrow("usuario_id")));
                uc.setUsuario(cursor.getString(cursor.getColumnIndexOrThrow(UsuarioTable.COL_USUARIO)));
                uc.setNombrePersona(cursor.getString(cursor.getColumnIndexOrThrow("persona_nombre")));
                uc.setApellidoPersona(cursor.getString(cursor.getColumnIndexOrThrow("persona_apellido")));
                uc.setCodigoPersona(cursor.getString(cursor.getColumnIndexOrThrow("persona_codigo")));
                uc.setRolNombre(cursor.getString(cursor.getColumnIndexOrThrow("rol_nombre")));
                uc.setFacultadNombre(cursor.getString(cursor.getColumnIndexOrThrow("facultad_nombre")));
                listaUsuarios.add(uc);
            }

            cursor.close();
            return uc;
        }
    }

    public List<UsuarioRequest> getAllUsuarios() {
        try (SQLiteDatabase db = this.helper.getReadableDatabase()) {
            List<UsuarioRequest> listaUsuarios = new ArrayList<>();
            Cursor cursor = db.rawQuery("SELECT " +
                    "u." + UsuarioTable.COL_ID + " AS usuario_id, u." + UsuarioTable.COL_USUARIO + ", " +
                    "p." + PersonaTable.COL_NOMBRE + " AS persona_nombre, p." + PersonaTable.COL_APELLIDO + " AS persona_apellido, p." + PersonaTable.COL_CODIGO + " AS persona_codigo, " +
                    "r." + RolTable.COL_NOMBRE + " AS rol_nombre, " +
                    "f." + FacultadTable.COL_NOMBRE + " AS facultad_nombre " +
                    "FROM " + UsuarioTable.TABLE_NAME + " u " +
                    "JOIN " + PersonaTable.TABLE_NAME + " p ON u.persona_id = p.id " +
                    "JOIN " + RolTable.TABLE_NAME + " r ON u." + UsuarioTable.COL_ROL_ID + " = r." + RolTable.COL_ID + " " +
                    "JOIN " + FacultadTable.TABLE_NAME + " f ON p." + PersonaTable.COL_IDFACULTAD + " = f." + FacultadTable.COL_ID + " " +
                    "WHERE u.estado = 1", null);

            if (cursor.moveToFirst()) {
                do {
                    UsuarioRequest uc = new UsuarioRequest();
                    uc.setIdUsuario(cursor.getInt(cursor.getColumnIndexOrThrow("usuario_id")));
                    uc.setUsuario(cursor.getString(cursor.getColumnIndexOrThrow(UsuarioTable.COL_USUARIO)));
                    uc.setNombrePersona(cursor.getString(cursor.getColumnIndexOrThrow("persona_nombre")));
                    uc.setApellidoPersona(cursor.getString(cursor.getColumnIndexOrThrow("persona_apellido")));
                    uc.setCodigoPersona(cursor.getString(cursor.getColumnIndexOrThrow("persona_codigo")));
                    uc.setRolNombre(cursor.getString(cursor.getColumnIndexOrThrow("rol_nombre")));
                    uc.setFacultadNombre(cursor.getString(cursor.getColumnIndexOrThrow("facultad_nombre")));
                    listaUsuarios.add(uc);
                } while (cursor.moveToNext());
            }

            cursor.close();
            return listaUsuarios;
        }
    }

    public List<UsuarioRequest> getBusquedaUsuarios(String valor) {
        String filtro = "%" + valor + "%";

        String sql =
                "SELECT " +
                        "  u." + UsuarioTable.COL_ID + "  AS usuario_id, " +
                        "  u." + UsuarioTable.COL_USUARIO + ", " +
                        "  p." + PersonaTable.COL_NOMBRE + "  AS persona_nombre, " +
                        "  p." + PersonaTable.COL_APELLIDO + "  AS persona_apellido, " +
                        "  p." + PersonaTable.COL_CODIGO + "  AS persona_codigo, " +
                        "  r." + RolTable.COL_NOMBRE + "  AS rol_nombre, " +
                        "  f." + FacultadTable.COL_NOMBRE + "  AS facultad_nombre " +
                        "FROM " + UsuarioTable.TABLE_NAME + " u " +
                        "JOIN " + PersonaTable.TABLE_NAME + " p ON u.persona_id = p.id " +
                        "JOIN " + RolTable.TABLE_NAME + " r ON u." + UsuarioTable.COL_ROL_ID + " = r." + RolTable.COL_ID + " " +
                        "JOIN " + FacultadTable.TABLE_NAME + " f ON p." + PersonaTable.COL_IDFACULTAD + " = f." + FacultadTable.COL_ID + " " +
                        "WHERE u." + UsuarioTable.COL_ESTADO + " = 1 AND (" +
                        "      u." + UsuarioTable.COL_USUARIO + " LIKE ? OR " +
                        "      p." + PersonaTable.COL_NOMBRE + " LIKE ? OR " +
                        "      p." + PersonaTable.COL_APELLIDO + " LIKE ? OR " +
                        "      p." + PersonaTable.COL_CODIGO + " LIKE ? OR " +
                        "      r." + RolTable.COL_NOMBRE + " LIKE ? OR " +
                        "      f." + FacultadTable.COL_NOMBRE + " LIKE ?)";

        try (SQLiteDatabase db = helper.getReadableDatabase();
             Cursor cursor = db.rawQuery(sql,
                     new String[]{filtro, filtro, filtro, filtro, filtro, filtro})) {

            List<UsuarioRequest> lista = new ArrayList<>();

            while (cursor.moveToNext()) {
                UsuarioRequest uc = new UsuarioRequest();
                uc.setIdUsuario(cursor.getInt(cursor.getColumnIndexOrThrow("usuario_id")));
                uc.setUsuario(cursor.getString(cursor.getColumnIndexOrThrow(UsuarioTable.COL_USUARIO)));
                uc.setNombrePersona(cursor.getString(cursor.getColumnIndexOrThrow("persona_nombre")));
                uc.setApellidoPersona(cursor.getString(cursor.getColumnIndexOrThrow("persona_apellido")));
                uc.setCodigoPersona(cursor.getString(cursor.getColumnIndexOrThrow("persona_codigo")));
                uc.setRolNombre(cursor.getString(cursor.getColumnIndexOrThrow("rol_nombre")));
                uc.setFacultadNombre(cursor.getString(cursor.getColumnIndexOrThrow("facultad_nombre")));
                lista.add(uc);
            }
            return lista;
        }
    }

}
