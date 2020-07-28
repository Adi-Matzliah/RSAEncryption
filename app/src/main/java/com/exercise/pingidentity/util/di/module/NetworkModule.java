package com.exercise.pingidentity.util.di.module;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.exercise.pingidentity.R;
import com.exercise.pingidentity.network.FirebaseApi;
import com.exercise.pingidentity.util.NetworkUtils;
import com.exercise.pingidentity.util.di.scope.AppScope;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = {ContextModule.class, StorageModule.class})
public class NetworkModule {

    @Provides
    @AppScope
    Cache provideHttpCache(Context context) {
        int cacheSize = 10 * 1024 * 1024;
        return new Cache(context.getCacheDir(), cacheSize);
    }

    @Provides
    @AppScope
    HttpLoggingInterceptor provideHttpLoginInterceptor() {
        return new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    }


    @Provides
    @AppScope
    OkHttpClient provideOkHttpClient(Cache cache, HttpLoggingInterceptor loggingInterceptor) {
        return new OkHttpClient.Builder()
            .cache(cache/*null*/)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .build();
    }

    @Provides
    @AppScope
    Retrofit provideRetrofit(Context context, Gson gson, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(context.getString(R.string.firebase_base_url))
            .client(okHttpClient)
            .build();
    }

    @Provides
    @AppScope
    Gson provideGson(GsonBuilder gsonBuilder) {
        return gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
    }

    @Provides
    @AppScope
    GsonBuilder provideGsonBuilder() {
        return new GsonBuilder();
    }

    @Provides
    @AppScope
    FirebaseApi provideFirebasehApi(Retrofit retrofit){
        return retrofit.create(FirebaseApi.class);
    }

    @Provides
    @AppScope
    NetworkUtils provideNetworkUtils(NetworkInfo networkInfo) {
        return new NetworkUtils(networkInfo);
    }

    @Provides
    @AppScope
    NetworkInfo provideNetworkInfo(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo();
    }
}
