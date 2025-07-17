package com.codedev.proyectmovil.Models;

public class DetalleModel {
    private int id;
    private int estadoAsistencia;
    private int idPersona;
    private int idClase;
    private int estado;

    public DetalleModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdClase() {
        return idClase;
    }

    public void setIdClase(int idClase) {
        this.idClase = idClase;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public int getEstadoAsistencia() {
        return estadoAsistencia;
    }

    public void setEstadoAsistencia(int estadoAsistencia) {
        this.estadoAsistencia = estadoAsistencia;
    }
}
