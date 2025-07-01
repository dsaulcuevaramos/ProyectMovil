package com.codedev.proyectmovil.Fragments.Configuracion;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codedev.proyectmovil.Fragments.CursoFacultadesAll;
import com.codedev.proyectmovil.Fragments.Usuario.UsuarioAll;
import com.codedev.proyectmovil.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConfiguracionMenu#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConfiguracionMenu extends Fragment {
    CardView carFacultad, carCurso, carusuario, carReporte;
    public ConfiguracionMenu() {
    }

    public static ConfiguracionMenu newInstance(String param1, String param2) {
        ConfiguracionMenu fragment = new ConfiguracionMenu();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.configuracion_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        carFacultad = view.findViewById(R.id.btnGestionFacultades);
        carCurso = view.findViewById(R.id.btnGestionCursos);
        carusuario = view.findViewById(R.id.btnGestionUsuarios);
        carReporte = view.findViewById(R.id.btnReportes);

        cargarListeners();
    }

    public void cargarListeners(){
        carusuario.setOnClickListener(v -> {
            cambiarFragmento(new UsuarioAll());
        });
        carCurso.setOnClickListener( v ->{
            cambiarFragmento(new CursoFacultadesAll());
        });
//        Agregar el resto de opciones
    }

    public void cambiarFragmento(Fragment fragmento){
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragmento);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}