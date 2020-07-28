package com.exercise.pingidentity.network;

import com.exercise.pingidentity.network.request.FcmRequest;
import com.exercise.pingidentity.network.response.FcmResponse;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface FirebaseApi {

    @POST("fcm/send")
    @Headers({
        "Content-Type: application/json",
        "Authorization: key=AAAAdIaxlAE:APA91bHimuGCH0eaCjDC-RhPq6wvTWyQgoB_yKJPW2SXupFMPzhTdgAjHQ2TRE4-1eYtb_L_WgsTX5epFyd1zWRoMNQiY6rvfNe5LzDcrzLk1tEpT5IgazHzjfZzBg6Wk18nFb1Gb0dP"
    })
    Single<FcmResponse> sendPushNotification(@Body FcmRequest body);
}