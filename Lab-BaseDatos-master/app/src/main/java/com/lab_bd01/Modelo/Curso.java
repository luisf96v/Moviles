package com.lab_bd01.Modelo;

import java.io.Serializable;

public class Curso implements Serializable {
    int id;
    String nombre;
    String descripcion;
    int creditos;
    Estudiante estudiante;

    public Curso() {
        this.id = 0;
        this.nombre = "";
        this.descripcion = "";
        this.creditos = 0;
        this.estudiante=null;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }
}
