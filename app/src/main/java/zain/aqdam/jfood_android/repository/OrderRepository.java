package zain.aqdam.jfood_android.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import zain.aqdam.jfood_android.model.FoodOrder;

/**
 * Used to request data to local database
 */
public class OrderRepository {
    private OrderDao orderDao;

    public OrderRepository(Application application){
        OrderDatabase database = OrderDatabase.getInstance(application);
        orderDao = database.orderDao();
    }

    public void insert(FoodOrder foodOrder){
        new InsertOrderAsyncTask(orderDao).execute(foodOrder);
    }

    public void deleteMyOrder(int id){
        new DeleteMyOrderAsyncTask(orderDao).execute(id);
    }

    public LiveData<List<FoodOrder>> getMyOrder(int id){
        return orderDao.getMyOrder(id);
    }

    public static class InsertOrderAsyncTask extends AsyncTask<FoodOrder, Void, Void> {
        private OrderDao orderDao;
        private InsertOrderAsyncTask(OrderDao orderDao){
            this.orderDao = orderDao;
        }
        @Override
        protected Void doInBackground(FoodOrder... foodOrders) {
            orderDao.insert(foodOrders[0]);
            return null;
        }
    }

    public static class DeleteMyOrderAsyncTask extends AsyncTask<Integer, Void, Void> {
        private OrderDao orderDao;
        private DeleteMyOrderAsyncTask(OrderDao orderDao){
            this.orderDao = orderDao;
        }
        @Override
        protected Void doInBackground(Integer... integers) {
            orderDao.deleteMyOrder(integers[0]);
            return null;
        }
    }

}
