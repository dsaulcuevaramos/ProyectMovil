package com.codedev.proyectmovil.Models;

public class AlumnoModel {
    private int id;
    private String codigo;
    private String nombre;
    private String correo;
    private int idFacultad;
    private int estado;

    public AlumnoModel() {
    }

    public AlumnoModel(String codigo, String nombre, String correo, int estado) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.correo = correo;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getIdFacultad() {
        return idFacultad;
    }

    public void setIdFacultad(int idFacultad) {
        this.idFacultad = idFacultad;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}
