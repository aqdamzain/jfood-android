package zain.aqdam.jfood_android.repository;

import android.content.Context;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import zain.aqdam.jfood_android.model.Customer;
import zain.aqdam.jfood_android.model.SessionLogin;

/**
 * provide and process user data
 */
public class UserRepository {
    /**
     * store return value of login request
     */
    private static boolean loginVal;
    /**
     * store return value of register request
     */
    private static boolean registerVal;


    /**
     * provide request for login validation from API
     * @param inputEmail email of the customer account
     * @param inputPassword password of the customer account
     */
    public static boolean loginRequest(String inputEmail, String inputPassword, Context context){
        final UserPreference userPreference = new UserPreference(context);
        final SessionLogin session = new SessionLogin();
        JFoodApiService jFoodApiService = ApiClient.getClient(ApiClient.JFood_URL)
                .create(JFoodApiService.class);
        Call<Customer> call = jFoodApiService.loginCustomer(inputEmail, inputPassword);
        call.enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                if (response.isSuccessful()) {

                    session.setCustomerId(response.body().getId());
                    userPreference.setUser(session);
                    loginVal = true;
                }else {
                    loginVal = false;
                }
            }
            @Override
            public void onFailure(Call<Customer> call, Throwable t) {
                loginVal = false;
            }
        });
        return loginVal;
    }

    /**
     * provide request for register new user from
     * API to create new customer
     * @param inputName name of new customer
     * @param inputEmail email of new customer, must be unique
     * @param inputPassword password for new customer
     */
    public static boolean registerRequest(String inputName, String inputEmail, String inputPassword) {
        JFoodApiService jFoodApiService = ApiClient.getClient(ApiClient.JFood_URL)
                .create(JFoodApiService.class);
        Call<Customer> call = jFoodApiService.registerCustomer(inputName, inputEmail,
                inputPassword);
        call.enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, retrofit2.Response<Customer> response) {
                registerVal = true;
            }
            @Override
            public void onFailure(Call<Customer> call, Throwable t) {
                registerVal = false;
            }
        });
        return registerVal;
    }
}
