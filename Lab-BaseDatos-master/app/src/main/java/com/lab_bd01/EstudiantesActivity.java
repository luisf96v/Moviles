package com.lab_bd01;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EstudiantesActivity extends AppCompatActivity {

    Button addEstudianteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estudiantes);

        addEstudianteBtn = (Button) findViewById(R.id.addEstudianteBtn);

        addEstudianteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EstudiantesActivity.this, FormEstudianteActivity.class);
                intent.putExtra("accion",1);
                EstudiantesActivity.this.startActivity(intent);
            }
        });



        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragmentEstudiantesContainer);
        if (fragment == null) {
            fragment = new EstudiantesFragment();
            fm.beginTransaction()
                    .add(R.id.fragmentEstudiantesContainer, fragment)
                    .commit();
        }
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Intent intent = new Intent(EstudiantesActivity.this, MainActivity.class);
        intent.putExtra("accion",1);
        EstudiantesActivity.this.startActivity(intent);
    }

}
