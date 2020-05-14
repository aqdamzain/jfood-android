package zain.aqdam.jfood_android.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import zain.aqdam.jfood_android.model.Seller;
import zain.aqdam.jfood_android.repository.ApiClient;
import zain.aqdam.jfood_android.model.Food;
import zain.aqdam.jfood_android.repository.JFoodApiService;

/**
 * provide and process data for MainActivity
 */
public class MainViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Food>> foods = new MutableLiveData<>();
    private ArrayList<Seller> listSeller = new ArrayList<>();
    private HashMap<Seller, ArrayList<Food>> childMapping = new HashMap<>();

    public LiveData<ArrayList<Food>> getFoodsRequest(){
        return foods;
    }

    public ArrayList<Seller> getListSeller() {
        return listSeller;
    }

    public HashMap<Seller, ArrayList<Food>> getChildMapping() {
        return childMapping;
    }

    /**
     * get list of all food data from API server
     */
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
                    inputDistinctSeller(response.body(), listSeller);
                    inputMapping(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Food>> call, Throwable t) {

            }
        });
    }

    /**
     * get list of food by category from API server
     * @param category category of the food
     */
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
                    inputDistinctSeller(response.body(), listSeller);
                    inputMapping(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Food>> call, Throwable t) {

            }
        });
    }

    /**
     * store distinct seller to the list of seller
     * @param foods list of food object
     * @param listSeller list of seller variable
     */
    private void inputDistinctSeller(ArrayList<Food> foods, ArrayList<Seller> listSeller) {
        for (Food food :
                foods) {
            if (listSeller != null) {
                boolean isSeller = false;
                //mengecheck apakah ada seller yang sama pada list
                for (Seller tSeller : listSeller) {
                    if (food.getSeller().getId() == tSeller.getId()) {
                        isSeller = true;
                    }
                }
                if (!isSeller) {
                    //memasukan seller baru ke list jika seller belum ada
                    listSeller.add(food.getSeller());
                }
            } else {
                listSeller.add(food.getSeller());
            }
        }
    }

    /**
     * mapping between list of seller and list of food
     * @param foods list of food
     */
    private void inputMapping(ArrayList<Food> foods){
        for (Seller mSeller : listSeller) {
            ArrayList<Food> foodTemp = new ArrayList<>();
            for (Food tFood : foods) {
                if (tFood.getSeller().getId() == mSeller.getId()) {
                    foodTemp.add(tFood);
                }
            }
            childMapping.put(mSeller, foodTemp);
        }
    }

}
