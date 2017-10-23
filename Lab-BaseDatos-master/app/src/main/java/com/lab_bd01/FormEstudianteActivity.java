package com.lab_bd01;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lab_bd01.Modelo.Estudiante;


public class FormEstudianteActivity extends Activity {

    EditText idEstudiante;
    EditText nombreEstudiante;
    EditText apellido1;
    EditText apellido2;
    EditText edad;
    Button guardar;
    Estudiante mEstudiante;
    Control control;
    int accion = 0; // 1 si es guardar 2 si es actualizar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_estudiante);
        control=Control.getInstance(this);
        this.idEstudiante = (EditText) findViewById(R.id.idEstudiante);
        this.nombreEstudiante = (EditText) findViewById(R.id.nombreEstudiante);
        this.apellido1 = (EditText) findViewById(R.id.apellido1);
        this.apellido2 = (EditText) findViewById(R.id.apellido2);
        this.edad = (EditText) findViewById(R.id.edad);
        this.guardar = (Button) findViewById(R.id.guardarEstudiante);

        // <-- jalar accion del intent y setearla
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            this.accion = extras.getInt("accion");
            this.mEstudiante=(Estudiante) extras.get("estudiante");
        }
        //  jalar accion del intent -->

        if(this.accion == 1) {

            this.guardar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(v.getContext().INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(edad.getWindowToken(), 0);
                    final View vi=v;
                    AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
                    alert.setTitle("Confirmacion");
                    alert.setMessage("Desea crear este Estudiante?");
                    alert.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {

                            //reiniciar los errores
                            nombreEstudiante.setError(null);
                            idEstudiante.setError(null);
                            apellido1.setError(null);
                            apellido2.setError(null);
                            edad.setError(null);
                            String ced=idEstudiante.getText().toString();
                            String nom=nombreEstudiante.getText().toString();
                            String ape1=apellido1.getText().toString();
                            String ape2=apellido2.getText().toString();
                            String eda=edad.getText().toString();

                            boolean cancel=false;
                            View focusView=null;

                            if (TextUtils.isEmpty(ced)) {
                                idEstudiante.setError("Cedula Vacio");
                                focusView = idEstudiante;
                                cancel = true;
                            }

                                if (TextUtils.isEmpty(nom)) {
                                    nombreEstudiante.setError("Nombre Vacio");
                                    focusView = nombreEstudiante;
                                    cancel = true;
                                }

                                if (TextUtils.isEmpty(ape1)) {
                                    apellido1.setError("Apellido 1 Vacio");
                                    focusView = apellido1;
                                    cancel = true;
                                }
                            if (TextUtils.isEmpty(ape2)) {
                                apellido2.setError("Apellido 2 Vacio");
                                focusView = apellido2;
                                cancel = true;
                            }

                                if (TextUtils.isEmpty(eda)) {
                                    edad.setError("Titulo Vacio");
                                    focusView = edad;
                                    cancel = true;
                                }


                                if (cancel) {
                                    focusView.requestFocus();
                                } else {
                                    if(control.agregarEstudiante(Integer.parseInt(ced),nom,ape1,ape2,Integer.parseInt(eda))){
                                        Intent intent = new Intent(FormEstudianteActivity.this, EstudiantesActivity.class);
                                        Toast.makeText(getApplicationContext(),"Estudiante agregado",Toast.LENGTH_SHORT).show();
                                        FormEstudianteActivity.this.startActivity(intent);
                                    }else Toast.makeText(getApplicationContext(),"Error, la cedula ya existe",Toast.LENGTH_SHORT).show();

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
            this.idEstudiante.setEnabled(false);
            this.guardar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(v.getContext().INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(edad.getWindowToken(), 0);
                    final View vi=v;
                    AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
                    alert.setTitle("Confirmacion");
                    alert.setMessage("Desea editar este Estudiante?");
                    alert.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {

                            //reiniciar los errores
                            nombreEstudiante.setError(null);
                            idEstudiante.setError(null);
                            apellido1.setError(null);
                            apellido2.setError(null);
                            edad.setError(null);
                            String ced=idEstudiante.getText().toString();
                            String nom=nombreEstudiante.getText().toString();
                            String ape1=apellido1.getText().toString();
                            String ape2=apellido2.getText().toString();
                            String eda=edad.getText().toString();

                            boolean cancel=false;
                            View focusView=null;

                            if (TextUtils.isEmpty(ced)) {
                                idEstudiante.setError("Cedula Vacio");
                                focusView = idEstudiante;
                                cancel = true;
                            }

                            if (TextUtils.isEmpty(nom)) {
                                nombreEstudiante.setError("Nombre Vacio");
                                focusView = nombreEstudiante;
                                cancel = true;
                            }

                            if (TextUtils.isEmpty(ape1)) {
                                apellido1.setError("Apellido 1 Vacio");
                                focusView = apellido1;
                                cancel = true;
                            }
                            if (TextUtils.isEmpty(ape2)) {
                                apellido2.setError("Apellido 2 Vacio");
                                focusView = apellido2;
                                cancel = true;
                            }

                            if (TextUtils.isEmpty(eda)) {
                                edad.setError("Titulo Vacio");
                                focusView = edad;
                                cancel = true;
                            }


                            if (cancel) {
                                focusView.requestFocus();
                            } else {
                                if(control.updateEstudiante(Integer.parseInt(ced),nom, ape1, ape2, Integer.parseInt(eda))){
                                    Toast.makeText(getApplicationContext(),"Estudiante editado",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(FormEstudianteActivity.this, EstudiantesActivity.class);
                                    FormEstudianteActivity.this.startActivity(intent);
                                }
                                else{
                                    Toast.makeText(getApplicationContext(),"Error al editar el estudiante",Toast.LENGTH_SHORT).show();
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
        nombreEstudiante.setText(mEstudiante.getNombre());
        idEstudiante.setText(Integer.toString(mEstudiante.getId()));
        edad.setText(Integer.toString(mEstudiante.getEdad()));
        apellido1.setText(mEstudiante.getApellido1());
        apellido2.setText(mEstudiante.getApellido2());
    }
}
