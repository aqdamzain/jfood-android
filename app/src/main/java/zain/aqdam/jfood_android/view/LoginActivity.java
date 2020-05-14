package zain.aqdam.jfood_android.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import zain.aqdam.jfood_android.repository.ApiClient;
import zain.aqdam.jfood_android.repository.JFoodApiService;
import zain.aqdam.jfood_android.R;
import zain.aqdam.jfood_android.repository.UserPreference;
import zain.aqdam.jfood_android.model.Customer;
import zain.aqdam.jfood_android.model.SessionLogin;
import zain.aqdam.jfood_android.repository.UserRepository;

/**
 * LoginActivity is used to create login interface for
 * customer to use application and saved the user session
 */
public class LoginActivity extends AppCompatActivity {

    private EditText edtPassword;
    private EditText edtEmail;
    private Button btnLogin;
    private TextView tvRegister;
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
                    boolean login = UserRepository.loginRequest(inputEmail, inputPassword,
                            LoginActivity.this);
                    if(login){
                        Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                        mainIntent.putExtra("ID", session);
                        startActivity(mainIntent);
                    }else{
                        Toast.makeText(LoginActivity.this, "Login Failed",
                                Toast.LENGTH_SHORT).show();
                    }
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
}
