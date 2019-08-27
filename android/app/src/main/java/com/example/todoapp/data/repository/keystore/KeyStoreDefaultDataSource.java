package com.example.todoapp.data.repository.keystore;

import android.content.Context;
import android.os.Build;
import android.security.KeyPairGeneratorSpec;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Base64;

import androidx.annotation.RequiresApi;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.Calendar;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.security.auth.x500.X500Principal;

public class KeyStoreDefaultDataSource implements KeyStoreDataSource {

    public static final String KEYSTORE_PROVIDER = "AndroidKeyStore";
    private static final String AES_MODE = "AES/GCM/NoPadding";
    private static final String RSA_MODE = "RSA/ECB/PKCS1Padding";

    private static final String KEYSTORE_ALIAS = "MaiPocket-Dark";

    private Context mContext;
    private KeyStore mKeyStore;

    // cache
    private SecretKeySpec aesKeySpec;
    private IvParameterSpec ivSpec;

    public KeyStoreDefaultDataSource(Context mContext, KeyStore mKeyStore) {
        this.mContext = mContext;
        this.mKeyStore = mKeyStore;
        init();
    }

    private void init() {
        try {
            if (mKeyStore == null) {
                mKeyStore = KeyStore.getInstance(KeyStoreDefaultDataSource.KEYSTORE_PROVIDER);
            }
            mKeyStore.load(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String[] generateKeyStoreRSAAndIVAndAESKey() {
        try {
            // 產生RSAKey
            generateRSAKey();

            // 產生IV, 用RSA加密過的AESKey
            return generateIVAndEncryptedAESKey();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void generateRSAKey() throws Exception {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            generateRSAKey_AboveApi23();
        } else {
            generateRSAKey_BelowApi23();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void generateRSAKey_AboveApi23() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator
                .getInstance(KeyProperties.KEY_ALGORITHM_RSA, KEYSTORE_PROVIDER);


        KeyGenParameterSpec keyGenParameterSpec = new KeyGenParameterSpec
                .Builder(KEYSTORE_ALIAS, KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                .setDigests(KeyProperties.DIGEST_SHA256, KeyProperties.DIGEST_SHA512)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1)
                .build();

        keyPairGenerator.initialize(keyGenParameterSpec);
        keyPairGenerator.generateKeyPair();

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void generateRSAKey_BelowApi23() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException {
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        end.add(Calendar.YEAR, 30);

        KeyPairGeneratorSpec spec = new KeyPairGeneratorSpec.Builder(mContext)
                .setAlias(KEYSTORE_ALIAS)
                .setSubject(new X500Principal("CN=" + KEYSTORE_ALIAS))
                .setSerialNumber(BigInteger.TEN)
                .setStartDate(start.getTime())
                .setEndDate(end.getTime())
                .build();

        KeyPairGenerator keyPairGenerator = KeyPairGenerator
                .getInstance(KeyProperties.KEY_ALGORITHM_RSA, KEYSTORE_PROVIDER);

        keyPairGenerator.initialize(spec);
        keyPairGenerator.generateKeyPair();
    }

    private String[] generateIVAndEncryptedAESKey() throws Exception {
        // 產AES
        byte[] aesKey = new byte[16];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(aesKey);

        // 產IV
        byte[] generated = secureRandom.generateSeed(12);
        String iv = Base64.encodeToString(generated, Base64.DEFAULT);

        // 將AES用KeyStore產生的RSA加密
        String encryptedAESKey = encryptByRSA(aesKey);

        return new String[]{iv, encryptedAESKey};
    }

    private String encryptByRSA(byte[] plainText) throws Exception {
        PublicKey publicKey = mKeyStore.getCertificate(KEYSTORE_ALIAS).getPublicKey();
        Cipher cipher = Cipher.getInstance(RSA_MODE);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedByte = cipher.doFinal(plainText);
        return Base64.encodeToString(encryptedByte, Base64.DEFAULT);
    }

    private byte[] decryptByRSA(String encryptedText) {
        try {
            PrivateKey privateKey = (PrivateKey) mKeyStore.getKey(KEYSTORE_ALIAS, null);
            Cipher cipher = Cipher.getInstance(RSA_MODE);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] encryptedBytes = Base64.decode(encryptedText, Base64.DEFAULT);
            return cipher.doFinal(encryptedBytes);
        } catch (Exception e) {
            return null;
        }
    }

    private String encryptAES(String plainText) throws Exception {
        Cipher cipher = Cipher.getInstance(AES_MODE);
        cipher.init(Cipher.ENCRYPT_MODE, getAESKey(), getIV());
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
        return Base64.encodeToString(encryptedBytes, Base64.DEFAULT);
    }

    private String decryptAES(String encryptedText) throws Exception {
        byte[] decodedBytes = Base64.decode(encryptedText.getBytes(), Base64.DEFAULT);
        Cipher cipher = Cipher.getInstance(AES_MODE);
        cipher.init(Cipher.DECRYPT_MODE, getAESKey(), getIV());

        return new String(cipher.doFinal(decodedBytes));
    }

    @Override
    public void setIV(String iv) {
        ivSpec = new IvParameterSpec(Base64.decode(iv, Base64.DEFAULT));
    }

    private IvParameterSpec getIV() {
        return ivSpec;
    }

    @Override
    public void setAESKey(String encryptedAESKey) {
        // 產出去的是加密過的, 所以set回來的應該也要是加密後的
        aesKeySpec = new SecretKeySpec(decryptByRSA(encryptedAESKey), AES_MODE);
    }

    private SecretKeySpec getAESKey() {
        return aesKeySpec;
    }

    @Override
    public void clearCache() {
        ivSpec = null;
        aesKeySpec = null;
    }

    @Override
    public String decrypt(String text) {
        try {
            return decryptAES(text);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override
    public String encrypt(String text) {
        try {
            return encryptAES(text);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
