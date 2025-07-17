package com.codedev.proyectmovil.Helpers.DetalleClase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.codedev.proyectmovil.Helpers.Clase.ClaseTable;
import com.codedev.proyectmovil.Helpers.DatabaseHelper;
import com.codedev.proyectmovil.Helpers.Persona.PersonaTable;
import com.codedev.proyectmovil.Helpers.Usuario.UsuarioTable;
import com.codedev.proyectmovil.Models.DetalleModel;
import com.codedev.proyectmovil.Models.Requests.DetalleClaseRequest;

import java.util.ArrayList;
import java.util.List;

public class DetalleClaseDAO {
    private DatabaseHelper helper;

    public DetalleClaseDAO(Context context) {
        helper = new DatabaseHelper(context);
    }

    public DetalleModel addDetallePeriodo(DetalleModel d) {
        try (SQLiteDatabase db = this.helper.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put(DetalleClaseTable.COL_CLASE_ID, d.getIdClase());
            values.put(DetalleClaseTable.COL_PERSONA_ID, d.getIdPersona());
            values.put(DetalleClaseTable.COL_ESTADO_ASISTENCIA, d.getEstadoAsistencia());
            values.put(DetalleClaseTable.COL_ESTADO, 1);
            long resultado = db.insert(DetalleClaseTable.TABLE_NAME, null, values);
            if (resultado != -1) {
                d.setId((int) resultado);
                return d;
            } else {
                return null;
            }
        }
    }

    public boolean deleteDetalleByClaseid(int idClase) {
        try (SQLiteDatabase db = this.helper.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put(DetalleClaseTable.COL_ESTADO, 0);
            int filas = db.update(DetalleClaseTable.TABLE_NAME, values, DetalleClaseTable.COL_CLASE_ID + " = ?", new String[]{String.valueOf(idClase)});
            return filas > 0;
        }
    }

    public boolean deleteDetalleById(int idDetalle) {
        try (SQLiteDatabase db = this.helper.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put(DetalleClaseTable.COL_ESTADO, 0);
            int filas = db.update(DetalleClaseTable.TABLE_NAME, values, DetalleClaseTable.COL_ID + " = ?", new String[]{String.valueOf(idDetalle)});
            return filas > 0;
        }
    }

    public List<DetalleClaseRequest> getDetalleClasePorRol(int idClase, int rolId) {
        try (SQLiteDatabase db = this.helper.getReadableDatabase()) {
            List<DetalleClaseRequest> lista = new ArrayList<>();

            String query = "SELECT dc." + DetalleClaseTable.COL_ID + " AS idDetalle, " +
                    "p." + PersonaTable.COL_ID + " AS idPersona, " +
                    "p." + PersonaTable.COL_CODIGO + " AS codigo, " +
                    "p." + PersonaTable.COL_NOMBRE + " || ' ' || p." + PersonaTable.COL_APELLIDO + " AS nombrePersona " +
                    "FROM " + UsuarioTable.TABLE_NAME + " u " +
                    "JOIN " + PersonaTable.TABLE_NAME + " p ON u." + UsuarioTable.COL_PERSONA_ID + " = p." + PersonaTable.COL_ID + " " +
                    "JOIN " + DetalleClaseTable.TABLE_NAME + " dc ON dc." + DetalleClaseTable.COL_PERSONA_ID + " = p." + PersonaTable.COL_ID + " " +
                    "WHERE u." + UsuarioTable.COL_ROL_ID + " = ? AND dc." + DetalleClaseTable.COL_CLASE_ID + " = ? AND dc." + DetalleClaseTable.COL_ESTADO + " = 1 AND" +
                    " u." + UsuarioTable.COL_ESTADO + " = 1 AND p." + PersonaTable.COL_ESTADO + " = 1";

            Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(rolId), String.valueOf(idClase)});

            if (cursor.moveToFirst()) {
                do {
                    DetalleClaseRequest d = new DetalleClaseRequest();
                    d.setIdDetalle(cursor.getInt(cursor.getColumnIndexOrThrow("idDetalle")));
                    d.setIdPersona(cursor.getInt(cursor.getColumnIndexOrThrow("idPersona")));
                    d.setCodigo(cursor.getString(cursor.getColumnIndexOrThrow("codigo")));
                    d.setNombrePersona(cursor.getString(cursor.getColumnIndexOrThrow("nombrePersona")));
                    d.setIdClase(idClase);
                    lista.add(d);
                } while (cursor.moveToNext());
            }

