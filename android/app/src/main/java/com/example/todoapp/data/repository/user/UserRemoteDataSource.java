package com.example.todoapp.data.repository.user;

import com.example.todoapp.data.network.todoapp.CallBackUtil;
import com.example.todoapp.data.network.todoapp.response.ChangePasswordResponse;
import com.example.todoapp.data.network.todoapp.response.LoginResponse;
import com.example.todoapp.data.network.todoapp.response.LogoutResponse;
import com.example.todoapp.data.network.todoapp.TodoService;
import com.example.todoapp.data.network.todoapp.error.ErrorResponse;
import com.example.todoapp.data.network.todoapp.response.SendOTPCodeResponse;
import com.example.todoapp.data.network.todoapp.response.SignUpResponse;

import okhttp3.Headers;

public class UserRemoteDataSource implements UserDataSource {

    private final TodoService mTodoService;

    public UserRemoteDataSource(TodoService mTodoService) {
        this.mTodoService = mTodoService;
    }

    @Override
    public void setUserId(String userId) {
        // do nothing
    }

    @Override
    public String getUserId() {
        // do nothing
        return null;
    }

    @Override
    public void setUserToken(String userToken) {
        // do nothing
    }

    @Override
    public String getUserToken() {
        // do nothing
        return null;
    }

    @Override
    public void logout() {
        mTodoService.logout(new CallBackUtil<LogoutResponse>(LogoutResponse.class) {
            @Override
            public void onSuccess(int statusCode, Headers headers, LogoutResponse response) {

            }

            @Override
            public void onError(Throwable throwable, ErrorResponse errorResponse) {

            }
        });
    }

    @Override
    public void login(String phone, String password, LoginCallback callback) {
        mTodoService.login(phone, password, new CallBackUtil<LoginResponse>(LoginResponse.class) {
            @Override
            public void onSuccess(int statusCode, Headers headers, LoginResponse response) {
                if (statusCode >= 200) {
                    callback.onLogin(response.getUser_id(), response.getUser_token());
                }
            }

            @Override
            public void onError(Throwable throwable, ErrorResponse errorResponse) {
                callback.onError(throwable, errorResponse);
            }
        });
    }

    @Override
    public void signUp(String phone, String password, String otpId, String otp, SignUpCallback callback) {
        mTodoService.signUp(phone, password, otpId, otp, new CallBackUtil<SignUpResponse>(SignUpResponse.class) {
            @Override
            public void onSuccess(int statusCode, Headers headers, SignUpResponse response) {
                if (statusCode >= 200) {
                    callback.onSignUp(response.getUser_id(), response.getUser_token());
                }
            }

            @Override
            public void onError(Throwable throwable, ErrorResponse errorResponse) {
                callback.onError(throwable, errorResponse);
            }
        });
    }

    @Override
    public void sendOTPCode(String phone, String deviceId, SendOTPCodeCallback callback) {
        mTodoService.sendOTPCode(phone, deviceId, new CallBackUtil<SendOTPCodeResponse>(SendOTPCodeResponse.class) {
            @Override
            public void onSuccess(int statusCode, Headers headers, SendOTPCodeResponse response) {
                if (statusCode >= 200) {
                    callback.onSendOTPCode(response.getOtp_id());
                }
            }

            @Override
            public void onError(Throwable throwable, ErrorResponse errorResponse) {
                callback.onError(throwable, errorResponse);
            }
        });
    }

    @Override
    public void changePassword(String password, String otpId, String otp, ChangePasswordCallback callback) {
        mTodoService.changePassword(password, otpId, otp, new CallBackUtil<ChangePasswordResponse>(ChangePasswordResponse.class) {
            @Override
            public void onSuccess(int statusCode, Headers headers, ChangePasswordResponse response) {
                if (statusCode >= 200) {
                    callback.onChangePassword(response.getUser_token());
                }
            }

            @Override
            public void onError(Throwable throwable, ErrorResponse errorResponse) {
                callback.onError(throwable, errorResponse);
            }
        });
    }

    @Override
    public void resetPassword(String password, String otpId, String otp, ResetPasswordCallback callback) {

    }

    @Override
    public void resendOTPCode(String otpId, ResendOTPCodeCallback callback) {

    }
}
