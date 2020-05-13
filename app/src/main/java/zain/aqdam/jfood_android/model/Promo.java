package zain.aqdam.jfood_android.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Promo implements Parcelable {
    private int id;
    private String code;
    private int discount;
    private int minPrice;
    private boolean active;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(int minPrice) {
        this.minPrice = minPrice;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.code);
        dest.writeInt(this.discount);
        dest.writeInt(this.minPrice);
        dest.writeByte(this.active ? (byte) 1 : (byte) 0);
    }

    public Promo() {
    }

    protected Promo(Parcel in) {
        this.id = in.readInt();
        this.code = in.readString();
        this.discount = in.readInt();
        this.minPrice = in.readInt();
        this.active = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Promo> CREATOR = new Parcelable.Creator<Promo>() {
        @Override
        public Promo createFromParcel(Parcel source) {
            return new Promo(source);
        }

        @Override
        public Promo[] newArray(int size) {
            return new Promo[size];
        }
    };
}
