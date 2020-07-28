package com.exercise.pingidentity.util.di.module;

import androidx.lifecycle.ViewModel;

import com.exercise.pingidentity.feature.biometric.BiometricViewModel;
import com.exercise.pingidentity.feature.encryption.EncryptionViewModel;
import com.exercise.pingidentity.feature.home.MainViewModel;
import com.exercise.pingidentity.util.di.key.ViewModelKey;
import com.exercise.pingidentity.util.di.scope.MainScope;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
abstract class MainViewModelsModule {

    @MainScope
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    abstract ViewModel bindHomeViewModel(MainViewModel viewModel);

    @MainScope
    @Binds
    @IntoMap
    @ViewModelKey(EncryptionViewModel.class)
    abstract ViewModel bindEncryptionViewModel(EncryptionViewModel viewModel);

    @MainScope
    @Binds
    @IntoMap
    @ViewModelKey(BiometricViewModel.class)
    abstract ViewModel bindBiometricViewModel(BiometricViewModel viewModel);
}
