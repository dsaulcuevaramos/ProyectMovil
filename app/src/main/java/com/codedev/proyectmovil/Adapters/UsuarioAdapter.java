package com.codedev.proyectmovil.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.codedev.proyectmovil.Models.Requests.UsuarioRequest;
import com.codedev.proyectmovil.R;

import java.util.List;

public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioAdapter.ViewHolder>{
    private Context context;
    private List<UsuarioRequest> usuarios;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onEditarClick(int id);

        void onEliminarClick(UsuarioRequest usuario);
    }

    public UsuarioAdapter(Context context, List<UsuarioRequest> usuarios, OnItemClickListener listener) {
        this.context = context;
        this.usuarios = usuarios;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNombre, txtRol, txtUsuario;
        ImageButton btnEditar, btnEliminar;

        public ViewHolder(View itemView) {
            super(itemView);
            txtNombre = itemView.findViewById(R.id.txtNombreCompleto);
            txtRol = itemView.findViewById(R.id.txtRol);
            txtUsuario = itemView.findViewById(R.id.txtUsuario);
            btnEditar = itemView.findViewById(R.id.btnEditar);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }

        public void bind(final UsuarioRequest u, final OnItemClickListener listener) {
            String nombre = u.getNombrePersona() + " " + u.getApellidoPersona();
            txtNombre.setText(nombre);
            txtRol.setText(u.getRolNombre());
            txtUsuario.setText(u.getUsuario());

            btnEditar.setOnClickListener(v -> listener.onEditarClick(u.getIdUsuario()));
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