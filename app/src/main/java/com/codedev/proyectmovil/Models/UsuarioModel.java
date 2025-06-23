package com.codedev.proyectmovil.Models;

public class UsuarioModel {
    private int id;
    private String nombre;
    private String correo;
    private String contrasenia;
    private int tipo;
    private int estado;

    public UsuarioModel(){}

    public UsuarioModel(String correo, String contrasenia, String nombre, int estado) {
        this.correo = correo;
        this.contrasenia = contrasenia;
        this.nombre = nombre;
        this.estado = estado;
    }

    public UsuarioModel(String correo, String contrasenia, String nombre, int tipo, int estado) {
        this.correo = correo;
        this.contrasenia = contrasenia;
        this.nombre = nombre;
        this.tipo = tipo;
        this.estado = estado;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
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
