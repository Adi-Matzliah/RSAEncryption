package com.exercise.pingidentity.util.di.module;

import android.app.NotificationManager;
import android.content.Context;

import com.exercise.pingidentity.data.repository.StorageRepository;
import com.exercise.pingidentity.feature.notification.PushNotificationManager;
import com.exercise.pingidentity.util.di.scope.AppScope;

import dagger.Module;
import dagger.Provides;

@Module(includes = {ContextModule.class, StorageModule.class})
public class PushNotificationModule {

    @Provides
    @AppScope
    NotificationManager provideNotificationManager(Context context) {
        return (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Provides
    @AppScope
    PushNotificationManager providePushNotificationManager(StorageRepository storageRepo, NotificationManager notificationManager) {
        return new PushNotificationManager(storageRepo, notificationManager);
    }
}