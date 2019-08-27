package com.example.todoapp.data.repository.app;

public interface AppDataSource {

    void setIsLogin(boolean isLogin);

    boolean isLogin();

    void clear();

    void setUserPrivateKey(String pri);

    void setUserPublicKey(String pub);

    void setIV(String iv);

    void setAESKey(String aesKey);
}
