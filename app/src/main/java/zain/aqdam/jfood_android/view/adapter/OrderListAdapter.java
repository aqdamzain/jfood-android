package zain.aqdam.jfood_android.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import zain.aqdam.jfood_android.R;
import zain.aqdam.jfood_android.model.FoodOrder;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ListViewHolder> {
    private List<FoodOrder> foodOrders;
    public OrderListAdapter(List<FoodOrder> foodOrders) {
        this.foodOrders = foodOrders;
    }
    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderListAdapter.ListViewHolder holder, int position) {

        final FoodOrder foodOrder = foodOrders.get(position);
        holder.tvName.setText(foodOrder.getName());
        holder.tvCategory.setText(foodOrder.getCategory());
        holder.tvPrice.setText("Rp. " + String.valueOf(foodOrder.getPrice()));

    }

    @Override
    public int getItemCount() {
        return foodOrders.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvCategory;
        TextView tvPrice;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.item_name);
            tvCategory = itemView.findViewById(R.id.item_category);
            tvPrice = itemView.findViewById(R.id.item_price);
        }
    }
}
