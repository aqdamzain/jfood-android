package zain.aqdam.jfood_android.repository;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import zain.aqdam.jfood_android.model.Customer;
import zain.aqdam.jfood_android.model.Food;
import zain.aqdam.jfood_android.model.Invoice;

/**
 * API URL builder for request to the server
 */
public interface JFoodApiService {
    @GET("food")
    Call<ArrayList<Food>> getAllFood();


    @POST("customer/login")
    Call<Customer> loginCustomer(@Query("email") String email,
                                 @Query("password") String password);

    @POST("customer/register")
    Call<Customer> registerCustomer(@Query("name") String name,
                                    @Query("email") String email,
                                    @Query("password") String password);

    @GET("food/category/{category}")
    Call<ArrayList<Food>> getByCategory(@Path("category") String category);

    @POST("invoice/createCashInvoice")
    Call<Invoice> createCashInvoice(@Query("foodIdList") ArrayList<Integer> foodIdList,
                                    @Query("customerId") int id);

    @POST("invoice/createCashlessInvoice")
    Call<Invoice> createCashlessInvoice(@Query("foodIdList") ArrayList<Integer> foodIdList,
                                        @Query("customerId") int id);

    @POST("invoice/createCashlessInvoice")
    Call<Invoice> createCashlessInvoice(@Query("foodIdList") ArrayList<Integer> foodIdList,
                                        @Query("customerId") int id,
                                        @Query("promoCode") String promoCode);

    @GET("invoice/customer/{customerId}")
    Call<ArrayList<Invoice>> getAllMyInvoice(@Path("customerId") int CustomerId);

    @PUT("invoice/invoiceStatus/{id}")
    Call<Invoice> changeInvoiceStatus(@Path("id") int id,
                                      @Query("status") String status);

}
