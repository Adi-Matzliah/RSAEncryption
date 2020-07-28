package com.exercise.pingidentity.feature.encryption;


import androidx.annotation.NonNull;
import androidx.biometric.BiometricManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.exercise.pingidentity.data.SingleLiveEvent;
import com.exercise.pingidentity.data.repository.RemoteRepository;
import com.exercise.pingidentity.data.repository.StorageRepository;
import com.exercise.pingidentity.feature.notification.PushNotificationManager;
import com.exercise.pingidentity.network.response.FcmResponse;
import com.exercise.pingidentity.util.EncryptionUtils;

import java.security.KeyPair;
import java.util.concurrent.TimeUnit;

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

    public MutableLiveData<Boolean> isBiometricEnabled = new MutableLiveData<>();

    public MutableLiveData<String> userText = new MutableLiveData<>("");

    private MutableLiveData<String> _toastText = new MutableLiveData<>();

    private SingleLiveEvent<Boolean> _isBiometricAlertDialogVisible = new SingleLiveEvent<>();

    private final static int DELAY_IN_SEC = 3;

    private String encryptedText;
    private String signature;

    @NonNull
    private CompositeDisposable disposable = new CompositeDisposable();

    @Inject
    EncryptionViewModel(@NonNull BiometricManager biometricManager, @NonNull RemoteRepository remoteRepo, @NonNull StorageRepository storageRepo, @NonNull PushNotificationManager notificationManager) {
        this.bioManager = biometricManager;
        this.remoteRepo = remoteRepo;
        this.storageRepo = storageRepo;
        this.notificationManager = notificationManager;
    }

    public void initialize() {
        isBiometricEnabled.setValue(storageRepo.getBiometricToggleStatus());
        createPushNotificationRegToken();
    }

    public void createPushNotificationRegToken() {
        notificationManager.createFcmRegistrationToken();
    }

    // 1) Create an RSA key pair for encrypting the string with a 1024 bit size and encrypt the string (please decide on the right PKCS padding).
    // 2) Create another RSA keypair for signing the encrypted string before sending it and attach the signature to the payload. Key should use digest of SHA256 and be used for signing and verifying the decrypted string.
    public void processText() {
        isLoading.setValue(true);
        try {
            EncryptionUtils.createAsymmetricKeyPair();
            KeyPair rsaKeyPair = EncryptionUtils.getAsymmetricKeyPair();
            encryptedText = EncryptionUtils.encrypt(userText.getValue(), rsaKeyPair.getPublic());
            signature = EncryptionUtils.signSHA256RSA(encryptedText, rsaKeyPair.getPrivate().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        _toastText.setValue(String.format("Please minimize the app for at least %s sec", DELAY_IN_SEC));
    }

    public void createBackgroundTask() {
        if (isLoading.getValue()) {
            disposable.add(
                    Single.timer(DELAY_IN_SEC, TimeUnit.SECONDS)
                    .flatMap(delay -> remoteRepo.sendPushNotification(storageRepo.getFirebaseNotificationToken(), encryptedText, signature))
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
            );
        }
    }

    public LiveData<Boolean> isBiometricAlertDialogVisible() {
        return _isBiometricAlertDialogVisible;
    }

    public LiveData<String> getToastText() {
        return _toastText;
    }

    public void onBiometricToggle() {
        storageRepo.setBiometricToggleStatus(isBiometricEnabled.getValue());
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
