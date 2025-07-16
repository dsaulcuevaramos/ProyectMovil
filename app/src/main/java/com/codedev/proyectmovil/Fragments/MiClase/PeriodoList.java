package com.codedev.proyectmovil.Fragments.MiClase;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codedev.proyectmovil.Adapters.PeriodoAdapter;
import com.codedev.proyectmovil.Adapters.PeriodoListAdapter;
import com.codedev.proyectmovil.Helpers.Periodo.PeriodoDAO;
import com.codedev.proyectmovil.Models.PeriodoModel;
import com.codedev.proyectmovil.R;
import com.codedev.proyectmovil.Utils.PreferencesUtil;

import java.util.List;

public class PeriodoList extends Fragment {

    PeriodoDAO periodoDao;
    RecyclerView recyclerPeriodos;
    List<PeriodoModel> lista;
    PeriodoListAdapter adapter;

    public PeriodoList() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.periodo_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String id = PreferencesUtil.getKey(requireContext(), "idPeriodo");
        if (!id.isEmpty()) {
            toFragmentoClase();
        }

        periodoDao = new PeriodoDAO(requireContext());
        recyclerPeriodos = view.findViewById(R.id.recyclerListPeriodos);
        recyclerPeriodos.setLayoutManager(new LinearLayoutManager(getContext()));

        lista = periodoDao.getAllPeriodos();

        adapter = new PeriodoListAdapter(getContext(), lista, new PeriodoListAdapter.OnItemClickListener() {
            @Override
            public void onCardClick(PeriodoModel periodo) {
                PreferencesUtil.saveKey(requireContext(), "idPeriodo", String.valueOf(periodo.getId()));
                toFragmentoClase();
            }
        });
        recyclerPeriodos.setAdapter(adapter);
    }

    private void toFragmentoClase() {
        Fragment claseFragment = new ClaseAll();
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, claseFragment)
                .commit();
    }
}