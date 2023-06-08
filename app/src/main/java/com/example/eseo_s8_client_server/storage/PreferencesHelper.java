package com.example.eseo_s8_client_server.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.eseo_s8_client_server.CoinApplication;

import org.json.JSONArray;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class PreferencesHelper {
    private static PreferencesHelper INSTANCE;

    private final SharedPreferences preferences;
    // TODO: nom explicite
    private static final String SHARED_PREFERENCES_NAME = "loutre";
    // TODO: plus besoin
    private static final String API_KEY = "apiKey";
    // TODO: nom explicite
    private static final String SHARED_PREFERENCES_LAST_COIN_CLICK = "aled";

    private List<String> favoriteCoins;

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

    public String getLastCoinClick(){
        return preferences.getString(SHARED_PREFERENCES_LAST_COIN_CLICK, null);
    }

    public void setLastCoinClick(String lastCoinName){
        preferences.edit().putString(SHARED_PREFERENCES_LAST_COIN_CLICK, lastCoinName).apply();
    }

    public String getApiKey() {
        return preferences.getString(API_KEY, null);
    }

    public void setApiKey(String apiKey){
        this.preferences.edit().putString(API_KEY, apiKey).apply();
    }

    private List<String> getFavoriteCoins() {
        if (this.favoriteCoins != null) return favoriteCoins;
        List<String> favList = new LinkedList<>();

        try {
            String scoresTxt = preferences.getString(SHARED_PREFERENCES_FAVORITE_COINS, "[]");
            JSONArray favArr = new JSONArray(scoresTxt);

            for (int i=0; i<favArr.length(); i++) {
                String uuid = favArr.getString(i);
                favList.add(uuid);
            }

            Collections.sort(favList);
        } catch (Exception ignored) {}

        this.favoriteCoins = favList;
        return favList;
    }

    public void addCoinToFavorite(String uuid) {
        if (favoriteCoins.contains(uuid)) return;
        // if coin not in favs
        favoriteCoins.add(uuid);
        saveFavorites();
    }

    public void removeCoinFromFavorite(String uuid) {
        if (!(favoriteCoins.contains(uuid))) return;
        // if coin in favs
        favoriteCoins.remove(uuid);
        saveFavorites();
    }

    private void saveFavorites() {
        String favoritesToString = Arrays.toString(favoriteCoins.toArray());
        preferences.edit().putString(SHARED_PREFERENCES_FAVORITE_COINS, favoritesToString).apply();
    }
}
