package com.example.todoapp.ui.signup;

import android.os.Handler;
import android.os.Looper;

import com.example.todoapp.data.network.todoapp.error.ErrorResponse;
import com.example.todoapp.data.repository.app.AppRepository;
import com.example.todoapp.data.repository.keystore.KeyStoreRepository;
import com.example.todoapp.data.repository.user.UserDataSource;
import com.example.todoapp.data.repository.user.UserRepository;
import com.example.todoapp.utils.SignatureUtils;
import com.example.todoapp.utils.StringUtils;

import java.security.KeyPair;

import javax.inject.Inject;

public class SignUpPresenter implements SignUpContract.Presenter {

    private final SignUpContract.View mView;
    private final UserRepository mUserRepository;
    private final AppRepository mAppRepository;
    private final KeyStoreRepository mKeyStoreRepository;

    private String otpId;

    @Inject
    public SignUpPresenter(SignUpContract.View mView, UserRepository mUserRepository,
                           AppRepository mAppRepository, KeyStoreRepository mKeyStoreRepository) {
        this.mView = mView;
        this.mUserRepository = mUserRepository;
        this.mAppRepository = mAppRepository;
        this.mKeyStoreRepository = mKeyStoreRepository;
    }

    @Override
    public void setOtpId(String otpId) {
        this.otpId = otpId;
    }

    @Override
    public void sendOtp(String email) {
        if (StringUtils.isEmpty(email)) {
            return;
        }

        mView.showLoadingView();
        mUserRepository.verifyEmail(email, new UserDataSource.VerifyEmailCallback() {
            @Override
            public void onVerifyEmail(String newOtpId) {
                mView.dismissLoadingView();
                setOtpId(newOtpId);
                mView.showSendOtpMessage();
            }

            @Override
            public void onError(Throwable throwable, ErrorResponse errorResponse) {
                mView.handleTodoPocketServiceError(throwable, errorResponse);
            }
        });
    }

    @Override
    public void signUp(String email, String password, String passwordConfirm,
                       String otp) {

        if (StringUtils.isEmpty(email, password, passwordConfirm, otp)) {
            return;
        }

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
                            mView.dismissLoadingView();

                            // 存Id, Token
                            mUserRepository.setUserId(userId);
                            mUserRepository.setUserToken(userToken);

                            // 設為Login
                            mAppRepository.setIsLogin(true);

                            // 直接開首頁
                            mView.openMainActivity();
                            mView.finishAllActivity();
                        }

                        @Override
                        public void onError(Throwable throwable, ErrorResponse errorResponse) {
                            mView.handleTodoPocketServiceError(throwable, errorResponse);
                        }
                    });
                });
            }
        }).start();
    }
}
