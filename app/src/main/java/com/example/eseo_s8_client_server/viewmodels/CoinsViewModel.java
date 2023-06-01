package com.example.eseo_s8_client_server.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.eseo_s8_client_server.models.CoinsData;
import com.example.eseo_s8_client_server.models.ListResponse;
import com.example.eseo_s8_client_server.network.NetworkCallBack;
import com.example.eseo_s8_client_server.network.RetrofitNetworkManager;

public class CoinsViewModel extends ViewModel implements IViewModel<CoinsData> {
    private final MutableLiveData<CoinsData> data = new MutableLiveData<>();

    public LiveData<CoinsData> getData() {
        return data;
    }

    public void fetchData(Object... parameters) {
        RetrofitNetworkManager.coinRankingAPI
                .getBitcoinCoins()
                .enqueue(new NetworkCallBack.RetrofitCallback<ListResponse>() {
                    @Override
                    public void onSuccess(ListResponse response) {
                        handleResponse(response);
                    }
                });
    }

    private void handleResponse(ListResponse response) {
        CoinsData coinsData = new CoinsData(response.getData());
        data.postValue(coinsData);
    }
}
