package zain.aqdam.jfood_android.repository;

import android.content.Context;
import android.content.SharedPreferences;

import zain.aqdam.jfood_android.model.SessionLogin;

/**
 * used to saved data to application cache
 */
public class UserPreference {
    private static final String PREFS_NAME = "user_pref";
    private static final String ID = "customer_id";
    private  final SharedPreferences preferences;
    public UserPreference(Context context) {
        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }
    public void setUser(SessionLogin value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(ID, value.getCustomerId());
        editor.apply();
    }
    public SessionLogin getUser() {
        SessionLogin session = new SessionLogin();
        session.setCustomerId(preferences.getInt(ID, 0));
        return session;
    }

    public void remove(){
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }
}
