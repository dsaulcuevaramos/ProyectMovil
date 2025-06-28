package com.codedev.proyectmovil.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codedev.proyectmovil.Adapters.UsuarioAdapter;
import com.codedev.proyectmovil.Helpers.Usuario.UsuarioDAO;
import com.codedev.proyectmovil.Models.UsuarioModel;
import com.codedev.proyectmovil.R;
import com.codedev.proyectmovil.Utils.SnackbarUtil;
import com.codedev.proyectmovil.Utils.ToastUtil;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class UsuarioAll extends Fragment {
    UsuarioDAO usuarioDAO;
    RecyclerView recyclerUsuarios;
    List<UsuarioModel> lista;
    UsuarioAdapter adapter;
    TextView txtTitulo;

    EditText edtNombre;
    EditText edtCorreo;
    EditText edtContra;
    FloatingActionButton btnAgregar;
    Button btnGuardar;
    MaterialToolbar toolbar;

    public UsuarioAll() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.usuario_fragment_all, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        usuarioDAO = new UsuarioDAO(getContext());
        recyclerUsuarios = view.findViewById(R.id.recyclerUsuarios);
        recyclerUsuarios.setLayoutManager(new LinearLayoutManager(getContext()));

        toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);

        lista = usuarioDAO.getUsuarios();
        adapter = new UsuarioAdapter(getContext(), lista, new UsuarioAdapter.OnItemClickListener() { // Usa getContext()

            @Override
            public void onEditarClick(UsuarioModel usuario) {
                mostrarDialogEditar(usuario);
            }

            @Override
            public void onEliminarClick(UsuarioModel usuario) {
                new AlertDialog.Builder(requireContext()).setTitle("¿Eliminar usuario?").setMessage("¿Estás seguro de que quieres eliminar a " + usuario.getNombre() + "?").setPositiveButton("Sí", (dialog, which) -> {
                    eliminarUsuarioConRevertir(usuario);
                    recargarLista();
                }).setNegativeButton("Cancelar", null).show();
            }
        });
        recyclerUsuarios.setAdapter(adapter);

        btnAgregar = view.findViewById(R.id.btnAgregarUsuario);
        btnAgregar.setOnClickListener(v -> mostrarDialogNuevoUsuario());

    }

    private void recargarLista() {
        lista.clear();
        lista.addAll(usuarioDAO.getUsuarios());
        adapter.notifyDataSetChanged();
    }

    private void mostrarDialogNuevoUsuario() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View view = getLayoutInflater().inflate(R.layout.usuario_dialog, null);
        builder.setView(view);

        txtTitulo = view.findViewById(R.id.txtTitulo);
        edtNombre = view.findViewById(R.id.edtNuevoNombre);
        edtCorreo = view.findViewById(R.id.edtNuevoCorreo);
        edtContra = view.findViewById(R.id.edtNuevaContrasenia);
        btnGuardar = view.findViewById(R.id.btnGuardarNuevo);
        txtTitulo.setText(R.string.usuario_opc_agregar);

        AlertDialog dialog = builder.create();

        btnGuardar.setOnClickListener(v -> {
            String nombre = edtNombre.getText().toString().trim();
            String correo = edtCorreo.getText().toString().trim();
            String contra = edtContra.getText().toString().trim();

            if (!nombre.isEmpty() && !correo.isEmpty() && !contra.isEmpty()) {
                UsuarioModel nuevo = new UsuarioModel(nombre, correo, contra, 1);

                boolean insertado = usuarioDAO.addUsuario(nuevo);
                if (insertado) {
                    ToastUtil.show(requireContext(), "Usuario agregado", "success");
                    recargarLista();
                    dialog.dismiss();
                } else {
                    SnackbarUtil.show(requireView(), "Ocurrio algo!", "danger");
                }
            } else {
                ToastUtil.show(requireContext(), "Completa todos los campos", "danger");
            }
        });

        dialog.show();
    }

    private void mostrarDialogEditar(UsuarioModel usuario) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View view = getLayoutInflater().inflate(R.layout.usuario_dialog, null);
        builder.setView(view);

        txtTitulo = view.findViewById(R.id.txtTitulo);
        edtNombre = view.findViewById(R.id.edtNuevoNombre);
        edtCorreo = view.findViewById(R.id.edtNuevoCorreo);
        edtContra = view.findViewById(R.id.edtNuevaContrasenia);
        btnGuardar = view.findViewById(R.id.btnGuardarNuevo);

        txtTitulo.setText(R.string.usuario_opc_editar);
        edtNombre.setText(usuario.getNombre());
        edtCorreo.setText(usuario.getCorreo());
        edtContra.setText(usuario.getContrasenia());

        AlertDialog dialog = builder.create();

        btnGuardar.setOnClickListener(v -> {
            String nuevoNombre = edtNombre.getText().toString();
            String nuevoCorreo = edtCorreo.getText().toString();
            String nuevaContra = edtContra.getText().toString();

            usuario.setNombre(nuevoNombre);
            usuario.setCorreo(nuevoCorreo);
            usuario.setContrasenia(nuevaContra);

            boolean actualizado = usuarioDAO.updateUsuario(usuario);
            if (actualizado) {
                ToastUtil.show(requireContext(), "Usuario actualizado!", "success");
                recargarLista();
            } else {
                ToastUtil.show(requireContext(), "Error al actualizar!", "danger");
            }

            dialog.dismiss();
        });

        dialog.show();
    }

    private void eliminarUsuarioConRevertir(UsuarioModel usuario) {
        usuarioDAO.deleteUsuario(usuario.getId());
        recargarLista();

        Snackbar.make(requireView(), "Usuario eliminado", Snackbar.LENGTH_LONG).setAction("Deshacer", v -> {
            usuario.setEstado(1);
            usuarioDAO.updateUsuario(usuario);
            recargarLista();
            ToastUtil.show(requireContext(), "Se recuperó el usuario", "success"); // Usar requireContext()
        }).show();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_busqueda, menu);
        super.onCreateOptionsMenu(menu, inflater);

        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ToastUtil.show(requireContext(), "Usuarios encontrados", "info");
                buscarUsuarios(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                buscarUsuarios(newText);
                return true;
            }
        });

        item.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override public boolean onMenuItemActionCollapse(MenuItem mi) {
                searchView.setQuery("",false);
                recargarLista();
                return true;
            }
            @Override public boolean onMenuItemActionExpand(MenuItem mi) {
                return true;
            }
        });
    }

    private void buscarUsuarios(String valor){
        lista.clear();
        lista.addAll(usuarioDAO.getBusquedaUsuarios(valor));
        adapter.notifyDataSetChanged();
    }
}