package com.lab_bd01.Modelo;

import java.io.Serializable;


public class Estudiante implements Serializable {
    int id;
    String nombre;
    String apellido1;
    String apellido2;
    int edad;

    public Estudiante() {
        this.id = 0;
        this.nombre = "";
        this.apellido1 = "";
        this.apellido2 = "";
        this.edad = 0;
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

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }
}
