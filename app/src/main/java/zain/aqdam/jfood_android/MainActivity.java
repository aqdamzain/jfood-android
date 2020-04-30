package zain.aqdam.jfood_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Seller> listSeller = new ArrayList<>();
    private ArrayList<Food> foodIdList = new ArrayList<>();
    private HashMap<Seller, ArrayList<Food>> childMapping = new HashMap<>();
    MainListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    Button btnRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        expListView = (ExpandableListView) findViewById(R.id.lvExp);
        btnRefresh = findViewById(R.id.btn_refresh);
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshList();
            }
        });
        prepareListData();
        listAdapter = new MainListAdapter(MainActivity.this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);
    }

    protected  void refreshList(){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonResponse = new JSONArray(response);

                    for(int i = 0; i < jsonResponse.length(); i++){
                        JSONObject food = jsonResponse.getJSONObject(i);
                        JSONObject seller = food.getJSONObject("seller");
                        JSONObject location = seller.getJSONObject("location");

                        Seller sellerObj = new Seller(seller.getInt("id"),
                                seller.getString("name"), seller.getString("email"), seller.getString("phoneNumber"),
                                new Location(location.getString("province"), location.getString("description"), location. getString("city"))
                        );
                        listSeller.add(sellerObj);
                        foodIdList.add(new Food(food.getInt("id"),
                                food.getInt("price"), food.getString("name"), food.getString("category"), sellerObj)
                        );
                    }

                    for(Seller sel: listSeller){
                        ArrayList<Food> temp = new ArrayList<>();
                        for(Food food: foodIdList){
                            if(food.getSeller().getName().equals(sel.getName()) || food.getSeller().getEmail().equals(sel.getEmail()) || food.getSeller().getPhoneNumber().equals(sel.getPhoneNumber())){
                                temp.add(food);
                            }
                        }
                        childMapping.put(sel, temp);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        MenuRequest menuRequest = new MenuRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(menuRequest);
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        for (Seller seller : listSeller) {
            listDataHeader.add(seller.getName());
            List<String> foods = new ArrayList<String>();
            for(Food food : foodIdList){
                if(food.getSeller().getName()==seller.getName()){
                    foods.add(food.getName());
                }
            }
            listDataChild.put(seller.getName(), foods);
        }
    }
}