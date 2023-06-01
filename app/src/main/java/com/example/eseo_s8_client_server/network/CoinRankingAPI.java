package com.example.eseo_s8_client_server.network;

import com.example.eseo_s8_client_server.models.BasicResponse;
import com.example.eseo_s8_client_server.models.ListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

// https://square.github.io/retrofit/
public interface CoinRankingAPI {

    @GET(NetworkConstants.BITCOIN_COINS_PATH)
    Call<ListResponse> getBitcoinCoins();

    @GET(NetworkConstants.BITCOIN_COIN_PATH)
    Call<BasicResponse> getBitcoin(@Path("uuri") String uuri);
}
