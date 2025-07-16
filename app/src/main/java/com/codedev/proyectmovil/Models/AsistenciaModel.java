package com.codedev.proyectmovil.Models;

public class AsistenciaModel {

    private int idAsitencia;
    private String fecha;
    private int activo;
    private int estado;

    public AsistenciaModel() {}

    public int getIdAsitencia() {return idAsitencia;}

    public void setIdAsitencia(int idAsitencia) {this.idAsitencia = idAsitencia;}

    public String getFecha() {return fecha;}

    public void setFecha(String fecha) {this.fecha = fecha;}

    public int getActivo() {return activo;}

    public void setActivo(int activo) {this.activo = activo;}

    public int getEstado() {return estado;}

    public void setEstado(int estado) {this.estado = estado;}
}
