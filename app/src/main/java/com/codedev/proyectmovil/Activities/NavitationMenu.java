package com.codedev.proyectmovil.Activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.codedev.proyectmovil.Fragments.Configuracion.ConfiguracionMenu;
import com.codedev.proyectmovil.Fragments.CursosAll;
import com.codedev.proyectmovil.Fragments.UsuarioAll;
import com.codedev.proyectmovil.R;
import com.codedev.proyectmovil.Utils.ToastUtil;
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

                    if (itemId == R.id.navigation_asistencia) {
                        selectedFragment = new UsuarioAll();
                        title = "Usuarios";
                    } else if (itemId == R.id.navigation_profesor) {
                        selectedFragment = new CursosAll();
                        title = "Asistencias";
                    } else if (itemId == R.id.navigation_alumno) {
                        selectedFragment = new UsuarioAll();
                        title = "Horario";
                    } else if (itemId == R.id.navigation_perfil) {
                        selectedFragment = new CursosAll();
                        title = "Perfil";
                    } else if (itemId == R.id.navigation_menu) {
                        selectedFragment = new ConfiguracionMenu();
                        title = "Opciones";
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
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu_top, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_config) {
            ToastUtil.show(this, "Abrir Configuración", "info");
            return true;
        } else if (id == R.id.menu_logout) {
            new AlertDialog.Builder(this).setTitle("Cerrar sesion").setMessage("¿Estás seguro que quieres cerrar sesion ?").setPositiveButton("Sí", (dialog, which) -> {
//                aqui ira el cerrado de sesion y shared preferences
            }).setNegativeButton("Cancelar", null).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onFragmentTitleChange(String title) {
        if (toolbar != null) {
            toolbar.setTitle(title);
        }
    }
}