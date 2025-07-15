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

public class PersonaSelectAdapter extends RecyclerView.Adapter<PersonaSelectAdapter.Vh>{
    private Context context;
    private PersonaSelectAdapter.OnItemClickListener listener;
    private List<DetalleClaseRequest> lista;

    public interface OnItemClickListener{
        void onQuitarDetalle(int id);
    }

    public PersonaSelectAdapter(Context context, List<DetalleClaseRequest> lista, PersonaSelectAdapter.OnItemClickListener listener) {
        this.context = context;
        this.listener = listener;
        this.lista = lista;
    }

    public static class Vh extends RecyclerView.ViewHolder{

        TextView nombre, codigo;
        ImageButton btnAgregar;

        public Vh(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.txtNombrePersona);
            codigo = itemView.findViewById(R.id.txtCodigoPersona);
            btnAgregar = itemView.findViewById(R.id.btn_agregar_persona);

        }

        public void bind(final DetalleClaseRequest c, final PersonaSelectAdapter.OnItemClickListener listener){
            nombre.setText(c.getNombrePersona());
            codigo.setText("Código: " + c.getCodigo());
            btnAgregar.setVisibility(View.VISIBLE);
            btnAgregar.setOnClickListener(v-> listener.onQuitarDetalle(c.getIdPersona()));
        }
    }

    @NonNull
    @Override
    public PersonaSelectAdapter.Vh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.detalle_selectable_item, parent, false);
        return new PersonaSelectAdapter.Vh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonaSelectAdapter.Vh holder, int position) {
        holder.bind(lista.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }
}
