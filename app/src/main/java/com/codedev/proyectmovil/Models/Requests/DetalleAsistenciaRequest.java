package com.codedev.proyectmovil.Models.Requests;

public class DetalleAsistenciaRequest {

    private int idDetalleAsistencia;
    private int idAsistencia;
    private int idDetalleClase;
    private int Nombre;
    private int codigo;
    private int asistenciaValor;
    private String observación;
    private int estado;

    public DetalleAsistenciaRequest() {}

    public int getEstado() {return estado;}

    public void setEstado(int estado) {this.estado = estado;}

    public String getObservación() {return observación;}

    public void setObservación(String observación) {this.observación = observación;}

    public int getNombre() {return Nombre;}

    public void setNombre(int nombre) {Nombre = nombre;}

    public int getIdDetalleClase() {return idDetalleClase;}

    public void setIdDetalleClase(int idDetalleClase) {this.idDetalleClase = idDetalleClase;}

    public int getIdAsistencia() {return idAsistencia;}

    public void setIdAsistencia(int idAsistencia) {this.idAsistencia = idAsistencia;}

    public int getIdDetalleAsistencia() {return idDetalleAsistencia;}

    public void setIdDetalleAsistencia(int idDetalleAsistencia) {this.idDetalleAsistencia = idDetalleAsistencia;}

    public int getAsistenciaValor() {return asistenciaValor;}

    public void setAsistenciaValor(int asistenciaValor) {this.asistenciaValor = asistenciaValor;}

    public int getCodigo() {return codigo;}

    public void setCodigo(int codigo) {this.codigo = codigo;}
}
