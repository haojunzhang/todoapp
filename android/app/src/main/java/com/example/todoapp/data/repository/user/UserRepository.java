package com.example.todoapp.data.repository.user;

import com.example.todoapp.di.qualifier.Pref;
import com.example.todoapp.di.qualifier.Remote;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UserRepository implements UserDataSource {

    private final UserDataSource mUserPrefDataSource;
    private final UserDataSource mUserRemoteDataSource;

    private String userId;
    private String userToken;
    private String email;

    @Inject
    public UserRepository(@Pref UserDataSource mUserPrefDataSource, @Remote UserDataSource mUserRemoteDataSource) {
        this.mUserPrefDataSource = mUserPrefDataSource;
        this.mUserRemoteDataSource = mUserRemoteDataSource;
    }

    @Override
    public void setUserId(String userId) {
        // set cache
        this.userId = userId;

        // set pref
        mUserPrefDataSource.setUserId(userId);
    }

    @Override
    public String getUserId() {
        if (userId == null) {
            userId = mUserPrefDataSource.getUserId();
        }
        return userId;
    }

    @Override
    public void setUserToken(String userToken) {
        // set cache
        this.userToken = userToken;

        // set pref
        mUserPrefDataSource.setUserToken(userToken);
    }

    @Override
    public String getUserToken() {
        if (userToken == null) {
            userToken = mUserPrefDataSource.getUserToken();
        }
        return userToken;
    }

    @Override
    public void setEmail(String email) {
        // set cache
        this.email = email;

        // set pref
        mUserPrefDataSource.setEmail(email);
    }

    @Override
    public String getEmail() {
        if (email == null) {
            email = mUserPrefDataSource.getEmail();
        }
        return email;
    }

    @Override
    public void logout() {

        // 清掉快取
        userId = null;
        userToken = null;

        mUserPrefDataSource.logout();
        mUserRemoteDataSource.logout();
    }

    @Override
    public void login(String phone, String password, LoginCallback callback) {
        mUserRemoteDataSource.login(phone, password, callback);
    }

    @Override
    public void signUp(String email, String password, String otpId, String otp, SignUpCallback callback) {
        mUserRemoteDataSource.signUp(email, password, otpId, otp, callback);
    }

    @Override
    public void verifyEmail(String email, VerifyEmailCallback callback) {
        mUserRemoteDataSource.verifyEmail(email, callback);
    }

    @Override
    public void sendOtp(String email, SendOtpCallback callback) {
        mUserRemoteDataSource.sendOtp(email, callback);
    }

    @Override
    public void resetPassword(String password, String otpId, String otp, ResetPasswordCallback callback) {
        mUserRemoteDataSource.resetPassword(password, otpId, otp, callback);
    }

    @Override
    public void resendOTPCode(String otpId, ResendOTPCodeCallback callback) {
        mUserRemoteDataSource.resendOTPCode(otpId, callback);
    }

    @Override
    public void changePassword(String password, String otpId, String otp, ChangePasswordCallback callback) {
        mUserRemoteDataSource.changePassword(password, otpId, otp, callback);
    }
}
