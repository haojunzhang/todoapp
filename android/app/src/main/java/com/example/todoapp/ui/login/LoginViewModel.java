package com.example.todoapp.ui.login;

import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.todoapp.data.network.todoapp.TodoService;
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

import javax.inject.Inject;

public class LoginViewModel extends BaseViewModel {

    private final UserRepository mUserRepository;
    private final AppRepository mAppRepository;
    private final KeyStoreRepository mKeyStoreRepository;
    private final TodoService mTodoService;

    private final MutableLiveData<Boolean> mIsLoginSuccess = new MutableLiveData<>();

    @Inject
    public LoginViewModel(UserRepository mUserRepository, AppRepository mAppRepository,
                          KeyStoreRepository mKeyStoreRepository, TodoService mTodoService) {
        this.mUserRepository = mUserRepository;
        this.mAppRepository = mAppRepository;
        this.mKeyStoreRepository = mKeyStoreRepository;
        this.mTodoService = mTodoService;
    }

    public LiveData<Boolean> isLoginSuccess() {
        return mIsLoginSuccess;
    }

    public void login(String email, String password) {
        if (StringUtils.isEmpty(email, password)) {
            return;
        }

        mIsLoading.setValue(true);
        new Thread(() -> {
            KeyPair keyPair = SignatureUtils.generateRSAKeyPair();
            if (keyPair != null) {
                // 清除快取
                mAppRepository.clear();
                mKeyStoreRepository.clearCache();
                mTodoService.clearCache();

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
                            mIsLoading.setValue(false);

                            // 存Id, Token
                            mUserRepository.setUserId(userId);
                            mUserRepository.setUserToken(userToken);

                            // 設為Login
                            mAppRepository.setIsLogin(true);

                            // 直接開首頁
                            mIsLoginSuccess.setValue(true);
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
