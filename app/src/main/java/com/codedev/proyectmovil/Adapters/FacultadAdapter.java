package com.codedev.proyectmovil.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codedev.proyectmovil.Models.FacultadModel;
import com.codedev.proyectmovil.R;

import java.util.List;

public class FacultadAdapter extends RecyclerView.Adapter<FacultadAdapter.FacultadViewHolder> {
    private List<FacultadModel> lista;
    private OnFacultadActionListener listener;

    public interface OnFacultadActionListener {
        void onEditar(FacultadModel facultad);
        void onEliminar(FacultadModel facultad);
    }

    public FacultadAdapter(List<FacultadModel> lista, OnFacultadActionListener listener) {
        this.lista = lista;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FacultadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.facultad_item, parent, false);
        return new FacultadViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FacultadViewHolder holder, int position) {
        FacultadModel facultad = lista.get(position);
        holder.tvNombre.setText(facultad.getNombre());
        holder.btnEditar.setOnClickListener(v -> listener.onEditar(facultad));
        holder.btnEliminar.setOnClickListener(v -> listener.onEliminar(facultad));
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    static class FacultadViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre;
        ImageButton btnEditar, btnEliminar;
        FacultadViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tv_nombre_facultad);
            btnEditar = itemView.findViewById(R.id.btn_editar_facultad);
            btnEliminar = itemView.findViewById(R.id.btn_eliminar_facultad);
        }
    }
}