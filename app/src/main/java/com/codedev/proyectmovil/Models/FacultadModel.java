package com.codedev.proyectmovil.Models;

public class FacultadModel {
    private int id;
    private String codigo;
    private String nombre;
    private int estado;

    public FacultadModel() {}

    public FacultadModel(String nombre, int estado, String codigo) {
        this.nombre = nombre;
        this.estado = estado;
        this.codigo = codigo;
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

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}
