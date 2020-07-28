package com.exercise.pingidentity.data.repository;

import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import javax.inject.Inject;

public class StorageRepository {

    @NonNull
    private SharedPreferences sharedPref;

    public static final String FIREBASE_PUSH_TOKEN_KEY = "firebase_notifications_token_key";
    public static final String RSA_KEY = "rsa_key";
    public static final String BIOMETRIC_TOGGLE_STATUS = "biometric_toggle_status";

    @Inject
    StorageRepository(@Nullable SharedPreferences sharedPref) {
        this.sharedPref = sharedPref;
    }

    public void setFirebaseNotificationToken(String token) {
        sharedPref.edit().putString(FIREBASE_PUSH_TOKEN_KEY, token).commit();
    }

    public String getFirebaseNotificationToken() {
        return sharedPref.getString(FIREBASE_PUSH_TOKEN_KEY, "");
    }

    void setRsaKey(String key) {
        sharedPref.edit().putString(RSA_KEY, key).commit();
    }

    String getRsaKey() {
        return sharedPref.getString(RSA_KEY, "");
    }

    public void setBiometricToggleStatus(Boolean status)  {
        sharedPref.edit().putBoolean(BIOMETRIC_TOGGLE_STATUS, status).commit();
    }

    public boolean getBiometricToggleStatus() {
        return sharedPref.getBoolean(BIOMETRIC_TOGGLE_STATUS, false);
    }
}
