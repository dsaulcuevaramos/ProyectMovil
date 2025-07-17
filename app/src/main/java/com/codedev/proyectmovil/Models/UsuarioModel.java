package com.codedev.proyectmovil.Models;

public class UsuarioModel {
    private int id;
    private String usuario;
    private String contrasenia;
    private int persona_id;
    private int rol_id;
    private int estado;

    public UsuarioModel(){}

    public UsuarioModel(String usuario, String contrasenia, int estado) {
        this.contrasenia = contrasenia;
        this.usuario = usuario;
        this.estado = estado;
    }

    public UsuarioModel(String usuario, String contrasenia, int persona_id, int rol_id, int estado) {
        this.contrasenia = contrasenia;
        this.usuario = usuario;
        this.persona_id = persona_id;
        this.rol_id = rol_id;
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

    public int getRol_id() {
        return rol_id;
    }

    public void setRol_id(int rol_id) {
        this.rol_id = rol_id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPersona_id() {
        return persona_id;
    }

    public void setPersona_id(int persona_id) {
        this.persona_id = persona_id;
    }
}
