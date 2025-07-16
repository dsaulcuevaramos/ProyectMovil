package com.codedev.proyectmovil.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codedev.proyectmovil.Models.CursosModel;
import com.codedev.proyectmovil.R;

import java.util.List;

public class CursoClaseAdapter extends RecyclerView.Adapter<CursoClaseAdapter.ViewHolder>{
    private Context context;
    private List<CursosModel> cursos;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(CursosModel curso);
    }
    public CursoClaseAdapter(Context context, List<CursosModel> cursos, CursoClaseAdapter.OnItemClickListener listener) {
        this.context = context;
        this.cursos = cursos;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNombre, txtCodigo;


        public ViewHolder(View itemView) {
            super(itemView);
            txtNombre = itemView.findViewById(R.id.txtNombreCurso);
            txtCodigo = itemView.findViewById(R.id.txtCodigoCurso);
        }

        public void bind(final CursosModel u, final OnItemClickListener listener) {
            txtNombre.setText(u.getNombre());
            txtCodigo.setText(u.getCodigo());
            itemView.setOnClickListener(v -> listener.onItemClick(u));
        }
    }


    @NonNull
    @Override
    public CursoClaseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.asistencia_curso_item,parent,false);
        return new CursoClaseAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CursoClaseAdapter.ViewHolder holder, int position) {
        holder.bind(cursos.get(position), listener);
    }


    @Override
    public int getItemCount() {
        return cursos.size();
    }
}
