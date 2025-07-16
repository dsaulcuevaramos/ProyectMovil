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

    public ClaseAsistenciaAdapter(Context context, List<ClaseModel> clasesAs, ClaseAsistenciaAdapter.OnItemClickListener listener) {
        this.context = context;
        this.clasesAs = clasesAs;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNombre, txtFacultad, txtGrupo;
        ImageButton btnEditar, btnEliminar, btnAdministrar;

        public ViewHolder(View itemView) {
            super(itemView);
            txtNombre = itemView.findViewById(R.id.txtNombreCurso);
            txtFacultad = itemView.findViewById(R.id.txtFacultadCurso);
            txtGrupo = itemView.findViewById(R.id.txtGrupoClase);
            btnEditar = itemView.findViewById(R.id.btnEditarClase);
            btnEliminar = itemView.findViewById(R.id.btnEliminarClase);
            btnAdministrar = itemView.findViewById(R.id.btnAdministrarClase);
        }

        public void bind(final ClaseModel c, final ClaseAsistenciaAdapter.OnItemClickListener listener) {
            txtNombre.setText(c.getGrupo());
            txtFacultad.setText(c.getIdPeriodo());
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
        return 0;
    }
}
