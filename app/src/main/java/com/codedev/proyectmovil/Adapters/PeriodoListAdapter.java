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
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.chip.Chip;

import java.util.List;

public class PeriodoListAdapter extends RecyclerView.Adapter<PeriodoListAdapter.Vh>{
    private Context context;
    private List<PeriodoModel> periodos;
    private PeriodoListAdapter.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onCardClick(PeriodoModel periodo);
    }

    public PeriodoListAdapter(Context context, List<PeriodoModel> periodos, PeriodoListAdapter.OnItemClickListener listener) {
        this.context = context;
        this.periodos = periodos;
        this.listener = listener;
    }
    public static class Vh extends RecyclerView.ViewHolder {
        TextView txtNombre, txtCodigo;
        Chip chipActivo;
        ImageButton btnEditar, btnEliminar, btnActivo;
        MaterialCardView card;


        public Vh(View itemView) {
            super(itemView);
            txtCodigo = itemView.findViewById(R.id.txtCodigoPeriodo);
            txtNombre = itemView.findViewById(R.id.txtNombrePeriodo);
            chipActivo = itemView.findViewById(R.id.chip_activo);
            btnEditar = itemView.findViewById(R.id.btnEditarPeriodo);
            btnEliminar = itemView.findViewById(R.id.btnEliminarPeriodo);
            btnActivo = itemView.findViewById(R.id.btnActivo);
            card = itemView.findViewById(R.id.card_periodo);
        }

        public void bind(final PeriodoModel u, final PeriodoListAdapter.OnItemClickListener listener) {
            txtNombre.setText(u.getNombre());
            txtCodigo.setText(u.getCodigo());
            btnActivo.setVisibility(View.GONE);
            btnEditar.setVisibility(View.GONE);
            btnEliminar.setVisibility(View.GONE);

            String text = u.getActivo() == 1 ? "Activo" : "Inactivo";
            int colorRes = u.getActivo() == 1 ? R.color.colorSuccess : R.color.colorWarning;
            chipActivo.setText(text);
            chipActivo.setChipBackgroundColorResource(colorRes);
            chipActivo.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.colorOnMessage));

            card.setOnClickListener(v -> listener.onCardClick(u));
        }
    }

    @Override
    public PeriodoListAdapter.Vh onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.periodo_item,parent,false);
        return new PeriodoListAdapter.Vh(view);
    }

    @Override
    public void onBindViewHolder(PeriodoListAdapter.Vh holder, int position) {
        holder.bind(periodos.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return periodos.size();
    }
}
