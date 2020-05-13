package zain.aqdam.jfood_android.view.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import zain.aqdam.jfood_android.R;
import zain.aqdam.jfood_android.model.Invoice;
import zain.aqdam.jfood_android.model.SessionLogin;
import zain.aqdam.jfood_android.view.InvoiceActivity;

public class InvoiceListAdapter extends RecyclerView.Adapter<InvoiceListAdapter.ListViewHolder> {

    private SessionLogin session;
    private ArrayList<Invoice> invoices;

    public InvoiceListAdapter(ArrayList<Invoice> invoices, SessionLogin session){
        this.invoices = invoices;
        this.session = session;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_invoice, parent, false);
        return new InvoiceListAdapter.ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        final Invoice invoice = invoices.get(position);
        holder.itemType.setText(invoice.getPaymentType());
        holder.itemStatus.setText(invoice.getInvoiceStatus());
        holder.itemDate.setText(String.valueOf(invoice.getDate()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), InvoiceActivity.class);
                intent.putExtra("INVOICE", invoice);
                intent.putExtra("ID", session);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return invoices.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        TextView itemType;
        TextView itemStatus;
        TextView itemDate;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            itemType = itemView.findViewById(R.id.item_type);
            itemStatus = itemView.findViewById(R.id.item_status);
            itemDate = itemView.findViewById(R.id.item_date);
        }
    }
}
