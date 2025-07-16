package com.codedev.proyectmovil.Fragments.Asistencia;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codedev.proyectmovil.Adapters.CursoClaseAdapter;
import com.codedev.proyectmovil.Adapters.CursosAdapter;
import com.codedev.proyectmovil.Helpers.Cursos.CursosDAO;
import com.codedev.proyectmovil.Models.CursosModel;
import com.codedev.proyectmovil.R;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;

public class CursoList extends Fragment {

    CursosDAO cursosDAO;
    private RecyclerView recyclerACursos;
    private CursoClaseAdapter adapter;
    private List<CursosModel> listaACursos;
    MaterialToolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.asistencia_curso_list_fragment_all, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cursosDAO = new CursosDAO(requireContext());
        recyclerACursos = view.findViewById(R.id.recyclerAsistenciaCursos);
        recyclerACursos.setLayoutManager(new LinearLayoutManager(requireContext()));

        toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);

        listaACursos = cursosDAO.getCursos();

        adapter = new CursoClaseAdapter(
                requireContext(),
                listaACursos,
                new CursoClaseAdapter.OnItemClickListener() {

                    public void onItemClick(CursosModel cursos) {
                        Fragment ClaseFragment = ClaseList.newInstance(cursos.getId());
                        requireActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container, ClaseFragment)
                                .addToBackStack(null)
                                .commit();
                    }
                }
        );
        recyclerACursos.setAdapter(adapter);

    }

}