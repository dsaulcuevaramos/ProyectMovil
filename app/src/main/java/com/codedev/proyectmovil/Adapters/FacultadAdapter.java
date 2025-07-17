package com.codedev.proyectmovil.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codedev.proyectmovil.Models.FacultadModel;
import com.codedev.proyectmovil.Models.PersonaModel;
import com.codedev.proyectmovil.R;

import java.util.List;

public class FacultadAdapter extends RecyclerView.Adapter<FacultadAdapter.ViewHolder>{

    private Context context;
    private List<FacultadModel> listFacultad;
    private FacultadAdapter.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onEditarClick(FacultadModel facultad);
        void onEliminarClick(FacultadModel facultad);
        void onItemClick(FacultadModel facultad);
    }

    public FacultadAdapter(Context context, List<FacultadModel> listFacultad, OnItemClickListener listener) {
        this.context = context;
        this.listFacultad = listFacultad;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtCodigo, txtNombre;
        ImageButton btnEditar, btnEliminar;

        public ViewHolder(View itemView){
            super(itemView);
            txtCodigo = itemView.findViewById(R.id.txtCodigoFacultad);
            txtNombre = itemView.findViewById(R.id.txtNombreFacultad);
            btnEditar = itemView.findViewById(R.id.btnEditar);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }
        public void bind(final FacultadModel f, final OnItemClickListener listener){
            txtCodigo.setText(f.getCodigo());
            txtNombre.setText(f.getNombre());
            btnEditar.setOnClickListener(v -> listener.onEditarClick(f));
            btnEliminar.setOnClickListener(v -> listener.onEliminarClick(f));
            itemView.setOnClickListener(v -> listener.onItemClick(f));
        }
    }

    @Override
    public FacultadAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.facultad_item,parent,false);
        return new FacultadAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FacultadAdapter.ViewHolder holder, int position) {
        holder.bind(listFacultad.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return listFacultad.size();
    }

}
