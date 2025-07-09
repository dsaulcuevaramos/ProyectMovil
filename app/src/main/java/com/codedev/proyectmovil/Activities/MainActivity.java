package com.codedev.proyectmovil.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.codedev.proyectmovil.R;
import com.codedev.proyectmovil.Utils.ToastUtil;

public class MainActivity extends AppCompatActivity {

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
        findViewById(R.id.btn_login).setOnClickListener(view -> {
            presiona(view);
        });
        findViewById(R.id.tv_registrarse).setOnClickListener(view -> {
            registro(view);
        });
    }

    public void presiona(View view){
        ToastUtil.show(this,"Hola ISaisssssssssssssss","success");
        Intent i = new Intent(this,NavitationMenu.class);

        startActivity(i);
    }

    public void registro(View view){
        Intent i = new Intent(this, RegistroUsuario.class);
        startActivity(i);
    }
}