package com.codedev.proyectmovil.Helpers.Cursos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.codedev.proyectmovil.Helpers.DatabaseHelper;
import com.codedev.proyectmovil.Models.CursosModel;

import java.util.ArrayList;
import java.util.List;

public class CursosDAO {
    private DatabaseHelper helper;

    public  CursosDAO(Context context){
        helper = new DatabaseHelper(context);
    }
    public boolean addCursos(CursosModel cursosModel){
        try (SQLiteDatabase db = this.helper.getWritableDatabase()){
            ContentValues values = new ContentValues();
            values.put(CursosTable.COL_NOMBRE, cursosModel.getNombre());
            values.put(CursosTable.COL_CODIGO, cursosModel.getCodigo());
            values.put(CursosTable.COL_IDFACULTAD, cursosModel.getIdFacultad());
            values.put(CursosTable.COL_ESTADO, 1);
            long resultado = db.insert(CursosTable.TABLE_NAME, null, values);
            return resultado != -1;
        }
    }

    public boolean updateCursos(CursosModel cursosModel){
        try (SQLiteDatabase db = this.helper.getWritableDatabase()){
            ContentValues values = new ContentValues();
            values.put(CursosTable.COL_NOMBRE, cursosModel.getNombre());
            values.put(CursosTable.COL_CODIGO, cursosModel.getCodigo());
            values.put(CursosTable.COL_IDFACULTAD, cursosModel.getIdFacultad());
            values.put(CursosTable.COL_ESTADO, cursosModel.getEstado());
            long filas = db.update(CursosTable.TABLE_NAME, values, CursosTable.COL_ID + " = ?", new String[]{String.valueOf(cursosModel.getId())});
            return filas > 0;
        }
    }

    public boolean deleteCursos(int id){
        try (SQLiteDatabase db = this.helper.getWritableDatabase()){
            ContentValues values = new ContentValues();
            values.put(CursosTable.COL_ESTADO, 0);
            long filas = db.update( CursosTable.TABLE_NAME,values, CursosTable.COL_ID+ " =?", new String[]{String.valueOf(id)});
            return filas > 0;
        }
    }

    public List<CursosModel> getCursos(){
        try (SQLiteDatabase db = this.helper.getReadableDatabase()){
            List<CursosModel> listaCursos = new ArrayList<>();
            Cursor cursor = db.rawQuery("SELECT * FROM " + CursosTable.TABLE_NAME+ " WHERE " + CursosTable.COL_ESTADO+ " = 1", null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    CursosModel cursosModel = new CursosModel();
                    cursosModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(CursosTable.COL_ID)));
                    cursosModel.setNombre(cursor.getString(cursor.getColumnIndexOrThrow(CursosTable.COL_NOMBRE)));
                    cursosModel.setCodigo(cursor.getString(cursor.getColumnIndexOrThrow(CursosTable.COL_CODIGO)));
                    cursosModel.setIdFacultad(cursor.getInt(cursor.getColumnIndexOrThrow(CursosTable.COL_IDFACULTAD)));
                    cursosModel.setEstado(cursor.getInt(cursor.getColumnIndexOrThrow(CursosTable.COL_ESTADO)));
                    listaCursos.add(cursosModel);
                } while (cursor.moveToNext());
            }
            Log.d("CursosDAO", "Cantidad de cursos encontrados: " + listaCursos.size());
            for (CursosModel c : listaCursos) {
                Log.d("CursosDAO", c.getNombre() + " - " + c.getCodigo());
            }
            cursor.close();
            return listaCursos;
        }
    }

    public List<CursosModel> getCursosPorFacultad(int idFacultad){
        List<CursosModel> listaCursos = new ArrayList<>();
        try (SQLiteDatabase db = this.helper.getReadableDatabase()){
            Cursor cursor = db.rawQuery(
                    "SELECT * FROM " + CursosTable.TABLE_NAME +
                            " WHERE " + CursosTable.COL_ESTADO + " = 1 AND " +
                            CursosTable.COL_IDFACULTAD + " = ?",
                    new String[]{String.valueOf(idFacultad)}
            );
            if (cursor.moveToFirst()) {
                do {
                    CursosModel curso = new CursosModel();
                    curso.setId(cursor.getInt(cursor.getColumnIndexOrThrow(CursosTable.COL_ID)));
                    curso.setNombre(cursor.getString(cursor.getColumnIndexOrThrow(CursosTable.COL_NOMBRE)));
                    curso.setCodigo(cursor.getString(cursor.getColumnIndexOrThrow(CursosTable.COL_CODIGO)));
                    curso.setIdFacultad(cursor.getInt(cursor.getColumnIndexOrThrow(CursosTable.COL_IDFACULTAD)));
                    curso.setEstado(cursor.getInt(cursor.getColumnIndexOrThrow(CursosTable.COL_ESTADO)));
                    listaCursos.add(curso);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return listaCursos;
    }

}