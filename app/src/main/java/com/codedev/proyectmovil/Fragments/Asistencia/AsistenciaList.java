package com.codedev.proyectmovil.Fragments.Asistencia;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codedev.proyectmovil.Adapters.AsistenciaAdapter;
import com.codedev.proyectmovil.Helpers.Asistencia.AsistenciaDAO;
import com.codedev.proyectmovil.Models.AsistenciaModel;
import com.codedev.proyectmovil.Models.CursosModel;
import com.codedev.proyectmovil.R;

import java.util.List;

public class AsistenciaList extends Fragment {

    AsistenciaDAO asistenciaDAO;
    private RecyclerView recyclerAsistencia;
    private AsistenciaAdapter adapter;
    private List<AsistenciaModel> listaAsistencia;
    private int idCurso =-1;

    public AsistenciaList(){}

    public static AsistenciaList newInstance(int idCurso) {
        AsistenciaList fragment = new AsistenciaList();
        Bundle args = new Bundle();
        args.putInt("idCurso", idCurso);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.asistencia_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        asistenciaDAO = new AsistenciaDAO(requireContext());
        recyclerAsistencia = view.findViewById(R.id.recyclerCursos);
        recyclerAsistencia.setLayoutManager(new LinearLayoutManager(requireContext()));

        if (getArguments() != null) {
            idCurso = getArguments().getInt("idCurso", -1);
        }

        listaAsistencia = asistenciaDAO.getAsistenciaByCurso(idCurso);
        adapter = new AsistenciaAdapter(getContext(), listaAsistencia, new AsistenciaAdapter.OnItemClickListener() {

            @Override
            public void onEditarClick(AsistenciaModel asistenciaModel) {
               // mostrarDialogEditar(cursoModel);
            }

            @Override
            public void onEliminarClick(AsistenciaModel asistenciaModel) {
                new AlertDialog.Builder(requireContext()).setTitle("¿Eliminar curso?").setMessage("¿Estás seguro de que quieres eliminar a " + asistenciaModel.getFecha()+ "?").setPositiveButton("Sí", (dialog, which) -> {
                    //eliminarCursoConRevertir(cursoModel);
                    //recargarLista();
                }).setNegativeButton("Cancelar", null).show();
            }

            @Override
            public void onItemClick(CursosModel cursoModel) {

            }
        });

        recyclerAsistencia.setAdapter(adapter);

    }
}