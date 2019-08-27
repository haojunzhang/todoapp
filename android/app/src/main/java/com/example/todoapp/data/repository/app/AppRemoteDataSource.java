package com.example.todoapp.data.repository.app;

public class AppRemoteDataSource implements AppDataSource {

    @Override
    public void setIsLogin(boolean isLogin) {
        // do nothing
    }

    @Override
    public boolean isLogin() {
        // do nothing
        return false;
    }

    @Override
    public void clear() {
        // do nothing
    }

    @Override
    public void setUserPrivateKey(String pri) {
        // do nothing
    }

    @Override
    public void setUserPublicKey(String pub) {
        // do nothing
    }

    @Override
    public void setIV(String iv) {
        // do nothing
    }

    @Override
    public void setAESKey(String aesKey) {
        // do nothing
    }
}
