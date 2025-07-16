package com.codedev.proyectmovil.Fragments.Asistencia;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codedev.proyectmovil.Adapters.CursoClaseAdapter;
import com.codedev.proyectmovil.Adapters.CursosAdapter;
import com.codedev.proyectmovil.Helpers.Cursos.CursosDAO;
import com.codedev.proyectmovil.Models.CursosModel;
import com.codedev.proyectmovil.R;
import com.codedev.proyectmovil.Utils.PreferencesUtil;

import java.util.List;

public class CursoList extends Fragment {

    CursosDAO cursosDAO;
    private RecyclerView recyclerACursos;
    private CursoClaseAdapter adapter;
    private List<CursosModel> listaACursos;
    private static final int ROL_ALUMNO = 2;
    private int idRol;

    public CursoList(){}

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

        String rolStr = PreferencesUtil.getKey(requireContext(), "idRol");
        idRol = TextUtils.isEmpty(rolStr) ? -1 : Integer.parseInt(rolStr);
        boolean canEdit = (idRol != ROL_ALUMNO);

        cursosDAO = new CursosDAO(requireContext());
        recyclerACursos = view.findViewById(R.id.recyclerAsistenciaCursos);
        recyclerACursos.setLayoutManager(new LinearLayoutManager(requireContext()));

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