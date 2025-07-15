package com.codedev.proyectmovil.Helpers.Clase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.codedev.proyectmovil.Helpers.Cursos.CursosTable;
import com.codedev.proyectmovil.Helpers.DatabaseHelper;
import com.codedev.proyectmovil.Models.ClaseModel;
import com.codedev.proyectmovil.Models.Requests.ClaseRequest;

import java.util.ArrayList;
import java.util.List;

public class ClaseDAO {
    private DatabaseHelper helper;

    public ClaseDAO(Context context) {
        this.helper = new DatabaseHelper(context);
    }

    public ClaseModel addClase(ClaseModel c) {
        try (SQLiteDatabase db = this.helper.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put(ClaseTable.COL_CURSO_ID, c.getIdCurso());
            values.put(ClaseTable.COL_GRUPO, c.getGrupo());
            values.put(ClaseTable.COL_PERIODO_ID, c.getIdPeriodo());
            values.put(ClaseTable.COL_ESTADO, 1);
            long resultado = db.insert(ClaseTable.TABLE_NAME, null, values);
            if (resultado != -1) {
                c.setId((int) resultado);
                return c;
            } else {
                return null;
            }
        }
    }

    public boolean updateClase(ClaseModel c) {
        try (SQLiteDatabase db = this.helper.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put(ClaseTable.COL_CURSO_ID, c.getIdCurso());
            values.put(ClaseTable.COL_GRUPO, c.getGrupo());
            values.put(ClaseTable.COL_PERIODO_ID, c.getIdPeriodo());
            values.put(ClaseTable.COL_ESTADO, c.getEstado());
            long filas = db.update(ClaseTable.TABLE_NAME, values, ClaseTable.COL_ID + " = ?", new String[]{String.valueOf(c.getId())});
            return filas > 0;
        }
    }

