package com.example.todoapp.data.repository.user;

import com.example.todoapp.data.network.todoapp.IDataSourceErrorCallback;

public interface UserDataSource {

    void setUserId(String userId);

    String getUserId();

    void setUserToken(String userToken);

    String getUserToken();

    void logout();

    void setEmail(String email);

    String getEmail();

    interface LoginCallbackI extends IDataSourceErrorCallback {
        void onLogin(String userId, String userToken);
    }

    void login(String email, String password, LoginCallbackI callback);



    interface SignUpCallbackI extends IDataSourceErrorCallback {
        void onSignUp(String userId, String userToken);
    }

    void signUp(String phone, String password, String otpId, String otp, SignUpCallbackI callback);

    interface VerifyEmailCallbackI extends IDataSourceErrorCallback {
        void onVerifyEmail(String otpId);
    }

    void verifyEmail(String email, VerifyEmailCallbackI callback);

    interface SendOtpCallbackI extends IDataSourceErrorCallback {
        void onSendOtp(String otpId);
    }

    void sendOtp(String email, SendOtpCallbackI callback);

    interface ResetPasswordCallbackI extends IDataSourceErrorCallback {
        void onResetPassword();
    }

    void resetPassword(String password, String otpId, String otp, ResetPasswordCallbackI callback);

    interface ResendOTPCodeCallbackI extends IDataSourceErrorCallback {
        void onResendOTPCode(String otpId);
    }

    void resendOTPCode(String otpId, ResendOTPCodeCallbackI callback);

    interface ChangePasswordCallbackI extends IDataSourceErrorCallback {
        void onChangePassword(String userToken);
    }

    void changePassword(String password, String otpId, String otp, ChangePasswordCallbackI callback);
}
