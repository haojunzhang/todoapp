package com.example.todoapp.data.repository.user;

import com.example.todoapp.data.entity.UserProfile;
import com.example.todoapp.data.repository.keystore.KeyStoreRepository;
import com.example.todoapp.utils.PrefUtils;
import com.google.gson.Gson;

public class UserPrefDataSource implements UserDataSource {

    private final PrefUtils mPrefUtils;
    private final KeyStoreRepository mKeyStoreRepository;

    public UserPrefDataSource(PrefUtils mPrefUtils, KeyStoreRepository mKeyStoreRepository) {
        this.mPrefUtils = mPrefUtils;
        this.mKeyStoreRepository = mKeyStoreRepository;
    }

    @Override
    public void setUserId(String userId) {
        String encryptedUserId = mKeyStoreRepository.encrypt(userId);
        mPrefUtils.setString(PrefUtils.USER_ID, encryptedUserId);
    }

    @Override
    public String getUserId() {
        String encryptedUserId = mPrefUtils.getString(PrefUtils.USER_ID);
        return mKeyStoreRepository.decrypt(encryptedUserId);
    }

    @Override
    public void setUserToken(String userToken) {
        String encryptedUserToken = mKeyStoreRepository.encrypt(userToken);
        mPrefUtils.setString(PrefUtils.USER_TOKEN, encryptedUserToken);
    }

    @Override
    public String getUserToken() {
        String encryptedUserToken = mPrefUtils.getString(PrefUtils.USER_TOKEN);
        return mKeyStoreRepository.decrypt(encryptedUserToken);
    }

    @Override
    public void setUserProfile(UserProfile userProfile) {
        String json = new Gson().toJson(userProfile);
        String encryptedUserProfile = mKeyStoreRepository.encrypt(json);
        mPrefUtils.setString(PrefUtils.USER_PROFILE, encryptedUserProfile);
    }

    @Override
    public UserProfile getUserProfile() {
        String encryptedUserProfile = mPrefUtils.getString(PrefUtils.USER_PROFILE);
        String json = mKeyStoreRepository.decrypt(encryptedUserProfile);
        UserProfile userProfile = new Gson().fromJson(json, UserProfile.class);
        if (userProfile == null) {
            userProfile = new UserProfile();
        }
        return userProfile;
    }

    @Override
    public void logout() {

    }

    @Override
    public void login(String phone, String password, LoginCallback callback) {
        // do nothing
    }

    @Override
    public void signUp(String phone, String password, String otpId, String otp, SignUpCallback callback) {
        // do nothing
    }

    @Override
    public void verifyEmail(String email, VerifyEmailCallback callback) {
        // do nothing
    }

    @Override
    public void sendOtp(String email, SendOtpCallback callback) {
        // do nothing
    }

    @Override
    public void resetPassword(String password, String otpId, String otp, ResetPasswordCallback callback) {
        // do nothing
    }

    @Override
    public void resendOTPCode(String otpId, ResendOTPCodeCallback callback) {
        // do nothing
    }

    @Override
    public void getUserProfile(GetProfileCallback callback) {
        // do nothing
    }

    @Override
    public void changePassword(String password, String otpId, String otp, ChangePasswordCallback callback) {
        // do nothing
    }
}
