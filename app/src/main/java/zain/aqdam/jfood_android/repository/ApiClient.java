package zain.aqdam.jfood_android.repository;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * API client for request to the server
 */
public class ApiClient {
    public static final String JFood_URL = "http://10.0.2.2:8080/";

    public static Retrofit getClient(String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
