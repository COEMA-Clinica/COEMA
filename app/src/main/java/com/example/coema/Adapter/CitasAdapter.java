package com.example.coema.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.coema.Fragments.RecyclerViewItemClickListener;
import com.example.coema.Listas.CitasOd;
import com.example.coema.R;

import java.util.List;

public class CitasAdapter extends RecyclerView.Adapter<CitasAdapter.ViewHolder> {

    private RecyclerViewItemClickListener clickListener;
    private List<CitasOd> citas;

    public CitasAdapter(List<CitasOd> citas, RecyclerViewItemClickListener listener) {
        this.citas = citas;
        this.clickListener = listener; // Inicializar el clickListener
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_citas, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CitasOd cita = citas.get(holder.getAdapterPosition());
        holder.txtNombre.setText(cita.getNom());
        holder.txtTratamiento.setText(cita.getTrat());
        holder.txtFecha.setText(String.valueOf(cita.getFec()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if (clickListener != null) {
                    clickListener.onItemClick(holder.getAdapterPosition(), cita.getId());
                //}
            }
        });
    }

    @Override
    public int getItemCount() {
        return citas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtNombre;
        public TextView txtTratamiento;
        public TextView txtFecha;

        public ViewHolder(View itemView) {
            super(itemView);
            txtNombre = itemView.findViewById(R.id.txtNombre);
            txtTratamiento = itemView.findViewById(R.id.txtTratamiento);
            txtFecha = itemView.findViewById(R.id.txtFecha);
        }
    }
}
