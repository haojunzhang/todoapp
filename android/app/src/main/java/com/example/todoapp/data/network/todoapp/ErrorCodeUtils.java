package com.example.todoapp.data.network.todoapp;

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
    private static final String SOCKET_TIMEOUT_EXCEPTION = "socket_timeout_exception";
    private static final String CONNECT_EXCEPTION = "connect_exception";
    private static final String IO_EXCEPTION = "io_exception";
    private static final String ILLEGAL_ARGUMENT_EXCEPTION = "illegal_argument_exception";
    private static final String OTHER_EXCEPTION = "other_exception";

    // server error 500
    private static final String SERVER_ERROR_500 = "server_error_500";

    // unknown error
    private static final String UNKNOWN_ERROR = "unknown_error";

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


            // check error
            if (errorInfo != null) {
                return errorInfo.getCode();
            }

            // check errors
            ErrorResponseInfo errorsInfo = getFirstErrorResponseInfoFromErrors(errorBodyString);
            if (errorsInfo != null) {
                return errorsInfo.getCode();
            }

            return errorBodyString;
        }

        return UNKNOWN_ERROR;
    }

    // get first
    private static ErrorResponseInfo getFirstErrorResponseInfoFromErrors(String errorBody) {
        try {
            JSONObject json = new JSONObject(errorBody);
            JSONObject jsonErrors = json.getJSONObject("errors");
            JSONArray arr = jsonErrors.names();
            if (arr != null && arr.length() >0){
                String key = arr.getString(0);
                JSONArray jsonArrInfo = jsonErrors.getJSONArray(key);
                if (jsonArrInfo.length() > 0) {
                    JSONObject jsonInfo = jsonArrInfo.getJSONObject(0);
                    ErrorResponseInfo info = new ErrorResponseInfo();
                    info.setCode(jsonInfo.getString("message"));
                    info.setMessage(jsonInfo.getString("message"));
                    return info;
                }
            }else{
                ErrorResponseInfo info = new ErrorResponseInfo();
                info.setCode("errors parse fail");
                return info;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
