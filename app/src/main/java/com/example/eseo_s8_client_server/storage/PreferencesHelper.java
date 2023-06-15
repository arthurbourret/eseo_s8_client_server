package com.example.eseo_s8_client_server.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.eseo_s8_client_server.CoinApplication;
import com.example.eseo_s8_client_server.models.Coin;

import org.json.JSONArray;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class PreferencesHelper {
    private static PreferencesHelper INSTANCE;
    private final SharedPreferences preferences;
    private static final String SHARED_PREFERENCES_NAME = "sharedPreferenceName";
    private static final String SHARED_PREFERENCES_FAVORITE_COINS = "favoriteCoins";

    private List<String> favoriteCoins;

    private PreferencesHelper() {
        preferences = CoinApplication.getContext()
                .getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);

        initFavoriteCoinsIds();
    }

    public static PreferencesHelper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PreferencesHelper();
        }
        return INSTANCE;
    }

    private void initFavoriteCoinsIds() {
        favoriteCoins = new LinkedList<>();

        try {
            String scoresTxt = preferences.getString(SHARED_PREFERENCES_FAVORITE_COINS, "[]");
            JSONArray favArr = new JSONArray(scoresTxt);

            for (int i = 0; i < favArr.length(); i++) {
                String uuid = favArr.getString(i);
                favoriteCoins.add(uuid);
            }

            Collections.sort(favoriteCoins);
        } catch (Exception ignored) {
        }
    }

    public List<String> getFavoriteCoinsIds() {
        return favoriteCoins;
    }

    public void addCoinToFavorite(Coin coin) {
        if (favoriteCoins.contains(coin.getUuid()) || coin.isFavorite()) return;
        // if coin not in favs
        favoriteCoins.add(coin.getUuid());
        saveFavorites();
        coin.setFavorite(true);
    }

    public void removeCoinFromFavorite(Coin coin) {
        if (!(favoriteCoins.contains(coin.getUuid())) || !coin.isFavorite()) return;
        // if coin in favs
        favoriteCoins.remove(coin.getUuid());
        saveFavorites();
        coin.setFavorite(false);
    }

    private void saveFavorites() {
        String favoritesToString = Arrays.toString(favoriteCoins.toArray());
        preferences.edit().putString(SHARED_PREFERENCES_FAVORITE_COINS, favoritesToString).apply();
    }
}
