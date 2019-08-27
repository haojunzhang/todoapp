package com.example.todoapp.data.network.todoapp;

import com.example.todoapp.data.network.todoapp.error.ErrorResponse;
import com.example.todoapp.data.network.todoapp.error.ErrorResponseInfo;
import com.example.todoapp.utils.LogUtils;
import com.example.todoapp.utils.SignatureUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class CallBackUtil<T> implements Callback {

    public static final String TAG = "api";

    private Class<T> clz;

    public CallBackUtil(Class<T> clz) {
        this.clz = clz;
    }

    @Override
    public void onResponse(Call call, Response response) {
        LogUtils.d(TAG, "url:" + call.request().url().toString());
        LogUtils.d(TAG, "statusCode:" + response.code());

        // 錯誤分析
        if (response.errorBody() != null) {
            String errorBody = "";
            try {
                errorBody = response.errorBody().string();
                LogUtils.d(TAG, "error body:" + errorBody);
                ErrorResponse errorResponse = new Gson().fromJson(errorBody, ErrorResponse.class);
                errorResponse.setErrorBodyString(errorBody);
                onError(null, errorResponse);
                return; // error
            } catch (Exception e) {
                ErrorResponse errorResponse = new ErrorResponse();
                errorResponse.setHttp_code(response.code());
                errorResponse.setErrorBodyString(errorBody);
                onError(null, errorResponse);
                return; // error
            }
        }
        if (response.body() != null) {
            String responseStr = response.body().toString();
            LogUtils.d(TAG, "response:" + responseStr);
            LogUtils.json(responseStr);

            // 驗證
            String signature = response.headers().get("X-TODO-SIGNATURE");
            if (signature != null) {
                String serverSignPubKey = "";
                boolean verify = SignatureUtils.verify(responseStr, signature, serverSignPubKey);
                LogUtils.d(TAG, "verify:" + verify);
                if (!verify) {
                    ErrorResponse errorResponse = new ErrorResponse();
                    errorResponse.setHttp_code(response.code());
                    ErrorResponseInfo info = new ErrorResponseInfo();
                    info.setCode("verify_fail");
                    errorResponse.setError(info);
                    onError(null, errorResponse);
                    return;
                }
            }


            // 成功
            try {
                onSuccess(response.code(), response.headers(), new Gson().fromJson(response.body().toString(), clz));
            } catch (JsonSyntaxException e) {
                // 回傳的JSON剖析失敗
                onError(e.getCause(), null);
            }
            LogUtils.d(TAG, "---");
            return;
        }

        // 成功(無回傳), ex:登出..
        onSuccess(response.code(), response.headers(), new Gson().fromJson("{}", clz));
        LogUtils.d(TAG, "---");
    }

    @Override
    public void onFailure(Call call, Throwable t) {
        onError(t, null);
    }

    public abstract void onSuccess(int statusCode, Headers headers, T response);

    public abstract void onError(Throwable throwable, ErrorResponse errorResponse);
}
