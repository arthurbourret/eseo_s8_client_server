package com.example.eseo_s8_client_server.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.eseo_s8_client_server.models.CoinsData;
import com.example.eseo_s8_client_server.models.CoinsResponse;
import com.example.eseo_s8_client_server.network.NetworkCallBack;
import com.example.eseo_s8_client_server.network.RetrofitNetworkManager;

public class CoinsViewModel extends ViewModel implements IViewModel<CoinsData> {
    private final MutableLiveData<CoinsData> data = new MutableLiveData<>();
    private CoinsData coins = new CoinsData();

    public LiveData<CoinsData> getData() {
        return data;
    }
    // TODO: pourquoi le varargs en paramètre ?
    public void fetchData(Object... parameters) {
        RetrofitNetworkManager.coinRankingAPI
                .getCoinsResponse()
                .enqueue(new NetworkCallBack.RetrofitCallback<CoinsResponse>() {
                    @Override
                    public void onSuccess(CoinsResponse response) {
                        handleResponse(response);
                    }
                });
    }

    private void handleResponse(CoinsResponse response) {
        CoinsData coinsData = new CoinsData(response.getData());
        this.coins = coinsData;
        this.data.postValue(coinsData);
    }

    public void fetchFavorites() {
        CoinsData coinsData = new CoinsData(coins.getFavorites());
        this.data.postValue(coinsData);
    }

    public void fetchAll() {
        CoinsData coinsData = new CoinsData(coins.getCoins());
        this.data.postValue(coinsData);
    }
}
