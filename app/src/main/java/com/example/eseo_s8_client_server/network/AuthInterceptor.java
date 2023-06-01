package com.example.eseo_s8_client_server.network;

import androidx.annotation.NonNull;

import com.example.eseo_s8_client_server.storage.PreferencesHelper;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {

    private static final String HOST_HEADER_NAME = "x-rapidapi-host";
    private static final String HOST_HEADER_VALUE = "coinranking1.p.rapidapi.com";
    private static final String KEY_HEADER_NAME = "x-rapidapi-key";

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        String apiKey = PreferencesHelper.getInstance().getApiKey();

        Request request = new Request.Builder(chain.request())
                .addHeader(HOST_HEADER_NAME, HOST_HEADER_VALUE)
                .addHeader(KEY_HEADER_NAME, apiKey)
                .build();

        return chain.proceed(request);
    }
}

