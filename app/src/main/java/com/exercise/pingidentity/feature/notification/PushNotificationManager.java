package com.exercise.pingidentity.feature.notification;

import android.app.NotificationManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.exercise.pingidentity.data.repository.StorageRepository;
import com.google.firebase.iid.FirebaseInstanceId;

import timber.log.Timber;

import javax.inject.Inject;

public class PushNotificationManager {

    @NonNull
    private StorageRepository storageRepo;

    @Nullable
    private NotificationManager notificationManager;

    @Inject
    public PushNotificationManager(@NonNull StorageRepository storageRepo, @Nullable NotificationManager notificationManager) {
        this.storageRepo = storageRepo;
        this.notificationManager = notificationManager;
    }

    public void createFcmRegistrationToken() {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Timber.w("getInstanceId failed %s", task.getException());
                        return;
                    }

                    // Get new Instance ID token
                    String token = task.getResult().getToken();
                    storageRepo.setFirebaseNotificationToken(token);
                    Timber.d("user FCM reg token is: %s", token);
                });

    }

}