package com.codedev.proyectmovil.Fragments;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codedev.proyectmovil.R;

public class CursoFacultadesAll extends Fragment {
    TextView txtFacultad1, txtFacultad2;

    public CursoFacultadesAll(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cursos_facultades_fragment_all, container, false);

        txtFacultad1 = view.findViewById(R.id.txtFacultad1);
        txtFacultad2 = view.findViewById(R.id.txtFacultad2);

        txtFacultad1.setOnClickListener(v -> abrirCursosDeFacultad(1));
        txtFacultad2.setOnClickListener(v -> abrirCursosDeFacultad(2));

        return view;
    }

    private void abrirCursosDeFacultad(int idFacultad) {
        Fragment fragment = CursosAll.newInstance(idFacultad);
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

}
