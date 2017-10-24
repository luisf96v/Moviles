package com.lab_bd01;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CursosActivity extends AppCompatActivity {

    Button addCursoBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cursos);

        addCursoBtn = (Button) findViewById(R.id.addCursoBtn);

        addCursoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CursosActivity.this, FormCursoActivity.class);
                intent.putExtra("accion",1);
                CursosActivity.this.startActivity(intent);
            }
        });

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragmentCursosContainer);
        if (fragment == null) {
            fragment = new CursosFragment();
            fm.beginTransaction()
                    .add(R.id.fragmentCursosContainer, fragment)
                    .commit();
        }
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Intent intent = new Intent(CursosActivity.this, MainActivity.class);
        intent.putExtra("accion",1);
        CursosActivity.this.startActivity(intent);
    }
}
