package com.codedev.proyectmovil.Activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.codedev.proyectmovil.Fragments.CursosAll;
import com.codedev.proyectmovil.Fragments.UsuarioAll;
import com.codedev.proyectmovil.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NavitationMenu extends AppCompatActivity {

    private Toolbar toolbar;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_navitation_menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new UsuarioAll())
                    .commit();
            toolbar.setTitle("Usuarios"); // Titulo inicial
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    String title = "";

                    int itemId = item.getItemId(); // Obtén el ID del elemento seleccionado

                    if (itemId == R.id.navigation_usuarios) {
                        selectedFragment = new UsuarioAll();
                        title = "Usuarios";
                    } else if (itemId == R.id.navigation_asistencias) {
                        selectedFragment = new CursosAll();
                        title = "Asistencias";
                    } else if (itemId == R.id.navigation_horario) {
                        selectedFragment = new UsuarioAll();
                        title = "Horario";
                    } else if (itemId == R.id.navigation_perfil) {
                        selectedFragment = new CursosAll();
                        title = "Perfil";
                    }

                    if (selectedFragment != null) {
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, selectedFragment)
                                .commit();
                        toolbar.setTitle(title); // Cambia el título de la Toolbar
                        return true;
                    }
                    return false;
                }
            };
}