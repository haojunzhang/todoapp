package com.example.todoapp.ui.login;

import android.os.Handler;
import android.os.Looper;

import com.example.todoapp.data.network.todoapp.error.ErrorResponse;
import com.example.todoapp.data.repository.app.AppRepository;
import com.example.todoapp.data.repository.keystore.KeyStoreRepository;
import com.example.todoapp.data.repository.user.UserDataSource;
import com.example.todoapp.data.repository.user.UserRepository;
import com.example.todoapp.utils.LogUtils;
import com.example.todoapp.utils.SignatureUtils;

import java.security.KeyPair;

import javax.inject.Inject;

public class LoginPresenter implements LoginContract.Presenter {

    private final LoginContract.View mView;
    private final UserRepository mUserRepository;
    private final AppRepository mAppRepository;
    private final KeyStoreRepository mKeyStoreRepository;

    @Inject
    public LoginPresenter(LoginContract.View mView, UserRepository mUserRepository,
                          AppRepository mAppRepository, KeyStoreRepository mKeyStoreRepository) {
        this.mView = mView;
        this.mUserRepository = mUserRepository;
        this.mAppRepository = mAppRepository;
        this.mKeyStoreRepository = mKeyStoreRepository;
    }

    @Override
    public void login(String email, String password) {
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

                    mUserRepository.login(email, password, new UserDataSource.LoginCallback() {
                        @Override
                        public void onLogin(String userId, String userToken) {
                            mView.dismissLoadingView();

                            // 存Id, Token
                            mUserRepository.setUserId(userId);
                            mUserRepository.setUserToken(userToken);

                            // 設為Login
                            mAppRepository.setIsLogin(true);

                            // 直接開首頁
                            mView.openMainActivity();
                            mView.finishActivity();
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
