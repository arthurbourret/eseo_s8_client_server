package com.example.eseo_s8_client_server.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.eseo_s8_client_server.models.PriceResponse;
import com.example.eseo_s8_client_server.models.SampleModel;
import com.example.eseo_s8_client_server.network.RetrofitNetworkManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RetrofitViewModel extends ViewModel implements IViewModel {

    private final MutableLiveData<SampleModel> data = new MutableLiveData<>();

    public LiveData<SampleModel> getData() {
        return data;
    }

    @Override
    public void generateNextValue() {
        RetrofitNetworkManager.coinRankingAPI.getBitcoinPrice().enqueue(new Callback<PriceResponse>() {
            @Override
            public void onResponse(Call<PriceResponse> call, Response<PriceResponse> response) {
                if (response.body() != null) {
                    handleResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<PriceResponse> call, Throwable t) {
                // NO-OP
            }
        });
    }

    private void handleResponse(PriceResponse response) {
        data.postValue(new SampleModel(response.getData().getPrice()));
    }
}