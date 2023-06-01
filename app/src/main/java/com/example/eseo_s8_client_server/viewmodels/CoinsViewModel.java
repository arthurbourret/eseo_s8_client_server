package com.example.eseo_s8_client_server.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.eseo_s8_client_server.models.CoinsData;
import com.example.eseo_s8_client_server.models.ListResponse;
import com.example.eseo_s8_client_server.network.RetrofitNetworkManager;
import com.example.eseo_s8_client_server.viewmodels.IViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CoinsViewModel extends ViewModel implements IViewModel<CoinsData> {
    private final MutableLiveData<CoinsData> data = new MutableLiveData<>();

    public LiveData<CoinsData> getData() {
        return data;
    }

    public void fetchData(Object... parameters) {
        RetrofitNetworkManager.coinRankingAPI.getBitcoinCoins().enqueue(new Callback<ListResponse>() {
            @Override
            public void onResponse(@NonNull Call<ListResponse> call, @NonNull Response<ListResponse> response) {
                if (response.body() == null) return;
                handleResponse(response.body());
                // TODO error fetch
            }

            @Override
            public void onFailure(@NonNull Call<ListResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                // TODO error fetch
            }
        });
    }

    private void handleResponse(ListResponse response) {
        CoinsData coinsData = new CoinsData(response.getData());
        data.postValue(coinsData);
    }
}
