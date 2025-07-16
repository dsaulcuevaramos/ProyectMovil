package com.codedev.proyectmovil.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codedev.proyectmovil.Models.AsistenciaModel;
import com.codedev.proyectmovil.Models.CursosModel;
import com.codedev.proyectmovil.R;

import java.util.List;

public class AsistenciaAdapter extends RecyclerView.Adapter<AsistenciaAdapter.ViewHolder>{

    private Context context;
    private List<AsistenciaModel> asistencia;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onEditarClick(AsistenciaModel curso);
        void onEliminarClick(AsistenciaModel curso);
        void onItemClick(CursosModel curso);
    }

    public AsistenciaAdapter(Context context, List<AsistenciaModel> asistencia, OnItemClickListener listener) {
        this.context = context;
        this.asistencia = asistencia;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtFecha, txtActivo;
        ImageButton btnEditar, btnEliminar;

        public ViewHolder(View itemView) {
            super(itemView);
            txtFecha = itemView.findViewById(R.id.txtNombreCompleto);
            txtActivo = itemView.findViewById(R.id.txtCodigo);
            btnEditar = itemView.findViewById(R.id.btnEditar);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }

        public void bind(final AsistenciaModel am, final OnItemClickListener listener) {
            txtFecha.setText(am.getFecha());
            txtActivo.setText(am.getActivo());
            btnEditar.setOnClickListener(v -> listener.onEditarClick(am));
            btnEliminar.setOnClickListener(v -> listener.onEliminarClick(am));
        }
    }

    @NonNull
    @Override
    public AsistenciaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.asistencia_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AsistenciaAdapter.ViewHolder holder, int position) {
        holder.bind(asistencia.get(position), listener);
    }

    @Override
    public int getItemCount() {return asistencia.size();}
}
