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
    private SingleLiveEvent<Boolean> _isBiometricEnabled = new SingleLiveEvent<>();

    private MutableLiveData<Boolean> _isTextVisible = new MutableLiveData<>(false);

    @Inject
    BiometricViewModel(@NonNull StorageRepository storageRepo, @NonNull PushNotificationManager notificationManager) {
        this.storageRepo = storageRepo;
    }

    public void setDecryptedText(String text) {
        _decryptedText.setValue(text);
    }

    public LiveData<String> getDecryptedText() {
        return _decryptedText;
    }

    public LiveData<Boolean> getIsBiometricEnabled() {
        return _isBiometricEnabled;
    }

    public LiveData<Boolean> getIsTextVisible() {
        return _isTextVisible;
    }

    public void setTextVisibility(boolean isVisible) {
        _isTextVisible.setValue(isVisible);
    }

    public void initialize() {
        _isBiometricEnabled.setValue(storageRepo.getBiometricToggleStatus());
        if (!_isBiometricEnabled.getValue()) {
            setTextVisibility(true);
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
