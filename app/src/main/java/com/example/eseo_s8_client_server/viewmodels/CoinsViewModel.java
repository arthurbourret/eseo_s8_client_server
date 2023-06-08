package com.example.eseo_s8_client_server.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.eseo_s8_client_server.models.Coin;
import com.example.eseo_s8_client_server.models.CoinsData;
import com.example.eseo_s8_client_server.models.CoinsResponse;
import com.example.eseo_s8_client_server.network.NetworkCallBack;
import com.example.eseo_s8_client_server.network.RetrofitNetworkManager;
import com.example.eseo_s8_client_server.storage.DataRepository;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CoinsViewModel extends AndroidViewModel implements IViewModel<List<Coin>> {
    private final LiveData<List<Coin>> data;
    private final DataRepository dataRepository;
    private final CoinsData coins;

    public CoinsViewModel(@NotNull Application application) {
        super(application);
        dataRepository = new DataRepository(application);
        data = dataRepository.getData();

        this.coins = new CoinsData();
    }

    public LiveData<List<Coin>> getData() {
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
        this.coins.setCoinList(response.getData());
        saveCoins(coins.getCoins());
    }

    private void saveCoins(List<Coin> coins) {
        for (Coin coin : coins) {
            this.dataRepository.insertData(coin);
        }
    }
}
