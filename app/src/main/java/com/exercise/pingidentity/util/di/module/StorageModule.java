package com.exercise.pingidentity.util.di.module;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import com.exercise.pingidentity.util.di.scope.AppScope;

import java.io.IOException;
import java.security.GeneralSecurityException;

import dagger.Module;
import dagger.Provides;

@Module(includes = {ContextModule.class})
public class StorageModule {

    @Provides
    @AppScope
    synchronized SharedPreferences provideSharedPreferences(Context context) {
        try {
            return EncryptedSharedPreferences
                    .create(
                            "EncryptedSharedPreferences",
                            MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
                            context,
                            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                    );
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return null;
    }
}