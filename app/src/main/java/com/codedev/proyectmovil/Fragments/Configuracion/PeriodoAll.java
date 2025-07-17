package com.codedev.proyectmovil.Fragments.Configuracion;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codedev.proyectmovil.Adapters.PeriodoAdapter;
import com.codedev.proyectmovil.Helpers.Periodo.PeriodoDAO;
import com.codedev.proyectmovil.Models.PeriodoModel;
import com.codedev.proyectmovil.R;
import com.codedev.proyectmovil.Utils.ToastUtil;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class PeriodoAll extends Fragment {

    PeriodoDAO periodoDao;
    RecyclerView recyclerPeriodos;
    List<PeriodoModel> lista;
    PeriodoAdapter adapter;
    FloatingActionButton btnAgregar;


    public PeriodoAll() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.periodo_all_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        periodoDao = new PeriodoDAO(requireContext());
        recyclerPeriodos = view.findViewById(R.id.recyclerPeriodos);
        recyclerPeriodos.setLayoutManager(new LinearLayoutManager(getContext()));

        lista = periodoDao.getAllPeriodos();

        adapter = new PeriodoAdapter(getContext(), lista, new PeriodoAdapter.OnItemClickListener() {
            @Override
            public void onEditarClick(PeriodoModel periodo) {
                mostrarModalPeriodo(periodo);
            }

            @Override
            public void onEliminarClick(PeriodoModel periodo) {
                eliminarConRecuperacion(periodo);
            }

            @Override
            public void onActivoClick(PeriodoModel periodo) {
                PeriodoModel activo = periodoDao.getPeriodoActivo();
                if (periodo.getActivo() == 0) {
                    if (activo != null && activo.getId() != periodo.getId()) {
                        ToastUtil.show(requireContext(), "Ya existe un periodo activo", "info");
                        return;
                    }
                    periodo.setActivo(1);
                    ToastUtil.show(requireContext(), "Periodo activado", "succes");
                } else {
                    periodo.setActivo(0);
                    ToastUtil.show(requireContext(), "Periodo desactivado", "succes");
                }
                periodoDao.updatePeriodo(periodo);

                recargarLista();
            }
        });

        recyclerPeriodos.setAdapter(adapter);

        btnAgregar = view.findViewById(R.id.btnAgregarPeriodo);
        btnAgregar.setOnClickListener(v -> {
            mostrarModalPeriodo(null);
        });
    }

    private void mostrarModalPeriodo(@Nullable PeriodoModel modeloExistente) {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.periodo_dialog, null);

        TextView title = dialogView.findViewById(R.id.title_form);
        TextInputLayout tilNombre = dialogView.findViewById(R.id.til_nombre_periodo);
        TextInputLayout tilCodigo = dialogView.findViewById(R.id.til_codigo_periodo);
        TextInputEditText etNombre = dialogView.findViewById(R.id.tiet_nombre_periodo);
        TextInputEditText etCodigo = dialogView.findViewById(R.id.tiet_codigo_preiodo);
        MaterialButton btnGuardar = dialogView.findViewById(R.id.btnGuardarPeriodo);

        final AlertDialog dialog = new AlertDialog.Builder(getContext(), com.google.android.material.R.style.ThemeOverlay_Material3_MaterialAlertDialog_Centered)
                .setView(dialogView)
                .setCancelable(true)
                .create();

        if (modeloExistente != null) {
            title.setText("Editar Periodo " + modeloExistente.getNombre());
            etNombre.setText(modeloExistente.getNombre());
            etCodigo.setText(modeloExistente.getCodigo());
            btnGuardar.setText("Actualizar");
        } else {
            title.setText("Registrar Periodo");
            btnGuardar.setText("Guardar");
        }

        btnGuardar.setOnClickListener(v -> {
            String nombre = etNombre.getText().toString().trim();
            String codigo = etCodigo.getText().toString().trim();
            boolean valido = validarCampos(tilNombre, etNombre.getText().toString())
                    & validarCampos(tilCodigo, etCodigo.getText().toString());

            if (!valido) return;

            if (modeloExistente == null) {
                if (periodoDao.getPeriodoActivo() != null) {
                    ToastUtil.show(requireContext(), "Existe un periodo activo", "danger");
                    return;
                }

                PeriodoModel nuevo = new PeriodoModel();
                nuevo.setNombre(nombre);
                nuevo.setCodigo(codigo);
                nuevo.setActivo(1);
                nuevo.setEstado(1);
                periodoDao.addPeriodo(nuevo);
                ToastUtil.show(requireContext(), "Periodo creado", "success");
            } else {
                modeloExistente.setNombre(nombre);
                modeloExistente.setCodigo(codigo);
                periodoDao.updatePeriodo(modeloExistente);
                ToastUtil.show(requireContext(), "Periodo actualizado", "success");
            }

            dialog.dismiss();
            recargarLista();
        });

        dialog.show();
    }

    private boolean validarCampos(TextInputLayout til, String texto) {
        if (texto.trim().isEmpty()) {
            til.setError("Campo requerido");
            return false;
        }
        til.setError(null);
        return true;
    }

    private void eliminarConRecuperacion(PeriodoModel periodo) {
        new AlertDialog.Builder(requireContext())
                .setTitle("¿Eliminar Periodo?")
                .setMessage("¿Estás seguro de eliminar el periodo \"" + periodo.getNombre() + "\"?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    periodoDao.deletePeriodo(periodo.getId());
                    ToastUtil.show(requireContext(), "Periodo eliminado", "warning");
                    recargarLista();

                    Snackbar.make(recyclerPeriodos, "Periodo eliminado", Snackbar.LENGTH_LONG)
                            .setAction("Deshacer", v -> {
                                periodo.setEstado(1);
                                periodoDao.updatePeriodo(periodo);
                                ToastUtil.show(requireContext(), "Periodo restaurado", "success");
                                recargarLista();
                            })
                            .setTextColor(Color.WHITE)
                            .setActionTextColor(ContextCompat.getColor(requireContext(), com.google.android.material.R.color.design_default_color_primary))
                            .show();
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void recargarLista() {
        lista.clear();
        lista.addAll(periodoDao.getAllPeriodos());
        adapter.notifyDataSetChanged();
    }
}