package com.codedev.proyectmovil.Fragments.Configuracion;

import android.content.SharedPreferences;
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
import com.codedev.proyectmovil.Fragments.FacultadesAll;
import com.codedev.proyectmovil.Fragments.Usuario.UsuarioAll;
import com.codedev.proyectmovil.R;
import com.codedev.proyectmovil.Utils.PreferencesUtil;

public class ConfiguracionMenu extends Fragment {

    private static final int ROL_ALUMNO = 1;
    CardView carFacultad, carCurso, carusuario, carReporte, carPeriodo;
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
        carPeriodo = view.findViewById(R.id.btnPeriodo);
        String rolStr = PreferencesUtil.getKey(requireContext(), "idRol");
        int idRol = -1;
        try {
            idRol = Integer.parseInt(rolStr);
        } catch (NumberFormatException ignored) {}

        final int ROL_ALUMNO = 2;
        if (idRol == ROL_ALUMNO) {
            carusuario.setVisibility(View.GONE);
        } else {
            carusuario.setVisibility(View.VISIBLE);
        }
        cargarListeners();
    }

    public void cargarListeners(){
        carFacultad.setOnClickListener(v->{
            cambiarFragmento(new FacultadesAll());
        });
        carCurso.setOnClickListener( v ->{
            cambiarFragmento(new CursoFacultadesAll());
        });
        carusuario.setOnClickListener(v -> {
            cambiarFragmento(new UsuarioAll());
        });
        carPeriodo.setOnClickListener( v ->{
            cambiarFragmento(new PeriodoAll());
        });
    }

    public void cambiarFragmento(Fragment fragmento){
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragmento);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}