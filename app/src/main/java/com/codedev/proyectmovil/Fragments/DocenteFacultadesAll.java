package com.codedev.proyectmovil.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codedev.proyectmovil.Adapters.FacultadAdapter;
import com.codedev.proyectmovil.Helpers.Facultad.FacultadDAO;
import com.codedev.proyectmovil.Models.FacultadModel;
import com.codedev.proyectmovil.R;

import java.util.List;

public class DocenteFacultadesAll extends Fragment{

    private FacultadDAO facultadDAO;
    private RecyclerView recyclerFacultades;
    private FacultadAdapter adapter;
    private List<FacultadModel> listaFacultades;

    public DocenteFacultadesAll(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.cursos_facultades_fragment_all, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        facultadDAO = new FacultadDAO(requireContext());
        recyclerFacultades = view.findViewById(R.id.recyclerFacultades);
        recyclerFacultades.setLayoutManager(new LinearLayoutManager(requireContext()));

        listaFacultades = facultadDAO.getAllFacultad();

        adapter = new FacultadAdapter(
                requireContext(),
                listaFacultades,
                new FacultadAdapter.OnItemClickListener() {
                    @Override
                    public void onEditarClick(FacultadModel facultad) {
                    }

                    @Override
                    public void onEliminarClick(FacultadModel facultad) {
                    }

                    public void onItemClick(FacultadModel facultad) {
                        // Cuando hacen clic en la facultad, abrimos CursosAll
                        Fragment DocentesFragment = PersonaAll.newInstance(facultad.getId());
                        requireActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container, DocentesFragment)
                                .addToBackStack(null)
                                .commit();
                    }
                }
        );

        recyclerFacultades.setAdapter(adapter);
    }

}
