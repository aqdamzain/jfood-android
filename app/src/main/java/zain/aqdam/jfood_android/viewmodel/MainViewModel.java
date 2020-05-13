package zain.aqdam.jfood_android.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import zain.aqdam.jfood_android.repository.ApiClient;
import zain.aqdam.jfood_android.model.Food;
import zain.aqdam.jfood_android.repository.JFoodApiService;

public class MainViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Food>> foods = new MutableLiveData<>();

    public LiveData<ArrayList<Food>> getFoodsRequest(){
        return foods;
    }

    public void setFoodsRequest(){
        JFoodApiService jFoodApiService = ApiClient.getClient(ApiClient.JFood_URL)
                .create(JFoodApiService.class);
        Call<ArrayList<Food>> call = jFoodApiService.getAllFood();

        call.enqueue(new Callback<ArrayList<Food>>() {
            @Override
            public void onResponse(Call<ArrayList<Food>> call, retrofit2.Response<ArrayList<Food>> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                if (response.body() != null) {
                    foods.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Food>> call, Throwable t) {

            }
        });
    }

    public void setCategoryRequest(String category){
        JFoodApiService jFoodApiService = ApiClient.getClient(ApiClient.JFood_URL)
                .create(JFoodApiService.class);
        Call<ArrayList<Food>> call = jFoodApiService.getByCategory(category);

        call.enqueue(new Callback<ArrayList<Food>>() {
            @Override
            public void onResponse(Call<ArrayList<Food>> call, retrofit2.Response<ArrayList<Food>> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                if (response.body() != null) {
                    foods.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Food>> call, Throwable t) {

            }
        });
    }
}
