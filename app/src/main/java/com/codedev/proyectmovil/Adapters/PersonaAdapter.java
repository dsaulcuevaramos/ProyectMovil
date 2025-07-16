package com.codedev.proyectmovil.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.codedev.proyectmovil.Models.PersonaModel;
import com.codedev.proyectmovil.R;

import java.util.List;

public class PersonaAdapter extends RecyclerView.Adapter<PersonaAdapter.ViewHolder>{
    private Context context;
    private List<PersonaModel> listPersonas;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
    }

    public PersonaAdapter(Context context, List<PersonaModel> listPersonas, PersonaAdapter.OnItemClickListener listener) {
        this.context = context;
        this.listPersonas = listPersonas;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView  txtCodigo, txtNombre, txtApellido;


        public ViewHolder(View itemView){
            super(itemView);
            txtCodigo = itemView.findViewById(R.id.txtCodigoPersona);
            txtNombre = itemView.findViewById(R.id.txtNombrePersona);
            txtApellido = itemView.findViewById(R.id.txtApellidoPersona);
        }
        public void bind(final PersonaModel p, final OnItemClickListener listener){
            txtCodigo.setText(p.getCodigo());
            txtNombre.setText(p.getNombre());
            txtApellido.setText(p.getApellido());
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.persona_item,parent,false);
        return new PersonaAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(listPersonas.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return listPersonas.size();
    }
}
