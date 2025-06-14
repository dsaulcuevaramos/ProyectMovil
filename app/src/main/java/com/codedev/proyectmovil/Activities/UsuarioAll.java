package com.codedev.proyectmovil.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codedev.proyectmovil.Adapters.UsuarioAdapter;
import com.codedev.proyectmovil.Helpers.Usuario.UsuarioDAO;
import com.codedev.proyectmovil.Models.UsuarioModel;
import com.codedev.proyectmovil.R;
import com.codedev.proyectmovil.Utils.ToastUtil;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class UsuarioAll extends AppCompatActivity {
    UsuarioDAO usuarioDAO;
    RecyclerView recyclerUsuarios;
    List<UsuarioModel> lista;
    UsuarioAdapter adapter;
    TextView txtTitulo;

    EditText edtNombre;
    EditText edtCorreo;
    EditText edtContra;
    Button btnAgregar, btnGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.usuario_activity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        usuarioDAO = new UsuarioDAO(this);
        recyclerUsuarios = findViewById(R.id.recyclerUsuarios);
        recyclerUsuarios.setLayoutManager(new LinearLayoutManager(this));

        lista = usuarioDAO.getUsuarios();
        adapter = new UsuarioAdapter(this, lista, new UsuarioAdapter.OnItemClickListener() {

            @Override
            public void onEditarClick(UsuarioModel usuario) {

                mostrarDialogEditar(usuario);
            }

            @Override
            public void onEliminarClick(UsuarioModel usuario) {
                new AlertDialog.Builder(UsuarioAll.this).setTitle("¿Eliminar usuario?").setMessage("¿Estás seguro de que quieres eliminar a " + usuario.getNombre() + "?").setPositiveButton("Sí", (dialog, which) -> {
                    eliminarUsuarioConRevertir(usuario);
                    recargarLista();
                }).setNegativeButton("Cancelar", null).show();
            }
        });
        recyclerUsuarios.setAdapter(adapter);

        btnAgregar = findViewById(R.id.btnAgregarUsuario);
        btnAgregar.setOnClickListener(v -> mostrarDialogNuevoUsuario());
    }

    private void recargarLista() {
        lista.clear();
        lista.addAll(usuarioDAO.getUsuarios());
        adapter.notifyDataSetChanged();
    }

    private void mostrarDialogNuevoUsuario() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
                UsuarioModel nuevo = new UsuarioModel(nombre,correo,contra,1);

                boolean insertado = usuarioDAO.addUsuario(nuevo);
                if (insertado) {
                    ToastUtil.show(this, "Usuario agregado", "success");
                    recargarLista();
                    dialog.dismiss();
                } else {
                    ToastUtil.show(this, "Error al agregar", "danger");
                }
            } else {
                ToastUtil.show(this, "Completa todos los campos", "danger");
            }
        });

        dialog.show();
    }

    private void mostrarDialogEditar(UsuarioModel usuario) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
                ToastUtil.show(this, "Usuario actualizado", "success");
                recargarLista();
            } else {
                ToastUtil.show(this, "Error al actualizar", "danger");
            }

            dialog.dismiss();
        });

        dialog.show();
    }

    private void eliminarUsuarioConRevertir(UsuarioModel usuario) {
        usuarioDAO.deleteUsuario(usuario.getId());
        recargarLista();

        Snackbar.make(findViewById(android.R.id.content), "Usuario eliminado", Snackbar.LENGTH_LONG).setAction("Deshacer", v -> {
            usuario.setEstado(1);
            usuarioDAO.updateUsuario(usuario);
            recargarLista();
            ToastUtil.show(this, "Se recuperó el usuario", "success");
        }).show();
    }
}