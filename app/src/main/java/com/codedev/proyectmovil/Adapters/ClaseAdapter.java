package com.codedev.proyectmovil.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.codedev.proyectmovil.Models.ClaseModel;
import com.codedev.proyectmovil.Models.Requests.ClaseRequest;
import com.codedev.proyectmovil.R;

import java.util.List;

public class ClaseAdapter extends RecyclerView.Adapter<ClaseAdapter.ViewHolder> {
    private Context context;
    private List<ClaseRequest> clases;

    private ClaseAdapter.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onEditarClick(int id);

        void onEliminarClick(ClaseRequest clase);

        void onAdministrarClick(int id);

        void onItemClick(int idcurso);
    }

    public ClaseAdapter(Context context, List<ClaseRequest> clases, ClaseAdapter.OnItemClickListener listener) {
        this.context = context;
        this.clases = clases;
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

        public void bind(final ClaseRequest c, final ClaseAdapter.OnItemClickListener listener) {

            txtNombre.setText(c.getNombreCurso());
            txtFacultad.setText(c.getNombreFacultad());
            txtGrupo.setText("Grupo " + c.getGrupo());

            btnEditar.setOnClickListener(v -> listener.onEditarClick(c.getIdClase()));
            btnEliminar.setOnClickListener(v -> listener.onEliminarClick(c));
            btnAdministrar.setOnClickListener(v -> listener.onAdministrarClick(c.getIdClase()));
        }
    }

    @Override
    public ClaseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.clase_item, parent, false);
        return new ClaseAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ClaseAdapter.ViewHolder holder, int position) {
        holder.bind(clases.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return clases.size();
    }
}
