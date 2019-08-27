package com.example.todoapp.data.repository.keystore;

import com.example.todoapp.di.qualifier.Default;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class KeyStoreRepository implements KeyStoreDataSource {

    private final KeyStoreDataSource mKeyStoreDefaultDataSource;

    @Inject
    public KeyStoreRepository(@Default KeyStoreDataSource mKeyStoreDefaultDataSource) {
        this.mKeyStoreDefaultDataSource = mKeyStoreDefaultDataSource;
    }

    @Override
    public String[] generateKeyStoreRSAAndIVAndAESKey() {
        return mKeyStoreDefaultDataSource.generateKeyStoreRSAAndIVAndAESKey();
    }

    @Override
    public void setIV(String iv) {
        mKeyStoreDefaultDataSource.setIV(iv);
    }

    @Override
    public void setAESKey(String aesKey) {
        mKeyStoreDefaultDataSource.setAESKey(aesKey);
    }

    @Override
    public void clearCache() {
        mKeyStoreDefaultDataSource.clearCache();
    }

    @Override
    public String decrypt(String text) {
        return mKeyStoreDefaultDataSource.decrypt(text);
    }

    @Override
    public String encrypt(String text) {
        return mKeyStoreDefaultDataSource.encrypt(text);
    }
}