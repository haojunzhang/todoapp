package com.example.todoapp.data.network.todoapp;

import com.example.todoapp.data.network.todoapp.request.ChangePasswordRequest;
import com.example.todoapp.data.network.todoapp.request.LoginRequest;
import com.example.todoapp.data.network.todoapp.request.ResetPasswordRequest;
import com.example.todoapp.data.network.todoapp.request.SendOtpRequest;
import com.example.todoapp.data.network.todoapp.request.SignUpRequest;
import com.example.todoapp.data.repository.keystore.KeyStoreRepository;
import com.example.todoapp.utils.DateTimeUtils;
import com.example.todoapp.utils.LogUtils;
import com.example.todoapp.utils.NativeUtils;
import com.example.todoapp.utils.PrefUtils;
import com.example.todoapp.utils.SignatureUtils;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TodoService {

    public static final String TAG = "api";

    private final ITodoService mService;
    private final PrefUtils mPrefUtils;
    private final KeyStoreRepository mKeyStoreRepository;

    private String userId;
    private String userToken;
    private String userPrivateKey;
    private String userPublicKey;

    @Inject
    public TodoService(ITodoService mService, PrefUtils mPrefUtils, KeyStoreRepository mKeyStoreRepository) {
        this.mService = mService;
        this.mPrefUtils = mPrefUtils;
        this.mKeyStoreRepository = mKeyStoreRepository;
    }

    private Map<String, String> getHeaders(String userType, String token, String signature) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("X-TODO-TOKEN", userType + " " + token);
        headers.put("X-TODO-SIGNATURE", signature);
        return headers;
    }

    private String getSignature(Object obj, String key) {
        String data;
        if (obj instanceof String) {
            // URL後的參數(aaa=aaa&bbb=bbb)
            data = (String) obj;
        } else {
            // 物件(json)
            data = new GsonBuilder().disableHtmlEscaping().create().toJson(obj);
        }
        LogUtils.d(TAG, "params:" + data);

        // 簽名
        return SignatureUtils.sign(data, key);
    }

    private String getAppSignPrivate() {
        return NativeUtils.getString(NativeUtils.APP_USER_SIGN_PRI_KEY);
    }

    private String getAppEncPublic() {
        return NativeUtils.getString(NativeUtils.APP_USER_ENC_PUB_KEY);
    }

    private String getAppUserToken() {
        return NativeUtils.getString(NativeUtils.APP_USER_TOKEN);
    }

    private String getUserId() {
        if (userId == null) {
            String encryptedUserId = mPrefUtils.getString(PrefUtils.USER_ID);
            userId = mKeyStoreRepository.decrypt(encryptedUserId);
        }
        return userId;
    }

    private String getUserToken() {
        if (userToken == null) {
            String encryptedUserToken = mPrefUtils.getString(PrefUtils.USER_TOKEN);
            userToken = mKeyStoreRepository.decrypt(encryptedUserToken);
        }
        return userToken;
    }

    private String getUserPrivateKey() {
        if (userPrivateKey == null) {
            String encryptedUserPrivateKey = mPrefUtils.getString(PrefUtils.USER_PRIVATE_KEY);
            userPrivateKey = mKeyStoreRepository.decrypt(encryptedUserPrivateKey);
        }
        return userPrivateKey;
    }

    private String getUserPublicKey() {
        if (userPublicKey == null) {
            String encryptedUserPublicKey = mPrefUtils.getString(PrefUtils.USER_PUBLIC_KEY);
            userPublicKey = mKeyStoreRepository.decrypt(encryptedUserPublicKey);
        }
        return userPublicKey;
    }

    public void clearCache() {
        userId = null;
        userToken = null;
        userPrivateKey = null;
        userPublicKey = null;
    }

    public void signUp(String email, String password, String otpId, String otp, CallBackUtil callback) {
        SignUpRequest request = new SignUpRequest();
        request.setTs(DateTimeUtils.getUnixTime());
        request.setOtp_id(otpId);
        request.setOtp(otp);
        request.setEmail(email);
        request.setPassword(SignatureUtils.encryptByPublicKey(password, getAppEncPublic()));
        request.setUser_sign_pub_key(getUserPublicKey());

        mService.signUp(getHeaders(
                TodoConst.User.Type.APP_USER,
                getAppUserToken(),
                getSignature(request, getAppSignPrivate())
        ), request).enqueue(callback);
    }

    public void login(String phone, String password, CallBackUtil callback) {
        LoginRequest request = new LoginRequest();
        request.setTs(DateTimeUtils.getUnixTime());
        request.setEmail(phone);
        request.setPassword(SignatureUtils.encryptByPublicKey(password, getAppEncPublic()));
        request.setUser_sign_pub_key(getUserPublicKey());

        mService.login(getHeaders(
                TodoConst.User.Type.APP_USER,
                getAppUserToken(),
                getSignature(request, getAppSignPrivate())
        ), request).enqueue(callback);
    }

    public void logout(CallBackUtil callback) {
        String userToken = getUserToken();
        String userPrivateKey = getUserPrivateKey();

        String uts = DateTimeUtils.getUnixTime();

        String params = String.format("uts=%s", uts);

        mService.logout(getHeaders(
                TodoConst.User.Type.TODO_USER,
                userToken,
                getSignature(params, userPrivateKey)), uts).enqueue(callback);
    }

    public void sendOtp(String email, CallBackUtil callback) {
        SendOtpRequest request = new SendOtpRequest();
        request.setTs(DateTimeUtils.getUnixTime());
        request.setEmail(email);

        mService.sendOtp(getHeaders(
                TodoConst.User.Type.APP_USER,
                getAppUserToken(),
                getSignature(request, getAppSignPrivate())
        ), request).enqueue(callback);
    }

    public void changePassword(String password, String otp_id, String otp, CallBackUtil callback) {
        String userToken = getUserToken();
        String userPrivateKey = getUserPrivateKey();
        String userId = getUserId();

        ChangePasswordRequest request = new ChangePasswordRequest();
        request.setTs(DateTimeUtils.getUnixTime());
        request.setUser_id(userId);
        request.setPassword(SignatureUtils.encryptByPublicKey(password, getAppEncPublic()));
        request.setOtp_id(otp_id);
        request.setOtp(otp);

        mService.changePassword(getHeaders(
                TodoConst.User.Type.TODO_USER,
                userToken,
                getSignature(request, userPrivateKey)), userId, request).enqueue(callback);
    }

    public void resetPassword(String password, String otp_id, String otp, CallBackUtil callback) {
        ResetPasswordRequest request = new ResetPasswordRequest();
        request.setTs(DateTimeUtils.getUnixTime());
        request.setPassword(SignatureUtils.encryptByPublicKey(password, getAppEncPublic()));
        request.setOtp_id(otp_id);
        request.setOtp(otp);

        mService.resetPassword(getHeaders(
                TodoConst.User.Type.APP_USER,
                getAppUserToken(),
                getSignature(request, getAppSignPrivate())
        ), request).enqueue(callback);
    }
}
