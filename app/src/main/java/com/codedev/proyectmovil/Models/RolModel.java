package com.codedev.proyectmovil.Models;

public class RolModel {
    private int id;
    private String nombre;
    private int estado;

    public RolModel() {
    }

    public RolModel(String nombre, int estado) {
        this.nombre = nombre;
        this.estado = estado;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
