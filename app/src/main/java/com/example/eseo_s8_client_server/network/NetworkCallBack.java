package com.example.eseo_s8_client_server.network;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.eseo_s8_client_server.CoinApplication;

import java.io.IOException;

public abstract class NetworkCallBack {

    private NetworkCallBack() {
        // private for non accessibility
    }

    private static void displayError() {
        try {
            Toast.makeText(CoinApplication.getContext(), "Data could not be loaded",
                    Toast.LENGTH_LONG).show();
        } catch (Exception ignored) {
            // Can make the app crash if context uncertain
        }
    }

    public abstract static class RetrofitCallback<T> implements retrofit2.Callback<T> {

        public abstract void onSuccess(T response);

        @Override
        public final void onResponse(@NonNull retrofit2.Call<T> call,
                                     @NonNull retrofit2.Response<T> response) {
            if (response.isSuccessful() && response.body() != null) {
                onSuccess(response.body());
            } else displayError();
        }

        @Override
        public final void onFailure(@NonNull retrofit2.Call<T> call, Throwable t) {
            t.printStackTrace();
            displayError();
        }
    }
    // TODO: besoin de ça ?
    public abstract static class OkHttpCallback implements okhttp3.Callback {

        public abstract void onSuccess(okhttp3.ResponseBody response);

        @Override
        public final void onResponse(@NonNull okhttp3.Call call,
                                     @NonNull okhttp3.Response response) {
            if (response.isSuccessful() && response.body() != null) {
                onSuccess(response.body());
            } else displayError();
            response.close();
        }

        @Override
        public final void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {
            e.printStackTrace();
            displayError();
        }
    }
}
