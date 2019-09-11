package com.example.todoapp.ui.signup;

import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.todoapp.data.network.todoapp.error.ErrorResponse;
import com.example.todoapp.data.repository.app.AppRepository;
import com.example.todoapp.data.repository.keystore.KeyStoreRepository;
import com.example.todoapp.data.repository.user.UserDataSource;
import com.example.todoapp.data.repository.user.UserRepository;
import com.example.todoapp.base.BaseViewModel;
import com.example.todoapp.data.network.todoapp.ErrorCodeUtils;
import com.example.todoapp.utils.SignatureUtils;
import com.example.todoapp.utils.StringUtils;

import java.security.KeyPair;

public class SignUpViewModel extends BaseViewModel {
    private final AppRepository mAppRepository;
    private final KeyStoreRepository mKeyStoreRepository;
    private final UserRepository mUserRepository;

    private final MutableLiveData<Boolean> mShowSendOtpMessage = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mIsSignUpSuccess = new MutableLiveData<>();

    private String otpId;

    public SignUpViewModel(AppRepository mAppRepository, KeyStoreRepository mKeyStoreRepository,
                           UserRepository mUserRepository) {
        this.mAppRepository = mAppRepository;
        this.mKeyStoreRepository = mKeyStoreRepository;
        this.mUserRepository = mUserRepository;
    }

    public LiveData<Boolean> showSendOtpMessage(){
        return mShowSendOtpMessage;
    }

    public LiveData<Boolean> isSignUpSuccess(){
        return mIsSignUpSuccess;
    }

    public void sendOtp(String email){
        if (StringUtils.isEmpty(email)) {
            return;
        }

        mIsLoading.setValue(true);
        mUserRepository.verifyEmail(email, new UserDataSource.VerifyEmailCallback() {
            @Override
            public void onVerifyEmail(String newOtpId) {
                mIsLoading.setValue(false);
                SignUpViewModel.this.otpId = newOtpId;
                mShowSendOtpMessage.setValue(true);
            }

            @Override
            public void onError(Throwable throwable, ErrorResponse errorResponse) {
                mIsLoading.setValue(false);
                mErrorCode.setValue(ErrorCodeUtils.getCode(throwable, errorResponse));
            }
        });
    }

    public void signUp(String email, String password, String passwordConfirm,
                       String otp){
        if (StringUtils.isEmpty(email, password, passwordConfirm, otp)) {
            return;
        }

        mIsLoading.setValue(true);
        new Thread(() -> {
            KeyPair keyPair = SignatureUtils.generateRSAKeyPair();
            if (keyPair != null) {
                // 重新產生Pref的加解密key
                String[] ivAndAESKey = mKeyStoreRepository.generateKeyStoreRSAAndIVAndAESKey();
                mAppRepository.setIV(ivAndAESKey[0]);
                mAppRepository.setAESKey(ivAndAESKey[1]);
                mKeyStoreRepository.setIV(ivAndAESKey[0]);
                mKeyStoreRepository.setAESKey(ivAndAESKey[1]);

                // 重新產生公私鑰
                String pri = SignatureUtils.getPrivateKeyString(keyPair);
                String pub = SignatureUtils.getPublicKeyString(keyPair);
                mAppRepository.setUserPrivateKey(pri);
                mAppRepository.setUserPublicKey(pub);

                new Handler(Looper.getMainLooper()).post(() -> {
                    // in main thread

                    mUserRepository.signUp(email, password, otpId, otp, new UserDataSource.SignUpCallback() {
                        @Override
                        public void onSignUp(String userId, String userToken) {
                            mIsLoading.setValue(false);

                            // 存Id, Token
                            mUserRepository.setUserId(userId);
                            mUserRepository.setUserToken(userToken);

                            // 設為Login
                            mAppRepository.setIsLogin(true);

                            // 直接開首頁
                            mIsSignUpSuccess.setValue(true);
                        }

                        @Override
                        public void onError(Throwable throwable, ErrorResponse errorResponse) {
                            mIsLoading.setValue(false);
                            mErrorCode.setValue(ErrorCodeUtils.getCode(throwable, errorResponse));
                        }
                    });
                });
            }
        }).start();
    }
}