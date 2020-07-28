package com.exercise.pingidentity.application;

import androidx.annotation.NonNull;

import com.exercise.pingidentity.BuildConfig;
import com.exercise.pingidentity.util.di.component.AppComponent;
import com.exercise.pingidentity.util.di.component.DaggerAppComponent;


import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import timber.log.Timber;

public class App extends DaggerApplication {

    @NonNull
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        appComponent = DaggerAppComponent.builder()
                    .application(this)
                    .build();
            return appComponent;
    }
}