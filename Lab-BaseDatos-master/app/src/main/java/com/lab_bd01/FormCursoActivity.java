package com.lab_bd01;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lab_bd01.Modelo.Curso;
import com.lab_bd01.Modelo.Estudiante;

import java.util.ArrayList;


public class FormCursoActivity extends Activity {

    TextView idCurso;
    EditText nombreCurso;
    EditText descripcion;
    EditText creditos;
    CheckBox check;
    Button guardar;
    int accion = 0; // 1 si es guardar 2 si es actualizar
    Curso mCurso;
    Control control;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_curso);

        control=Control.getInstance(this);
        this.idCurso = (TextView) findViewById(R.id.idCurso);
        this.nombreCurso = (EditText) findViewById(R.id.nombreCurso);
        this.descripcion = (EditText) findViewById(R.id.descripcion);
        this.creditos = (EditText) findViewById(R.id.creditos);
        this.guardar = (Button) findViewById(R.id.guardarCurso);
        this.check = (CheckBox) findViewById(R.id.selectOption);

        // <-- jalar accion del intent y setearla
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            this.accion = extras.getInt("accion");
            this.mCurso=(Curso) extras.get("curso");
        }
        //  jalar accion del intent -->

        //cargando datos en spinner
        ArrayList<String> nombresEstudiantes=new ArrayList<>();
        ArrayList<Estudiante> estudiantesBase=control.getListaEstudiantes();




        if(this.accion == 1) {
            TextView textoID=(TextView) findViewById(R.id.idCurLabel);
            idCurso.setVisibility(View.INVISIBLE);
            textoID.setVisibility(View.INVISIBLE);

            final ArrayList<Estudiante> estudiantes = estudiantesBase;

            for (int i=0;i<estudiantes.size();i++) {
                nombresEstudiantes.add(estudiantes.get(i).getNombre());
            }
            final ArrayAdapter<String> spinner_carreras = new  ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, nombresEstudiantes);

            spinner.setAdapter(spinner_carreras);


            this.guardar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(v.getContext().INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(creditos.getWindowToken(), 0);
                    final View vi=v;
                    AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
                    alert.setTitle("Confirmacion");
                    alert.setMessage("Desea crear este Curso?");
                    alert.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {

                            //reiniciar los errores
                            nombreCurso.setError(null);
                            descripcion.setError(null);
                            creditos.setError(null);
                            String nom=nombreCurso.getText().toString();
                            String desc=descripcion.getText().toString();
                            String cre=creditos.getText().toString();

                            boolean cancel=false;
                            View focusView=null;

                            if(estudiantes.size()<=0){
                                Toast.makeText(getApplicationContext(),"No hay Estudiantes, cree uno primero",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                if (TextUtils.isEmpty(nom)) {
                                    nombreCurso.setError("Codigo Vacio");
                                    focusView = nombreCurso;
                                    cancel = true;
                                }

                                if (TextUtils.isEmpty(desc)) {
                                    descripcion.setError("Nombre Vacio");
                                    focusView = descripcion;
                                    cancel = true;
                                }

                                if (TextUtils.isEmpty(cre)) {
                                    creditos.setError("Titulo Vacio");
                                    focusView = creditos;
                                    cancel = true;
                                }


                                if (cancel) {
                                    focusView.requestFocus();
                                } else {
                                    Curso c=new Curso();
                                    c.setNombre(nom);
                                    c.setDescripcion(desc);
                                    c.setCreditos(Integer.parseInt(cre));
                                    c.setEstudiante(estudiantes.get(spinner.getSelectedItemPosition()));
                                    control.agregarCurso(nom, desc, Integer.parseInt(cre), estudiantes.get(spinner.getSelectedItemPosition()).getId());
                                    Intent intent = new Intent(FormCursoActivity.this, CursosActivity.class);
                                    FormCursoActivity.this.startActivity(intent);
                                }
                            }




                        }
                    });
                    alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            // Canceled.
                        }
                    });
                    alert.show();






                }
            });
        }
        else if(this.accion == 2){

            this.cargarDatos();

            final ArrayList<Estudiante> estudiantes;
            if(mCurso.getEstudiante() != null){
                estudiantes = this.reordenarListaEstudiantes(mCurso.getEstudiante(),estudiantesBase);
            }else {
                estudiantes = estudiantesBase;
            }
            for (int i=0;i<estudiantes.size();i++) {
                nombresEstudiantes.add(estudiantes.get(i).getNombre());
            }
            final ArrayAdapter<String> spinner_carreras = new  ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, nombresEstudiantes);

            spinner.setAdapter(spinner_carreras);

            this.guardar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(v.getContext().INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(creditos.getWindowToken(), 0);
                    final View vi=v;
                    AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
                    alert.setTitle("Confirmacion");
                    alert.setMessage("Desea editar este Curso?");
                    alert.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {

                            //reiniciar los errores
                            nombreCurso.setError(null);
                            descripcion.setError(null);
                            creditos.setError(null);
                            String nom=nombreCurso.getText().toString();
                            String desc=descripcion.getText().toString();
                            String cre=creditos.getText().toString();

                            boolean cancel=false;
                            View focusView=null;

                            if(estudiantes.size()<=0){
                                Toast.makeText(getApplicationContext(),"No hay Estudiantes, cree uno primero",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                if (TextUtils.isEmpty(nom)) {
                                    nombreCurso.setError("Codigo Vacio");
                                    focusView = nombreCurso;
                                    cancel = true;
                                }

                                if (TextUtils.isEmpty(desc)) {
                                    descripcion.setError("Nombre Vacio");
                                    focusView = descripcion;
                                    cancel = true;
                                }

                                if (TextUtils.isEmpty(cre)) {
                                    creditos.setError("Titulo Vacio");
                                    focusView = creditos;
                                    cancel = true;
                                }


                                if (cancel) {
                                    focusView.requestFocus();
                                } else {
                                    if(control.updateCurso(Integer.parseInt(idCurso.getText().toString()), nom, desc, Integer.parseInt(cre), estudiantes.get(spinner.getSelectedItemPosition()).getId())){
                                        Toast.makeText(getApplicationContext(),"Curso Editado",Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(FormCursoActivity.this, CursosActivity.class);
                                        FormCursoActivity.this.startActivity(intent);
                                    }
                                    else Toast.makeText(getApplicationContext(),"Error al editar el curso",Toast.LENGTH_SHORT).show();
                                }
                            }

                        }
                    });
                    alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            // Canceled.
                        }
                    });
                    alert.show();
                }
            });
        }
    }



    public void cargarDatos(){
        nombreCurso.setText(mCurso.getNombre());
        idCurso.setText(Integer.toString(mCurso.getId()));
        descripcion.setText(mCurso.getDescripcion());
        creditos.setText(Integer.toString(mCurso.getCreditos()));
    }

    public ArrayList<Estudiante> reordenarListaEstudiantes(Estudiante estudiante, ArrayList<Estudiante> estudiantes){
        ArrayList<Estudiante> listaNueva = new ArrayList<Estudiante>();
        listaNueva.add(estudiante);
        for(Estudiante est : estudiantes){
            if(est.getId() != estudiante.getId()){
                listaNueva.add(est);
            }
        }
        return listaNueva;
    }

}
