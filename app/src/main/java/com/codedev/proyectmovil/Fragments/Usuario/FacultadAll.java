package com.codedev.proyectmovil.Fragments.Usuario;
import android.app.AlertDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codedev.proyectmovil.Adapters.FacultadAdapter;
import com.codedev.proyectmovil.Helpers.Facultad.FacultadDAO;
import com.codedev.proyectmovil.Models.FacultadModel;
import com.codedev.proyectmovil.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class FacultadAll extends Fragment implements FacultadAdapter.OnFacultadActionListener {
    private RecyclerView recyclerView;
    private FacultadAdapter adapter;
    private FacultadDAO facultadDAO;
    private FloatingActionButton fabAdd;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.facultad_fragment_all, container, false);
        recyclerView = view.findViewById(R.id.recycler_facultades);
        fabAdd = view.findViewById(R.id.fab_add_facultad);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        facultadDAO = new FacultadDAO(getContext());
        cargarFacultades();
        fabAdd.setOnClickListener(v -> mostrarDialogoAgregar());
        return view;
    }

    private void cargarFacultades() {
        List<FacultadModel> lista = facultadDAO.getFacultades();
        adapter = new FacultadAdapter(lista, this);
        recyclerView.setAdapter(adapter);
    }

    private boolean esNombreValido(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) return false;
        // Validar que no exista ya una facultad con ese nombre (ignorando mayúsculas/minúsculas)
        List<FacultadModel> lista = facultadDAO.getFacultades();
        for (FacultadModel f : lista) {
            if (f.getNombre().trim().equalsIgnoreCase(nombre.trim())) {
                return false;
            }
        }
        return true;
    }

    private void mostrarDialogoAgregar() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View dialogView = inflater.inflate(R.layout.facultad_dialog, null);
        final EditText edtNombre = dialogView.findViewById(R.id.edtNombreFacultad);
        final TextView txtTitulo = dialogView.findViewById(R.id.txtTituloFacultad);
        txtTitulo.setText("Agregar Facultad");
        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setView(dialogView)
                .create();
        dialogView.findViewById(R.id.btnGuardarFacultad).setOnClickListener(v -> {
            String nombre = edtNombre.getText().toString().trim();
            if (nombre.isEmpty()) {
                edtNombre.setError("El nombre es obligatorio");
            } else if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ ]+")) {
                edtNombre.setError("Solo letras y espacios");
            } else if (!esNombreValido(nombre)) {
                edtNombre.setError("Ya existe una facultad con ese nombre");
            } else {
                FacultadModel nueva = new FacultadModel(nombre, 1, "");
                facultadDAO.addFacultad(nueva);
                cargarFacultades();
                Toast.makeText(getContext(), "Facultad agregada", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void onEditar(FacultadModel facultad) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View dialogView = inflater.inflate(R.layout.facultad_dialog, null);
        final EditText edtNombre = dialogView.findViewById(R.id.edtNombreFacultad);
        final TextView txtTitulo = dialogView.findViewById(R.id.txtTituloFacultad);
        txtTitulo.setText("Editar Facultad");
        edtNombre.setText(facultad.getNombre());
        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setView(dialogView)
                .create();
        dialogView.findViewById(R.id.btnGuardarFacultad).setOnClickListener(v -> {
            String nombre = edtNombre.getText().toString().trim();
            if (nombre.isEmpty()) {
                edtNombre.setError("El nombre es obligatorio");
            } else if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ ]+")) {
                edtNombre.setError("Solo letras y espacios");
            } else if (!facultad.getNombre().equalsIgnoreCase(nombre) && !esNombreValido(nombre)) {
                edtNombre.setError("Ya existe una facultad con ese nombre");
            } else {
                facultad.setNombre(nombre);
                facultadDAO.updateFacultad(facultad);
                cargarFacultades();
                Toast.makeText(getContext(), "Facultad actualizada", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void onEliminar(FacultadModel facultad) {
        new AlertDialog.Builder(getContext())
                .setTitle("Eliminar Facultad")
                .setMessage("¿Estás seguro de eliminar esta facultad?")
                .setPositiveButton("Eliminar", (dialog, which) -> {
                    facultadDAO.deleteFacultad(facultad.getId());
                    cargarFacultades();
                    Toast.makeText(getContext(), "Facultad eliminada", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }
}