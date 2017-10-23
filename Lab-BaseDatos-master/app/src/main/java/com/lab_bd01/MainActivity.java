package com.lab_bd01;

        import android.content.Intent;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteException;
        import android.support.v4.app.Fragment;
        import android.support.v4.app.FragmentManager;
        import android.support.v4.app.FragmentTransaction;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    Button btnCrudEst;
    Button btnCrudCur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("Cursos y Estudiantes");

        btnCrudCur=(Button) findViewById(R.id.botonCrudCursos);
        btnCrudEst=(Button) findViewById(R.id.botonCrudEstudiantes);


        btnCrudCur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CursosActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });


        btnCrudEst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EstudiantesActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

    }
    @Override
    public void onBackPressed()
    {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}
