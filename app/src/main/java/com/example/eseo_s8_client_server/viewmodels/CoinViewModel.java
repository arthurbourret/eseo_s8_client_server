package com.example.eseo_s8_client_server.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.eseo_s8_client_server.models.CoinResponse;
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
    public void fetchData(Object... parameters) { // TODO: pourquoi le varargs ? on devrait avoid un String
        if (parameters == null || parameters.length < 1 || !(parameters[0] instanceof String))
            return;
        String uuid = (String) parameters[0];

        RetrofitNetworkManager.coinRankingAPI
                .getCoinResponse(uuid)
                .enqueue(new Callback<CoinResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<CoinResponse> call,
                                           @NonNull Response<CoinResponse> response) {
                        if (!response.isSuccessful() || response.body() == null) return;
                        handleResponse(response.body());
                        // TODO: gestion des erreurs
                    }

                    @Override
                    public void onFailure(@NonNull Call<CoinResponse> call, @NonNull Throwable t) {
                        // TODO: gestion des erreurs
                    }
                });
    }

    private void handleResponse(CoinResponse response) {
        data.postValue(response.getData());
    }
}
