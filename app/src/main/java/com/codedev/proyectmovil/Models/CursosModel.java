package com.codedev.proyectmovil.Models;

public class CursosModel {
    private int id;
    private String nombre;
    private String codigo;
    private int idFacultad;
    private int estado;

    public CursosModel() {}

    public CursosModel(String nombre, String codigo, int idFacultad, int estado) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.idFacultad = idFacultad;
        this.estado = estado;
    }

    public CursosModel(String nombre, String codigo, int estado) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
