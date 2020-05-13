package zain.aqdam.jfood_android.repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

/**
 * Query builder for local database
 */
import zain.aqdam.jfood_android.model.FoodOrder;

@Dao
public interface OrderDao {

    @Insert
    void insert(FoodOrder order);

    @Delete
    void delete(FoodOrder order);

    @Query("DELETE FROM food_order where customerId = :uid")
    void deleteMyOrder(int uid);

    @Query("SELECT * FROM food_order where customerId = :uid")
    LiveData<List<FoodOrder>> getMyOrder(int uid);
}
