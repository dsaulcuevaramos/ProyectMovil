package com.example.proyectofinal;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

        findViewById(R.id.loginButton).setOnClickListener(v -> login());
    }

    private void login() {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Llamar al servidor para verificar usuario y contraseña
        String url = "http://tuservidor.com/api/login"; // Cambia esta URL con tu endpoint
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
            response -> {
                // Si el login es exitoso
                // Manejar el inicio de sesión exitoso aquí
            }, error -> {
                // Si ocurre un error
                Toast.makeText(this, "Error en el inicio de sesión", Toast.LENGTH_SHORT).show();
            }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("usuario", email);
                params.put("contrasena", password);
                return params;
            }
        };
        Volley.newRequestQueue(this).add(stringRequest);
    }
}
