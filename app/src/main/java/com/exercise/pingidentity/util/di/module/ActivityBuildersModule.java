package com.exercise.pingidentity.util.di.module;

import com.exercise.pingidentity.feature.home.MainActivity;
import com.exercise.pingidentity.util.di.scope.MainScope;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {

    @MainScope
    @ContributesAndroidInjector(modules = {MainViewModelsModule.class, MainFragmentBuildersModule.class})
    abstract MainActivity contributeMainActivity();

}
