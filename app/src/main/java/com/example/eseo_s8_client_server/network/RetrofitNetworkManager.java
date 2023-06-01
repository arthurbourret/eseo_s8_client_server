package com.example.eseo_s8_client_server.network;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitNetworkManager {
    public static final CoinRankingAPI coinRankingAPI;

    static {
        OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(new AuthInterceptor())
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetworkConstants.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        coinRankingAPI = retrofit.create(CoinRankingAPI.class);
    }

}
