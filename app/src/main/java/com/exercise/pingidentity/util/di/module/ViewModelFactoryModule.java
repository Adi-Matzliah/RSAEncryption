package com.exercise.pingidentity.util.di.module;

import androidx.lifecycle.ViewModelProvider;

import com.exercise.pingidentity.util.di.ViewModelProviderFactory;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ViewModelFactoryModule {

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelProviderFactory viewModelFactory);
}
