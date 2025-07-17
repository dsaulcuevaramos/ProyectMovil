package com.codedev.proyectmovil.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.codedev.proyectmovil.Models.PeriodoModel;
import com.codedev.proyectmovil.R;
import com.google.android.material.chip.Chip;

import java.util.List;

public class PeriodoAdapter extends RecyclerView.Adapter<PeriodoAdapter.Vh>{

    private Context context;
    private List<PeriodoModel> periodos;
    private PeriodoAdapter.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onEditarClick(PeriodoModel periodo);
        void onEliminarClick(PeriodoModel periodo);
        void onActivoClick(PeriodoModel periodo);
    }

    public PeriodoAdapter(Context context, List<PeriodoModel> periodos, PeriodoAdapter.OnItemClickListener listener) {
        this.context = context;
        this.periodos = periodos;
        this.listener = listener;
    }
    public static class Vh extends RecyclerView.ViewHolder {
        TextView txtNombre, txtCodigo;
        Chip chipActivo;
        ImageButton btnEditar, btnEliminar, btnActivo;


        public Vh(View itemView) {
            super(itemView);
            txtCodigo = itemView.findViewById(R.id.txtCodigoPeriodo);
            txtNombre = itemView.findViewById(R.id.txtNombrePeriodo);
            chipActivo = itemView.findViewById(R.id.chip_activo);
            btnEditar = itemView.findViewById(R.id.btnEditarPeriodo);
            btnEliminar = itemView.findViewById(R.id.btnEliminarPeriodo);
            btnActivo = itemView.findViewById(R.id.btnActivo);
        }

        public void bind(final PeriodoModel u, final PeriodoAdapter.OnItemClickListener listener) {
            txtNombre.setText(u.getNombre());
            txtCodigo.setText(u.getCodigo());

            String text = u.getActivo() == 1 ? "Activo" : "Inactivo";
            int colorRes = u.getActivo() == 1 ? R.color.colorSuccess : R.color.colorWarning;
            chipActivo.setText(text);
            chipActivo.setChipBackgroundColorResource(colorRes);
            chipActivo.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.colorOnMessage));
            btnActivo.setImageDrawable(ContextCompat.getDrawable(itemView.getContext(),
                    u.getActivo() == 1 ? R.drawable.ic_toggle_on : R.drawable.ic_toggle_off));

            btnEditar.setOnClickListener(v -> listener.onEditarClick(u));
            btnEliminar.setOnClickListener(v -> listener.onEliminarClick(u));
            btnActivo.setOnClickListener(v -> listener.onActivoClick(u));
        }
    }

    @Override
    public PeriodoAdapter.Vh onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.periodo_item,parent,false);
        return new PeriodoAdapter.Vh(view);
    }

    @Override
    public void onBindViewHolder(Vh holder, int position) {
        holder.bind(periodos.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return periodos.size();
    }
}
