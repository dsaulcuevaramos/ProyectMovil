package com.codedev.proyectmovil.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.codedev.proyectmovil.Models.UsuarioModel;
import com.codedev.proyectmovil.R;

import java.util.List;

public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioAdapter.ViewHolder>{
    private Context context;
    private List<UsuarioModel> usuarios;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onEditarClick(UsuarioModel usuario);

        void onEliminarClick(UsuarioModel usuario);
    }

    public UsuarioAdapter(Context context, List<UsuarioModel> usuarios, OnItemClickListener listener) {
        this.context = context;
        this.usuarios = usuarios;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNombre, txtCorreo;
        ImageButton btnEditar, btnEliminar;

        public ViewHolder(View itemView) {
            super(itemView);
            txtNombre = itemView.findViewById(R.id.txtNombre);
            txtCorreo = itemView.findViewById(R.id.txtCorreo);
            btnEditar = itemView.findViewById(R.id.btnEditar);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }

        public void bind(final UsuarioModel u, final OnItemClickListener listener) {
            txtNombre.setText(u.getNombre());
            txtCorreo.setText(u.getCorreo());

            btnEditar.setOnClickListener(v -> listener.onEditarClick(u));
            btnEliminar.setOnClickListener(v -> listener.onEliminarClick(u));
        }
    }

    @Override
    public UsuarioAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.usuario_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UsuarioAdapter.ViewHolder holder, int position) {
        holder.bind(usuarios.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }


}