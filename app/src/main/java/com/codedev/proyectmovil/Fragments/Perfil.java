package com.codedev.proyectmovil.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codedev.proyectmovil.Activities.MainActivity;
import com.codedev.proyectmovil.Helpers.Usuario.UsuarioDAO;
import com.codedev.proyectmovil.Models.Requests.UsuarioRequest;
import com.codedev.proyectmovil.R;
import com.codedev.proyectmovil.Utils.PreferencesUtil;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class Perfil extends Fragment {

    private UsuarioDAO usuarioDAO;
    private UsuarioRequest usuarioActual;
    private MaterialToolbar toolbar;

    public Perfil() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_perfil, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        toolbar = view.findViewById(R.id.toolbar_perfil);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);

        setHasOptionsMenu(true);

        usuarioDAO = new UsuarioDAO(requireContext());
        String id = PreferencesUtil.getKey(requireContext(), "idUsuario");
        if(!id.isEmpty()){
            usuarioActual = usuarioDAO.getUsuarioCompletoById(Integer.parseInt(id));
            cargarDatos(view);
        }
    }

    private void cargarDatos(View view) {

        String nombreCompleto = usuarioActual.getNombrePersona() + " " + usuarioActual.getApellidoPersona();
        TextView tvNombre = view.findViewById(R.id.tvNombreCompleto);
        tvNombre.setText(nombreCompleto);

        View datoUsuarioView = view.findViewById(R.id.usuario_perfil);
        View datoCodigoView = view.findViewById(R.id.codigo_perfil);
        View datoFacultadView = view.findViewById(R.id.facultad_perfil);
        View datoRolView = view.findViewById(R.id.rol_perfil);

        cargarDatosDeVista(datoUsuarioView, R.drawable.ic_perfil, "Usuario", usuarioActual.getUsuario());
        cargarDatosDeVista(datoCodigoView, R.drawable.ic_user_badge, "Código", usuarioActual.getCodigoPersona());
        cargarDatosDeVista(datoFacultadView, R.drawable.ic_school, "Facultad", usuarioActual.getFacultadNombre());
        cargarDatosDeVista(datoRolView, R.drawable.ic_manage_accounts, "Rol", usuarioActual.getRolNombre());
    }

    private void cargarDatosDeVista(View datoView, int iconResId, String label, String value) {
        ImageView icon = datoView.findViewById(R.id.item_icon);
        TextView labelTextView = datoView.findViewById(R.id.item_label);
        TextView valueTextView = datoView.findViewById(R.id.item_value);

        icon.setImageResource(iconResId);
        labelTextView.setText(label);
        valueTextView.setText(value);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_top, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            mostrarDialogoCerrarSesion();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void mostrarDialogoCerrarSesion() {
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle("¿Cerrar sesión?")
                .setMessage("¿Estás seguro de que deseas salir de tu cuenta?")
                .setIcon(R.drawable.ic_logout)
                .setPositiveButton("Sí, salir", (dialog, which) -> {
                    PreferencesUtil.clearAllKeys(requireContext());
                    Intent i = new Intent(requireContext(), MainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                })
                .setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss())
                .show();
    }
}