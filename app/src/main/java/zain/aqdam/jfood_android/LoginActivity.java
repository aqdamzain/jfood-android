package zain.aqdam.jfood_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private EditText edtPassword;
    private EditText edtEmail;
    private Button btnLogin;
    private TextView tvRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

                if (TextUtils.isEmpty(inputEmail)){
                    isEmptyFields = true;
                    edtEmail.setError("Email tidak boleh kosong");
                }
                if (TextUtils.isEmpty(inputPassword)){
                    isEmptyFields = true;
                    edtPassword.setError("Password tidak boleh kosong");
                }

                if(!isEmptyFields){

                    Response.Listener<String> responseListener = new Response.Listener<String>(){
                        @Override
                        public void onResponse(String response){
                            try{
                                JSONObject jsonObject = new JSONObject(response);
                                if(jsonObject != null){
                                    Toast.makeText(LoginActivity.this, "Login Successful",
                                            Toast.LENGTH_SHORT).show();
                                    Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(mainIntent);
                                }
                            }catch (JSONException e){
                                Toast.makeText(LoginActivity.this, "Login Failed",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    };
                    LoginRequest loginRequest = new LoginRequest(inputEmail, inputPassword,
                            responseListener);
                    RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                    queue.add(loginRequest);
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
