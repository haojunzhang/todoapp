package com.example.todoapp.data.repository.app;

import com.example.todoapp.di.qualifier.Pref;
import com.example.todoapp.di.qualifier.Remote;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AppRepository implements AppDataSource {

    private AppDataSource mAppPrefDataSource;
    private AppDataSource mAppRemoteDataSource;

    private Boolean isLogin;

    @Inject
    public AppRepository(@Pref AppDataSource mAppPrefDataSource, @Remote AppDataSource mAppRemoteDataSource) {
        this.mAppPrefDataSource = mAppPrefDataSource;
        this.mAppRemoteDataSource = mAppRemoteDataSource;
    }

    @Override
    public void setIsLogin(boolean isLogin) {
        this.isLogin = isLogin;
        mAppPrefDataSource.setIsLogin(isLogin);
    }

    @Override
    public boolean isLogin() {
        if (isLogin == null) {
            isLogin = mAppPrefDataSource.isLogin();
        }
        return isLogin;
    }

    @Override
    public void clear() {
        isLogin = null;

        mAppPrefDataSource.clear();
    }

    @Override
    public void setUserPrivateKey(String pri) {
        mAppPrefDataSource.setUserPrivateKey(pri);
    }

    @Override
    public void setUserPublicKey(String pub) {
        mAppPrefDataSource.setUserPublicKey(pub);
    }

    @Override
    public void setIV(String iv) {
        mAppPrefDataSource.setIV(iv);
    }

    @Override
    public void setAESKey(String aesKey) {
        mAppPrefDataSource.setAESKey(aesKey);
    }
}
