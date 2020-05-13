package zain.aqdam.jfood_android.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "food_order")
public class FoodOrder implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int foodId;
    private String name;
    private String category;
    private int price;
    private int customerId;

    public FoodOrder(int foodId, String name, String category, int price, int customerId) {
        this.foodId = foodId;
        this.name = name;
        this.category = category;
        this.price = price;
        this.customerId = customerId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getFoodId() {
        return foodId;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public int getPrice() {
        return price;
    }

    public int getCustomerId() {
        return customerId;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.foodId);
        dest.writeString(this.name);
        dest.writeString(this.category);
        dest.writeInt(this.price);
        dest.writeInt(this.customerId);
    }

    protected FoodOrder(Parcel in) {
        this.id = in.readInt();
        this.foodId = in.readInt();
        this.name = in.readString();
        this.category = in.readString();
        this.price = in.readInt();
        this.customerId = in.readInt();
    }

    public static final Parcelable.Creator<FoodOrder> CREATOR = new Parcelable.Creator<FoodOrder>() {
        @Override
        public FoodOrder createFromParcel(Parcel source) {
            return new FoodOrder(source);
        }

        @Override
        public FoodOrder[] newArray(int size) {
            return new FoodOrder[size];
        }
    };

}
