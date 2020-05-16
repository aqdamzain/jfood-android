package zain.aqdam.jfood_android.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import zain.aqdam.jfood_android.model.Invoice;
import zain.aqdam.jfood_android.model.SessionLogin;
import zain.aqdam.jfood_android.repository.ApiClient;
import zain.aqdam.jfood_android.repository.JFoodApiService;
import zain.aqdam.jfood_android.view.adapter.OrderListAdapter;
import zain.aqdam.jfood_android.R;
import zain.aqdam.jfood_android.model.FoodOrder;

/**
 * is used to show the interface for create new invoice
 */
public class ProcessInvoice extends AppCompatActivity implements View.OnClickListener {
    private TextView tvPromo;
    private EditText edtPromo;
    private List<FoodOrder> foodOrders;
    private String paymentType;
    private SessionLogin session;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_invoice);
        session = getIntent().getParcelableExtra("ID");
        foodOrders = getIntent().getParcelableArrayListExtra("ORDER");
        final RecyclerView rvOrder = findViewById(R.id.recyclerView);
        final TextView tvTotPrice = findViewById(R.id.tv_PriceInvoice);
        progressBar = findViewById(R.id.progressBar);
        tvPromo = findViewById(R.id.PromoLabel);
        tvPromo.setVisibility(View.GONE);
        edtPromo = findViewById(R.id.edt_promoCode);
        edtPromo.setVisibility(View.GONE);
        final Button btnMakeInvoice = findViewById(R.id.btn_makeInvoice);
        btnMakeInvoice.setOnClickListener(this);
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.viaCash:
                        tvPromo.setVisibility(View.GONE);
                        edtPromo.setVisibility(View.GONE);
                        paymentType = "Cash";
                        break;
                    case R.id.viaCashless:
                        tvPromo.setVisibility(View.VISIBLE);
                        edtPromo.setVisibility(View.VISIBLE);
                        paymentType = "Cashless";
                        break;
                }
            }
        });

        rvOrder.setLayoutManager(new LinearLayoutManager(ProcessInvoice.this));
        OrderListAdapter orderListAdapter = new OrderListAdapter(foodOrders);
        rvOrder.setAdapter(orderListAdapter);
        tvTotPrice.setText("Rp. " + String.valueOf(calculateTotalPrice(foodOrders)));

    }

    /**
     * calculate total price of all food in the cart
     * @param orders all food in the cart
     * @return
     */
    private int calculateTotalPrice(List<FoodOrder> orders){
        int tot = 0;
        for(FoodOrder order: orders){
            tot = tot + order.getPrice();
        }
        return tot;
    }

    /**
     * get list id of all foods in the cart
     * @param orders
     * @return
     */
    private ArrayList<Integer> getOrderFoodId(List<FoodOrder> orders){
        ArrayList<Integer> tot = new ArrayList<>();
        for(FoodOrder order: orders){
            tot.add(order.getFoodId());
        }
        return tot;
    }

    @Override
    public void onClick(View v) {
        String inputPromo = edtPromo.getText().toString().trim();
        switch (v.getId()){
            case R.id.btn_makeInvoice:
                progressBar.setVisibility(View.VISIBLE);
                if(paymentType=="Cash"){
                    createCashInvoiceRequest();
                }else if(paymentType=="Cashless"){
                    createCashlessInvoiceRequest(inputPromo);
                }
                break;
        }
    }

    /**
     * Provide API Request to create cash invoice
     */
    private void createCashInvoiceRequest(){
        JFoodApiService jFoodApiService = ApiClient.getClient(ApiClient.JFood_URL)
                .create(JFoodApiService.class);
        Call<Invoice> call = jFoodApiService.createCashInvoice(getOrderFoodId(foodOrders),
                session.getCustomerId());
        call.enqueue(new Callback<Invoice>() {
            @Override
            public void onResponse(Call<Invoice> call, Response<Invoice> response) {
                if(response.body() != null){
                    Toast.makeText(
                            getApplicationContext(),
                            "Pesanan Berhasil Dibuat", Toast.LENGTH_SHORT)
                            .show();
                    progressBar.setVisibility(View.GONE);
                    Intent intent = new Intent(ProcessInvoice.this, HistoryActivity.class);
                    intent.putExtra("ID", session);
                    startActivity(intent);
                }else{
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(
                            getApplicationContext(),
                            "Pesanan Gagal Dibuat", Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onFailure(Call<Invoice> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(
                        getApplicationContext(),
                        "Pesanan Gagal Dibuat", Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    /**
     * Provide API Request to create cashless invoice
     * @param inputPromo code of the promo
     */
    private void createCashlessInvoiceRequest(String inputPromo){
        JFoodApiService jFoodApiService = ApiClient.getClient(ApiClient.JFood_URL)
                .create(JFoodApiService.class);
        Call<Invoice> call = null;
        if(TextUtils.isEmpty(inputPromo)){
            call = jFoodApiService.createCashlessInvoice(getOrderFoodId(foodOrders),
                    session.getCustomerId());
        }else{
            call = jFoodApiService.createCashlessInvoice(getOrderFoodId(foodOrders),
                    session.getCustomerId(), inputPromo);
        }

        call.enqueue(new Callback<Invoice>() {
            @Override
            public void onResponse(Call<Invoice> call, Response<Invoice> response) {
                if(response.body() != null){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(
                            getApplicationContext(),
                            "Pesanan Berhasil Dibuat", Toast.LENGTH_SHORT)
                            .show();
                    Intent intent = new Intent(ProcessInvoice.this, HistoryActivity.class);
                    intent.putExtra("ID", session);
                    startActivity(intent);
                }else{
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(
                            getApplicationContext(),
                            "Pesanan Gagal Dibuat", Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onFailure(Call<Invoice> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(
                        getApplicationContext(),
                        "Pesanan Gagal Dibuat", Toast.LENGTH_SHORT)
                        .show();
            }
        });

    }
}
