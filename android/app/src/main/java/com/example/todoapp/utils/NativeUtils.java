package com.example.todoapp.utils;

public class NativeUtils {

    static {
        System.loadLibrary("native-lib");
    }

    public static native String getString(int i);
}
