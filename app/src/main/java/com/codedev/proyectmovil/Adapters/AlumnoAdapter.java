package com.codedev.proyectmovil.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codedev.proyectmovil.Models.AlumnoModel;
import com.codedev.proyectmovil.R;

import java.util.List;

public class AlumnoAdapter extends RecyclerView.Adapter<AlumnoAdapter.ViewHolder> {

    private Context context;
    private List<AlumnoModel> alumnos;
    private OnItemClickListener listener;

    public interface OnItemClickListener{
        void onEditarClick(AlumnoModel a);
        void onEliminarClick(AlumnoModel a);
    }

    public AlumnoAdapter(Context context, List<AlumnoModel> alumnos, OnItemClickListener listener) {
        this.context = context;
        this.alumnos = alumnos;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtCodigo, txtNombre, txtCorreo;
        ImageButton btnEditar, btnEliminar;

        public ViewHolder(View itemView){
            super(itemView);
            txtCodigo = itemView.findViewById(R.id.txtCodigo);
            txtNombre = itemView.findViewById(R.id.txtNombre);
            txtCorreo = itemView.findViewById(R.id.txtCorreo);
            btnEditar = itemView.findViewById(R.id.btnEditar);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }
        public void bind(final AlumnoModel a, final OnItemClickListener listener) {
            txtNombre.setText(a.getNombre());
            txtCorreo.setText(a.getCorreo());

            btnEditar.setOnClickListener(v -> listener.onEditarClick(a));
            btnEliminar.setOnClickListener(v -> listener.onEliminarClick(a));
        }


    }

    @NonNull
    @Override
    public AlumnoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.alumno_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlumnoAdapter.ViewHolder holder, int position) {
        holder.bind(alumnos.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return alumnos.size();
    }
}
