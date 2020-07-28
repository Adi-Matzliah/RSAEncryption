package com.exercise.pingidentity.util;

import android.net.NetworkInfo;

import androidx.annotation.Nullable;

import javax.inject.Inject;

public class NetworkUtils {

    @Nullable
    private final NetworkInfo networkInfo;

    @Inject
    public NetworkUtils(NetworkInfo networkInfo) {
        this.networkInfo = networkInfo;
    }

    Boolean isNetworkAvailable() {
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        else {
            return false;
        }
    }

/*    String getActiveNetworkName() {
        return getConnectionType().connectionName;
    }

    private ConnectionType getConnectionType() {
        if (networkInfo == null) return ConnectionType.OFFLINE;
        else return ConnectionType.fromValue(networkInfo.type);
    }*/
}