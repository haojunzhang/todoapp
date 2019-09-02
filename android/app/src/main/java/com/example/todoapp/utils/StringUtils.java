package com.example.todoapp.utils;

import android.text.TextUtils;
import android.util.Base64;

import androidx.core.util.PatternsCompat;

import com.google.gson.GsonBuilder;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Pattern;

public class StringUtils {

    public static String decodeUnicode(String text) {
        return new String(text.getBytes());
    }

    public static String toBase64(byte[] bytes) {
        return Base64.encodeToString(bytes, Base64.NO_WRAP);
    }

    public static String toJson(Object obj) {
        return new GsonBuilder().disableHtmlEscaping().create().toJson(obj);
    }

    public static String toAmountFormat(String text) {
        return new BigDecimal(text).setScale(3, BigDecimal.ROUND_FLOOR).toString();
    }

    public static String toDisplayTime(String text) {
        try {
            SimpleDateFormat sdfFrom = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
            sdfFrom.setTimeZone(TimeZone.getTimeZone("UTC"));
            SimpleDateFormat sdfTo = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            return sdfTo.format(sdfFrom.parse(text));
        } catch (Exception ignored) {
            return text;
        }
    }

    public static boolean isValidEmailAddress(String email) {
        if (email == null) {
            return false;
        }

        return PatternsCompat.EMAIL_ADDRESS
                .matcher(email)
                .matches();
    }

    public static boolean isValidOtp(String otp) {
        if (otp == null || otp.length() != 6) {
            return false;
        }
        return Pattern.compile("[0-9]{6}")
                .matcher(otp)
                .matches();
    }


    public static boolean isValidPassword(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }
        return Pattern.compile("(?=.*[a-zA-Z])(?=.*[0-9]).{8,}")
                .matcher(password)
                .matches();
    }

    public static boolean isEmpty(String... texts) {
        for (String t : texts) {
            if (TextUtils.isEmpty(t)) {
                return true;
            }
        }
        return false;
    }
}
