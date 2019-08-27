package com.example.todoapp.utils;

import android.os.Build;
import android.util.Base64;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

public class SignatureUtils {
    public static final String RSA = "RSA";
    public static final String ECB_PKCS1_PADDING = "RSA/ECB/PKCS1Padding";
    public static final String UTF_8 = "UTF-8";
    public static final String SHA256WITHRSA = "SHA256withRSA";

    // 產生RSA-2048密鑰, 公鑰與私鑰
    public static KeyPair generateRSAKeyPair() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA);
            keyPairGenerator.initialize(2048);
            return keyPairGenerator.genKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 取得公鑰字串
    public static String getPublicKeyString(KeyPair keyPair) {
        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
        return StringUtils.toBase64(rsaPublicKey.getEncoded());
    }

    // 取得私鑰字串
    public static String getPrivateKeyString(KeyPair keyPair) {
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
        return StringUtils.toBase64(rsaPrivateKey.getEncoded());
    }

    // 字串轉公鑰
    private static PublicKey getPublicKeyByString(String key) throws Exception {
        byte[] keyBytes = Base64.decode(key, Base64.NO_WRAP);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        return keyFactory.generatePublic(keySpec);
    }

    // 字串轉私鑰
    private static PrivateKey getPrivateKeyByString(String key) throws Exception {
        byte[] keyBytes = Base64.decode(key, Base64.NO_WRAP);

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            keyFactory = KeyFactory.getInstance(RSA, "BC");
        } else {
            keyFactory = KeyFactory.getInstance(RSA);
        }
        return keyFactory.generatePrivate(keySpec);
    }

    public static String encryptByPublicKey(String data, String publicKey) {
        try {
            Cipher cipher = Cipher.getInstance(ECB_PKCS1_PADDING);
            cipher.init(Cipher.ENCRYPT_MODE, getPublicKeyByString(publicKey));
            return StringUtils.toBase64(cipher.doFinal(data.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String data, String privateKey) {
        try {
            Cipher cipher = Cipher.getInstance(ECB_PKCS1_PADDING);
            cipher.init(Cipher.DECRYPT_MODE, getPrivateKeyByString(privateKey));
            return new String(cipher.doFinal(Base64.decode(data, Base64.URL_SAFE)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String sign(String data, String privateKey) {
        try {
            String encodeStr = StringUtils.toBase64(data.getBytes(UTF_8));
            Signature signature = Signature.getInstance(SHA256WITHRSA);
            signature.initSign(getPrivateKeyByString(privateKey));
            signature.update(encodeStr.getBytes(UTF_8));
            return StringUtils.toBase64(signature.sign());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean verify(String payload, String signedPayload, String publicKey) {
        try {
            Signature signature = Signature.getInstance(SHA256WITHRSA);
            signature.initVerify(getPublicKeyByString(publicKey));

            signature.update(StringUtils.toBase64(payload.getBytes(UTF_8)).getBytes());

            byte[] signedPayloadContent = Base64.decode(signedPayload,
                    Base64.NO_WRAP);
            return signature.verify(signedPayloadContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
