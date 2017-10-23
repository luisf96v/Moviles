package com.lab_bd01;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.lab_bd01.Modelo.Curso;
import com.lab_bd01.Modelo.Estudiante;

import java.util.ArrayList;

public class BaseDatos extends SQLiteOpenHelper {

    public static final String DB_NAME="movilesDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLA_CURSO = "Curso";
    private static final String TABLA_ESTUDIANTE = "Estudiante";
    private static BaseDatos sInstance;

    private BaseDatos(Context context) {
        super(context,DB_NAME,null,DATABASE_VERSION);
    }


    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String crearestudiante = "create table " + TABLA_ESTUDIANTE +
                "(" +
                "id integer primary key, " + // Define a primary key
                "nombre text, " +
                "apellido1 text, " +
                "apellido2 text, " +
                "edad integer" +
                ");";

        String crear_curso = "create table " + TABLA_CURSO +
                "(" +
                "id integer primary key autoincrement, "+
                "nombre text, "+
                "descripcion text, "+
                "creditos integer, "+
                "estudiante_id integer REFERENCES "+TABLA_ESTUDIANTE+
                ");";

        db.execSQL(crearestudiante);
        Log.d("Base de Datos", "Tabla estudiante");
        db.execSQL(crear_curso);
        Log.d("Base de Datos", "Tabla curso");

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + TABLA_ESTUDIANTE);
            db.execSQL("DROP TABLE IF EXISTS " + TABLA_CURSO);
            onCreate(db);
        }
    }


    //singleton para usar la misma instancia
    public static synchronized BaseDatos getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new BaseDatos(context.getApplicationContext());
        }
        return sInstance;
    }





    public boolean agregarEstudiante(int id, String nom, String ape1, String ape2, int eda){
        try{
            SQLiteDatabase db=this.getWritableDatabase();
            db.execSQL("insert into Estudiante(id, nombre, apellido1, apellido2, edad) values ('"+id+"', '"+nom+"', '"+ape1+"', '"+ape2+"', '"+eda+"');");
            return true;
        }catch (SQLiteException ex){
            Log.e("Base de Datos", "Excepcion en agregar estudiante", ex);
            return false;
        }
    }

    public Estudiante buscarEstudiante(String nombre, String apellido1, String apellido2, int edad){
        try{
            SQLiteDatabase db=this.getReadableDatabase();
            String query= "select * from Estudiante where nombre='"+nombre+"' and apellido1='"+apellido1+"' and apellido2='"+apellido2+"' and edad='"+edad+"';";
            Cursor cursor = db.rawQuery(query,null);
            Estudiante aux=new Estudiante();
            aux.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            aux.setNombre(cursor.getString(cursor.getColumnIndexOrThrow("nombre")));
            aux.setApellido1(cursor.getString(cursor.getColumnIndexOrThrow("apellido1")));
            aux.setApellido2(cursor.getString(cursor.getColumnIndexOrThrow("apellido2")));
            aux.setEdad(cursor.getInt(cursor.getColumnIndexOrThrow("edad")));


            return aux;

        }catch (SQLiteException ex){
            Log.e("Base de Datos", "Excepcion en bucarEstudiante", ex);
            return null;
        }
    }

    public Estudiante estudianteById(int id){
        try{
            SQLiteDatabase db=this.getReadableDatabase();
            String query= "select * from Estudiante where id='"+id+"';";
            Cursor cursor = db.rawQuery(query,null);

            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    Estudiante aux=new Estudiante();
                    aux.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                    aux.setNombre(cursor.getString(cursor.getColumnIndexOrThrow("nombre")));
                    aux.setApellido1(cursor.getString(cursor.getColumnIndexOrThrow("apellido1")));
                    aux.setApellido2(cursor.getString(cursor.getColumnIndexOrThrow("apellido2")));
                    aux.setEdad(cursor.getInt(cursor.getColumnIndexOrThrow("edad")));
                    return aux;
                }
            }
            return null;

        }catch (SQLiteException ex){
            Log.e("Base de Datos", "Excepcion en estudianteById", ex);
            return null;
        }
    }

    public boolean updateEstudiante(int id, String nom, String ape1, String ape2, int edad){
        try{
            SQLiteDatabase db=this.getWritableDatabase();
            db.execSQL("update Estudiante set nombre='"+nom+"', apellido1='"+ape1+"', apellido2='"+ape2+"', edad='"+edad+"' where id="+id+";");
            return true;
        }catch (SQLiteException ex){
            Log.e("Base de Datos", "Excepcion en updateEstudiante", ex);
            return false;
        }
    }

    public boolean deleteEstudiante(int id){
        try{
            SQLiteDatabase db=this.getWritableDatabase();
            db.execSQL("delete from Estudiante where id="+id+";");
            return true;
        }catch (SQLiteException ex){
            Log.e("Base de Datos", "Excepcion en deleteEstudiante", ex);
            return false;
        }
    }

    public ArrayList<Estudiante> getListaEstudiantes(){
        try{
            SQLiteDatabase db=this.getReadableDatabase();
            String query= "select * from Estudiante;";
            Cursor cursor = db.rawQuery(query,null);
            ArrayList<Estudiante> lista=new ArrayList<>();
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    Estudiante aux=new Estudiante();
                    aux.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                    aux.setNombre(cursor.getString(cursor.getColumnIndexOrThrow("nombre")));
                    aux.setApellido1(cursor.getString(cursor.getColumnIndexOrThrow("apellido1")));
                    aux.setApellido2(cursor.getString(cursor.getColumnIndexOrThrow("apellido2")));
                    aux.setEdad(cursor.getInt(cursor.getColumnIndexOrThrow("edad")));
                    lista.add(aux);
                    cursor.moveToNext();
                }
            }

            return lista;

        }catch (SQLiteException ex){
            Log.e("Base de Datos", "Excepcion en getListaEstudiantes", ex);
            return null;
        }
    }

    public ArrayList<Estudiante> getEstudiantesLike(String busqueda){
        try{
            SQLiteDatabase db=this.getReadableDatabase();
            String query= "select * from Estudiante where id like '%%%"+busqueda+"%%%' or nombre like '%%%"+busqueda+"%%%' or apellido1 like '%%%"+busqueda+"%%%' or apellido2 like '%%%"+busqueda+"%%%' or edad like '%%%"+busqueda+"%%%';";

            Cursor cursor = db.rawQuery(query,null);
            ArrayList<Estudiante> lista=new ArrayList<>();
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    Estudiante aux=new Estudiante();
                    aux.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                    aux.setNombre(cursor.getString(cursor.getColumnIndexOrThrow("nombre")));
                    aux.setApellido1(cursor.getString(cursor.getColumnIndexOrThrow("apellido1")));
                    aux.setApellido2(cursor.getString(cursor.getColumnIndexOrThrow("apellido2")));
                    aux.setEdad(cursor.getInt(cursor.getColumnIndexOrThrow("edad")));
                    lista.add(aux);
                    cursor.moveToNext();
                }
            }

            return lista;

        }catch (SQLiteException ex){
            Log.e("Base de Datos", "Excepcion en getListaEstudiantes", ex);
            return null;
        }
    }




    //CRUD CURSO


    public boolean agregarCurso(String nom, String des, int cred, int id_est){
        try{
            SQLiteDatabase db=this.getWritableDatabase();
            db.execSQL("insert into Curso(nombre, descripcion, creditos, estudiante_id) values ('"+nom+"', '"+des+"', '"+cred+"', '"+id_est+"');");
            return true;
        }catch (SQLiteException ex){
            Log.e("Base de Datos", "Excepcion en agregarCurso", ex);
            return false;
        }
    }

    public Curso buscarCurso(String nombre, String descripcion, int creditos, int estudiante_id){
        try{
            SQLiteDatabase db=this.getReadableDatabase();
            String query= "select * from Curso where nombre='"+nombre+"' and descripcion='"+descripcion+"' and creditos='"+creditos+"';";
            Cursor cursor = db.rawQuery(query,null);
            Curso aux=new Curso();
            aux.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            aux.setNombre(cursor.getString(cursor.getColumnIndexOrThrow("nombre")));
            aux.setDescripcion(cursor.getString(cursor.getColumnIndexOrThrow("descripcion")));
            aux.setCreditos(cursor.getInt(cursor.getColumnIndexOrThrow("creditos")));
            aux.setEstudiante(estudianteById(cursor.getInt(cursor.getColumnIndexOrThrow("id"))));
            return aux;

        }catch (SQLiteException ex){
            Log.e("Base de Datos", "Excepcion en buscarCurso", ex);
            return null;
        }
    }

    public Curso cursoById(int id){
        try{
            SQLiteDatabase db=this.getReadableDatabase();
            String query= "select * from Curso where id='"+id+"';";
            Cursor cursor = db.rawQuery(query,null);
            Curso aux=new Curso();
            aux.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            aux.setNombre(cursor.getString(cursor.getColumnIndexOrThrow("nombre")));
            aux.setDescripcion(cursor.getString(cursor.getColumnIndexOrThrow("descripcion")));
            aux.setCreditos(cursor.getInt(cursor.getColumnIndexOrThrow("creditos")));
            aux.setEstudiante(estudianteById(cursor.getInt(cursor.getColumnIndexOrThrow("estudiante_id"))));

            return aux;

        }catch (SQLiteException ex){
            Log.e("Base de Datos", "Excepcion en cursoById", ex);
            return null;
        }
    }


    public boolean updateCurso(int id, String nom, String des, int cred, int id_est){
        try{
            SQLiteDatabase db=this.getWritableDatabase();
            db.execSQL("update Curso set nombre='"+nom+"', descripcion='"+des+"', creditos='"+cred+"', estudiante_id='"+id_est+"' where id="+id+";");
            return true;
        }catch (SQLiteException ex){
            Log.e("Base de Datos", "Excepcion en updateCurso", ex);
            return false;
        }
    }


    public boolean deletecurso(int id){
        try{
            SQLiteDatabase db=this.getWritableDatabase();
            db.execSQL("delete from Curso where id="+id+";");
            return true;
        }catch (SQLiteException ex){
            Log.e("Base de Datos", "Excepcion en deleteCurso", ex);
            return false;
        }
    }


    public ArrayList<Curso> getListaCursos(){
        try{
            SQLiteDatabase db=this.getReadableDatabase();
            String query= "select * from Curso;";
            Cursor cursor = db.rawQuery(query,null);
            ArrayList<Curso> lista=new ArrayList<>();
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    Curso aux=new Curso();
                    aux.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                    aux.setNombre(cursor.getString(cursor.getColumnIndexOrThrow("nombre")));
                    aux.setDescripcion(cursor.getString(cursor.getColumnIndexOrThrow("descripcion")));
                    aux.setCreditos(cursor.getInt(cursor.getColumnIndexOrThrow("creditos")));
                    aux.setEstudiante(estudianteById(cursor.getInt(cursor.getColumnIndexOrThrow("estudiante_id"))));
                    lista.add(aux);
                    cursor.moveToNext();
                }
            }

            return lista;

        }catch (SQLiteException ex){
            Log.e("Base de Datos", "Excepcion en getListaCursos", ex);
            return null;
        }
    }

    public ArrayList<Curso> getCursosLike(String busqueda){
        try{
            SQLiteDatabase db=this.getReadableDatabase();
            String query= "select * from Curso where id like '%%%"+busqueda+"%%%' or nombre like '%%%"+busqueda+"%%%' or descripcion like '%%%"+busqueda+"%%%' or creditos like '%%%"+busqueda+"%%%';";
            Cursor cursor = db.rawQuery(query,null);
            ArrayList<Curso> lista=new ArrayList<>();
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    Curso aux=new Curso();
                    aux.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                    aux.setNombre(cursor.getString(cursor.getColumnIndexOrThrow("nombre")));
                    aux.setDescripcion(cursor.getString(cursor.getColumnIndexOrThrow("descripcion")));
                    aux.setCreditos(cursor.getInt(cursor.getColumnIndexOrThrow("creditos")));
                    aux.setEstudiante(estudianteById(cursor.getInt(cursor.getColumnIndexOrThrow("estudiante_id"))));
                    lista.add(aux);
                    cursor.moveToNext();
                }
            }

            return lista;

        }catch (SQLiteException ex){
            Log.e("Base de Datos", "Excepcion en getListaCursos", ex);
            return null;
        }
    }


    public ArrayList<Curso> getCursosDeEstudiante(int idEstudiante){
        try{
            SQLiteDatabase db=this.getReadableDatabase();
            String query= "select * from Curso where estudiante_id='"+idEstudiante+"';";
            Cursor cursor = db.rawQuery(query,null);
            ArrayList<Curso> lista=new ArrayList<>();
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    Curso aux=new Curso();
                    aux.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                    aux.setNombre(cursor.getString(cursor.getColumnIndexOrThrow("nombre")));
                    aux.setDescripcion(cursor.getString(cursor.getColumnIndexOrThrow("descripcion")));
                    aux.setCreditos(cursor.getInt(cursor.getColumnIndexOrThrow("creditos")));
                    aux.setEstudiante(estudianteById(cursor.getInt(cursor.getColumnIndexOrThrow("estudiante_id"))));
                    lista.add(aux);
                    cursor.moveToNext();
                }
            }

            return lista;

        }catch (SQLiteException ex){
            Log.e("Base de Datos", "Excepcion en getListaCursos", ex);
            return null;
        }
    }


}
