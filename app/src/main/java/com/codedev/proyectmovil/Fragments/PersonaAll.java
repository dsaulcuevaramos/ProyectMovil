package com.codedev.proyectmovil.Fragments;

import android.annotation.SuppressLint;
import android.app.Person;
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

import com.codedev.proyectmovil.Adapters.PersonaAdapter;
import com.codedev.proyectmovil.Helpers.Persona.PersonaDAO;
import com.codedev.proyectmovil.Models.CursosModel;
import com.codedev.proyectmovil.Models.PersonaModel;
import com.codedev.proyectmovil.Models.UsuarioModel;
import com.codedev.proyectmovil.R;
import com.codedev.proyectmovil.Utils.ToastUtil;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class PersonaAll extends Fragment {

    PersonaDAO personaDAO;
    RecyclerView recyclerPersona;
    List<PersonaModel> listaPersona;
    PersonaAdapter adapter;
    TextView txtTitulo;
    EditText edtCodigo, edtNombre, edtApellido;
    Button btnGuardar;
    FloatingActionButton btnAgregar;
    MaterialToolbar toolbar;
    private int idFacultad =-1;

    public PersonaAll(){}

    public static PersonaAll newInstance(int idFacultad){
        PersonaAll fragment = new PersonaAll();
        Bundle args = new Bundle();
        args.putInt("idFacultad", idFacultad);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.persona_fragment_all, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        personaDAO = new PersonaDAO(getContext());
        recyclerPersona = view.findViewById(R.id.recyclerPersona);
        recyclerPersona.setLayoutManager(new LinearLayoutManager(getContext()));

        toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);

        if (getArguments() != null) {
            idFacultad = getArguments().getInt("idFacultad", -1);
        }

        listaPersona = personaDAO.getPersonas();
        adapter = new PersonaAdapter(getContext(), listaPersona, new PersonaAdapter.OnItemClickListener() {

            @Override
            public void onEditarClick(PersonaModel persona) {
                mostrarDialogEditar(persona);
            }

            @Override
            public void onEliminarClick(PersonaModel persona) {
                new AlertDialog.Builder(requireContext()).setTitle("¿Eliminar Persona?").setMessage("¿Estás seguro de que quieres eliminar a " + persona.getNombre() + "?").setPositiveButton("Sí", (dialog, which) -> {
                    eliminarPersonaConRevertir(persona);
                    recargarLista();
                }).setNegativeButton("Cancelar", null).show();
            }
        });
        recyclerPersona.setAdapter(adapter);

        btnAgregar = view.findViewById(R.id.btnAgregarUsuario);
        btnAgregar.setOnClickListener(v -> mostrarDialogNuevaPersona());

    }

    private void recargarLista() {
        listaPersona.clear();
        //list.addAll(cursosDAO.getCursos());
        listaPersona.addAll(personaDAO.getPersonas());
        adapter.notifyDataSetChanged();
    }

    private void mostrarDialogNuevaPersona() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View view = getLayoutInflater().inflate(R.layout.persona_dialog, null);
        builder.setView(view);

        txtTitulo = view.findViewById(R.id.txtTituloPersona);
        edtCodigo = view.findViewById(R.id.edtNuevoCodigoPersona);
        edtNombre = view.findViewById(R.id.edtNuevoNombrePersona);
        edtApellido = view.findViewById(R.id.edtNuevoApellidoPersona);
        btnGuardar = view.findViewById(R.id.btnGuardarPersona);
        txtTitulo.setText(R.string.persona_opc_agregar);

        AlertDialog dialog = builder.create();

        btnGuardar.setOnClickListener(v -> {
            String codigo   = edtCodigo.getText().toString().trim();
            String nombre   = edtNombre.getText().toString().trim();
            String apellido = edtApellido.getText().toString().trim();

            if (!nombre.isEmpty() && !codigo.isEmpty()) {
                //CursosModel nuevo = new CursosModel(nombre, codigo, 1);
                PersonaModel nuevaPersona = new PersonaModel(codigo,nombre,apellido,idFacultad, 1);

                boolean insertado = personaDAO.addPersona(nuevaPersona);
                if (insertado) {
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



    private void mostrarDialogEditar(PersonaModel persona) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View view = getLayoutInflater().inflate(R.layout.persona_dialog, null);
        builder.setView(view);

        txtTitulo = view.findViewById(R.id.txtTituloPersona);
        edtCodigo = view.findViewById(R.id.edtNuevoCodigoPersona);
        edtNombre = view.findViewById(R.id.edtNuevoNombrePersona);
        edtApellido = view.findViewById(R.id.edtNuevoApellidoPersona);
        btnGuardar = view.findViewById(R.id.btnGuardarPersona);

        txtTitulo.setText(R.string.persona_opc_editar);
        edtCodigo.setText(persona.getCodigo());
        edtNombre.setText(persona.getNombre());
        edtApellido.setText(persona.getApellido());

        AlertDialog dialog = builder.create();

        btnGuardar.setOnClickListener(v -> {
            String nuevoCodigo = edtCodigo.getText().toString();
            String nuevoNombre = edtNombre.getText().toString();
            String nuevoApellido = edtApellido.getText().toString();

            persona.setCodigo(nuevoCodigo);
            persona.setNombre(nuevoNombre);
            persona.setApellido(nuevoApellido);

            boolean actualizado = personaDAO.updatePersona(persona);
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

    private void eliminarPersonaConRevertir(PersonaModel persona) {
        personaDAO.deletePersona(persona.getId());
        recargarLista();

        Snackbar.make(requireView(), "Persona eliminado", Snackbar.LENGTH_LONG).setAction("Deshacer", v -> {
            persona.setEstado(1);
            personaDAO.updatePersona(persona);
            recargarLista();
            ToastUtil.show(requireContext(), "Se recuperó la persona", "success"); // Usar requireContext()
        }).show();
    }

}
