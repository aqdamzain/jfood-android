package zain.aqdam.jfood_android.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import zain.aqdam.jfood_android.repository.ApiClient;
import zain.aqdam.jfood_android.repository.JFoodApiService;
import zain.aqdam.jfood_android.R;
import zain.aqdam.jfood_android.repository.UserPreference;
import zain.aqdam.jfood_android.model.Customer;
import zain.aqdam.jfood_android.model.SessionLogin;

/**
 * LoginActivity is used to create login interface for
 * customer to use application and saved the user session
 */
public class LoginActivity extends AppCompatActivity {

    private EditText edtPassword;
    private EditText edtEmail;
    private Button btnLogin;
    private TextView tvRegister;
    private ProgressBar progressBar;
    /**
     * interface to save login session
     */
    private UserPreference mUserPreference;
    /**
     * store session of the user
     */
    private SessionLogin session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mUserPreference = new UserPreference(this);
        session = mUserPreference.getUser();
        if(session.getCustomerId()!=0) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("ID", session);
            startActivity(intent);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_password);
        btnLogin = findViewById(R.id.btn_login);
        tvRegister = findViewById(R.id.tv_register);
        progressBar = findViewById(R.id.progressBar);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputEmail = edtEmail.getText().toString().trim();
                String inputPassword = edtPassword.getText().toString().trim();
                boolean isEmptyFields = false;

                if (TextUtils.isEmpty(inputEmail)) {
                    isEmptyFields = true;
                    edtEmail.setError("Email tidak boleh kosong");
                }
                if (TextUtils.isEmpty(inputPassword)) {
                    isEmptyFields = true;
                    edtPassword.setError("Password tidak boleh kosong");
                }

                if (!isEmptyFields) {
                    progressBar.setVisibility(View.VISIBLE);
                    loginRequest(inputEmail, inputPassword);
                }
            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(moveIntent);
            }
        });
    }

    private void loginRequest(String inputEmail, String inputPassword){
        final UserPreference userPreference = new UserPreference(this);
        final SessionLogin session = new SessionLogin();
        JFoodApiService jFoodApiService = ApiClient.getClient(ApiClient.JFood_URL)
                .create(JFoodApiService.class);
        Call<Customer> call = jFoodApiService.loginCustomer(inputEmail, inputPassword);
        call.enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    session.setCustomerId(response.body().getId());
                    userPreference.setUser(session);
                    Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                    mainIntent.putExtra("ID", session);
                    startActivity(mainIntent);
                }
            }
            @Override
            public void onFailure(Call<Customer> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(LoginActivity.this, "Login Failed",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
