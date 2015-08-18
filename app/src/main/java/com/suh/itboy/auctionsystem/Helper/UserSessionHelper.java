package com.suh.itboy.auctionsystem.Helper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by itboy on 8/2/2015.
 */
public class UserSessionHelper {
    private static final String PREF_NAME = "auction_pref";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_IS_USER_LOGIN = "isUserLogin";
    private static UserSessionHelper sInstance;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private UserSessionHelper(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public static synchronized UserSessionHelper getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new UserSessionHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    //Wrap for getInstance can be used either
    public static UserSessionHelper initialize(Context context) {
        return getInstance(context);
    }

    public boolean createUserLoginSession(String name, String email) {
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_EMAIL, email);
        editor.putBoolean(KEY_IS_USER_LOGIN, true);
        return editor.commit();
    }

    public boolean logOutUser() {
        editor.clear();
        return editor.commit();
    }

    public boolean isUserLogin() {
        return preferences.getBoolean(KEY_IS_USER_LOGIN, false);
    }

    public String getUserName() {
        return preferences.getString(KEY_NAME, "");
    }

    public String getUserEmail() {
        return preferences.getString(KEY_EMAIL, "");
    }
}
