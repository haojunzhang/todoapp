package com.example.todoapp.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtils {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    private static final SimpleDateFormat sdfApi = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());

    public static String getUnixTime() {
        return String.valueOf(System.currentTimeMillis());
    }

    public static String now() {
        return sdf.format(new Date());
    }

    public static int getCurrentYear() {
        return Integer.parseInt(now().substring(0, 4));
    }

    public static int getCurrentMonth() {
        return Integer.parseInt(now().substring(5, 7));
    }

    public static int getCurrentDay() {
        return Integer.parseInt(now().substring(8, 10));
    }

    public static String getFirstDayPreviousSecond(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1, 0, 0, -1);
        return sdfApi.format(calendar.getTime());
    }

    public static String getNextMonthFirstDay(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, 1, 0, 0, 0);
        return sdfApi.format(calendar.getTime());
    }
}
