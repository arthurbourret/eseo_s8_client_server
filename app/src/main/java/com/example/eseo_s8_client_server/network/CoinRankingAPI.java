package com.example.eseo_s8_client_server.network;

import com.example.eseo_s8_client_server.models.PriceResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

// https://square.github.io/retrofit/
public interface CoinRankingAPI {

    @Headers({
            "x-rapidapi-host: coinranking1.p.rapidapi.com",
            "x-rapidapi-key: 4044e6d441msh112775bd9365d18p1c6ef5jsn79b99212405b"
    })
    @GET("/coin/Qwsogvtv82FCd/price")
    Call<PriceResponse> getBitcoinPrice();
}
