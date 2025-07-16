package com.codedev.proyectmovil.Fragments.Asistencia;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codedev.proyectmovil.Adapters.CursosAdapter;
import com.codedev.proyectmovil.Helpers.Cursos.CursosDAO;
import com.codedev.proyectmovil.Models.CursosModel;
import com.codedev.proyectmovil.R;

import java.util.List;

public class CursoList extends Fragment {

    CursosDAO cursosDAO;
    private RecyclerView recyclerACursos;
    private CursosAdapter adapter;
    private List<CursosModel> listaACursos;

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

        listaACursos = cursosDAO.getCursos();

        adapter = new CursosAdapter(
                requireContext(),
                listaACursos,
                new CursosAdapter.OnItemClickListener() {
                    @Override
                    public void onEditarClick(CursosModel cursos) {
                    }
                    @Override
                    public void onEliminarClick(CursosModel cursos) {
                    }

                    public void onItemClick(CursosModel cursos) {
                        Fragment AsitenciasFragment = AsistenciaList.newInstance(cursos.getId());
                        requireActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container, AsitenciasFragment)
                                .addToBackStack(null)
                                .commit();
                    }
                }
        );
        recyclerACursos.setAdapter(adapter);

    }

}