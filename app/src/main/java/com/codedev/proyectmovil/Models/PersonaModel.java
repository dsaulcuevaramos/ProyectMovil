package com.codedev.proyectmovil.Models;

public class PersonaModel {

    private int id;
    private String codigo;
    private String nombre;
    private String apellido;
    private int idFacultad;
    private int estado;

    public PersonaModel() {
    }

    public PersonaModel(String codigo, String nombre, String apellido, int idFacultad) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.apellido = apellido;
        this.idFacultad = idFacultad;
    }

    public PersonaModel( String codigo, String nombre, String apellido, int idFacultad, int estado) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.apellido = apellido;
        this.idFacultad = idFacultad;
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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
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
