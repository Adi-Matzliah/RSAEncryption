package com.exercise.pingidentity.data.repository;

import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import javax.inject.Inject;

public class StorageRepository {

    @NonNull
    private SharedPreferences sharedPref;

     private static final String FIREBASE_PUSH_TOKEN_KEY = "firebase_notifications_token_key";
    private static final String RSA_KEY = "rsa_key";


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

}
