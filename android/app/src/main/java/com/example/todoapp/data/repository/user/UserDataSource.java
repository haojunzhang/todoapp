package com.example.todoapp.data.repository.user;

import com.example.todoapp.data.network.todoapp.DataSourceErrorCallback;

public interface UserDataSource {

    void setUserId(String userId);

    String getUserId();

    void setUserToken(String userToken);

    String getUserToken();

    void logout();

    void setEmail(String email);

    String getEmail();

    interface LoginCallback extends DataSourceErrorCallback {
        void onLogin(String userId, String userToken);
    }

    void login(String email, String password, LoginCallback callback);



    interface SignUpCallback extends DataSourceErrorCallback {
        void onSignUp(String userId, String userToken);
    }

    void signUp(String phone, String password, String otpId, String otp, SignUpCallback callback);

    interface SendOtpCallback extends DataSourceErrorCallback {
        void onSendOtp(String otpId);
    }

    void sendOtp(String email, SendOtpCallback callback);

    interface ResetPasswordCallback extends DataSourceErrorCallback {
        void onResetPassword();
    }

    void resetPassword(String password, String otpId, String otp, ResetPasswordCallback callback);

    interface ResendOTPCodeCallback extends DataSourceErrorCallback {
        void onResendOTPCode(String otpId);
    }

    void resendOTPCode(String otpId, ResendOTPCodeCallback callback);

    interface ChangePasswordCallback extends DataSourceErrorCallback {
        void onChangePassword(String userToken);
    }

    void changePassword(String password, String otpId, String otp, ChangePasswordCallback callback);
}
