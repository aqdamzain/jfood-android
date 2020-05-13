package zain.aqdam.jfood_android.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import zain.aqdam.jfood_android.view.adapter.OrderListAdapter;
import zain.aqdam.jfood_android.repository.OrderRepository;
import zain.aqdam.jfood_android.R;
import zain.aqdam.jfood_android.repository.UserPreference;
import zain.aqdam.jfood_android.model.FoodOrder;
import zain.aqdam.jfood_android.model.SessionLogin;

/**
 * User Interface to show all food in the cart
 */
public class CartActivity extends AppCompatActivity implements View.OnClickListener {
    /**
     * interface to get list of food in the cart from database
     */
    private OrderRepository repository;
    private SessionLogin session;
    private List<FoodOrder> mFoodOrders;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        final RecyclerView rvOrder = findViewById(R.id.rv_order);
        final TextView tvTotalPrice = findViewById(R.id.tv_totPrice);
        final Button btnBuy = findViewById(R.id.btn_buy);
        btnBuy.setOnClickListener(this);
        final Button btnClear = findViewById(R.id.btn_clear);
        btnClear.setOnClickListener(this);
        session = getIntent().getParcelableExtra("ID");
        repository = new OrderRepository(getApplication());
        repository.getMyOrder(session.getCustomerId()).observe(CartActivity.this,
                new Observer<List<FoodOrder>>() {
                    @Override
                    public void onChanged(List<FoodOrder> foodOrders) {
                        if(foodOrders != null){
                            mFoodOrders = foodOrders;
                            rvOrder.setLayoutManager(new LinearLayoutManager(CartActivity.this));
                            OrderListAdapter orderListAdapter = new OrderListAdapter(mFoodOrders);
                            rvOrder.setAdapter(orderListAdapter);
                            tvTotalPrice.setText("Rp. " + String.valueOf(calculateTotalPrice(mFoodOrders)));
                        }
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.common_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = null;
        switch (item.getItemId()) {
            case R.id.logout:
                UserPreference userPreference = new UserPreference(this);
                userPreference.remove();
                intent = new Intent(CartActivity.this, LoginActivity.class);
                startActivity(intent);
                return true;
            case R.id.history:
                intent = new Intent(CartActivity.this, HistoryActivity.class);
                intent.putExtra("ID", session);
                startActivity(intent);
                return true;
            default:
                return false;
        }
    }

    /**
     * Calculate price of all food in the cart
     * @param orders list of food in the cart
     * @return
     */
    private int calculateTotalPrice(List<FoodOrder> orders){
        int tot = 0;
        for(FoodOrder order: orders){
            tot = tot + order.getPrice();
        }
        return tot;
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()){
            case R.id.btn_buy:
                intent = new Intent(CartActivity.this, ProcessInvoice.class);
                intent.putExtra("ID", session);
                intent.putExtra("ORDER", (ArrayList<FoodOrder>) mFoodOrders);
                startActivity(intent);
                break;
            case R.id.btn_clear:
                repository.deleteMyOrder(session.getCustomerId());
                intent = new Intent(CartActivity.this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }
}
