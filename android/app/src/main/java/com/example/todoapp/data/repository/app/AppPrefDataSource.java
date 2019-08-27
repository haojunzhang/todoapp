package com.example.todoapp.data.repository.app;

import com.example.todoapp.data.repository.keystore.KeyStoreRepository;
import com.example.todoapp.utils.PrefUtils;

public class AppPrefDataSource implements AppDataSource {

    private final PrefUtils mPrefUtils;
    private final KeyStoreRepository mKeyStoreRepository;

    public AppPrefDataSource(PrefUtils mPrefUtils, KeyStoreRepository mKeyStoreRepository) {
        this.mPrefUtils = mPrefUtils;
        this.mKeyStoreRepository = mKeyStoreRepository;
    }

    @Override
    public void setIsLogin(boolean isLogin) {
        mPrefUtils.setBoolean(PrefUtils.IS_LOGIN, isLogin);
    }

    @Override
    public boolean isLogin() {
        return mPrefUtils.getBoolean(PrefUtils.IS_LOGIN);
    }

    @Override
    public void clear() {
        mPrefUtils.clear();
    }

    @Override
    public void setUserPrivateKey(String pri) {
        String encryptedPri = mKeyStoreRepository.encrypt(pri);
        mPrefUtils.setString(PrefUtils.USER_PRIVATE_KEY, encryptedPri);
    }

    @Override
    public void setUserPublicKey(String pub) {
        String encryptedPub = mKeyStoreRepository.encrypt(pub);
        mPrefUtils.setString(PrefUtils.USER_PUBLIC_KEY, encryptedPub);
    }

    @Override
    public void setIV(String iv) {
        mPrefUtils.setString(PrefUtils.IV, iv);
    }

    @Override
    public void setAESKey(String aesKey) {
        mPrefUtils.setString(PrefUtils.AES_KEY, aesKey);
    }
}
