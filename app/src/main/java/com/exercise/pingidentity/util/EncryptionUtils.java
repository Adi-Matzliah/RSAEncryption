package com.exercise.pingidentity.util;

import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Base64;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;

import javax.crypto.Cipher;
import javax.inject.Singleton;/**/

@Singleton
public class EncryptionUtils {

    final private static String AES_NOPAD_TRANS = "RSA/ECB/PKCS1Padding";
    final private static String ANDROID_KEYSTORE = "AndroidKeyStore";
    final private static String KEY_ALIAS = "KeyAliasRSA";
    final private static int KEY_SIZE_BIT = 1024;
    final private static String EMPTY_STR = "";

    private static KeyStore createKeyStore() {
        try {
            KeyStore keyStore = KeyStore.getInstance(ANDROID_KEYSTORE);
            keyStore.load(null);
            return keyStore;
        } catch (Exception e) {
            return null;
        }

    }

    public static KeyPair createAsymmetricKeyPair() {
        KeyPairGenerator generator;
        try {
            generator = KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_RSA, ANDROID_KEYSTORE);
            generator.initialize(KEY_SIZE_BIT);
            getKeyGenParameterSpec(generator);
            return generator.generateKeyPair();
        } catch (Exception e) {
            return null;
        }
    }

    private static void getKeyGenParameterSpec(KeyPairGenerator generator) {
        try {
            KeyGenParameterSpec.Builder builder = new KeyGenParameterSpec.Builder(KEY_ALIAS, KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_ECB)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(KeyProperties
                            .ENCRYPTION_PADDING_RSA_PKCS1);

            generator.initialize(builder.build());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static KeyPair getAsymmetricKeyPair() {
        KeyStore keyStore = createKeyStore();
        try {
            PrivateKey privateKey = (PrivateKey) keyStore.getKey(KEY_ALIAS, null);
            PublicKey publicKey = keyStore.getCertificate(KEY_ALIAS).getPublicKey();
            if (privateKey != null && publicKey != null) {
                return new KeyPair(publicKey, privateKey);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public void removeKeyStoreKey() {
        try {
            createKeyStore().deleteEntry(KEY_ALIAS);
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
    }

    public static String encrypt(String data, Key key) {
        try {
            Cipher cipher = Cipher.getInstance(AES_NOPAD_TRANS);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] bytes = cipher.doFinal(data.getBytes());
            return Base64.encodeToString(bytes, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
            return EMPTY_STR;
        }
    }

    public String decrypt(String data, Key key) {
        try {
            Cipher cipher = Cipher.getInstance(AES_NOPAD_TRANS);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] encryptedData = Base64.decode(data, Base64.DEFAULT);
            byte[] decodedData = cipher.doFinal(encryptedData);
            return new String(decodedData);
        } catch (Exception e) {
            e.printStackTrace();
            return EMPTY_STR;
        }
    }


    public static void main(String[] args) throws Exception {
        String input = "sample input";

        // Not a real private key! Replace with your private key!
        String strPk = "-----BEGIN PRIVATE KEY-----\nMIIEvwIBADANBgkqhkiG9"
                + "w0BAQEFAASCBKkwggSlAgEAAoIBAQDJUGqaRB11KjxQ\nKHDeG"
                + "........................................................"
                + "Ldt0hAPNl4QKYWCfJm\nNf7Afqaa/RZq0+y/36v83NGENQ==\n"
                + "-----END PRIVATE KEY-----\n";

        String base64Signature = signSHA256RSA(input, strPk);
        System.out.println("Signature="+base64Signature);
    }

    // Create base64 encoded signature using SHA256/RSA.
    public static String signSHA256RSA(String input, String strPk) throws Exception {

        byte[] b1 = Base64.decode(strPk, Base64.DEFAULT);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(b1);
        KeyFactory kf = KeyFactory.getInstance("RSA");

        Signature privateSignature = Signature.getInstance("SHA256withRSA");
        privateSignature.initSign(kf.generatePrivate(spec));
        privateSignature.update(input.getBytes(StandardCharsets.UTF_8));
        byte[] s = privateSignature.sign();
        return Base64.encodeToString(s, Base64.DEFAULT);
    }

}
