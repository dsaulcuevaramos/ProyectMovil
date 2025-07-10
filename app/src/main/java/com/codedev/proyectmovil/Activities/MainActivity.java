package com.codedev.proyectmovil.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.codedev.proyectmovil.Helpers.Usuario.UsuarioDAO;
import com.codedev.proyectmovil.Models.Requests.UsuarioRequest;
import com.codedev.proyectmovil.R;
import com.codedev.proyectmovil.Utils.PreferencesUtil;
import com.codedev.proyectmovil.Utils.ToastUtil;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    private UsuarioDAO usuarioDAO;
    private TextInputLayout tilusuario, tilContrasenia;
    private EditText txtUsuario, txtContrasenia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        usuarioDAO = new UsuarioDAO(this);

        if(!PreferencesUtil.getKey(this,"idUsuario").isEmpty()){
            toMenu();
        }

        txtUsuario = findViewById(R.id.tiet_usuario);
        txtContrasenia = findViewById(R.id.tiet_contrasenia);
        tilusuario = findViewById(R.id.til_usuario);
        tilContrasenia = findViewById(R.id.til_contrasenia);

        findViewById(R.id.btn_login).setOnClickListener(this::loguearUsuario);
        findViewById(R.id.tv_registrarse).setOnClickListener(this::toRegistro);

        txtUsuario.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) tilusuario.setError(null);
        });

        txtContrasenia.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) tilContrasenia.setError(null);
        });
    }

    public void loguearUsuario(View view) {
        String usuario = txtUsuario.getText().toString().trim();
        String password = txtContrasenia.getText().toString().trim();

        tilusuario.setError(null);
        tilContrasenia.setError(null);

        boolean isValid = true;

        if (usuario.isEmpty()) {
            tilusuario.setError("Ingrese su usuario");
            isValid = false;
        }

        if (password.isEmpty()) {
            tilContrasenia.setError("Ingrese su contraseña");
            isValid = false;
        }

        if (!isValid) return;

        UsuarioRequest u = usuarioDAO.getUsuarioByAuthentication(usuario, password);

        if (u != null) {
            PreferencesUtil.saveKey(this, "idUsuario", String.valueOf(u.getIdUsuario()));
            PreferencesUtil.saveKey(this, "idFacultad", String.valueOf(u.getIdFacultad()));
            PreferencesUtil.saveKey(this, "idRol", String.valueOf(u.getIdRol()));

            ToastUtil.show(this, "Bienvenido " + u.getUsuario(), "success");
            toMenu();
        } else {
            ToastUtil.show(this, "Credenciales incorrectas", "error");
        }
    }

    public void toMenu(){
        startActivity(new Intent(this, NavitationMenu.class));
        finish();
    }

    public void toRegistro(View view){
        Intent i = new Intent(this, RegistroUsuario.class);
        startActivity(i);
    }
}