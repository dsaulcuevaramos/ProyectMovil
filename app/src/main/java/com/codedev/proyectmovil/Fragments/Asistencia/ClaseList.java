package com.codedev.proyectmovil.Fragments.Asistencia;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codedev.proyectmovil.Adapters.ClaseAdapter;
import com.codedev.proyectmovil.Adapters.ClaseAsistenciaAdapter;
import com.codedev.proyectmovil.Helpers.Asistencia.AsistenciaDAO;
import com.codedev.proyectmovil.Helpers.Clase.ClaseDAO;
import com.codedev.proyectmovil.Models.ClaseModel;
import com.codedev.proyectmovil.Models.Requests.ClaseRequest;
import com.codedev.proyectmovil.R;

import java.util.List;

public class ClaseList extends Fragment {

    AsistenciaDAO asistenciaDAO;
    private RecyclerView recyclerClase;
    private ClaseAsistenciaAdapter adapter;
    private List<ClaseModel> listaClase;
    private int idCurso =-1;


    public ClaseList(){}

    public static ClaseList newInstance(int idCurso) {
        ClaseList fragment = new ClaseList();
        Bundle args = new Bundle();
        args.putInt("idCurso", idCurso);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.clase_curso_fragment_all, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        asistenciaDAO = new AsistenciaDAO(requireContext());
        recyclerClase = view.findViewById(R.id.recyclerClasesCurso);
        recyclerClase.setLayoutManager(new LinearLayoutManager(requireContext()));

        if (getArguments() != null) {
            idCurso = getArguments().getInt("idCurso", -1);
        }

        listaClase = asistenciaDAO.getClaseByCurso(idCurso);
        adapter = new ClaseAsistenciaAdapter(getContext(),listaClase, new ClaseAsistenciaAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(ClaseModel claseModel) {
                Fragment AsitenciasFragment = AsistenciaList.newInstance(claseModel.getId());
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, AsitenciasFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        recyclerClase.setAdapter(adapter);

    }

}
