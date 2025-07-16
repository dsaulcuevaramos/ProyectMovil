package com.codedev.proyectmovil.Fragments;

import android.annotation.SuppressLint;
import android.app.Person;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codedev.proyectmovil.Adapters.PersonaAdapter;
import com.codedev.proyectmovil.Helpers.Persona.PersonaDAO;
import com.codedev.proyectmovil.Helpers.Usuario.UsuarioDAO;
import com.codedev.proyectmovil.Models.CursosModel;
import com.codedev.proyectmovil.Models.PersonaModel;
import com.codedev.proyectmovil.Models.Requests.UsuarioRequest;
import com.codedev.proyectmovil.Models.UsuarioModel;
import com.codedev.proyectmovil.R;
import com.codedev.proyectmovil.Utils.ToastUtil;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class PersonaAll extends Fragment {

    PersonaDAO personaDAO;
    RecyclerView recyclerPersona;
    List<PersonaModel> listaPersonaDocente;
    PersonaAdapter adapter;
    TextView txtTitulo;
    EditText edtCodigo, edtNombre, edtApellido;
    Button btnGuardar;
    MaterialToolbar toolbar;
    private int idFacultad =-1;

    public PersonaAll(){}

    public static PersonaAll newInstance(int idFacultad) {
        PersonaAll fragment = new PersonaAll();
        Bundle args = new Bundle();
        args.putInt("idFacultad", idFacultad);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.persona_fragment_all, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        personaDAO = new PersonaDAO(getContext());
        recyclerPersona = view.findViewById(R.id.recyclerPersona);
        recyclerPersona.setLayoutManager(new LinearLayoutManager(getContext()));

        if (getArguments() != null) {
            idFacultad = getArguments().getInt("idFacultad", -1);
        }

        listaPersonaDocente = personaDAO.getAllDoccenteByFacultad(idFacultad);
        adapter = new PersonaAdapter(getContext(), listaPersonaDocente, new PersonaAdapter.OnItemClickListener() {});
        recyclerPersona.setAdapter(adapter);

    }

}
