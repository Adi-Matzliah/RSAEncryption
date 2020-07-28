package com.exercise.pingidentity.util.di.component;

import com.exercise.pingidentity.application.App;
import com.exercise.pingidentity.util.di.module.ActivityBuildersModule;
import com.exercise.pingidentity.util.di.module.AppModule;
import com.exercise.pingidentity.util.di.module.AuthModule;
import com.exercise.pingidentity.util.di.module.ContextModule;
import com.exercise.pingidentity.util.di.module.NetworkModule;
import com.exercise.pingidentity.util.di.module.PushNotificationModule;
import com.exercise.pingidentity.util.di.module.StorageModule;
import com.exercise.pingidentity.util.di.module.ViewModelFactoryModule;
import com.exercise.pingidentity.util.di.scope.AppScope;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@AppScope
@Component(modules = {
        AndroidSupportInjectionModule.class,
        AppModule.class,
        ContextModule.class,
        ActivityBuildersModule.class,
        ViewModelFactoryModule.class,
        AuthModule.class,
        NetworkModule.class,
        StorageModule.class,
        PushNotificationModule.class,
        /*
        ServiceBuilderModule.class,
        ,*/
    })

public interface AppComponent extends AndroidInjector<App> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(App application);

        AppComponent build();
    }

}