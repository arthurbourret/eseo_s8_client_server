package com.example.eseo_s8_client_server.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.eseo_s8_client_server.CoinApplication;

public class PreferencesHelper {
    private static PreferencesHelper INSTANCE;

    private final SharedPreferences preferences;
    // TODO: nom explicite
    private static final String SHARED_PREFERENCES_NAME = "loutre";
    // TODO: plus besoin
    private static final String API_KEY = "apiKey";

    private PreferencesHelper (){
        preferences = CoinApplication.getContext()
                .getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public static PreferencesHelper getInstance(){
        if (INSTANCE==null){
            INSTANCE = new PreferencesHelper();
        }
        return INSTANCE;
    }

    public String getApiKey() {
        return preferences.getString(API_KEY, null);
    }

    public void setApiKey(String apiKey){
        this.preferences.edit().putString(API_KEY, apiKey).apply();
    }

    public Object getFavorites() {
        // TODO
        return null;
    }

    public void addCoinToFavorite(String uuid) {
        // TODO
    }

    public void removeCoinFromFavorite(String uuid) {
        // TODO
    }
}
