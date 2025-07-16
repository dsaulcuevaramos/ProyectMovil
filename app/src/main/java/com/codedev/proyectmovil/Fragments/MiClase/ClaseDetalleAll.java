package com.codedev.proyectmovil.Fragments.MiClase;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.codedev.proyectmovil.Adapters.DetalleClaseAdapter;
import com.codedev.proyectmovil.Adapters.PersonaSelectAdapter;
import com.codedev.proyectmovil.Helpers.Clase.ClaseDAO;
import com.codedev.proyectmovil.Helpers.Cursos.CursosDAO;
import com.codedev.proyectmovil.Helpers.DetalleClase.DetalleClaseDAO;
import com.codedev.proyectmovil.Models.ClaseModel;
import com.codedev.proyectmovil.Models.CursosModel;
import com.codedev.proyectmovil.Models.DetalleModel;
import com.codedev.proyectmovil.Models.Requests.DetalleClaseRequest;
import com.codedev.proyectmovil.R;
import com.codedev.proyectmovil.Utils.PreferencesUtil;
import com.codedev.proyectmovil.Utils.ToastUtil;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class ClaseDetalleAll extends Fragment {

    private ClaseDAO claseDAO;
    private CursosDAO cursoDAO;
    private DetalleClaseDAO detalleDAO;
    private RecyclerView recyclerDetalle;
    private List<DetalleClaseRequest> lista;
    private DetalleClaseAdapter adapter;
    private MaterialToolbar toolbar;
    private FloatingActionButton btnAgregar;
    private MaterialButton btnAgregarProfesor;
    private ImageButton btnEliminar;
    public static final int ROL_ALUMNO = 2;
    public static final int ROL_PROFESOR = 3;
    int idClase = 0;
    int idFacultad = 0;

    public ClaseDetalleAll() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.detalle_all_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String idClaseEnviado = PreferencesUtil.getKey(requireContext(), "idClase");
        if (idClaseEnviado.isEmpty()) {
            toClase();
        }

        idClase = Integer.parseInt(idClaseEnviado);

        detalleDAO = new DetalleClaseDAO(requireContext());
        cursoDAO = new CursosDAO(requireContext());
        claseDAO = new ClaseDAO(requireContext());

        ClaseModel clseSeleccionada = claseDAO.getClaseById(idClase);
        CursosModel cursoSelec = cursoDAO.getCursoById(clseSeleccionada.getIdCurso());
        idFacultad = cursoSelec != null ? cursoSelec.getIdFacultad() : -1;


        recyclerDetalle = view.findViewById(R.id.recyclerAlumnosClase);
        recyclerDetalle.setLayoutManager(new LinearLayoutManager(getContext()));

        toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_right);
        toolbar.setNavigationOnClickListener(v -> toClase());
        lista = detalleDAO.getDetalleClasePorRol(idClase, ROL_ALUMNO);

        adapter = new DetalleClaseAdapter(getContext(), lista, new DetalleClaseAdapter.OnItemClickListener() {
            @Override
            public void onQuitarDetalle(int id) {
                eliminarDetalle(id, ROL_ALUMNO);
            }
        });
        recyclerDetalle.setAdapter(adapter);

        btnAgregar = view.findViewById(R.id.btnAgregarAlumno);
        btnAgregar.setOnClickListener(v -> {
            mostrarDialogoAgregarPersona(ROL_ALUMNO);
        });
        btnAgregarProfesor = view.findViewById(R.id.btn_asignar_profesor);
        btnAgregarProfesor.setOnClickListener(v -> {
            mostrarDialogoAgregarPersona(ROL_PROFESOR);
        });
        mostrarProfesorAsignado();

    }

    private void mostrarDialogoAgregarPersona(int rolId) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.detalle_dialog, null);
        AlertDialog dialog = new AlertDialog.Builder(getContext()).setView(view).create();

        TextInputEditText edtBuscar = view.findViewById(R.id.tiet_search);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_search_results);
        TextView txtTitle = view.findViewById(R.id.dialog_title);
        txtTitle.setText(rolId == ROL_ALUMNO ? "Agregar Alumno" : "Asignar Profesor");

        List<DetalleClaseRequest> resultados = new ArrayList<>();
        PersonaSelectAdapter adapter = new PersonaSelectAdapter(getContext(), resultados, idPersona -> {

            if (rolId == ROL_PROFESOR && detalleDAO.existeProfesorActivo(idClase)) {
                ToastUtil.show(requireContext(), "Ya hay un profesor asignado. Debes quitarlo primero.", "info");
                return;
            }

            if (detalleDAO.existeEnClase(idClase, idPersona)) {
                ToastUtil.show(requireContext(), "La persona ya está registrada en esta clase.", "info");
                return;
            }

            if (detalleDAO.existeEnOtroGrupo(idClase, idPersona)) {
                ToastUtil.show(requireContext(), "La persona ya está registrada en otro grupo del mismo curso.", "danger");
                return;
            }

            DetalleModel detalle = new DetalleModel();
            detalle.setIdClase(idClase);
            detalle.setIdPersona(idPersona);
            detalle.setEstadoAsistencia(1);
            detalleDAO.addDetallePeriodo(detalle);
            dialog.dismiss();

            if (rolId == ROL_PROFESOR) mostrarProfesorAsignado();
            else recargarLista();
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        edtBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().isEmpty()) {
                    resultados.clear();
                    adapter.notifyDataSetChanged();
                    return;
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                resultados.clear();
                resultados.addAll(detalleDAO.buscarPersonasPorRol(rolId, idFacultad, s.toString()));
                adapter.notifyDataSetChanged();
            }
        });

        dialog.show();
    }

    private void mostrarProfesorAsignado() {
        View profesorItem = requireView().findViewById(R.id.profesor_asignado_item);
        TextView txtNoProfesor = requireView().findViewById(R.id.txt_no_profesor);

        List<DetalleClaseRequest> listaProfesores = detalleDAO.getDetalleClasePorRol(idClase, 3);

        if (!listaProfesores.isEmpty()) {
            txtNoProfesor.setVisibility(View.GONE);
            profesorItem.setVisibility(View.VISIBLE);

            DetalleClaseRequest profesor = listaProfesores.get(0);
            TextView nombre = profesorItem.findViewById(R.id.txtNombrePersona);
            TextView codigo = profesorItem.findViewById(R.id.txtCodigoPersona);
            ImageButton btnQuitar = profesorItem.findViewById(R.id.btn_quitar_persona);
            btnQuitar.setVisibility(View.VISIBLE);
            btnQuitar.setOnClickListener(v -> eliminarDetalle(profesor.getIdDetalle(), ROL_PROFESOR));
            nombre.setText(profesor.getNombrePersona());
            codigo.setText("Código: " + profesor.getCodigo());
        } else {
            txtNoProfesor.setVisibility(View.VISIBLE);
            profesorItem.setVisibility(View.GONE);
        }
    }

    private void eliminarDetalle(int id, int rol) {
        String msg = rol == ROL_ALUMNO ? "alumno" : "profesor asignado";
        String msg2 = rol == ROL_ALUMNO ? "Alumno" : "Profesor";
        new AlertDialog.Builder(requireContext())
                .setTitle("Confirmar eliminación")
                .setMessage("¿Estás seguro de que deseas quitar al " + msg + "?")
                .setPositiveButton("Sí, eliminar", (dialog, which) -> {
                    detalleDAO.deleteDetalleById(id);
                    ToastUtil.show(requireContext(), msg2 + " eliminado de la clase.", "success");

                    if (rol == ROL_PROFESOR) {
                        mostrarProfesorAsignado();
                    } else {
                        recargarLista();
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void recargarLista() {
        lista.clear();
        lista.addAll(detalleDAO.getDetalleClasePorRol(idClase, 2));
        adapter.notifyDataSetChanged();
    }

    private void toClase() {
        PreferencesUtil.deleteKey(requireContext(), "idClase");
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, new PeriodoList());
        transaction.addToBackStack(null);
        transaction.commit();
    }
}