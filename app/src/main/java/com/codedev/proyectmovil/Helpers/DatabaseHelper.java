package com.codedev.proyectmovil.Helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.codedev.proyectmovil.Helpers.Clase.ClaseTable;
import com.codedev.proyectmovil.Helpers.Cursos.CursosTable;
import com.codedev.proyectmovil.Helpers.DetalleClase.DetalleClaseTable;
import com.codedev.proyectmovil.Helpers.Facultad.FacultadTable;
import com.codedev.proyectmovil.Helpers.Periodo.PeriodoTable;
import com.codedev.proyectmovil.Helpers.Persona.PersonaTable;
import com.codedev.proyectmovil.Helpers.Rol.RolTable;
import com.codedev.proyectmovil.Helpers.Usuario.UsuarioTable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Personas.db";
    private static final int DATABASE_VERSION = 13;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(FacultadTable.SQL_CREATE);
        db.execSQL(RolTable.SQL_CREATE);
        db.execSQL(PersonaTable.SQL_CREATE);
        db.execSQL(UsuarioTable.SQL_CREATE);
        db.execSQL(CursosTable.SQL_CREATE);
        db.execSQL(PeriodoTable.SQL_CREATE);
        db.execSQL(ClaseTable.SQL_CREATE);
        db.execSQL(DetalleClaseTable.SQL_CREATE);
        insertarFacultadesIniciales(db);
        insertarAdminInicial(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(FacultadTable.SQL_DROP);
        db.execSQL(RolTable.SQL_DROP);
        db.execSQL(PersonaTable.SQL_DROP);
        db.execSQL(UsuarioTable.SQL_DROP);
        db.execSQL(CursosTable.SQL_DROP);
        db.execSQL(PeriodoTable.SQL_DROP);
        db.execSQL(ClaseTable.SQL_DROP);
        db.execSQL(DetalleClaseTable.SQL_DROP);
        onCreate(db);
    }

    private void insertarAdminInicial(SQLiteDatabase db) {
        db.execSQL("INSERT INTO " + PersonaTable.TABLE_NAME +
                " (" + PersonaTable.COL_NOMBRE + ", " +
                PersonaTable.COL_APELLIDO + ", " +
                PersonaTable.COL_CODIGO + ", " +
                PersonaTable.COL_IDFACULTAD + ", " +
                PersonaTable.COL_ESTADO + ") VALUES (" +
                "'Admin','','ADMIN01',1,1)");

        db.execSQL("INSERT INTO " + UsuarioTable.TABLE_NAME +
                " (" + UsuarioTable.COL_USUARIO + ", " +
                UsuarioTable.COL_CONTRASENIA + ", " +
                UsuarioTable.COL_PERSONA_ID + ", " +
                UsuarioTable.COL_ROL_ID + ", " +
                UsuarioTable.COL_ESTADO + ") VALUES (" +
                "'admin','admin', 1, 1, 1)");
    }

    private void insertarFacultadesIniciales(SQLiteDatabase db) {
        db.execSQL("INSERT INTO " + FacultadTable.TABLE_NAME + " (" +
                FacultadTable.COL_CODIGO + ", " +
                FacultadTable.COL_NOMBRE + ", " +
                FacultadTable.COL_ESTADO + ") VALUES ('F001', 'Facultad de Ingeniería', 1)");

        db.execSQL("INSERT INTO " + FacultadTable.TABLE_NAME + " (" +

                FacultadTable.COL_CODIGO + ", " +
                FacultadTable.COL_NOMBRE + ", " +
                FacultadTable.COL_ESTADO + ") VALUES ('F002', 'Facultad de Medicina', 1)");

        db.execSQL("INSERT INTO " + RolTable.TABLE_NAME + " (" +
                RolTable.COL_ID + ", " +
                RolTable.COL_NOMBRE + ", " +
                RolTable.COL_ESTADO + ") VALUES (1, 'Administrador', 1)");
        db.execSQL("INSERT INTO " + RolTable.TABLE_NAME + " (" +
                RolTable.COL_ID + ", " +
                RolTable.COL_NOMBRE + ", " +
                RolTable.COL_ESTADO + ") VALUES (2, 'Alumno', 1)");
        db.execSQL("INSERT INTO " + RolTable.TABLE_NAME + " (" +
                RolTable.COL_ID + ", " +
                RolTable.COL_NOMBRE + ", " +
                RolTable.COL_ESTADO + ") VALUES (3, 'Profesor', 1)");

        // ================= PERIODOS =================
        db.execSQL("INSERT INTO Periodo (codigo, nombre, activo, estado) VALUES ('2024-I', 'Periodo 2024-I', 0, 1)");
        db.execSQL("INSERT INTO Periodo (codigo, nombre, activo, estado) VALUES ('2024-II', 'Periodo 2024-II', 0, 1)");
        db.execSQL("INSERT INTO Periodo (codigo, nombre, activo, estado) VALUES ('2025-I', 'Periodo 2025-I', 0, 1)");
        db.execSQL("INSERT INTO Periodo (codigo, nombre, activo, estado) VALUES ('2025-II', 'Periodo 2025-II', 1, 1)");

        // ================= CURSOS =================
        db.execSQL("INSERT INTO Cursos (codigo, nombre, idFacultad, estado) VALUES ('INF101', 'Algoritmos', 1, 1)");
        db.execSQL("INSERT INTO Cursos (codigo, nombre, idFacultad, estado) VALUES ('INF102', 'Base de Datos', 1, 1)");
        db.execSQL("INSERT INTO Cursos (codigo, nombre, idFacultad, estado) VALUES ('MED201', 'Anatomía I', 2, 1)");
        db.execSQL("INSERT INTO Cursos (codigo, nombre, idFacultad, estado) VALUES ('MED202', 'Fisiología', 2, 1)");


        db.execSQL("INSERT INTO Persona (codigo, nombre, apellido, facultad_id, estado) VALUES ('P001', 'Luis', 'Ramírez', 1, 1)");
        db.execSQL("INSERT INTO Persona (codigo, nombre, apellido, facultad_id, estado) VALUES ('P002', 'Marta', 'Salinas', 2, 1)");

        // Alumnos
        for (int i = 1; i <= 10; i++) {
            String cod = String.format("A%03d", i);
            String nombre = "Alumno" + i;
            String apellido = "Apellido" + i;
            int facultad = (i % 2 == 0) ? 1 : 2; // alternar facultades
            db.execSQL("INSERT INTO Persona (codigo, nombre, apellido, facultad_id, estado) " +
                    "VALUES ('" + cod + "', '" + nombre + "', '" + apellido + "', " + facultad + ", 1)");
        }

        // Profesores
        db.execSQL("INSERT INTO Usuario (usuario, contrasenia, persona_id, rol_id, estado) VALUES ('lramirez', '123', 1, 3, 1)");
        db.execSQL("INSERT INTO Usuario (usuario, contrasenia, persona_id, rol_id, estado) VALUES ('msalinas', '123', 2, 3, 1)");

        // Alumnos
        for (int i = 1; i <= 10; i++) {
            int personaId = i + 3; // porque ya se insertaron 2 profesores primero
            db.execSQL("INSERT INTO Usuario (usuario, contrasenia, persona_id, rol_id, estado) " +
                    "VALUES ('alumno" + i + "', '123', " + personaId + ", 2, 1)");
        }
    }
}

