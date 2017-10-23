package com.lab_bd01;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lab_bd01.Modelo.Curso;
import com.lab_bd01.Modelo.Estudiante;

import java.util.ArrayList;


public class EstudiantesFragment extends Fragment {

    ArrayList<Estudiante> estudiantesList = new ArrayList<Estudiante>();
    RecyclerView estudiantesRecycler;
    Control control;
    Button buscarEstudianteBtn;
    EditText consultaText;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        control=Control.getInstance(getContext());
        initializeList();
        getActivity().setTitle("Lista de Estudiantes");
    }

    public void initializeList() {
        estudiantesList.clear();
        estudiantesList=control.getListaEstudiantes();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.estudiante_card, container, false);
        estudiantesRecycler = (RecyclerView) view.findViewById(R.id.estudianteCardsView);
        estudiantesRecycler.setHasFixedSize(true);
        LinearLayoutManager MyLayoutManager = new LinearLayoutManager(getActivity());
        MyLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        buscarEstudianteBtn = (Button) view.findViewById(R.id.buscarEstudianteBtn);
        consultaText = (EditText) view.findViewById(R.id.consultaEstudianteText);

        buscarEstudianteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String consulta = consultaText.getText().toString();
                estudiantesList.clear();
                estudiantesList=control.getEstudiantesLike(consulta);

                LinearLayoutManager MyLayoutManager = new LinearLayoutManager(getActivity());
                MyLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                if (estudiantesList.size() >= 0 & estudiantesRecycler != null) {
                    estudiantesRecycler.setAdapter(new ListaDeEstudiantesAdapter(estudiantesList));
                }
                estudiantesRecycler.setLayoutManager(MyLayoutManager);
            }
        });


        if (estudiantesList.size() > 0 & estudiantesRecycler != null) {
            estudiantesRecycler.setAdapter(new EstudiantesFragment.ListaDeEstudiantesAdapter(estudiantesList));
        }
        estudiantesRecycler.setLayoutManager(MyLayoutManager);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public class ListaDeEstudiantesAdapter extends RecyclerView.Adapter<EstudiantesFragment.EstudianteHolder> {
        private ArrayList<Estudiante> list;

        public ListaDeEstudiantesAdapter(ArrayList<Estudiante> Data) {
            list = Data;
        }

        @Override
        public EstudiantesFragment.EstudianteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // create a new view
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.estudiante_items, parent, false);
            EstudiantesFragment.EstudianteHolder estudianteHolder = new EstudiantesFragment.EstudianteHolder(view);
            return estudianteHolder;
        }

        @Override
        public void onBindViewHolder(final EstudiantesFragment.EstudianteHolder holder, int position) {
            System.out.println("ID DEL ESTUDIANTE ES:" + list.get(position).getId());
            System.out.println("INPUT ID TEXT: " + holder.idText.getText());
            holder.idText.setText(String.valueOf(list.get(position).getId()));
            holder.nombreText.setText(list.get(position).getNombre());
            holder.apellido1Text.setText(list.get(position).getApellido1());
            holder.apellido2Text.setText(list.get(position).getApellido2());
            holder.edadText.setText(String.valueOf(list.get(position).getEdad()));

        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    public class EstudianteHolder extends RecyclerView.ViewHolder {

        public TextView idText;
        public TextView nombreText;
        public TextView apellido1Text;
        public TextView apellido2Text;
        public TextView edadText;
        public TextView cursosEstudianteText;

        public ImageView edit;
        public ImageView delete;

        public EstudianteHolder(View v) {
            super(v);
            idText = (TextView) v.findViewById(R.id.idEstudianteText);
            nombreText = (TextView) v.findViewById(R.id.nombreEstudianteText);
            apellido1Text = (TextView) v.findViewById(R.id.apellido1EstudianteText);
            apellido2Text = (TextView) v.findViewById(R.id.apellido2EstudianteText);
            edadText = (TextView) v.findViewById(R.id.edadEstudianteText);
            cursosEstudianteText=(TextView) v.findViewById(R.id.cursosDeEstudiante);

            edit = (ImageView) v.findViewById(R.id.editEstudiante);
            delete = (ImageView) v.findViewById(R.id.deleteEstudiante);

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    Toast.makeText(getActivity(), "Editar Estudiante...", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), FormEstudianteActivity.class);
                    if (position != RecyclerView.NO_POSITION) {
                        Estudiante estudiante = estudiantesList.get(position);
                        intent.putExtra("accion",2);
                        intent.putExtra("estudiante",estudiante);
                        EstudiantesFragment.this.startActivity(intent);
                    }



                }
            });
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        int position=getAdapterPosition();

                        if(control.deleteEstudiante(estudiantesList.get(position).getId())){
                            Toast.makeText(getActivity(), "Estudiante Eliminado...", Toast.LENGTH_SHORT).show();
                            getActivity().finish();
                            startActivity(getActivity().getIntent());
                        }
                        else{
                            Toast.makeText(getActivity(), "Elimine los cursos asociados al estudiante antes de continuar", Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e){
                        Toast.makeText(getActivity(), "Error al eliminar el estudiante", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            cursosEstudianteText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builderSingle = new AlertDialog.Builder(getActivity());
                    builderSingle.setTitle("Cursos del estudiante");
                    final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_item);
                    int position=getAdapterPosition();
                    ArrayList<Curso> curEst=control.getCursosDeEstudiante(estudiantesList.get(position).getId());
                    for(Curso c : curEst){
                        arrayAdapter.add(c.getNombre());
                    }

                    builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builderSingle.show();

                }
            });

        }
    }


}
