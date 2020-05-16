package zain.aqdam.jfood_android.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import zain.aqdam.jfood_android.R;
import zain.aqdam.jfood_android.model.Invoice;
import zain.aqdam.jfood_android.model.SessionLogin;
import zain.aqdam.jfood_android.repository.ApiClient;
import zain.aqdam.jfood_android.repository.JFoodApiService;
import zain.aqdam.jfood_android.view.adapter.InvoiceListAdapter;

/**
 * is used to show all invoice that customer has
 */
public class HistoryActivity extends AppCompatActivity {
    private ArrayList<Invoice> invoices;
    private SessionLogin session;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        progressBar = findViewById(R.id.progressBar);
        session = getIntent().getParcelableExtra("ID");
        final RecyclerView rvInvoice = findViewById(R.id.rv_invoice);
        progressBar.setVisibility(View.VISIBLE);

        JFoodApiService jFoodApiService = ApiClient.getClient(ApiClient.JFood_URL)
                .create(JFoodApiService.class);
        Call<ArrayList<Invoice>> call = jFoodApiService.getAllMyInvoice(session.getCustomerId());
        call.enqueue(new Callback<ArrayList<Invoice>>() {
            @Override
            public void onResponse(Call<ArrayList<Invoice>> call, Response<ArrayList<Invoice>> response) {
                progressBar.setVisibility(View.GONE);
                invoices = response.body();
                rvInvoice.setLayoutManager(new LinearLayoutManager(HistoryActivity.this));
                InvoiceListAdapter invoiceListAdapter = new InvoiceListAdapter(response.body(), session);
                rvInvoice.setAdapter(invoiceListAdapter);
            }

            @Override
            public void onFailure(Call<ArrayList<Invoice>> call, Throwable t) {
                Toast.makeText(HistoryActivity.this, t.getMessage(),
                        Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);

            }
        });

    }
}
