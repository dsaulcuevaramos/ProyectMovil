package com.codedev.proyectmovil.Fragments.Usuario;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.codedev.proyectmovil.Helpers.Facultad.FacultadDAO;
import com.codedev.proyectmovil.Helpers.Persona.PersonaDAO;
import com.codedev.proyectmovil.Helpers.Rol.RolDAO;
import com.codedev.proyectmovil.Helpers.Usuario.UsuarioDAO;
import com.codedev.proyectmovil.Models.FacultadModel;
import com.codedev.proyectmovil.Models.PersonaModel;
import com.codedev.proyectmovil.Models.RolModel;
import com.codedev.proyectmovil.Models.UsuarioModel;
import com.codedev.proyectmovil.R;
import com.codedev.proyectmovil.Utils.ToastUtil;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class UsuarioCreate extends Fragment {
    private String accion;
    private int idUsuario;

    private AutoCompleteTextView actvFacultad, actvRol;
    private FacultadDAO facultadDAO;
    private RolDAO rolDAO;
    private PersonaDAO personaDAO;
    private UsuarioDAO usuarioDAO;
    private List<FacultadModel> listafacultades;
    private List<RolModel> listaRoles;
    private UsuarioModel usuario;
    private PersonaModel persona;

    private MaterialToolbar toolbarTitulo;
    private EditText tietNombre, tietApellido, tietCodigo, tietUsuario, tietContrasenia;
    private TextInputLayout tilNombre, tilApellido, tilCodigo, tilFacultad, tilRol, tilUsuario, tilContrasenia;
    private Button btnRegistrar;
    private int idfacultadSelect = -1, idRolSelect = -1;

    public UsuarioCreate() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.usuario_create, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view,@Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            accion = getArguments().getString("accion");
            idUsuario = getArguments().getInt("id");
        }

        facultadDAO = new FacultadDAO(requireContext());
        personaDAO = new PersonaDAO(requireContext());
        usuarioDAO = new UsuarioDAO(requireContext());
        rolDAO = new RolDAO(requireContext());

        actvFacultad = view.findViewById(R.id.actv_facultad_gestionar);

        inicializarVistas(view);
        setTitulo();
        cargarFacultades();
        cargarRoles();

        actvFacultad.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String nombreSeleccionado = (String) parent.getItemAtPosition(position);
                if(listafacultades != null && !listafacultades.isEmpty()){
                    FacultadModel facultadSeleccionada = listafacultades.get(position);
                    idfacultadSelect = facultadSeleccionada.getId();
                }
            }
        });

        actvRol.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String nombreSeleccionado = (String) parent.getItemAtPosition(position);
                if(listaRoles != null && !listaRoles.isEmpty()){
                    RolModel facultadSeleccionada = listaRoles.get(position);
                    idRolSelect = facultadSeleccionada.getId();
                }
            }
        });

        if(accion.equals("editar")){
            cargarUsuario();
        }

        btnRegistrar.setOnClickListener(v -> {
            if(accion.equals("crear")){
                registrarUsuario();
            }
            else{
                actualizarUsuario();
            }
        });
    }

    private void inicializarVistas(View view) {
        toolbarTitulo = view.findViewById(R.id.toolbar_titulo);
        tietNombre = view.findViewById(R.id.tiet_nombre_gestionar);
        tietApellido = view.findViewById(R.id.tiet_apellido_gestionar);
        tietCodigo = view.findViewById(R.id.tiet_codigo_gestionar);
        tietUsuario = view.findViewById(R.id.tiet_usuario_gestionar);
        tietContrasenia = view.findViewById(R.id.tiet_contrasenia_gestionar);

        tilNombre = view.findViewById(R.id.til_nombre_gestionar);
        tilApellido = view.findViewById(R.id.til_apellido_gestionar);
        tilCodigo = view.findViewById(R.id.til_codigo_gestionar);
        tilFacultad = view.findViewById(R.id.til_facultad_gestionar);
        tilRol = view.findViewById(R.id.til_rol_gestionar);
        tilUsuario = view.findViewById(R.id.til_usuario_gestionar);
        tilContrasenia = view.findViewById(R.id.til_contrasenia_gestionar);

        actvFacultad = view.findViewById(R.id.actv_facultad_gestionar);
        actvRol = view.findViewById(R.id.actv_rol_gestionar);

        btnRegistrar = view.findViewById(R.id.btnGuardarNuevo);
    }

    private void setTitulo(){
        if(accion.equals("crear")){
            toolbarTitulo.setTitle("Agregar usuario nuevo");
            btnRegistrar.setText(R.string.usuario_opc_agregar);
        }
        else{
            usuario = usuarioDAO.getUsuarioById(idUsuario);
            toolbarTitulo.setTitle("Actualizar " + usuario.getUsuario());
            btnRegistrar.setText(R.string.usuario_opc_editar);
        }
    }
    private void cargarFacultades(){
        listafacultades = facultadDAO.getFacultad();

        List<String> nombres = new ArrayList<>();
        for(FacultadModel model: listafacultades){
            nombres.add(model.getNombre());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(), android.R.layout.simple_dropdown_item_1line, nombres);

        actvFacultad.setAdapter(adapter);
    }

    private void cargarRoles(){
        listaRoles = rolDAO.getRol();

        List<String> nombres = new ArrayList<>();
        for(RolModel model: listaRoles){
            nombres.add(model.getNombre());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(), android.R.layout.simple_dropdown_item_1line, nombres);

        actvRol.setAdapter(adapter);
    }

    private void cargarUsuario(){
        if(usuario != null){
            persona = personaDAO.getPersonaById(usuario.getPersona_id());
            tietNombre.setText(persona.getNombre());
            tietApellido.setText(persona.getApellido());
            tietCodigo.setText(persona.getCodigo());
            idfacultadSelect = persona.getIdFacultad();
            actvFacultad.setText(buscarNombrePorId(listafacultades, idfacultadSelect),false);
            idRolSelect = usuario.getRol_id();
            actvRol.setText(buscarNombrePorId(listaRoles, idRolSelect), false);
            tietUsuario.setText(usuario.getUsuario());
            tietContrasenia.setText(usuario.getContrasenia());
        }
    }

    private <T> String buscarNombrePorId(List<T> lista, int id) {
        for (T item : lista) {
            if (item instanceof FacultadModel && ((FacultadModel) item).getId() == id)
                return ((FacultadModel) item).getNombre();
            if (item instanceof RolModel && ((RolModel) item).getId() == id)
                return ((RolModel) item).getNombre();
        }
        return "";
    }

    private void registrarUsuario() {
        String codigo = tietCodigo.getText().toString().trim();
        String usuario = tietUsuario.getText().toString().trim();
        String contrasenia = tietContrasenia.getText().toString().trim();

        boolean esValido = validar();

        if (esValido) {
            PersonaModel p = new PersonaModel(codigo, tietNombre.getText().toString(), tietApellido.getText().toString(), idfacultadSelect, 1);
            int id = personaDAO.addPersona(p).getId();
            UsuarioModel u = new UsuarioModel(usuario, contrasenia, id, idRolSelect, 1);

            UsuarioModel registroExitoso = usuarioDAO.addUsuario(u);
            if (registroExitoso != null) {
                ToastUtil.show(requireContext(), "Usuario registrado exitosamente!.", "success");
                volver();
            } else {
                ToastUtil.show(requireContext(), "Error al registrar el usuario. Intenta de nuevo.", "danger");
            }
        } else {
            ToastUtil.show(requireContext(), "Por favor, completa todos los campos correctamente.", "danger");
        }
    }

    private void actualizarUsuario() {
        String codigo = tietCodigo.getText().toString().trim();
        String usuario = tietUsuario.getText().toString().trim();
        String contrasenia = tietContrasenia.getText().toString().trim();

        boolean esValido = validar();

        if (esValido) {
            PersonaModel p = new PersonaModel(codigo, tietNombre.getText().toString(), tietApellido.getText().toString(), idfacultadSelect, 1);
            p.setId(this.usuario.getPersona_id());
            boolean ejecutado = personaDAO.updatePersona(p);

            UsuarioModel u = new UsuarioModel(usuario, contrasenia, this.usuario.getPersona_id(), idRolSelect, 1);
            u.setId(this.idUsuario);

            boolean registroExitoso = usuarioDAO.updateUsuario(u);
            if (registroExitoso && ejecutado) {
                ToastUtil.show(requireContext(), "Usuario actualizado exitosamente!.", "success");
                volver();
            } else {
                ToastUtil.show(requireContext(), "Error al actualizar el usuario. Intenta de nuevo.", "danger");
            }
        } else {
            ToastUtil.show(requireContext(), "Por favor, completa todos los campos correctamente.", "danger");
        }
    }

    private boolean validar(){
        limpiarErrores();

        String nombre = tietNombre.getText().toString().trim();
        String apellido = tietApellido.getText().toString().trim();
        String codigo = tietCodigo.getText().toString().trim();
        String usuario = tietUsuario.getText().toString().trim();
        String contrasenia = tietContrasenia.getText().toString().trim();

        boolean esValido = true;

        if (nombre.isEmpty()) {
            tilNombre.setError("El nombre es obligatorio");
            esValido = false;
        }
        if (apellido.isEmpty()) {
            tilApellido.setError("El apellido es obligatorio");
            esValido = false;
        }
        if (codigo.isEmpty()) {
            tilCodigo.setError("El código es obligatorio");
            esValido = false;
        }
        if (idfacultadSelect == -1) {
            tilFacultad.setError("Selecciona una facultad");
            esValido = false;
        }
        if (idRolSelect == -1) {
            tilRol.setError("Selecciona un rol");
            esValido = false;
        }
        if (usuario.isEmpty()) {
            tilUsuario.setError("El usuario es obligatorio");
            esValido = false;
        }
        if (contrasenia.isEmpty()) {
            tilContrasenia.setError("La contraseña es obligatoria");
            esValido = false;
        }
        return esValido;
    }

    private void limpiarErrores() {
        tilNombre.setError(null);
        tilApellido.setError(null);
        tilCodigo.setError(null);
        tilFacultad.setError(null);
        tilRol.setError(null);
        tilUsuario.setError(null);
        tilContrasenia.setError(null);
    }

    private void volver(){
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new UsuarioAll());
        transaction.commit();
    }
}