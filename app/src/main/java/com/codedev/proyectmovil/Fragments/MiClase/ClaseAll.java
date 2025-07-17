package com.codedev.proyectmovil.Fragments.MiClase;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.SearchView;

import com.codedev.proyectmovil.Adapters.ClaseAdapter;
import com.codedev.proyectmovil.Helpers.Clase.ClaseDAO;
import com.codedev.proyectmovil.Helpers.Periodo.PeriodoDAO;
import com.codedev.proyectmovil.Models.PeriodoModel;
import com.codedev.proyectmovil.Models.Requests.ClaseRequest;
import com.codedev.proyectmovil.R;
import com.codedev.proyectmovil.Utils.PreferencesUtil;
import com.codedev.proyectmovil.Utils.ToastUtil;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class ClaseAll extends Fragment {


    private ClaseDAO claseDAO;
    private PeriodoDAO periodoDAO;
    private RecyclerView recyclerClases;
    private List<ClaseRequest> lista;
    private ClaseAdapter adapter;
    private MaterialToolbar toolbar;
    private FloatingActionButton btnAgregar;
    int idPeriodo = 0;

    public ClaseAll() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.clase_all_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String idPeriodoEnviado = PreferencesUtil.getKey(requireContext(), "idPeriodo");
        if (idPeriodoEnviado.isEmpty()) {
            toListPeriodos();
        }
        String idClaseEnviado = PreferencesUtil.getKey(requireContext(), "idClase");
        if (!idClaseEnviado.isEmpty()) {
            toDetalleClase();
        }

        idPeriodo = Integer.parseInt(idPeriodoEnviado);

        claseDAO = new ClaseDAO(requireContext());
        periodoDAO = new PeriodoDAO(requireContext());
        recyclerClases = view.findViewById(R.id.recyclerClases);
        recyclerClases.setLayoutManager(new LinearLayoutManager(getContext()));

        PeriodoModel periodoSeleccionado = periodoDAO.getPeriodoById(idPeriodo);

        toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        toolbar.setSubtitle("Periodo " + periodoSeleccionado.getNombre());
        toolbar.setNavigationIcon(R.drawable.ic_arrow_right);
        toolbar.setNavigationOnClickListener(v -> toListPeriodos());
        setHasOptionsMenu(true);

        lista = claseDAO.getClasesByPeriodo(idPeriodo);

        adapter = new ClaseAdapter(getContext(), lista , new ClaseAdapter.OnItemClickListener() {
            @Override
            public void onEditarClick(int id) {
                cambiarFragmento(new ClaseCreate(), id, "editar");
            }

            @Override
            public void onEliminarClick(ClaseRequest clase) {
                new AlertDialog.Builder(requireContext())
                        .setTitle("¿Eliminar clase?")
                        .setMessage("¿Estás seguro de eliminar el curso " + clase.getNombreCurso() + "?")
                        .setPositiveButton("Sí", (dialog, which) -> {
                            eliminarClaseConRevertir(clase);
                        })
                        .setNegativeButton("Cancelar", null)
                        .show();
            }

            @Override
            public void onAdministrarClick(int id) {
                PreferencesUtil.saveKey(requireContext(), "idClase", String.valueOf(id));
                toDetalleClase();
            }

            @Override
            public void onItemClick(int idcurso) {

            }
        });
        recyclerClases.setAdapter(adapter);

        btnAgregar = view.findViewById(R.id.btnAgregarClase);
        btnAgregar.setOnClickListener(v -> cambiarFragmento(new ClaseCreate(), 0, "crear"));


    }


    public void cambiarFragmento(Fragment fragmento, int id, String accion) {
        Bundle bund = new Bundle();
        bund.putInt("id", id);
        bund.putInt("idPeriodo", idPeriodo);
        bund.putString("accion", accion);
        fragmento.setArguments(bund);

        FragmentManager manager = getParentFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragmento);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void eliminarClaseConRevertir(ClaseRequest clase) {
        claseDAO.deleteClase(clase.getIdClase());
        recargarLista();

        Snackbar.make(requireView(), "Clase eliminada", Snackbar.LENGTH_LONG)
                .setAction("Deshacer", v -> {
                    claseDAO.recoverClase(clase.getIdClase());
                    recargarLista();
                    ToastUtil.show(requireContext(), "Clase restaurada", "success");
                }).show();
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_busqueda, menu);
        super.onCreateOptionsMenu(menu, inflater);

        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                buscarClases(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                buscarClases(newText);
                return true;
            }
        });

        item.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                recargarLista();
                return true;
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }
        });
    }

    private void buscarClases(String valor) {
        lista.clear();
        lista.addAll(claseDAO.getBusquedaClasesByPeriodo(idPeriodo, valor));
        adapter.notifyDataSetChanged();
    }

    private void toListPeriodos() {
        PreferencesUtil.deleteKey(requireContext(), "idPeriodo");
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, new PeriodoList());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void toDetalleClase() {
        Fragment claseFragment = new ClaseDetalleAll();
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, claseFragment)
                .commit();
    }
    private void recargarLista() {
        lista.clear();
        lista.addAll(claseDAO.getClasesByPeriodo(idPeriodo));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        recargarLista();
    }
}