package com.example.todoapp.utils;

import android.content.Context;

import com.example.todoapp.R;
import com.example.todoapp.data.network.todoapp.error.ErrorResponse;
import com.example.todoapp.data.network.todoapp.error.ErrorResponseInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

public class ErrorCodeUtils {
    // android exception
    public static final String SOCKET_TIMEOUT_EXCEPTION = "socket_timeout_exception";
    public static final String CONNECT_EXCEPTION = "connect_exception";
    public static final String IO_EXCEPTION = "io_exception";
    public static final String ILLEGAL_ARGUMENT_EXCEPTION = "illegal_argument_exception";
    public static final String OTHER_EXCEPTION = "other_exception";

    // server error 500
    public static final String SERVER_ERROR_500 = "server_error_500";

    // unknown error
    public static final String UNKNOWN_ERROR = "unknown_error";

    public static String getCode(Throwable throwable, ErrorResponse errorResponse) {
        if (throwable != null) {
            if (throwable instanceof SocketTimeoutException) {
                return SOCKET_TIMEOUT_EXCEPTION;
            } else if (throwable instanceof ConnectException) {
                return CONNECT_EXCEPTION;
            } else if (throwable instanceof IOException) {
                return IO_EXCEPTION;
            } else if (throwable instanceof IllegalArgumentException) {
                return ILLEGAL_ARGUMENT_EXCEPTION;
            } else {
                return OTHER_EXCEPTION;
            }
        } else if (errorResponse != null) {
            int httpCode = errorResponse.getHttp_code();
            ErrorResponseInfo errorInfo = errorResponse.getError();
            String errorBodyString = errorResponse.getErrorBodyString();

            // server error 500
            if (500 <= httpCode && httpCode <= 599) {
                return SERVER_ERROR_500;
            }


            // 檢查error
            if (errorInfo != null) {
                return errorInfo.getCode();
            }

            // 檢查errors
            ErrorResponseInfo errorsInfo = getFirstErrorResponseInfoFromErrors(errorBodyString);
            if (errorsInfo != null) {
                return errorsInfo.getCode();
            }

            // 直接回傳body
            return errorBodyString;
        }

        // 未知錯誤
        return UNKNOWN_ERROR;
    }

    public static String getMessage(Context context, String code, Throwable throwable, ErrorResponse errorResponse) {
        switch (code) {
            default:
                return code;
        }
    }

    // 從errors挑第一個
    private static ErrorResponseInfo getFirstErrorResponseInfoFromErrors(String errorBody) {
        try {
            JSONObject json = new JSONObject(errorBody);
            JSONObject jsonErrors = json.getJSONObject("errors");
            JSONArray arr = jsonErrors.names();
            for (int i = 0; i < arr.length(); i++) {
                String key = arr.getString(i);
                JSONArray jsonArrInfo = jsonErrors.getJSONArray(key);
                for (int j = 0; j < jsonArrInfo.length(); j++) {
                    JSONObject jsonInfo = jsonArrInfo.getJSONObject(j);
                    ErrorResponseInfo info = new ErrorResponseInfo();
                    info.setCode(jsonInfo.getString("code"));
                    info.setMessage(jsonInfo.getString("message"));
                    return info;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 解析throttled的秒數字串
    private static String parseWaitingSeconds(String text) {
        // ex:Request was throttled. Expected available in 282 seconds.
        if (text == null) {
            return null;
        }
        String[] arr = text.split(" ");
        if (arr.length != 8) {
            return null;
        }
        try {
            Integer.valueOf(arr[6]);
            return arr[6];
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
