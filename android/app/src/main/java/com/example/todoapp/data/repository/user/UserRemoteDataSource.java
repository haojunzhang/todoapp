package com.example.todoapp.data.repository.user;

import com.example.todoapp.data.network.todoapp.CallBackUtil;
import com.example.todoapp.data.network.todoapp.request.ResetPasswordRequest;
import com.example.todoapp.data.network.todoapp.response.ChangePasswordResponse;
import com.example.todoapp.data.network.todoapp.response.LoginResponse;
import com.example.todoapp.data.network.todoapp.response.LogoutResponse;
import com.example.todoapp.data.network.todoapp.TodoService;
import com.example.todoapp.data.network.todoapp.error.ErrorResponse;
import com.example.todoapp.data.network.todoapp.response.ResetPasswordResponse;
import com.example.todoapp.data.network.todoapp.response.SendOtpResponse;
import com.example.todoapp.data.network.todoapp.response.SignUpResponse;
import com.example.todoapp.data.network.todoapp.response.VerifyEmailResponse;

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
    public void setEmail(String email) {
        // do nothing
    }

    @Override
    public String getEmail() {
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
    public void signUp(String email, String password, String otpId, String otp, SignUpCallback callback) {
        mTodoService.signUp(email, password, otpId, otp, new CallBackUtil<SignUpResponse>(SignUpResponse.class) {
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
    public void verifyEmail(String email, VerifyEmailCallback callback) {
        mTodoService.verifyEmail(email, new CallBackUtil<VerifyEmailResponse>(VerifyEmailResponse.class) {
            @Override
            public void onSuccess(int statusCode, Headers headers, VerifyEmailResponse response) {
                if (statusCode >= 200) {
                    callback.onVerifyEmail(response.getOtp_id());
                }
            }

            @Override
            public void onError(Throwable throwable, ErrorResponse errorResponse) {
                callback.onError(throwable, errorResponse);
            }
        });
    }

    @Override
    public void sendOtp(String email, SendOtpCallback callback) {
        mTodoService.sendOtp(email, new CallBackUtil<SendOtpResponse>(SendOtpResponse.class) {
            @Override
            public void onSuccess(int statusCode, Headers headers, SendOtpResponse response) {
                if (statusCode >= 200) {
                    callback.onSendOtp(response.getOtp_id());
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
        mTodoService.resetPassword(password, otpId, otp, new CallBackUtil<ResetPasswordResponse>(ResetPasswordResponse.class) {
            @Override
            public void onSuccess(int statusCode, Headers headers, ResetPasswordResponse response) {
                if (statusCode >= 200){
                    callback.onResetPassword();
                }
            }

            @Override
            public void onError(Throwable throwable, ErrorResponse errorResponse) {
                callback.onError(throwable, errorResponse);
            }
        });
    }

    @Override
    public void resendOTPCode(String otpId, ResendOTPCodeCallback callback) {

    }
}
