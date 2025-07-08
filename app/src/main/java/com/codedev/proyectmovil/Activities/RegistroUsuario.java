package com.codedev.proyectmovil.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.codedev.proyectmovil.Helpers.Facultad.FacultadDAO;
import com.codedev.proyectmovil.Helpers.Persona.PersonaDAO;
import com.codedev.proyectmovil.Helpers.Usuario.UsuarioDAO;
import com.codedev.proyectmovil.Models.FacultadModel;
import com.codedev.proyectmovil.Models.PersonaModel;
import com.codedev.proyectmovil.Models.UsuarioModel;
import com.codedev.proyectmovil.R;
import com.codedev.proyectmovil.Utils.ToastUtil;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class RegistroUsuario extends AppCompatActivity {

    private AutoCompleteTextView actvFacultad;
    private FacultadDAO facultadDAO;
    private PersonaDAO personaDAO;
    private UsuarioDAO usuarioDAO;
    private List<FacultadModel> listafacultades;

    private EditText tietNombre, tietApellido, tietCodigo, tietUsuario, tietContrasenia, tietConfirmarContrasenia;
    private TextInputLayout tilNombre, tilApellido, tilCodigo, tilFacultad, tilUsuario, tilContrasenia, tilConfirmarContrasenia;
    private MaterialButton btnRegistrar;
    private int idfacultadSelect = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.usuario_registro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        facultadDAO = new FacultadDAO(this);
        personaDAO = new PersonaDAO(this);
        usuarioDAO = new UsuarioDAO(this);

        inicializarVistas();
        cargarFacultades();
        btnRegistrar.setOnClickListener(v ->{
            registrarUsuario();
        });

        actvFacultad.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String nombreSeleccionado = (String) parent.getItemAtPosition(position);

                if(listafacultades != null && !listafacultades.isEmpty()){
                    FacultadModel facultadSeleccionada = listafacultades.get(position);
                    idfacultadSelect = facultadSeleccionada.getId();
                }
            }
        });
    }

    private void cargarFacultades(){
        listafacultades = facultadDAO.getFacultad();

        List<String> nombres = new ArrayList<>();
        for(FacultadModel model: listafacultades){
            nombres.add(model.getNombre());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_dropdown_item_1line, nombres);

        actvFacultad.setAdapter(adapter);
    }

    private void inicializarVistas() {
        tietNombre = findViewById(R.id.tiet_nombre_registrar);
        tietApellido = findViewById(R.id.tiet_apellido_registrar);
        tietCodigo = findViewById(R.id.tiet_codigo_registrar);
        tietUsuario = findViewById(R.id.tiet_usuario_registrar);
        tietContrasenia = findViewById(R.id.tiet_contrasenia_registrar);
        tietConfirmarContrasenia = findViewById(R.id.tiet_confirmar_contrasenia_registrar);

        tilNombre = findViewById(R.id.til_nombre_registrar);
        tilApellido = findViewById(R.id.til_apellido_registrar);
        tilCodigo = findViewById(R.id.til_codigo_registrar);
        tilFacultad = findViewById(R.id.til_facultad_registrar);
        tilUsuario = findViewById(R.id.til_usuario_registrar);
        tilContrasenia = findViewById(R.id.til_contrasenia_registrar);
        tilConfirmarContrasenia = findViewById(R.id.til_confirmar_contrasenia_registrar);

        actvFacultad = findViewById(R.id.actv_facultad_registrar);

        btnRegistrar = findViewById(R.id.btn_registrar);
    }

    private void registrarUsuario() {
        limpiarErrores();

        String nombre = tietNombre.getText().toString().trim();
        String apellido = tietApellido.getText().toString().trim();
        String codigo = tietCodigo.getText().toString().trim();
        String usuario = tietUsuario.getText().toString().trim();
        String contrasenia = tietContrasenia.getText().toString().trim();
        String confirmarContrasenia = tietConfirmarContrasenia.getText().toString().trim();

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
        if (usuario.isEmpty()) {
            tilUsuario.setError("El usuario es obligatorio");
            esValido = false;
        }
        if (contrasenia.isEmpty()) {
            tilContrasenia.setError("La contraseña es obligatoria");
            esValido = false;
        }
        if (confirmarContrasenia.isEmpty()) {
            tilConfirmarContrasenia.setError("Confirma la contraseña");
            esValido = false;
        }
        if (!contrasenia.equals(confirmarContrasenia)) {
            tilContrasenia.setError("Las contraseñas no coinciden");
            tilConfirmarContrasenia.setError("Las contraseñas no coinciden");
            esValido = false;
        }

        if (esValido) {
            PersonaModel p = new PersonaModel(codigo, tietNombre.getText().toString(), tietApellido.getText().toString(), idfacultadSelect, 1);
            int id = personaDAO.addPersona(p).getId();
            Log.d("Persona", "Usuario ingresado con id: "+id);
            UsuarioModel u = new UsuarioModel(usuario, contrasenia, id, 2, 1);

            UsuarioModel registroExitoso = usuarioDAO.addUsuario(u);

            if (registroExitoso != null) {
                ToastUtil.show(this, "Usuario registrado exitosamente!.", "success");

            } else {
                ToastUtil.show(this, "Error al registrar el usuario. Intenta de nuevo.", "danger");
            }
        } else {
            ToastUtil.show(this, "Por favor, completa todos los campos correctamente.", "danger");
        }
    }

    private void limpiarErrores() {
        tilNombre.setError(null);
        tilApellido.setError(null);
        tilCodigo.setError(null);
        tilFacultad.setError(null);
        tilUsuario.setError(null);
        tilContrasenia.setError(null);
        tilConfirmarContrasenia.setError(null);
    }
}