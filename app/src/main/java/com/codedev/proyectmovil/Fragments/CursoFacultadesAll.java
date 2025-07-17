package com.codedev.proyectmovil.Fragments;

import android.os.Bundle;
import android.text.TextUtils;
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
import com.codedev.proyectmovil.Utils.PreferencesUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CursoFacultadesAll extends Fragment {

    private static final int ROL_ALUMNO = 2;
    private FacultadDAO facultadDAO;
    private RecyclerView recyclerFacultades;
    private FacultadAdapter adapter;
    private List<FacultadModel> listaFacultades;

    public CursoFacultadesAll(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
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

        // Carga dinámicamente todas las facultades activas
        String rolStr      = PreferencesUtil.getKey(requireContext(), "idRol");
        String facStr      = PreferencesUtil.getKey(requireContext(), "idFacultad");
        int idRol          = TextUtils.isEmpty(rolStr) ? -1 : Integer.parseInt(rolStr);
        int idFacultadUser = TextUtils.isEmpty(facStr) ? -1 : Integer.parseInt(facStr);

        // 2) Cargamos la lista según el rol
        if (idRol == ROL_ALUMNO && idFacultadUser > 0) {
            // Solo la facultad del alumno
            FacultadModel f = facultadDAO.getFacultadById(idFacultadUser);
            listaFacultades = f != null
                    ? Collections.singletonList(f)
                    : new ArrayList<>();
        } else {
            // Todos los que estén activos
            listaFacultades = facultadDAO.getAllFacultad();
        }

        adapter = new FacultadAdapter(
                requireContext(),
                listaFacultades,
                new FacultadAdapter.OnItemClickListener() {
                    @Override
                    public void onEditarClick(FacultadModel facultad) {
                        // No usamos editar aquí, pero podrías implementarlo
                    }

                    @Override
                    public void onEliminarClick(FacultadModel facultad) {
                        // Tampoco eliminamos en este listado
                    }

                    public void onItemClick(FacultadModel facultad) {
                        // Cuando hacen clic en la facultad, abrimos CursosAll
                        Fragment cursosFragment = CursosAll.newInstance(facultad.getId());
                        requireActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container, cursosFragment)
                                .addToBackStack(null)
                                .commit();
                    }
                }
        );

        recyclerFacultades.setAdapter(adapter);
    }
}
