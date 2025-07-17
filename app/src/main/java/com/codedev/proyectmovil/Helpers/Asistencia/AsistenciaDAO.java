package com.codedev.proyectmovil.Helpers.Asistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.codedev.proyectmovil.Helpers.Clase.ClaseTable;
import com.codedev.proyectmovil.Helpers.Cursos.CursosTable;
import com.codedev.proyectmovil.Helpers.DatabaseHelper;
import com.codedev.proyectmovil.Helpers.Facultad.FacultadTable;
import com.codedev.proyectmovil.Helpers.Persona.PersonaTable;
import com.codedev.proyectmovil.Helpers.Rol.RolTable;
import com.codedev.proyectmovil.Helpers.Usuario.UsuarioTable;
import com.codedev.proyectmovil.Models.AsistenciaModel;
import com.codedev.proyectmovil.Models.ClaseModel;
import com.codedev.proyectmovil.Models.FacultadModel;
import com.codedev.proyectmovil.Models.Requests.UsuarioRequest;

import java.util.ArrayList;
import java.util.List;

public class AsistenciaDAO {

    private DatabaseHelper helper;
    public AsistenciaDAO(Context context) {
        this.helper = new DatabaseHelper(context);
    }


    public List<AsistenciaModel> getAsistenciaByCurso(int idCurso){
        try (SQLiteDatabase db = this.helper.getReadableDatabase()) {
            List<AsistenciaModel> asistenciaModel = new ArrayList<>();

            String sql = "SELECT da.id, dclas.id, cla.id, c.id, a.activo AS activo, a.fecha AS fecha_asistencia , cla.nombre AS clase_nombre, cla.grupo AS clase_grupo " +
                    "FROM Detalle_asistencia da " +
                    "JOIN Asistencia a ON da.asistencia_id = a.id " +
                    "JOIN Detalle_clase dcla ON da.detaleclase_id = dcla.id " +
                    "JOIN Clase cla ON dcla.clase_id = cla.id " +
                    "JOIN Cursos c ON cla.curso_id = c.id " +
                    "WHERE da.estado = 1 AND c.id = ?";

            Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(idCurso)});

            if (cursor.moveToFirst()) {
                do {
                    AsistenciaModel am = new AsistenciaModel();
                    am.setFecha(cursor.getString(cursor.getColumnIndexOrThrow("fecha_asistencia")));
                    am.setActivo(cursor.getInt(cursor.getColumnIndexOrThrow("activo")));
                    asistenciaModel.add(am);
                } while (cursor.moveToNext());
            }

            cursor.close();
            return asistenciaModel;
        }
    }

    public AsistenciaModel addAsistencia(AsistenciaModel asistencia) {
        try (SQLiteDatabase db = this.helper.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put(AsistenciaTable.COL_FECHA, asistencia.getFecha());
            values.put(AsistenciaTable.COL_ACTIVO, 1);
            values.put(AsistenciaTable.COL_ESTADO, 1);
            long resultado = db.insert(ClaseTable.TABLE_NAME, null, values);
            if (resultado != -1) {
                asistencia.setIdAsitencia((int) resultado);
                return asistencia;
            } else {
                return null;
            }
        }
    }

    public boolean updateAsistencia(AsistenciaModel asistencia){
        try (SQLiteDatabase db = this.helper.getWritableDatabase()){
            ContentValues values = new ContentValues();
            values.put(AsistenciaTable.COL_FECHA, asistencia.getFecha());
            values.put(AsistenciaTable.COL_ACTIVO, asistencia.getActivo());
            values.put(AsistenciaTable.COL_ESTADO, asistencia.getEstado());
            long resultado = db.update(FacultadTable.TABLE_NAME, values, FacultadTable.COL_ID + "=?", new String[]{String.valueOf(asistencia.getIdAsitencia())});
            return resultado != -1;
        }
    }


    public List<ClaseModel> getClaseByCurso(int id){
        SQLiteDatabase db = helper.getReadableDatabase();
        List<ClaseModel> lista = new ArrayList<>();

        String sql = "SELECT * FROM "+ClaseTable.TABLE_NAME+" WHERE "+ClaseTable.COL_CURSO_ID+"= ? AND estado = 1";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()) {
            do {
                ClaseModel cm = new ClaseModel();
                cm.setId(cursor.getInt(cursor.getColumnIndexOrThrow(ClaseTable.COL_ID)));
                cm.setIdCurso(cursor.getInt(cursor.getColumnIndexOrThrow(ClaseTable.COL_CURSO_ID)));
                cm.setGrupo(cursor.getString(cursor.getColumnIndexOrThrow(ClaseTable.COL_GRUPO)));
                cm.setIdPeriodo(cursor.getInt(cursor.getColumnIndexOrThrow(ClaseTable.COL_PERIODO_ID)));
                lista.add(cm);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return lista;
    }

    public List<AsistenciaModel> getAsistenciasPorClase(int idClase) {
        SQLiteDatabase db = helper.getReadableDatabase();
        List<AsistenciaModel> lista = new ArrayList<>();

        String sql = "SELECT DISTINCT A.* FROM Asistencia A " +
                "JOIN Detalle_asistencia DA ON A.id = DA.asistencia_id " +
                "JOIN Detalle_clase DC ON DA.detaleclase_id = DC.id " +
                "WHERE DC.clase_id = ? AND A.estado = 1";

        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(idClase)});
        if (cursor.moveToFirst()) {
            do {
                AsistenciaModel am = new AsistenciaModel();
                am.setIdAsitencia(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                am.setFecha(cursor.getString(cursor.getColumnIndexOrThrow("fecha")));
                am.setActivo(cursor.getInt(cursor.getColumnIndexOrThrow("activo")));
                am.setEstado(cursor.getInt(cursor.getColumnIndexOrThrow("estado")));
                lista.add(am);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return lista;
    }

}
