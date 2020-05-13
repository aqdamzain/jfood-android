package zain.aqdam.jfood_android.view;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import zain.aqdam.jfood_android.repository.OrderDao;
import zain.aqdam.jfood_android.repository.OrderRepository;
import zain.aqdam.jfood_android.R;
import zain.aqdam.jfood_android.model.Food;
import zain.aqdam.jfood_android.model.FoodOrder;
import zain.aqdam.jfood_android.model.Seller;
import zain.aqdam.jfood_android.model.SessionLogin;

/**
 * User interface to show the detail of the food
 */
public class DetailMenu extends AppCompatActivity {
    private TextView tvMenuName;
    private TextView tvMenuCategory;
    private TextView tvMenuPrice;
    private TextView tvMenuSeller;
    private TextView tvMenuLocation;
    private Button btnAddToCart;
    private Food food;
    private Seller seller;
    private SessionLogin session;
    private OrderDao orderDao;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_menu);
        tvMenuName = findViewById(R.id.tv_menuName);
        tvMenuCategory = findViewById(R.id.tv_menuCategory);
        tvMenuPrice = findViewById(R.id.tv_menuPrice);
        tvMenuSeller = findViewById(R.id.tv_menuSeller);
        tvMenuLocation = findViewById(R.id.tv_menuLocation);
        btnAddToCart = findViewById(R.id.btn_addCart);
        food = getIntent().getParcelableExtra("FOOD");
        seller = getIntent().getParcelableExtra("SELLER");
        session = getIntent().getParcelableExtra("ID");
        if(food != null && seller != null){
            tvMenuName.setText(food.getName());
            tvMenuCategory.setText(food.getCategory());
            tvMenuPrice.setText(String.valueOf(food.getPrice()));
            tvMenuSeller.setText(seller.getName());
            tvMenuLocation.setText(seller.getLocation().getCity() + ", "
            + seller.getLocation().getProvince());
        }

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderRepository repository = new OrderRepository(getApplication());

                FoodOrder foodOrder = new FoodOrder(food.getId(), food.getName(),
                        food.getCategory(), food.getPrice(), session.getCustomerId());
                repository.insert(foodOrder);
                Toast.makeText(
                        getApplicationContext(), "Successfully add to cart"
                        , Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }
}
