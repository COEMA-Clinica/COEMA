package com.example.coema.Adapter;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.coema.Listas.Descanso;
import com.example.coema.R;

import java.util.List;

public class DescansoAdapter extends RecyclerView.Adapter<DescansoAdapter.ViewHolder> {

    private List<Descanso> descansos;
    private int selectedDescansoId = -1; // Variable para almacenar el ID del recibo seleccionado
    private Handler handler = new Handler(Looper.getMainLooper());

    public DescansoAdapter(List<Descanso> descansos) {
        this.descansos = descansos;
    }

    public int getSelectedDescansoId() {
        return selectedDescansoId;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_descanso, parent, false);
        return new ViewHolder(view);
    }

    public void setDescanso(List<Descanso> descansos) {
        this.descansos = descansos;
        notifyDataSetChanged(); // Notificar cambios en los datos para que el RecyclerView se actualice
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (position < 0 || position >= descansos.size()) {
            // Log the error if position is out of bounds
            Log.e("DescansoAdapter", "Invalid position: " + position);
            return; // Return early to avoid further errors
        }

        Descanso descanso = descansos.get(position);

        holder.idTextView.setText(String.valueOf(descanso.getId()));
        holder.nameTextView.setText(descanso.getNombre());
        holder.dateTextView.setText(descanso.getTratamiento()); // Update this line
        holder.amountTextView.setText(String.valueOf(descanso.getDescanso()));

        // Configurar el estado del CheckBox
        holder.selectCheckbox.setOnCheckedChangeListener(null); // Desactivar el listener temporalmente
        holder.selectCheckbox.setChecked(descanso.getId() == selectedDescansoId);

        // Escuchar cambios en el estado del CheckBox
        holder.selectCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectedDescansoId = descanso.getId();
                    notifyItemRangeChanged(0, descansos.size()); // Actualizar toda la lista para deseleccionar otros items
                } else if (selectedDescansoId == descanso.getId()) {
                    selectedDescansoId = -1; // Si el item seleccionado se desmarca, reiniciar selectedDescansoId
                    notifyItemChanged(position); // Actualizar solo el item desmarcado
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return descansos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView dateTextView; // This should match the XML layout
        public TextView amountTextView;
        public TextView idTextView;
        public CheckBox selectCheckbox;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView); // Update this line
            amountTextView = itemView.findViewById(R.id.amountTextView);
            selectCheckbox = itemView.findViewById(R.id.selectCheckbox);
            idTextView = itemView.findViewById(R.id.idTextView);
        }
    }
}
