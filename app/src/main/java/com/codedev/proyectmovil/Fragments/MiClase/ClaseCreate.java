package com.codedev.proyectmovil.Fragments.MiClase;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.codedev.proyectmovil.Helpers.Clase.ClaseDAO;
import com.codedev.proyectmovil.Helpers.Cursos.CursosDAO;
import com.codedev.proyectmovil.Helpers.Facultad.FacultadDAO;
import com.codedev.proyectmovil.Models.ClaseModel;
import com.codedev.proyectmovil.Models.CursosModel;
import com.codedev.proyectmovil.Models.FacultadModel;
import com.codedev.proyectmovil.R;
import com.codedev.proyectmovil.Utils.ToastUtil;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class ClaseCreate extends Fragment {

    private TextInputEditText tietGrupo;
    private AutoCompleteTextView actvFacultad, actvCurso;
    private MaterialToolbar toolbar;
    private View btnGuardar;

    private FacultadDAO facultadDAO;
    private CursosDAO cursoDAO;
    private ClaseDAO claseDAO;

    private int idPeriodo;
    private int idClase = 0;
    private String accion = "crear";

    private List<FacultadModel> listaFacultades;
    private List<CursosModel> listaCursos;

    public ClaseCreate() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.clase_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        tietGrupo = v.findViewById(R.id.tiet_grupo_clase);
        actvFacultad = v.findViewById(R.id.actv_facultad_clase);
        actvCurso = v.findViewById(R.id.actv_curso_clase);
        toolbar = v.findViewById(R.id.toolbar_titulo);
        btnGuardar = v.findViewById(R.id.btnGuardarClase);

        facultadDAO = new FacultadDAO(requireContext());
        cursoDAO = new CursosDAO(requireContext());
        claseDAO = new ClaseDAO(requireContext());

        if (getArguments() != null) {
            idClase = getArguments().getInt("id", 0);
            accion = getArguments().getString("accion", "crear");
            idPeriodo = getArguments().getInt("idPeriodo");
        }

        cargarFacultades();

        if (accion.equals("editar") && idClase > 0) {
            cargarDatos();
            toolbar.setTitle("Editar Clase");
        } else {
            toolbar.setTitle("Crear Clase");
        }

        toolbar.setNavigationOnClickListener(v1 -> requireActivity().onBackPressed());

        actvFacultad.setOnItemClickListener((parent, view, position, id) -> {
            int idFacultad = listaFacultades.get(position).getId();
            cargarCursos(idFacultad);
        });

        btnGuardar.setOnClickListener(v1 -> guardarClase());
    }

    private void cargarDatos() {
        ClaseModel clase = claseDAO.getClaseById(idClase);
        tietGrupo.setText(clase.getGrupo());

        CursosModel curso = cursoDAO.getCursoById(clase.getIdCurso());
        
        int idFacultad = curso != null ? curso.getIdFacultad() : 0;
        int idxFac = getIndexFacultadById(idFacultad);
        actvFacultad.setText(listaFacultades.get(idxFac).getNombre(), false);
        cargarCursos(idFacultad);

        int idxCurso = getIndexCursoById(clase.getIdCurso());
        actvCurso.setText(listaCursos.get(idxCurso).getNombre(), false);
    }

    private void cargarFacultades() {
        listaFacultades = facultadDAO.getAllFacultad();
        List<String> nombres = new ArrayList<>();
        for (FacultadModel f : listaFacultades) nombres.add(f.getNombre());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, nombres);
        actvFacultad.setAdapter(adapter);
    }

    private void cargarCursos(int idFacultad) {
        listaCursos = cursoDAO.getCursosPorFacultad(idFacultad);
        List<String> nombres = new ArrayList<>();
        for (CursosModel c : listaCursos) nombres.add(c.getNombre());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, nombres);
        actvCurso.setAdapter(adapter);
    }

    private void guardarClase() {
        String grupo = tietGrupo.getText().toString().trim();
        String nombreFacultad = actvFacultad.getText().toString().trim();
        String nombreCurso = actvCurso.getText().toString().trim();

        if (TextUtils.isEmpty(grupo) || TextUtils.isEmpty(nombreFacultad) || TextUtils.isEmpty(nombreCurso)) {
            ToastUtil.show(requireContext(), "Complete todos los campos", "warning");
            return;
        }

        int idCurso = -1;
        for (CursosModel c : listaCursos) {
            if (c.getNombre().equals(nombreCurso)) {
                idCurso = c.getId();
                break;
            }
        }

        if (idCurso == -1) {
            ToastUtil.show(requireContext(), "Curso no válido", "error");
            return;
        }

        if (claseDAO.existeClaseEnPeriodo(idPeriodo, idCurso, grupo, idClase)) {
            ToastUtil.show(requireContext(), "Ya existe una clase con ese curso y grupo", "error");
            return;
        }

        ClaseModel clase = new ClaseModel();
        clase.setGrupo(grupo);
        clase.setIdCurso(idCurso);
        clase.setIdPeriodo(idPeriodo);

        if (accion.equals("crear")) {
            ClaseModel nueva = claseDAO.addClase(clase);
            if (nueva != null)
                ToastUtil.show(requireContext(), "Clase creada", "success");
        } else {
            clase.setId(idClase);
            clase.setEstado(1);
            if (claseDAO.updateClase(clase))
                ToastUtil.show(requireContext(), "Clase actualizada", "success");
        }

        requireActivity().onBackPressed();
    }


    public int getIndexCursoById(int idCurso) {
        for (int i = 0; i < listaCursos.size(); i++) {
            if (listaCursos.get(i).getId() == idCurso) {
                return i;
            }
        }
        return -1;
    }

    public int getIndexFacultadById(int idFacultad) {
        for (int i = 0; i < listaFacultades.size(); i++) {
            if (listaFacultades.get(i).getId() == idFacultad) {
                return i;
            }
        }
        return -1;
    }
}