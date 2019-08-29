package com.example.todoapp.utils;

public class NativeUtils {

    public static final int URL = 0;
    public static final int APP_USER_TOKEN = 1001;
    public static final int APP_USER_ENC_PUB_KEY = 1002;
    public static final int APP_USER_SIGN_PRI_KEY = 1003;

    static {
        System.loadLibrary("native-lib");
    }

    public static native String getString(int i);
}
