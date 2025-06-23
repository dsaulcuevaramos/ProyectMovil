package com.codedev.proyectmovil.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codedev.proyectmovil.Adapters.AlumnoAdapter;
import com.codedev.proyectmovil.Helpers.Alumno.AlumnoDAO;
import com.codedev.proyectmovil.Helpers.Usuario.UsuarioDAO;
import com.codedev.proyectmovil.Models.AlumnoModel;
import com.codedev.proyectmovil.R;
import com.codedev.proyectmovil.Utils.ToastUtil;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class AlumnosAll extends Fragment {

    AlumnoDAO alumnoDAO;
    RecyclerView recyclerUsuarios;
    List<AlumnoModel> listaAlumno;
    AlumnoAdapter adapterAlumno;
    TextView txtTitulo;

    EditText edtCodigo;
    EditText edtNombre;
    EditText edtCorreo;

    Button btnAgregar, btnGuardar;


    public static AlumnosAll newInstance() {
        return new AlumnosAll();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.alumnos_fragment_all, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        alumnoDAO = new AlumnoDAO(getContext());
        recyclerUsuarios = view.findViewById(R.id.recyclerAlumnos);
        recyclerUsuarios.setLayoutManager(new LinearLayoutManager(getContext()));

        listaAlumno = alumnoDAO.getAlumnos();
        adapterAlumno = new AlumnoAdapter(getContext(), listaAlumno, new AlumnoAdapter.OnItemClickListener() { // Usa getContext()

            @Override
            public void onEditarClick(AlumnoModel alumno) {
                mostrarDialogEditar(alumno);
            }

            @Override
            public void onEliminarClick(AlumnoModel alumno) {
                new AlertDialog.Builder(requireContext()).setTitle("¿Eliminar usuario?").setMessage("¿Estás seguro de que quieres eliminar a " + alumno.getNombre() + "?").setPositiveButton("Sí", (dialog, which) -> {
                    eliminarAlumnoConRevertir(alumno);
                    recargarLista();
                }).setNegativeButton("Cancelar", null).show();
            }
        });
        recyclerUsuarios.setAdapter(adapterAlumno);

        btnAgregar = view.findViewById(R.id.btnAgregarAlumno);
        btnAgregar.setOnClickListener(v -> mostrarDialogNuevoAlumno());
    }

    private void recargarLista() {
        listaAlumno.clear();
        listaAlumno.addAll(alumnoDAO.getAlumnos());
        adapterAlumno.notifyDataSetChanged();
    }

    private void mostrarDialogNuevoAlumno() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View view = getLayoutInflater().inflate(R.layout.alumno_dialog, null);
        builder.setView(view);

        txtTitulo = view.findViewById(R.id.txtTitulo);
        edtCodigo = view.findViewById(R.id.edtNuevoCodigo);
        edtNombre = view.findViewById(R.id.edtNuevoNombre);
        edtCorreo = view.findViewById(R.id.edtNuevoCorreo);
        btnGuardar = view.findViewById(R.id.btnGuardarNuevo);
        txtTitulo.setText(R.string.alumno_opc_agregar);

        AlertDialog dialog = builder.create();

        btnGuardar.setOnClickListener(v -> {
            String codigo = edtCodigo.getText().toString().trim();
            String nombre = edtNombre.getText().toString().trim();
            String correo = edtCorreo.getText().toString().trim();


            if (!nombre.isEmpty() && !correo.isEmpty() && !codigo.isEmpty()) {
                AlumnoModel nuevo = new AlumnoModel(codigo, nombre, correo, 1);

                boolean insertado = alumnoDAO.addAlumno(nuevo);
                if (insertado) {
                    ToastUtil.show(requireContext(), "Usuario agregado", "success");
                    recargarLista();
                    dialog.dismiss();
                } else {
                    ToastUtil.show(requireContext(), "Error al agregar", "danger");
                }
            } else {
                ToastUtil.show(requireContext(), "Completa todos los campos", "danger");
            }
        });

        dialog.show();
    }

    private void mostrarDialogEditar(AlumnoModel alumno) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View view = getLayoutInflater().inflate(R.layout.alumno_dialog, null);
        builder.setView(view);

        txtTitulo = view.findViewById(R.id.txtTitulo);
        edtCodigo = view.findViewById(R.id.edtNuevoCodigo);
        edtNombre = view.findViewById(R.id.edtNuevoNombre);
        edtCorreo = view.findViewById(R.id.edtNuevoCorreo);
        btnGuardar = view.findViewById(R.id.btnGuardarNuevo);

        txtTitulo.setText(R.string.alumno_opc_editar);
        edtCodigo.setText(alumno.getCodigo());
        edtNombre.setText(alumno.getNombre());
        edtCorreo.setText(alumno.getCorreo());


        AlertDialog dialog = builder.create();

        btnGuardar.setOnClickListener(v -> {
            String nuevoCodigo = edtCodigo.getText().toString();
            String nuevoNombre = edtNombre.getText().toString();
            String nuevoCorreo = edtCorreo.getText().toString();

            alumno.setCodigo(nuevoCodigo);
            alumno.setNombre(nuevoNombre);
            alumno.setCorreo(nuevoCorreo);


            boolean actualizado = alumnoDAO.updateAlumno(alumno);
            if (actualizado) {
                ToastUtil.show(requireContext(), "Usuario actualizado", "success");
                recargarLista();
            } else {
                ToastUtil.show(requireContext(), "Error al actualizar", "danger");
            }

            dialog.dismiss();
        });

        dialog.show();
    }

    private void eliminarAlumnoConRevertir(AlumnoModel alumno) {
        alumnoDAO.deleteAlumno(alumno.getId());
        recargarLista();

        Snackbar.make(requireView(), "Usuario eliminado", Snackbar.LENGTH_LONG).setAction("Deshacer", v -> {
            alumno.setEstado(1);
            alumnoDAO.updateAlumno(alumno);
            recargarLista();
            ToastUtil.show(requireContext(), "Se recuperó el usuario", "success"); // Usar requireContext()
        }).show();
    }


}
