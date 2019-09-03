package com.example.todoapp.data.repository.user;

import com.example.todoapp.data.entity.UserProfile;
import com.example.todoapp.data.network.todoapp.IDataSourceErrorCallback;

public interface UserDataSource {

    void setUserId(String userId);

    String getUserId();

    void setUserToken(String userToken);

    String getUserToken();

    void logout();

    void setUserProfile(UserProfile userProfile);

    UserProfile getUserProfile();

    interface LoginCallback extends IDataSourceErrorCallback {
        void onLogin(String userId, String userToken);
    }

    void login(String email, String password, LoginCallback callback);


    interface SignUpCallback extends IDataSourceErrorCallback {
        void onSignUp(String userId, String userToken);
    }

    void signUp(String phone, String password, String otpId, String otp, SignUpCallback callback);

    interface VerifyEmailCallback extends IDataSourceErrorCallback {
        void onVerifyEmail(String otpId);
    }

    void verifyEmail(String email, VerifyEmailCallback callback);

    interface SendOtpCallback extends IDataSourceErrorCallback {
        void onSendOtp(String otpId);
    }

    void sendOtp(String email, SendOtpCallback callback);

    interface ResetPasswordCallback extends IDataSourceErrorCallback {
        void onResetPassword();
    }

    void resetPassword(String password, String otpId, String otp, ResetPasswordCallback callback);

    interface ResendOTPCodeCallback extends IDataSourceErrorCallback {
        void onResendOTPCode(String otpId);
    }

    void resendOTPCode(String otpId, ResendOTPCodeCallback callback);

    interface GetProfileCallback extends IDataSourceErrorCallback {
        void onGetProfile(String userId, String email);
    }

    void getUserProfile(GetProfileCallback callback);

    interface ChangePasswordCallback extends IDataSourceErrorCallback {
        void onChangePassword(String userToken);
    }

    void changePassword(String password, String otpId, String otp, ChangePasswordCallback callback);
}
