package com.exercise.pingidentity.feature.biometric;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.exercise.pingidentity.data.repository.StorageRepository;
import com.exercise.pingidentity.data.SingleLiveEvent;
import com.exercise.pingidentity.feature.notification.PushNotificationManager;

import javax.inject.Inject;

public class BiometricViewModel extends ViewModel {

    @NonNull
    private final StorageRepository storageRepo;

    private MutableLiveData<String> _decryptedText = new MutableLiveData<>();
    SingleLiveEvent<Boolean> _isBiometricEnabled = new SingleLiveEvent<>();

    public MutableLiveData<String> userText = new MutableLiveData<>("");

    @Inject
    BiometricViewModel(@NonNull StorageRepository storageRepo, @NonNull PushNotificationManager notificationManager) {
        this.storageRepo = storageRepo;
    }

    public LiveData<String> getDecryptedText() {
        return _decryptedText;
    }

    public LiveData<Boolean> getIsBiometricEnabled() {
        return _isBiometricEnabled;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
