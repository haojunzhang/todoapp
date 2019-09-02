package com.example.todoapp.utils;

import android.content.SharedPreferences;

public class PrefUtils {
    public static final String AES_KEY = "aes_key";
    public static final String IV = "iv";
    public static final String USER_ID = "user_id";
    public static final String USER_TOKEN = "user_token";
    public static final String USER_PRIVATE_KEY = "user_private_key";
    public static final String USER_PUBLIC_KEY = "user_public_key";
    public static final String IS_LOGIN = "is_login";
    public static final String EMAIL = "email";

    private final SharedPreferences mSharedPreferences;

    public PrefUtils(SharedPreferences mSharedPreferences) {
        this.mSharedPreferences = mSharedPreferences;
    }

    public void setString(String key, String data) {
        mSharedPreferences.edit()
                .putString(key, data)
                .apply();
    }

    public String getString(String key) {
        return mSharedPreferences.getString(key, null);
    }

    public void setBoolean(String key, boolean data) {
        mSharedPreferences.edit()
                .putBoolean(key, data)
                .apply();
    }

    public boolean getBoolean(String key) {
        return mSharedPreferences.getBoolean(key, false);
    }

    public void clear() {
        mSharedPreferences.edit().clear().apply();
    }
}
