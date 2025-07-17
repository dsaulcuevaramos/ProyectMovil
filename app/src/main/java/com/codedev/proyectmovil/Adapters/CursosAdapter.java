package com.codedev.proyectmovil.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.codedev.proyectmovil.Models.CursosModel;
import com.codedev.proyectmovil.R;

import java.util.List;

public  class CursosAdapter extends RecyclerView.Adapter<CursosAdapter.ViewHolder> {
    private Context context;
    private List<CursosModel> cursos;
    private OnItemClickListener listener;
    private boolean canEdit;

    public interface OnItemClickListener {
        void onEditarClick(CursosModel curso);
        void onEliminarClick(CursosModel curso);
        void onItemClick(CursosModel curso);
    }

    public CursosAdapter(Context context, List<CursosModel> cursos, boolean canEdit,CursosAdapter.OnItemClickListener listener) {
        this.context = context;
        this.cursos = cursos;
        this.canEdit = canEdit;
        this.listener = listener;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNombre, txtCodigo;
        ImageButton btnEditar, btnEliminar;

        public ViewHolder(View itemView) {
            super(itemView);
            txtNombre = itemView.findViewById(R.id.txtNombreCompleto);
            txtCodigo = itemView.findViewById(R.id.txtCodigo);
            btnEditar = itemView.findViewById(R.id.btnEditar);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }

        public void bind(final CursosModel u, final OnItemClickListener listener, boolean canEdit) {
            txtNombre.setText(u.getNombre());
            txtCodigo.setText(u.getCodigo());

            if (canEdit) {
                btnEditar .setVisibility(View.VISIBLE);
                btnEliminar.setVisibility(View.VISIBLE);
            } else {
                btnEditar .setVisibility(View.GONE);
                btnEliminar.setVisibility(View.GONE);
            }
            itemView.setOnClickListener(v -> listener.onItemClick(u));
            btnEditar .setOnClickListener(v -> listener.onEditarClick(u));
            btnEliminar.setOnClickListener(v -> listener.onEliminarClick(u));
        }
    }

    @Override
    public CursosAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cursos_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CursosAdapter.ViewHolder holder, int position) {
        holder.bind(cursos.get(position), listener, canEdit);
    }

    @Override
    public int getItemCount() {
        return cursos.size();
    }
}
