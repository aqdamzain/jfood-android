package zain.aqdam.jfood_android.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import zain.aqdam.jfood_android.R;
import zain.aqdam.jfood_android.model.Invoice;
import zain.aqdam.jfood_android.model.SessionLogin;
import zain.aqdam.jfood_android.repository.ApiClient;
import zain.aqdam.jfood_android.repository.JFoodApiService;
import zain.aqdam.jfood_android.repository.OrderRepository;

/**
 * user interface to show the detail of the invoice
 */
public class InvoiceActivity extends AppCompatActivity implements View.OnClickListener {
    private Invoice invoice;
    private SessionLogin session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);
        invoice = getIntent().getParcelableExtra("INVOICE");
        session = getIntent().getParcelableExtra("ID");
        final TextView tvInvoiceId = findViewById(R.id.tv_invoiceId);
        tvInvoiceId.setText(String.valueOf(invoice.getId()));
        final TextView tvInvoiceDate = findViewById(R.id.tv_invoiceDate);
        tvInvoiceDate.setText(String.valueOf(invoice.getDate()));
        final TextView tvInvoiceType = findViewById(R.id.tv_invoiceType);
        tvInvoiceType.setText(invoice.getPaymentType());
        final TextView tvInvoiceStatus = findViewById(R.id.tv_invoiceStatus);
        tvInvoiceStatus.setText(invoice.getInvoiceStatus());
        final TextView tvTotalInvoice = findViewById(R.id.tv_totalInvoice);
        tvTotalInvoice.setText(String.valueOf(invoice.getTotalPrice()));
        final Button btnPayInvoice = findViewById(R.id.btn_pay);
        final Button btnCancelInvoice = findViewById(R.id.btn_cancel);
        if(invoice.getInvoiceStatus().equals("Ongoing")){
            btnPayInvoice.setVisibility(View.VISIBLE);
            btnPayInvoice.setOnClickListener(this);
            btnCancelInvoice.setVisibility(View.VISIBLE);
            btnCancelInvoice.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        JFoodApiService jFoodApiService = ApiClient.getClient(ApiClient.JFood_URL)
                .create(JFoodApiService.class);
        Call<Invoice> call = null;
        switch (v.getId()){
            case R.id.btn_pay:
                call = jFoodApiService.changeInvoiceStatus(invoice.getId(), "Finished");
                break;
            case R.id.btn_cancel:
                call = jFoodApiService.changeInvoiceStatus(invoice.getId(), "Cancelled");
                break;
        }
        call.enqueue(new Callback<Invoice>() {
            @Override
            public void onResponse(Call<Invoice> call, Response<Invoice> response) {
                if(response.body() != null){
                    Toast.makeText(
                            getApplicationContext(), "Invoice Status Has Changed"
                            , Toast.LENGTH_SHORT)
                            .show();
                    OrderRepository repository = new OrderRepository(getApplication());
                    repository.deleteMyOrder(session.getCustomerId());
                    Intent intent = new Intent(InvoiceActivity.this, HistoryActivity.class);
                    intent.putExtra("ID", session);
                    startActivity(intent);
                }else{
                    Toast.makeText(
                            getApplicationContext(), "Failed Request to server"
                            , Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onFailure(Call<Invoice> call, Throwable t) {
                Toast.makeText(
                        getApplicationContext(), "Failed Request to server"
                        , Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }
}
