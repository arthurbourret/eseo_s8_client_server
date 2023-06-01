package com.example.eseo_s8_client_server.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.eseo_s8_client_server.models.BasicResponse;
import com.example.eseo_s8_client_server.models.Coin;
import com.example.eseo_s8_client_server.network.NetworkCallBack;
import com.example.eseo_s8_client_server.network.RetrofitNetworkManager;

public class CoinViewModel extends ViewModel implements IViewModel<Coin> {
    private final MutableLiveData<Coin> data = new MutableLiveData<>();

    @Override
    public LiveData<Coin> getData() {
        return data;
    }

    @Override
    public void fetchData(Object... parameters) {
        if (parameters == null || parameters.length < 1 || !(parameters[0] instanceof String))
            return;
        String uuid = (String) parameters[0];

        RetrofitNetworkManager.coinRankingAPI
                .getBitcoin(uuid)
                .enqueue(new NetworkCallBack.RetrofitCallback<BasicResponse>() {
                    @Override
                    public void onSuccess(BasicResponse response) {
                        handleResponse(response);
                    }
                });
    }

    private void handleResponse(BasicResponse response) {
        data.postValue(response.getData());
    }
}
