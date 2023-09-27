package com.example.coema.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coema.Modelos.Receipt;
import com.example.coema.R;
import java.util.List;

public class ReceiptAdapter extends RecyclerView.Adapter<ReceiptAdapter.ViewHolder> {

    private List<Receipt> receipts;

    private long selectedReceiptId = -1; // Variable para almacenar el ID del recibo seleccionado

    public ReceiptAdapter(List<Receipt> receipts) {
        this.receipts = receipts;
    }

    public long getSelectedReceiptId() {
        return selectedReceiptId;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recibo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Receipt receipt = receipts.get(position);

        holder.idTextView.setText(String.valueOf(receipt.getId()));
        holder.nameTextView.setText(receipt.getName());
        holder.dateTextView.setText(receipt.getDate());
        holder.amountTextView.setText(String.valueOf(receipt.getAmount()));

        // Configurar el estado del CheckBox
        holder.selectCheckbox.setChecked(receipt.getId() == selectedReceiptId);

        // Escuchar cambios en el estado del CheckBox
        holder.selectCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Actualizar el ID del recibo seleccionado antes de notificar el cambio
                    selectedReceiptId = receipt.getId();

                    // Notificar un cambio en los datos para actualizar la lista
                    notifyDataSetChanged();
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return receipts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView dateTextView;
        public TextView amountTextView;
        public TextView idTextView;
        public CheckBox selectCheckbox;


        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            amountTextView = itemView.findViewById(R.id.amountTextView);
            selectCheckbox = itemView.findViewById(R.id.selectCheckbox);
            idTextView = itemView.findViewById(R.id.idTextView);
        }
    }
}
