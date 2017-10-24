package com.lab_bd01;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lab_bd01.Modelo.Curso;

import java.util.ArrayList;


public class CursosFragment extends Fragment {

    ArrayList<Curso> cursosList = new ArrayList<>();
    RecyclerView cursosRecycler;
    Control control;
    Button buscarCursoBtn;
    EditText consultaText;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        control=Control.getInstance(getContext());
        initializeList();
        getActivity().setTitle("Lista de Cursos");

    }

    public void initializeList() {
        cursosList.clear();
        cursosList=control.getListaCursos();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.curso_card, container, false);
        cursosRecycler = (RecyclerView) view.findViewById(R.id.cursoCardsView);
        cursosRecycler.setHasFixedSize(true);
        LinearLayoutManager MyLayoutManager = new LinearLayoutManager(getActivity());
        MyLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        buscarCursoBtn = (Button) view.findViewById(R.id.buscarCursoBtn);
        consultaText = (EditText) view.findViewById(R.id.consultaCursoText);

        buscarCursoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String consulta = consultaText.getText().toString();
                cursosList.clear();
                cursosList=control.getCursosLike(consulta);

                LinearLayoutManager MyLayoutManager = new LinearLayoutManager(getActivity());
                MyLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                if (cursosList.size() >= 0 & cursosRecycler != null) {
                    cursosRecycler.setAdapter(new ListaDeCursosAdapter(cursosList));
                }
                cursosRecycler.setLayoutManager(MyLayoutManager);
            }
        });


        if (cursosList.size() > 0 & cursosRecycler != null) {
            cursosRecycler.setAdapter(new ListaDeCursosAdapter(cursosList));
        }
        cursosRecycler.setLayoutManager(MyLayoutManager);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public class ListaDeCursosAdapter extends RecyclerView.Adapter<CursoHolder> {
        private ArrayList<Curso> list;

        public ListaDeCursosAdapter(ArrayList<Curso> Data) {
            list = Data;
        }

        @Override
        public CursoHolder onCreateViewHolder(ViewGroup parent,int viewType) {
            // create a new view
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.curso_items, parent, false);
            CursoHolder cursoholder = new CursoHolder(view);
            return cursoholder;
        }

        @Override
        public void onBindViewHolder(final CursoHolder holder, int position) {
            System.out.println("ID DEL CURSO ES:" + list.get(position).getId());
            System.out.println("INPUT ID TEXT: " + holder.idText.getText());
            holder.idText.setText(String.valueOf(list.get(position).getId()));
            holder.nombreText.setText(list.get(position).getNombre());
            holder.descripcionText.setText(list.get(position).getDescripcion());
            holder.creditosText.setText(String.valueOf(list.get(position).getCreditos()));
           // holder.estudiante.setText(list.get(position).getEstudiante().getNombre());
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    public class CursoHolder extends RecyclerView.ViewHolder {

        public TextView idText;
        public TextView nombreText;
        public TextView descripcionText;
        public TextView creditosText;
        public TextView estudiante;

        public ImageView edit;
        public ImageView delete;

        public CursoHolder(View v) {
            super(v);
            idText = (TextView) v.findViewById(R.id.idCursoText);
            nombreText = (TextView) v.findViewById(R.id.nombreCursoText);
            descripcionText = (TextView) v.findViewById(R.id.descripcionCursoText);
            creditosText = (TextView) v.findViewById(R.id.creditosCursoText);
           // estudiante=(TextView) v.findViewById(R.id.estudianteCurso);

            edit = (ImageView) v.findViewById(R.id.editCurso);
            delete = (ImageView) v.findViewById(R.id.deleteCurso);

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position=getAdapterPosition();

                    if (position != RecyclerView.NO_POSITION) {
                        Intent intent = new Intent(getActivity(), FormCursoActivity.class);
                        Curso curso = cursosList.get(position);
                        intent.putExtra("accion",2);
                        intent.putExtra("curso",curso);
                        CursosFragment.this.startActivity(intent);
                    }
                }
            });
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        int position=getAdapterPosition();
                        control.deletecurso(cursosList.get(position).getId());
                        Toast.makeText(getActivity(), "Curso Eliminado...", Toast.LENGTH_SHORT).show();
                        getActivity().finish();
                        startActivity(getActivity().getIntent());
                    }catch (Exception e){
                        Toast.makeText(getActivity(), "Error al eliminar el curso", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }


}
