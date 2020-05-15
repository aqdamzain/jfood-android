package zain.aqdam.jfood_android.view;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import zain.aqdam.jfood_android.model.SessionLogin;
import zain.aqdam.jfood_android.repository.ApiClient;
import zain.aqdam.jfood_android.repository.JFoodApiService;
import zain.aqdam.jfood_android.R;
import zain.aqdam.jfood_android.model.Customer;
import zain.aqdam.jfood_android.repository.UserPreference;
import zain.aqdam.jfood_android.repository.UserRepository;

/**
 * is used to create register interface for
 * register new customer for application user
 */
public class RegisterActivity extends AppCompatActivity {

    private EditText edtName;
    private EditText edtEmail;
    private EditText edtPassword;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtName = findViewById(R.id.edt_nameRegister);
        edtEmail = findViewById(R.id.edt_emailRegister);
        edtPassword = findViewById(R.id.edt_passwordRegister);
        btnRegister = findViewById(R.id.btn_register);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputName = edtName.getText().toString().trim();
                String inputEmail = edtEmail.getText().toString().trim();
                String inputPassword = edtPassword.getText().toString().trim();
                boolean isEmptyFields = false;

                if (TextUtils.isEmpty(inputName)) {
                    isEmptyFields = true;
                    edtName.setError("Name tidak boleh kosong");
                }
                if (TextUtils.isEmpty(inputEmail)) {
                    isEmptyFields = true;
                    edtEmail.setError("Email tidak boleh kosong");
                }
                if (TextUtils.isEmpty(inputPassword)) {
                    isEmptyFields = true;
                    edtPassword.setError("Password tidak boleh kosong");
                }

                if (!isEmptyFields) {
                    registerRequest(inputName, inputEmail, inputPassword);
                }

            }
        });
    }


    /**
     * provide request for register new user from
     * API to create new customer
     * @param inputName name of new customer
     * @param inputEmail email of new customer, must be unique
     * @param inputPassword password for new customer
     */
    private void registerRequest(String inputName, String inputEmail, String inputPassword) {
        JFoodApiService jFoodApiService = ApiClient.getClient(ApiClient.JFood_URL)
                .create(JFoodApiService.class);
        Call<Customer> call = jFoodApiService.registerCustomer(inputName, inputEmail,
                inputPassword);
        call.enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, retrofit2.Response<Customer> response) {
                Toast.makeText(RegisterActivity.this, "Register Successful",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Register Failed",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

}
