package zain.aqdam.jfood_android.view;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.HashMap;

import zain.aqdam.jfood_android.view.adapter.MainListAdapter;
import zain.aqdam.jfood_android.R;
import zain.aqdam.jfood_android.repository.UserPreference;
import zain.aqdam.jfood_android.model.Food;
import zain.aqdam.jfood_android.model.Seller;
import zain.aqdam.jfood_android.model.SessionLogin;
import zain.aqdam.jfood_android.viewmodel.MainViewModel;

/**
 * is used to create a food menus interface
 */
public class MainActivity extends AppCompatActivity {

    MainListAdapter listAdapter;
    ExpandableListView expListView;
    private ArrayList<Seller> listSeller;
    private HashMap<Seller, ArrayList<Food>> childMapping;
    private MainViewModel mainViewModel;
    private SessionLogin session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        session = getIntent().getParcelableExtra("ID");
        expListView = findViewById(R.id.lvExp);
        mainViewModel = new ViewModelProvider(this, new ViewModelProvider
                .NewInstanceFactory()).get(MainViewModel.class);
        mainViewModel.setFoodsRequest();
        mainViewModel.getFoodsRequest().observe(MainActivity.this,
                new Observer<ArrayList<Food>>() {
                    @Override
                    public void onChanged(ArrayList<Food> foods) {
                        prepareExpList(foods);
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        if (searchManager != null) {
            final SearchView searchView = (SearchView) (menu.findItem(R.id.search)).getActionView();
            searchView.setQueryHint("Cari Category..");
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    searchView.onActionViewCollapsed();
                    mainViewModel.setCategoryRequest(query);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = null;
        switch (item.getItemId()) {
            case R.id.refresh:
                mainViewModel.setFoodsRequest();
                return true;
            case R.id.logout:
                UserPreference userPreference = new UserPreference(this);
                userPreference.remove();
                intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                return true;
            case R.id.cart:
                intent = new Intent(MainActivity.this, CartActivity.class);
                intent.putExtra("ID", session);
                startActivity(intent);
                return true;
            case R.id.history:
                intent = new Intent(MainActivity.this, HistoryActivity.class);
                intent.putExtra("ID", session);
                startActivity(intent);
                return true;
            default:
                return true;
        }
    }

    /**
     * prepare the adapter for list of food
     * @param foods
     */
    private void prepareExpList(ArrayList<Food> foods) {
        // ListView on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(
                        getApplicationContext(),
                        listSeller.get(groupPosition).getName()
                                + " : "
                                + childMapping.get(
                                listSeller.get(groupPosition)).get(
                                childPosition).getName(), Toast.LENGTH_SHORT)
                        .show();
                Intent intent = new Intent(MainActivity.this, DetailMenu.class);
                intent.putExtra("FOOD", childMapping.get(listSeller.get(groupPosition))
                        .get(childPosition));
                intent.putExtra("SELLER", listSeller.get(groupPosition));
                intent.putExtra("ID", session);
                startActivity(intent);
                return true;
            }
        });
        listSeller = new ArrayList<>();
        childMapping = new HashMap<>();
        for (Food food :
                foods) {
            inputDistinctSeller(food.getSeller(), listSeller);
        }
        //mapping seller dan list food
        for (Seller mSeller : listSeller) {
            ArrayList<Food> foodTemp = new ArrayList<>();
            for (Food tFood : foods) {
                if (tFood.getSeller().getId() == mSeller.getId()) {
                    foodTemp.add(tFood);
                }
            }
            childMapping.put(mSeller, foodTemp);
        }

        listAdapter = new MainListAdapter(MainActivity.this, listSeller, childMapping);
        expListView.setAdapter(listAdapter);
    }

    /**
     * store distinct seller to the list of seller
     * @param seller seller object
     * @param listSeller list of seller variable
     */
    private void inputDistinctSeller(Seller seller, ArrayList<Seller> listSeller) {
        if (listSeller != null) {
            boolean isSeller = false;
            //mengecheck apakah ada seller yang sama pada list
            for (Seller tSeller : listSeller) {
                if (seller.getId() == tSeller.getId()) {
                    isSeller = true;
                }
            }
            if (!isSeller) {
                //memasukan seller baru ke list jika seller belum ada
                listSeller.add(seller);
            }
        } else {
            listSeller.add(seller);
        }
    }
}