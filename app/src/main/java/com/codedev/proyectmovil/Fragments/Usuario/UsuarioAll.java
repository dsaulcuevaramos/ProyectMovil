package com.codedev.proyectmovil.Fragments.Usuario;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codedev.proyectmovil.Adapters.UsuarioAdapter;
import com.codedev.proyectmovil.Helpers.Usuario.UsuarioDAO;
import com.codedev.proyectmovil.Models.Requests.UsuarioRequest;
import com.codedev.proyectmovil.R;
import com.codedev.proyectmovil.Utils.ToastUtil;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class UsuarioAll extends Fragment {
    UsuarioDAO usuarioDAO;
    RecyclerView recyclerUsuarios;
    List<UsuarioRequest> lista;
    UsuarioAdapter adapter;
    TextView txtTitulo;

    EditText edtUsuario;
    EditText edtPersona;
    EditText edtContra;
    FloatingActionButton btnAgregar;
    Button btnGuardar;
    MaterialToolbar toolbar;

    public UsuarioAll() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.usuario_fragment_all, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        usuarioDAO = new UsuarioDAO(getContext());
        recyclerUsuarios = view.findViewById(R.id.recyclerUsuarios);
        recyclerUsuarios.setLayoutManager(new LinearLayoutManager(getContext()));

        toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);

        lista = usuarioDAO.getAllUsuarios();
        adapter = new UsuarioAdapter(getContext(), lista, new UsuarioAdapter.OnItemClickListener() { // Usa getContext()

            @Override
            public void onEditarClick(int id) {
                cambiarFragmento(new UsuarioCreate(), id, "editar");
            }

            @Override
            public void onEliminarClick(UsuarioRequest usuario) {
                new AlertDialog.Builder(requireContext()).setTitle("¿Eliminar usuario?").setMessage("¿Estás seguro de que quieres eliminar a " + usuario.getUsuario() + "?").setPositiveButton("Sí", (dialog, which) -> {
                    eliminarUsuarioConRevertir(usuario);
                    recargarLista();
                }).setNegativeButton("Cancelar", null).show();
            }
        });
        recyclerUsuarios.setAdapter(adapter);

        btnAgregar = view.findViewById(R.id.btnAgregarUsuario);
        btnAgregar.setOnClickListener(v -> cambiarFragmento(new UsuarioCreate(), 0, "crear"));

    }

    private void recargarLista() {
        lista.clear();
        lista.addAll(usuarioDAO.getAllUsuarios());
        adapter.notifyDataSetChanged();
    }

    public void cambiarFragmento(Fragment fragmento, int id, String accion){
        Bundle bund = new Bundle();
        bund.putInt("id", id);
        bund.putString("accion", accion);
        fragmento.setArguments(bund);

        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragmento);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void eliminarUsuarioConRevertir(UsuarioRequest usuario) {
        usuarioDAO.deleteUsuario(usuario.getIdUsuario());
        recargarLista();

        Snackbar.make(requireView(), "Usuario eliminado", Snackbar.LENGTH_LONG).setAction("Deshacer", v -> {
            usuarioDAO.recoverUsuario(usuario.getIdUsuario());
            recargarLista();
            ToastUtil.show(requireContext(), "Se recuperó el usuario", "success"); // Usar requireContext()
        }).show();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_busqueda, menu);
        super.onCreateOptionsMenu(menu, inflater);

        final MenuItem item = menu.findItem(R.id.action_search);

        final SearchView searchView = (androidx.appcompat.widget.SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                ToastUtil.show(requireContext(), "Usuarios encontrados", "info");
                buscarUsuarios(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                buscarUsuarios(newText);
                return true;
            }
        });

        item.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override public boolean onMenuItemActionCollapse(MenuItem mi) {
                searchView.setQuery("",false);
                recargarLista();
                return true;
            }
            @Override public boolean onMenuItemActionExpand(MenuItem mi) {
                return true;
            }
        });
    }

    private void buscarUsuarios(String valor){
        lista.clear();
        lista.addAll(usuarioDAO.getBusquedaUsuarios(valor));
        adapter.notifyDataSetChanged();
    }
}