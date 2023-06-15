package com.example.eseo_s8_client_server.viewmodels;

import android.annotation.SuppressLint;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.eseo_s8_client_server.models.Coin;
import com.example.eseo_s8_client_server.models.CoinsData;
import com.example.eseo_s8_client_server.models.CoinsResponse;
import com.example.eseo_s8_client_server.models.Listener;
import com.example.eseo_s8_client_server.network.RetrofitNetworkManager;
import com.example.eseo_s8_client_server.storage.DataRepository;
import com.example.eseo_s8_client_server.storage.PreferencesHelper;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoinsViewModel extends AndroidViewModel implements IViewModel<List<Coin>> {
    private enum Onglet {
        TOUS,
        FAVORIS,
    }

    private final MutableLiveData<List<Coin>> data = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private final CoinsData coins = new CoinsData();
    private final DataRepository dataRepository;
    private Onglet current = Onglet.TOUS;

    private final MutableLiveData<String> orderMessage = new MutableLiveData<>();
    private String orderColumn;
    private boolean order;

    public CoinsViewModel(@NotNull Application application) {
        super(application);
        this.dataRepository = new DataRepository(application);

        // init data from db
        this.dataRepository.fetchData();
        this.connectDRtoData();
    }

    public LiveData<List<Coin>> getData() {
        return data;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public LiveData<String> getOrderMessage() {
        return orderMessage;
    }

    // TODO: pourquoi le varargs en paramètre ?
    public void fetchData(Object... parameters) {
        RetrofitNetworkManager.coinRankingAPI
                .getCoinsResponse()
                .enqueue(new Callback<CoinsResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<CoinsResponse> call,
                                           @NonNull Response<CoinsResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            handleResponse(response.body());
                        } else errorMessage.postValue("Data was not loaded properly");
                    }

                    @Override
                    public void onFailure(@NonNull Call<CoinsResponse> call,
                                          @NonNull Throwable t) {
                        errorMessage.postValue("Could not fetch data");
                    }
                });
    }

    public void getFavorites() {
        data.postValue(coins.getFavorites());
        current = Onglet.FAVORIS;
    }

    public void getAll() {
        data.postValue(coins.getCoins());
        current = Onglet.TOUS;
    }

    public Boolean noOrder() {
        orderColumn = null;
        dataRepository.fetchData();
        orderMessage.postValue("Reset order");
        connectDRtoData();
        return null;
    }

    public Boolean orderByName() {
        if (Objects.equals(orderColumn, "name") && !order) {
            return noOrder();
        } else {
            boolean boolOrder = !Objects.equals(orderColumn, "name");
            this.orderColumn = "name";
            this.order = boolOrder;

            dataRepository.fetchDataByName(boolOrder);
            orderMessage.postValue("Order by name " + (boolOrder ? "▴" : "▾"));
            connectDRtoData();

            return boolOrder;
        }
    }

    public Boolean orderByPrice() {
        if (Objects.equals(orderColumn, "price") && order) {
            return noOrder();
        } else {
            boolean boolOrder = Objects.equals(orderColumn, "price");
            this.orderColumn = "price";
            this.order = boolOrder;

            dataRepository.fetchDataByPrice(boolOrder);
            orderMessage.postValue("Order by price " + (boolOrder ? "▴" : "▾"));
            connectDRtoData();

            return boolOrder;
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public final Listener changeFavoriteListener = coin -> {
        if (coin.isFavorite()) {
            PreferencesHelper.getInstance().removeCoinFromFavorite(coin);
        } else {
            PreferencesHelper.getInstance().addCoinToFavorite(coin);
        }

        if (current == Onglet.FAVORIS) {
            if (!coin.isFavorite()) coins.removeCoin(coin.getUuid());
            data.postValue(coins.getFavorites());
        } else {
            data.postValue(coins.getCoins());
        }
    };

    private void connectDRtoData() {
        // TODO dataRepository.getData().removeObservers();
        dataRepository.getData().observeForever(coinList -> {
            coins.setCoinList(coinList);
            getFavoriteFromPrefs(coins.getCoins());

            if (current == Onglet.TOUS) data.postValue(coins.getCoins());
            else data.postValue(coins.getFavorites());
        });
    }

    private void getFavoriteFromPrefs(List<Coin> coins) {
        List<String> favorites = PreferencesHelper.getInstance().getFavoriteCoinsIds();
        if (favorites == null || favorites.size() < 1) return;

        for (Coin coin : coins) {
            if (favorites.contains(coin.getUuid())) coin.setFavorite(true);
        }
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
