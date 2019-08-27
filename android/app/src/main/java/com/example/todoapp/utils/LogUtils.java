package com.example.todoapp.utils;

import android.util.Log;

import com.orhanobut.logger.Logger;

public class LogUtils {
    public static final String TAG = "@@@";

    public static void d(String msg){
        Log.d(TAG, msg);
    }

    public static void d(String key, String msg){
        Log.d(key, msg);
    }

    public static void json(String json) {
        Logger.json(json);
    }
}
