package com.example.eseo_s8_client_server.network;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = new Request.Builder(chain.request())
                .addHeader(NetworkConstants.HOST_HEADER_NAME, NetworkConstants.HOST_HEADER_VALUE)
                .addHeader(NetworkConstants.KEY_HEADER_NAME, NetworkConstants.KEY_HEADER_VALUE)
                .build();

        return chain.proceed(request);
    }
}