    public ClaseModel getClaseById(int idClase) {
        SQLiteDatabase db = helper.getReadableDatabase();
        ClaseModel clase = null;

        String sql = "SELECT * FROM " + ClaseTable.TABLE_NAME + " WHERE " + ClaseTable.COL_ID + " = ? LIMIT 1";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(idClase)});

        if (cursor.moveToFirst()) {
            clase = new ClaseModel();
            clase.setId(cursor.getInt(cursor.getColumnIndexOrThrow(ClaseTable.COL_ID)));
            clase.setGrupo(cursor.getString(cursor.getColumnIndexOrThrow(ClaseTable.COL_GRUPO)));
            clase.setIdCurso(cursor.getInt(cursor.getColumnIndexOrThrow(ClaseTable.COL_CURSO_ID)));
            clase.setIdPeriodo(cursor.getInt(cursor.getColumnIndexOrThrow(ClaseTable.COL_PERIODO_ID)));
            clase.setEstado(cursor.getInt(cursor.getColumnIndexOrThrow(ClaseTable.COL_ESTADO)));
        }

        cursor.close();
        return clase;
    }

    public ClaseModel getClaseByDetalleId(int idDetalle) {
        try (SQLiteDatabase db = helper.getReadableDatabase()) {
            ClaseModel clase = null;

            String sql = "SELECT c." + ClaseTable.COL_ID + ", c." + ClaseTable.COL_GRUPO + ", " +
                    "c." + ClaseTable.COL_CURSO_ID + ", c." + ClaseTable.COL_PERIODO_ID + ", c." + ClaseTable.COL_ESTADO +
                    " FROM " + ClaseTable.TABLE_NAME + " c " +
                    " JOIN DetalleClase dc ON dc.clase_id = c." + ClaseTable.COL_ID +
                    " WHERE dc.id = ? AND c.estado = 1 LIMIT 1";

            Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(idDetalle)});

            if (cursor.moveToFirst()) {
                clase = new ClaseModel();
                clase.setId(cursor.getInt(0));
                clase.setGrupo(cursor.getString(1));
                clase.setIdCurso(cursor.getInt(2));
                clase.setIdPeriodo(cursor.getInt(3));
                clase.setEstado(cursor.getInt(4));
            }

            cursor.close();
            db.close();
            return clase;
        }
    }


    public boolean existeClaseEnPeriodo(int idPeriodo, int idCurso, String grupo, int idClaseActual) {
        SQLiteDatabase db = helper.getReadableDatabase();

        String sql = "SELECT COUNT(*) FROM " + ClaseTable.TABLE_NAME + " WHERE " + ClaseTable.COL_PERIODO_ID +
                " = ? AND " + ClaseTable.COL_CURSO_ID + " = ? AND " + ClaseTable.COL_GRUPO + " = ? AND " + ClaseTable.COL_ESTADO +
                " = 1 AND " + ClaseTable.COL_ID + " != ?";
        Cursor cursor = db.rawQuery(sql, new String[]{
                String.valueOf(idPeriodo),
                String.valueOf(idCurso),
                grupo,
                String.valueOf(idClaseActual)
        });

        boolean existe = false;
        if (cursor.moveToFirst()) {
            existe = cursor.getInt(0) > 0;
        }

        cursor.close();
        return existe;
    }

    public boolean deleteClase(int id) {
        try (SQLiteDatabase db = this.helper.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put(ClaseTable.COL_ESTADO, 0);
            long filas = db.update(ClaseTable.TABLE_NAME, values, ClaseTable.COL_ID + " =?", new String[]{String.valueOf(id)});
            return filas > 0;
        }
    }

    public List<ClaseRequest> getClasesByPeriodo(int idPeriodo) {
        SQLiteDatabase db = helper.getReadableDatabase();
        List<ClaseRequest> lista = new ArrayList<>();

        String sql = "SELECT c.id AS clase_id, c.grupo AS clase_grupo, " +
                "cur.id AS curso_id, cur.nombre AS curso_nombre, cur.codigo AS curso_codigo, " +
                "f.nombre AS facultad_nombre, c.periodo_id AS periodo_id " +
                "FROM Clase c " +
                "JOIN Cursos cur ON c.curso_id = cur.id " +
                "JOIN Facultad f ON cur." + CursosTable.COL_IDFACULTAD + " = f.id " +
                "WHERE c.periodo_id = ? AND c.estado = 1";

        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(idPeriodo)});

        if (cursor.moveToFirst()) {
            do {
                ClaseRequest cr = new ClaseRequest();
                cr.setIdClase(cursor.getInt(cursor.getColumnIndexOrThrow("clase_id")));
                cr.setGrupo(cursor.getString(cursor.getColumnIndexOrThrow("clase_grupo")));
                cr.setCursoId(cursor.getInt(cursor.getColumnIndexOrThrow("curso_id")));
                cr.setNombreCurso(cursor.getString(cursor.getColumnIndexOrThrow("curso_nombre")));
                cr.setCodigoCurso(cursor.getString(cursor.getColumnIndexOrThrow("curso_codigo")));
                cr.setNombreFacultad(cursor.getString(cursor.getColumnIndexOrThrow("facultad_nombre")));
                cr.setPeriodoId(cursor.getInt(cursor.getColumnIndexOrThrow("periodo_id")));
                lista.add(cr);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return lista;
    }

    public boolean recoverClase(int id) {
        try (SQLiteDatabase db = this.helper.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put(ClaseTable.COL_ESTADO, 1);
            long filas = db.update(ClaseTable.TABLE_NAME, values, ClaseTable.COL_ID + " = ?", new String[]{String.valueOf(id)});
            return filas > 0;
        }
    }

    public List<ClaseRequest> getBusquedaClasesByPeriodo(int id, String valor) {
        SQLiteDatabase db = helper.getReadableDatabase();
        List<ClaseRequest> lista = new ArrayList<>();

        String filtro = "%" + valor + "%";
        String sql = "SELECT c.id AS clase_id, c.grupo AS clase_grupo, " +
                "cur.id AS curso_id, cur.nombre AS curso_nombre, cur.codigo AS curso_codigo, " +
                "f.nombre AS facultad_nombre, c.periodo_id AS periodo_id " +
                "FROM Clase c " +
                "JOIN Cursos cur ON c.curso_id = cur.id " +
                "JOIN Facultad f ON cur." + CursosTable.COL_IDFACULTAD + " = f.id " +
                "WHERE c.estado = 1 AND  c.periodo_id = ? AND (cur.nombre LIKE ? OR cur.codigo LIKE ? OR f.nombre LIKE ? OR c.grupo LIKE ?)";

        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(id),filtro, filtro, filtro, filtro});

        if (cursor.moveToFirst()) {
            do {
                ClaseRequest cr = new ClaseRequest();
                cr.setIdClase(cursor.getInt(cursor.getColumnIndexOrThrow("clase_id")));
                cr.setGrupo(cursor.getString(cursor.getColumnIndexOrThrow("clase_grupo")));
                cr.setCursoId(cursor.getInt(cursor.getColumnIndexOrThrow("curso_id")));
                cr.setNombreCurso(cursor.getString(cursor.getColumnIndexOrThrow("curso_nombre")));
                cr.setCodigoCurso(cursor.getString(cursor.getColumnIndexOrThrow("curso_codigo")));
                cr.setNombreFacultad(cursor.getString(cursor.getColumnIndexOrThrow("facultad_nombre")));
                cr.setPeriodoId(cursor.getInt(cursor.getColumnIndexOrThrow("periodo_id")));
                lista.add(cr);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return lista;
    }
}
