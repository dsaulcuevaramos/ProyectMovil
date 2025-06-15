package com.codedev.proyectmovil.Models;

public class ProfesorModel {
    private String id;
    private String codigo;
    private String nombre;
    private String correo;
    private String idfacultad;
    private String estado;

    public ProfesorModel() {
    }

    public ProfesorModel(String id, String codigo, String correo, String nombre, String idfacultad,String estado) {
        this.id = id;
        this.codigo = codigo;
        this.correo = correo;
        this.nombre = nombre;
        this.idfacultad = idfacultad;
        this.estado = estado;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getIdfacultad() {
        return idfacultad;
    }

    public void setIdfacultad(String idfacultad) {
        this.idfacultad = idfacultad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
