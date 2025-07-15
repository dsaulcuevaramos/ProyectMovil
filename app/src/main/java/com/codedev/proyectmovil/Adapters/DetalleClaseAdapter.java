package com.codedev.proyectmovil.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codedev.proyectmovil.Models.Requests.DetalleClaseRequest;
import com.codedev.proyectmovil.R;

import java.util.List;

public class DetalleClaseAdapter extends RecyclerView.Adapter<DetalleClaseAdapter.Vh> {

    private Context context;
    private OnItemClickListener listener;
    private List<DetalleClaseRequest> lista;

    public interface OnItemClickListener{
        void onQuitarDetalle(int id);
    }

    public DetalleClaseAdapter(Context context, List<DetalleClaseRequest> lista, OnItemClickListener listener) {
        this.context = context;
        this.listener = listener;
        this.lista = lista;
    }

    public static class Vh extends RecyclerView.ViewHolder{

        TextView nombre, codigo;
        ImageButton btnQuitar;

        public Vh(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.txtNombrePersona);
            codigo = itemView.findViewById(R.id.txtCodigoPersona);
            btnQuitar = itemView.findViewById(R.id.btn_quitar_persona);

        }

        public void bind(final DetalleClaseRequest c, final DetalleClaseAdapter.OnItemClickListener listener){
            nombre.setText(c.getNombrePersona());
            codigo.setText("Código: " + c.getCodigo());
            btnQuitar.setVisibility(View.VISIBLE);
            btnQuitar.setOnClickListener(v-> listener.onQuitarDetalle(c.getIdDetalle()));
        }
    }

    @NonNull
    @Override
    public Vh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.detalle_item, parent, false);
        return new DetalleClaseAdapter.Vh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Vh holder, int position) {
        holder.bind(lista.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }
}
