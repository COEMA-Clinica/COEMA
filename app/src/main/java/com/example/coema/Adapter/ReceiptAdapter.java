package com.example.coema.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coema.Modelos.Receipt;
import com.example.coema.R;
import java.util.List;

public class ReceiptAdapter extends RecyclerView.Adapter<ReceiptAdapter.ViewHolder> {

    private List<Receipt> receipts;

    public ReceiptAdapter(List<Receipt> receipts) {
        this.receipts = receipts;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_recibos, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Receipt receipt = receipts.get(position);

        holder.nameTextView.setText(receipt.getName());
        holder.dateTextView.setText(receipt.getDate());
        holder.amountTextView.setText(String.valueOf(receipt.getAmount()));


    }

    @Override
    public int getItemCount() {
        return receipts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView dateTextView;
        public TextView amountTextView;
        public CheckBox selectCheckbox;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            amountTextView = itemView.findViewById(R.id.amountTextView);
            selectCheckbox = itemView.findViewById(R.id.selectCheckbox);
        }
    }
}
