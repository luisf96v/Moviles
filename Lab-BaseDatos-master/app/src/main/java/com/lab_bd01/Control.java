package com.lab_bd01;


import android.content.Context;

import com.lab_bd01.Modelo.Curso;
import com.lab_bd01.Modelo.Estudiante;

import java.util.ArrayList;

public class Control {
    private BaseDatos basedatos;
    private static Control instance;

    private Control(Context c) {
        this.basedatos = BaseDatos.getInstance(c);
    }

    public static synchronized Control getInstance(Context c){
        if(instance==null)
            instance=new Control(c);
        return instance;
    }

    public boolean agregarEstudiante(int ced, String nom, String ape1, String ape2, int eda){
        return basedatos.agregarEstudiante(ced,nom,ape1,ape2,eda);
    }

    public boolean updateEstudiante(int id, String nom, String ape1, String ape2, int edad){
        return basedatos.updateEstudiante(id, nom, ape1, ape2, edad);
    }

    public ArrayList<Estudiante> getListaEstudiantes(){
        return basedatos.getListaEstudiantes();
    }

    public ArrayList<Estudiante> getEstudiantesLike(String consulta){
        return basedatos.getEstudiantesLike(consulta);
    }

    public boolean deleteEstudiante(int id){
        return basedatos.deleteEstudiante(id);
    }

    public ArrayList<Curso> getCursosDeEstudiante(int id){
        return basedatos.getCursosDeEstudiante(id);
    }


    public ArrayList<Curso> getListaCursos(){
        return basedatos.getListaCursos();
    }

    public ArrayList<Curso> getCursosLike(String consulta){
        return basedatos.getCursosLike(consulta);
    }

    public boolean deletecurso(int id){
        return basedatos.deletecurso(id);
    }

    public boolean updateCurso(int id, String nom, String des, int cred, int id_est){
        return basedatos.updateCurso(id, nom, des, cred,id_est);
    }

    public boolean agregarCurso(String nom, String des, int cred, int id_est){
        return basedatos.agregarCurso(nom, des, cred,id_est);
    }



}

