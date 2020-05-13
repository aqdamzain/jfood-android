package zain.aqdam.jfood_android.model;

import android.os.Parcel;
import android.os.Parcelable;

public class SessionLogin implements Parcelable {
    private int CustomerId;

    public int getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(int customerId) {
        CustomerId = customerId;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.CustomerId);
    }

    public SessionLogin() {
    }

    protected SessionLogin(Parcel in) {
        this.CustomerId = in.readInt();
    }

    public static final Parcelable.Creator<SessionLogin> CREATOR = new Parcelable.Creator<SessionLogin>() {
        @Override
        public SessionLogin createFromParcel(Parcel source) {
            return new SessionLogin(source);
        }

        @Override
        public SessionLogin[] newArray(int size) {
            return new SessionLogin[size];
        }
    };
}