            cursor.close();
            db.close();
            return lista;
        }
    }

    public List<DetalleClaseRequest> buscarPersonasPorRol(int idRol, int idFacultad, String texto) {
        try (SQLiteDatabase db = this.helper.getReadableDatabase()) {
            List<DetalleClaseRequest> lista = new ArrayList<>();

            String query = "SELECT p." + PersonaTable.COL_ID + ", " +
                    "p." + PersonaTable.COL_NOMBRE + " || ' ' || p." + PersonaTable.COL_APELLIDO + " AS nombre, " +
                    "p." + PersonaTable.COL_CODIGO + " " +
                    "FROM " + UsuarioTable.TABLE_NAME + " u " +
                    "JOIN " + PersonaTable.TABLE_NAME + " p ON p." + PersonaTable.COL_ID + " = u." + UsuarioTable.COL_PERSONA_ID + " " +
                    "WHERE u." + UsuarioTable.COL_ROL_ID + " = ? AND p." + PersonaTable.COL_IDFACULTAD + " = ? " +
                    "AND (p." + PersonaTable.COL_NOMBRE + " LIKE ? OR p." + PersonaTable.COL_APELLIDO + " LIKE ? OR p." + PersonaTable.COL_CODIGO + " LIKE ?)";

            String like = "%" + texto + "%";
            Cursor c = db.rawQuery(query, new String[]{
                    String.valueOf(idRol), String.valueOf(idFacultad), like, like, like
            });

            while (c.moveToNext()) {
                DetalleClaseRequest d = new DetalleClaseRequest();
                d.setIdPersona(c.getInt(0));
                d.setNombrePersona(c.getString(1));
                d.setCodigo(c.getString(2));
                lista.add(d);
            }

            c.close();
            db.close();
            return lista;
        }
    }

    public boolean existeEnClase(int idClase, int idPersona) {
        try (SQLiteDatabase db = this.helper.getReadableDatabase()) {
            String sql = "SELECT COUNT(*) FROM " + DetalleClaseTable.TABLE_NAME +
                    " WHERE " + DetalleClaseTable.COL_CLASE_ID + " = ? AND " + DetalleClaseTable.COL_PERSONA_ID + " = ? AND " + DetalleClaseTable.COL_ESTADO + " = 1";
            Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(idClase), String.valueOf(idPersona)});
            boolean existe = false;
            if (cursor.moveToFirst()) {
                existe = cursor.getInt(0) > 0;
            }
            cursor.close();
            return existe;
        }
    }

    public boolean existeEnOtroGrupo(int idClase, int idPersona) {
        try (SQLiteDatabase db = this.helper.getReadableDatabase()) {

            String claseSQL = "SELECT " + ClaseTable.COL_CURSO_ID + ", " + ClaseTable.COL_PERIODO_ID +
                    " FROM " + ClaseTable.TABLE_NAME +
                    " WHERE " + ClaseTable.COL_ID + " = ? AND " + ClaseTable.COL_ESTADO + " = 1";

            Cursor claseCursor = db.rawQuery(claseSQL, new String[]{String.valueOf(idClase)});

            if (!claseCursor.moveToFirst()) {
                claseCursor.close();
                return false;
            }

            int idCurso = claseCursor.getInt(0);
            int idPeriodo = claseCursor.getInt(1);
            claseCursor.close();

            String sql = "SELECT COUNT(*) FROM " + DetalleClaseTable.TABLE_NAME + " d " +
                    "JOIN " + ClaseTable.TABLE_NAME + " c ON d." + DetalleClaseTable.COL_CLASE_ID + " = c." + ClaseTable.COL_ID + " " +
                    "WHERE d." + DetalleClaseTable.COL_PERSONA_ID + " = ? AND c." + ClaseTable.COL_CURSO_ID + " = ? " +
                    "AND c." + ClaseTable.COL_PERIODO_ID + " = ? AND c." + ClaseTable.COL_ID + " != ? AND d." + DetalleClaseTable.COL_ESTADO + " = 1";

            Cursor cursor = db.rawQuery(sql, new String[]{
                    String.valueOf(idPersona),
                    String.valueOf(idCurso),
                    String.valueOf(idPeriodo),
                    String.valueOf(idClase)
            });

            boolean existe = false;
            if (cursor.moveToFirst()) {
                existe = cursor.getInt(0) > 0;
            }

            cursor.close();
            return existe;
        }
    }

    public boolean existeProfesorActivo(int idClase) {
        try (SQLiteDatabase db = this.helper.getReadableDatabase()) {
            String sql = "SELECT COUNT(*) FROM " + DetalleClaseTable.TABLE_NAME +
                    " dc JOIN " + UsuarioTable.TABLE_NAME + " u ON u." + UsuarioTable.COL_PERSONA_ID + " = dc." + DetalleClaseTable.COL_PERSONA_ID +
                    " WHERE dc." + DetalleClaseTable.COL_CLASE_ID + " = ? AND dc." + DetalleClaseTable.COL_ESTADO + " = 1 AND u." + UsuarioTable.COL_ROL_ID + " = 3";

            Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(idClase)});
            boolean existe = false;
            if (cursor.moveToFirst()) {
                existe = cursor.getInt(0) > 0;
            }
            cursor.close();
            return existe;
        }
    }
}
