package com.codedev.proyectmovil.Fragments;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.codedev.proyectmovil.Adapters.CursosAdapter;
import com.codedev.proyectmovil.Helpers.Cursos.CursosDAO;
import com.codedev.proyectmovil.Models.CursosModel;
import com.codedev.proyectmovil.R;
import com.codedev.proyectmovil.Utils.ToastUtil;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class CursosAll extends Fragment {

    CursosDAO cursosDAO;
    RecyclerView recyclerView;
    List<CursosModel> list;
    CursosAdapter adapter;
    TextView txtTitulo;
    EditText edtNombre, edtCodigo;
    Button btnAgregar, btnGuardar;

    private int idFacultad =-1;

    public CursosAll(){}

    public static CursosAll newInstance(int idFacultad) {
        CursosAll fragment = new CursosAll();
        Bundle args = new Bundle();
        args.putInt("idFacultad", idFacultad);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.cursos_fragment_all, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cursosDAO = new CursosDAO(getContext());
        recyclerView = view.findViewById(R.id.recyclerCursos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        if (getArguments() != null) {
            idFacultad = getArguments().getInt("idFacultad", -1);
        }
        //list = cursosDAO.getCursos();
        list = cursosDAO.getCursosPorFacultad(idFacultad);
        adapter = new CursosAdapter(getContext(), list, new CursosAdapter.OnItemClickListener() {

            @Override
            public void onEditarClick(CursosModel cursoModel) {
                mostrarDialogEditar(cursoModel);
            }

            @Override
            public void onEliminarClick(CursosModel cursoModel) {
                new AlertDialog.Builder(requireContext()).setTitle("¿Eliminar curso?").setMessage("¿Estás seguro de que quieres eliminar a " + cursoModel.getNombre() + "?").setPositiveButton("Sí", (dialog, which) -> {
                    eliminarCursoConRevertir(cursoModel);
                    recargarLista();
                }).setNegativeButton("Cancelar", null).show();
            }
        });
        recyclerView.setAdapter(adapter);
        btnAgregar = view.findViewById(R.id.btnAgregarCurso);
        btnAgregar.setOnClickListener(v -> mostrarDialogNuevoCurso());
    }

    private void recargarLista() {
        list.clear();
        //list.addAll(cursosDAO.getCursos());
        list.addAll(cursosDAO.getCursosPorFacultad(idFacultad));
        adapter.notifyDataSetChanged();
    }

    private void mostrarDialogNuevoCurso() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View view = getLayoutInflater().inflate(R.layout.cursos_dialog, null);
        builder.setView(view);

        txtTitulo = view.findViewById(R.id.txtTitulo);
        edtNombre = view.findViewById(R.id.edtNombreCurso);
        edtCodigo = view.findViewById(R.id.edtCodigoCurso);
        btnGuardar = view.findViewById(R.id.btnGuardarCurso);
        txtTitulo.setText(R.string.cursos_opc_agregar);

        AlertDialog dialog = builder.create();

        btnGuardar.setOnClickListener(v -> {
            String nombre = edtNombre.getText().toString().trim();
            String codigo = edtCodigo.getText().toString().trim();

            if (!nombre.isEmpty() && !codigo.isEmpty()) {
                //CursosModel nuevo = new CursosModel(nombre, codigo, 1);
                CursosModel nuevo = new CursosModel(nombre, codigo,idFacultad, 1);

                boolean insertado = cursosDAO.addCursos(nuevo);
                if (insertado) {
                    ToastUtil.show(requireContext(), "Curso agregado correctamente", "success");
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

    private void mostrarDialogEditar(CursosModel cursosModel) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View view = getLayoutInflater().inflate(R.layout.cursos_dialog, null);
        builder.setView(view);

        txtTitulo = view.findViewById(R.id.txtTitulo);
        edtNombre = view.findViewById(R.id.edtNombreCurso);
        edtCodigo = view.findViewById(R.id.edtCodigoCurso);
        btnGuardar = view.findViewById(R.id.btnGuardarCurso);

        txtTitulo.setText(R.string.cursos_opc_editar);
        edtNombre.setText(cursosModel.getNombre());
        edtCodigo.setText(cursosModel.getCodigo());

        AlertDialog dialog = builder.create();

        btnGuardar.setOnClickListener(v -> {
            String nombre = edtNombre.getText().toString();
            String codigo = edtCodigo.getText().toString();

            cursosModel.setNombre(nombre);
            cursosModel.setCodigo(codigo);

            boolean actualizado = cursosDAO.updateCursos(cursosModel);
            if (actualizado) {
                ToastUtil.show(requireContext(), "Curso actualizado", "success");
                recargarLista();
            } else {
                ToastUtil.show(requireContext(), "Error al actualizar", "danger");
            }

            dialog.dismiss();
        });

        dialog.show();
    }
    private void eliminarCursoConRevertir(CursosModel cursosModel) {
        cursosDAO.deleteCursos(cursosModel.getId());
        recargarLista();

        Snackbar.make(requireView(), "Curso eliminado", Snackbar.LENGTH_LONG).setAction("Deshacer", v -> {
            cursosModel.setEstado(1);
            cursosDAO.updateCursos(cursosModel);
            recargarLista();
            ToastUtil.show(requireContext(), "Se recuperó el curso", "success"); // Usar requireContext()
        }).show();
    }

}