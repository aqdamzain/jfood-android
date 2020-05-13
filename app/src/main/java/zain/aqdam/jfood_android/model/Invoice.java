package zain.aqdam.jfood_android.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Timestamp;
import java.util.ArrayList;

public class Invoice implements Parcelable {
    private int totalPrice;
    private int id;
    private ArrayList<Food> foods;
    private Timestamp date;
    private Customer customer;
    private String invoiceStatus;
    private int deliveryFee;
    private Promo promo;
    private String paymentType;

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Food> getFoods() {
        return foods;
    }

    public void setFoods(ArrayList<Food> foods) {
        this.foods = foods;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(String invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public int getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(int deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public Promo getPromo() {
        return promo;
    }

    public void setPromo(Promo promo) {
        this.promo = promo;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.totalPrice);
        dest.writeInt(this.id);
        dest.writeTypedList(this.foods);
        dest.writeSerializable(this.date);
        dest.writeParcelable(this.customer, flags);
        dest.writeString(this.invoiceStatus);
        dest.writeInt(this.deliveryFee);
        dest.writeParcelable(this.promo, flags);
        dest.writeString(this.paymentType);
    }

    public Invoice() {
    }

    protected Invoice(Parcel in) {
        this.totalPrice = in.readInt();
        this.id = in.readInt();
        this.foods = in.createTypedArrayList(Food.CREATOR);
        this.date = (Timestamp) in.readSerializable();
        this.customer = in.readParcelable(Customer.class.getClassLoader());
        this.invoiceStatus = in.readString();
        this.deliveryFee = in.readInt();
        this.promo = in.readParcelable(Promo.class.getClassLoader());
        this.paymentType = in.readString();
    }

    public static final Parcelable.Creator<Invoice> CREATOR = new Parcelable.Creator<Invoice>() {
        @Override
        public Invoice createFromParcel(Parcel source) {
            return new Invoice(source);
        }

        @Override
        public Invoice[] newArray(int size) {
            return new Invoice[size];
        }
    };
}
