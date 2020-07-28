package com.exercise.pingidentity.feature.encryption;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.biometric.BiometricManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.exercise.pingidentity.data.SingleLiveEvent;
import com.exercise.pingidentity.data.repository.RemoteRepository;
import com.exercise.pingidentity.data.repository.StorageRepository;
import com.exercise.pingidentity.feature.notification.PushNotificationManager;
import com.exercise.pingidentity.network.request.FcmRequest;
import com.exercise.pingidentity.network.response.FcmResponse;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class EncryptionViewModel extends ViewModel {

    @NonNull
    private final PushNotificationManager notificationManager;

    @NonNull
    private final RemoteRepository remoteRepo;

    @NonNull
    private final StorageRepository storageRepo;

    @NonNull
    private final BiometricManager bioManager;

    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);

    public MutableLiveData<Boolean> isBiometricEnabled = new MutableLiveData<>(false);

    public MutableLiveData<String> userText = new MutableLiveData<>("");

    private SingleLiveEvent<Boolean> _isBiometricAlertDialogVisible = new SingleLiveEvent<>();

    @NonNull
    private CompositeDisposable disposable = new CompositeDisposable();

    @Inject
    EncryptionViewModel(@NonNull BiometricManager biometricManager, @NonNull RemoteRepository remoteRepo, @NonNull StorageRepository storageRepo, @NonNull PushNotificationManager notificationManager) {
        this.bioManager = biometricManager;
        this.remoteRepo = remoteRepo;
        this.storageRepo = storageRepo;
        this.notificationManager = notificationManager;
    }

    public void createPushNotificationRegToken() {
        notificationManager.createFcmRegistrationToken();
    }


    public void processText() {
        //isLoading.setValue(true);
        disposable.add(remoteRepo.sendPushNotification(storageRepo.getFirebaseNotificationToken(), "dsfdsf", "asdasdsa")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableSingleObserver<FcmResponse>() {
                        @Override
                        public void onSuccess(FcmResponse fcmResponse) {
                            Timber.d("onSuccess %s", fcmResponse.toString());
                        }

                        @Override
                    public void onError(Throwable e) {
                            Timber.d("onError %s", e.getMessage());
                    }
                })
                    /*.onsubscribe(result -> Timber.d("sdfsdf"))*/);
    }

    public LiveData<Boolean> isBiometricAlertDialogVisible() {
        return _isBiometricAlertDialogVisible;
    }

    public void onBiometricToggle() {
        if (isBiometricEnabled.getValue()) {
            switch (bioManager.canAuthenticate()) {
                case BiometricManager.BIOMETRIC_SUCCESS:
                    isBiometricEnabled.setValue(true);
                    Timber.d("App can authenticate using biometrics.");
                    break;
                case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                    isBiometricEnabled.setValue(false);
                    Timber.d("No biometric features available on this device.");
                    break;
                case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                    isBiometricEnabled.setValue(false);
                    Timber.d("Biometric features are currently unavailable.");
                    break;
                case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                    isBiometricEnabled.setValue(false);
                    _isBiometricAlertDialogVisible.setValue(true);
                    Timber.d("The user hasn't associated any biometric credentials with their account.");
                    break;
            }
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
