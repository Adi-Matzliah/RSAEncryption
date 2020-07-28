package com.exercise.pingidentity.data.repository;

import androidx.annotation.NonNull;

import com.exercise.pingidentity.network.FirebaseApi;
import com.exercise.pingidentity.network.request.FcmRequest;
import com.exercise.pingidentity.network.response.FcmResponse;

import javax.inject.Inject;

import io.reactivex.Single;

public class RemoteRepository {

    @NonNull
    private final FirebaseApi firebaseApi;

    @Inject
    public RemoteRepository(@NonNull FirebaseApi firebaseApi) {
        this.firebaseApi = firebaseApi;
    }

    public Single<FcmResponse> sendPushNotification(String regToken, String payload, String signature) {
        return firebaseApi.sendPushNotification(new FcmRequest(regToken, payload, signature));
    }

}
