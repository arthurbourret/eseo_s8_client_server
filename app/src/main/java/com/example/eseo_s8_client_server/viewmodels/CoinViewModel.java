package com.example.eseo_s8_client_server.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.eseo_s8_client_server.models.BasicResponse;
import com.example.eseo_s8_client_server.models.Coin;
import com.example.eseo_s8_client_server.network.RetrofitNetworkManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoinViewModel extends ViewModel implements IViewModel<Coin> {
    private final MutableLiveData<Coin> data = new MutableLiveData<>();

    @Override
    public LiveData<Coin> getData() {
        return data;
    }

    @Override
    public void generateNextValue(Object... parameters) {
        if (parameters == null || parameters.length < 1 || !(parameters[0] instanceof String))
            return;
        String uuid = (String) parameters[0];

        RetrofitNetworkManager.coinRankingAPI.getBitcoin(uuid).enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(@NonNull Call<BasicResponse> call,
                                   @NonNull Response<BasicResponse> response) {
                if (response.body() == null) return;
                handleResponse(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<BasicResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void handleResponse(BasicResponse response) {
        Coin coin = response.getData();
        data.postValue(coin);
    }
}
