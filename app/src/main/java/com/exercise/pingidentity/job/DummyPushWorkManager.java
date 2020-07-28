package com.exercise.pingidentity.job;

import android.content.Context;

import androidx.annotation.NonNull;

import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.exercise.pingidentity.data.repository.RemoteRepository;
import com.exercise.pingidentity.data.repository.StorageRepository;
import com.exercise.pingidentity.network.response.FcmResponse;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

@Deprecated
public class DummyPushWorkManager extends Worker {

    @Inject
    StorageRepository storageRepo;

    @Inject
    RemoteRepository remoteRepo;

    public DummyPushWorkManager(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
    }

    @Override
    public Result doWork() {
        sendPush();
        // Indicate whether the work finished successfully with the Result
        return Result.success();
    }

    private void sendPush() {
        remoteRepo.sendPushNotification("sdfsdsfsdf", "dsfdsf", "asdasdsa")
                .subscribeWith(new DisposableSingleObserver<FcmResponse>() {
                    @Override
                    public void onSuccess(FcmResponse fcmResponse) {
                        Timber.d("onSuccess %s", fcmResponse.toString());
                        dispose();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.d("onError %s", e.getMessage());
                        dispose();
                    }
                });
    }
}