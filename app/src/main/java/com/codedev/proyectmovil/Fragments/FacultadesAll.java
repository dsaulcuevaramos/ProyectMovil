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
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codedev.proyectmovil.Adapters.FacultadAdapter;
import com.codedev.proyectmovil.Helpers.Facultad.FacultadDAO;
import com.codedev.proyectmovil.Models.FacultadModel;
import com.codedev.proyectmovil.R;
import com.codedev.proyectmovil.Utils.ToastUtil;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class FacultadesAll extends Fragment {

    FacultadDAO facultadDAO;
    RecyclerView recyclerFacultad;
    List<FacultadModel> listaFacultad;
    FacultadAdapter adapter;
    TextView txtTitulo;
    EditText edtCodigo, edtNombre;
    Button btnGuardar;
    FloatingActionButton btnAgregar;
    MaterialToolbar toolbar;

    public FacultadesAll(){}


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.facultad_fragment_all, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        facultadDAO = new FacultadDAO(getContext());
        recyclerFacultad = view.findViewById(R.id.recyclerFacultad);
        recyclerFacultad.setLayoutManager(new LinearLayoutManager(getContext()));

        toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);

        listaFacultad = facultadDAO.getAllFacultad();
        adapter = new FacultadAdapter(getContext(), listaFacultad, new FacultadAdapter.OnItemClickListener() {

            @Override
            public void onEditarClick(FacultadModel facultad) {
                mostrarDialogEditar(facultad);
            }

            @Override
            public void onEliminarClick(FacultadModel facultad) {
                new AlertDialog.Builder(requireContext()).setTitle("¿Eliminar Facultad?").setMessage("¿Estás seguro de que quieres eliminar a " + facultad.getNombre() + "?").setPositiveButton("Sí", (dialog, which) -> {
                    eliminarFacultadConRevertir(facultad);
                    recargarLista();
                }).setNegativeButton("Cancelar", null).show();
            }

            @Override
            public void onItemClick(FacultadModel facultad) {
                
            }
        });
        recyclerFacultad.setAdapter(adapter);

        btnAgregar = view.findViewById(R.id.btnAgregarFacultad);
        btnAgregar.setOnClickListener(v -> mostrarDialogNuevaFacultad());

    }

    private void recargarLista() {
        listaFacultad.clear();
        listaFacultad.addAll(facultadDAO.getAllFacultad());
        adapter.notifyDataSetChanged();
    }

    private void mostrarDialogNuevaFacultad() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View view = getLayoutInflater().inflate(R.layout.facultad_dialog, null);
        builder.setView(view);

        txtTitulo = view.findViewById(R.id.txtTituloFacultad);
        edtCodigo = view.findViewById(R.id.edtNuevoCodigoFacultad);
        edtNombre = view.findViewById(R.id.edtNuevoNombreFacultad);
        btnGuardar = view.findViewById(R.id.btnGuardarFacultad);
        txtTitulo.setText(R.string.facultad_opc_agregar);

        AlertDialog dialog = builder.create();

        btnGuardar.setOnClickListener(v -> {
            String codigo   = edtCodigo.getText().toString().trim();
            String nombre   = edtNombre.getText().toString().trim();

            if (!nombre.isEmpty() && !codigo.isEmpty()) {
                FacultadModel nuevaFacultad = new FacultadModel(codigo,nombre, 1);

                FacultadModel insertado = facultadDAO.addFacultad(nuevaFacultad);
                if (insertado != null) {
                    ToastUtil.show(requireContext(), "Persona agregada correctamente", "success");
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



    private void mostrarDialogEditar(FacultadModel facultad) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View view = getLayoutInflater().inflate(R.layout.facultad_dialog, null);
        builder.setView(view);

        txtTitulo = view.findViewById(R.id.txtTituloFacultad);
        edtCodigo = view.findViewById(R.id.edtNuevoCodigoFacultad);
        edtNombre = view.findViewById(R.id.edtNuevoNombreFacultad);
        btnGuardar = view.findViewById(R.id.btnGuardarFacultad);

        txtTitulo.setText(R.string.persona_opc_editar);
        edtCodigo.setText(facultad.getCodigo());
        edtNombre.setText(facultad.getNombre());

        AlertDialog dialog = builder.create();

        btnGuardar.setOnClickListener(v -> {
            String nuevoCodigo = edtCodigo.getText().toString();
            String nuevoNombre = edtNombre.getText().toString();

            facultad.setCodigo(nuevoCodigo);
            facultad.setNombre(nuevoNombre);

            boolean actualizado = facultadDAO.updateFacultad(facultad);
            if (actualizado) {
                ToastUtil.show(requireContext(), "Persona actualizada!", "success");
                recargarLista();
            } else {
                ToastUtil.show(requireContext(), "Error al actualizar!", "danger");
            }

            dialog.dismiss();
        });

        dialog.show();
    }

    private void eliminarFacultadConRevertir(FacultadModel facultad) {
        facultadDAO.deleteFacultad(facultad.getId());
        recargarLista();

        Snackbar.make(requireView(), "Persona eliminado", Snackbar.LENGTH_LONG).setAction("Deshacer", v -> {
            facultad.setEstado(1);
            facultadDAO.updateFacultad(facultad);
            recargarLista();
            ToastUtil.show(requireContext(), "Se recuperó la persona", "success"); // Usar requireContext()
        }).show();
    }

}
