package com.codedev.proyectmovil.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.codedev.proyectmovil.R;

public class CursoFacultadesAll extends Fragment {

    private TextView txtFacultad1, txtFacultad2;

    public CursoFacultadesAll() {
        // Constructor vacío requerido
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cursos_facultades_fragment_all, container, false);

        txtFacultad1 = view.findViewById(R.id.txtFacultad1);
        txtFacultad2 = view.findViewById(R.id.txtFacultad2);

        // Asociar eventos de clic
        txtFacultad1.setOnClickListener(v -> abrirCursosDeFacultad(1));
        txtFacultad2.setOnClickListener(v -> abrirCursosDeFacultad(2));

        return view;
    }

    private void abrirCursosDeFacultad(int idFacultad) {
        // Reemplazar el fragmento actual con CursosAll, pasándole el idFacultad
        Fragment fragment = CursosAll.newInstance(idFacultad);
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null) // Permite volver con el botón "Atrás"
                .commit();
    }
}
