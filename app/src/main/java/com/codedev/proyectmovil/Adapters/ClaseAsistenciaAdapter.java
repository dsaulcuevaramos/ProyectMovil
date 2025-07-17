package com.codedev.proyectmovil.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codedev.proyectmovil.Models.ClaseModel;
import com.codedev.proyectmovil.Models.Requests.ClaseRequest;
import com.codedev.proyectmovil.R;

import java.util.List;

public class ClaseAsistenciaAdapter extends RecyclerView.Adapter<ClaseAsistenciaAdapter.ViewHolder>{
    private Context context;
    private List<ClaseModel> clasesAs;
    private ClaseAsistenciaAdapter.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(ClaseModel claseModel);
    }

    public ClaseAsistenciaAdapter(Context context, List<ClaseModel> clasesAs, OnItemClickListener listener) {
        this.context = context;
        this.clasesAs = clasesAs;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtPeriodo, txtGrupo;


        public ViewHolder(View itemView) {
            super(itemView);
            txtPeriodo = itemView.findViewById(R.id.txtPeriodo);
            txtGrupo = itemView.findViewById(R.id.txtNombreGrupo);
        }

        public void bind(final ClaseModel c, final OnItemClickListener listener) {
            txtPeriodo.setText(c.getIdPeriodo());
            txtGrupo.setText(c.getGrupo());
            itemView.setOnClickListener(v -> listener.onItemClick(c));
        }
    }

    @NonNull
    @Override
    public ClaseAsistenciaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.clase_curso_item, parent, false);
        return new ClaseAsistenciaAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClaseAsistenciaAdapter.ViewHolder holder, int position) {
        holder.bind(clasesAs.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return clasesAs.size();
    }
}
