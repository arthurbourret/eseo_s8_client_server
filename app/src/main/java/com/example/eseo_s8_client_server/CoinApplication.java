package com.example.eseo_s8_client_server;

import android.app.Application;
import android.content.Context;

public class CoinApplication extends Application {
    private static Context APPLICATION_CONTEXT;

    public static Context getContext() {
        return APPLICATION_CONTEXT;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        CoinApplication.APPLICATION_CONTEXT = this.getApplicationContext();
    }
}
