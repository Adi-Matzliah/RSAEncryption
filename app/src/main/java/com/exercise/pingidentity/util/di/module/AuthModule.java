package com.exercise.pingidentity.util.di.module;

import android.content.Context;

import androidx.biometric.BiometricManager;

import com.exercise.pingidentity.util.di.scope.AppScope;

import dagger.Module;
import dagger.Provides;

@Module(includes = {ContextModule.class})
public class AuthModule {

    @Provides
    @AppScope
    BiometricManager provideBiometricManager(Context context) {
        return BiometricManager.from(context);
    }
}
