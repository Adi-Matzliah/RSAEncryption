package com.exercise.pingidentity.util.di.module;

import com.exercise.pingidentity.feature.biometric.BiometricFragment;
import com.exercise.pingidentity.feature.encryption.EncryptionFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class MainFragmentBuildersModule {

    @ContributesAndroidInjector
    abstract EncryptionFragment contributeEncryptionFragment();

    @ContributesAndroidInjector
    abstract BiometricFragment contributeBiometricFragment();
}
