package zain.aqdam.jfood_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

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

                if (TextUtils.isEmpty(inputName)){
                    isEmptyFields = true;
                    edtName.setError("Name tidak boleh kosong");
                }
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
                                    Toast.makeText(RegisterActivity.this, "Register Successful",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }catch (JSONException e){
                                Toast.makeText(RegisterActivity.this, "Register Failed",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    };
                    RegisterRequest registerRequest = new RegisterRequest(inputName, inputEmail, inputPassword,
                            responseListener);
                    RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                    queue.add(registerRequest);
                }

            }
        });
    }
}
