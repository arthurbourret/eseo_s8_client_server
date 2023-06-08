package com.example.eseo_s8_client_server.network;

import com.example.eseo_s8_client_server.models.CoinResponse;
import com.example.eseo_s8_client_server.models.CoinsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

// https://square.github.io/retrofit/
public interface CoinRankingAPI {
    @GET(NetworkConstants.COINS_PATH)
    Call<CoinsResponse> getCoinsResponse();

    @GET(NetworkConstants.COIN_PATH)
    Call<CoinResponse> getCoinResponse(@Path("uuri") String uuri);
}
