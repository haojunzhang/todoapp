package com.example.todoapp.data.repository.keystore;

public interface KeyStoreDataSource {

    String[] generateKeyStoreRSAAndIVAndAESKey();

    void setIV(String iv);

    void setAESKey(String aesKey);

    void clearCache();

    String decrypt(String text);

    String encrypt(String text);

}
