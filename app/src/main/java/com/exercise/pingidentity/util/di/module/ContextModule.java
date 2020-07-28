package com.exercise.pingidentity.util.di.module;

import android.content.Context;

import com.exercise.pingidentity.application.App;
import com.exercise.pingidentity.util.di.scope.AppScope;

import dagger.Module;
import dagger.Provides;

@Module
public class ContextModule {

    @Provides
    @AppScope
    Context provideContext(App app) {
        return app;
    }
}
