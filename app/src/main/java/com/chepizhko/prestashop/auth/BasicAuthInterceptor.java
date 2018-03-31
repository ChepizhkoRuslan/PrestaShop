package com.chepizhko.prestashop.auth;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class BasicAuthInterceptor implements Interceptor {
    public final static String TAG = "myLogs";
    private String credentials;

    public BasicAuthInterceptor(String credentials) {
        //this.credentials = Credentials.basic(user, password);
        this.credentials = credentials;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        Request authenticatedRequest = request.newBuilder()
                .header("Authorization", credentials)
                .build();

        return chain.proceed(authenticatedRequest);
    }


}
