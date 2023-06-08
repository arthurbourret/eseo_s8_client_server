package com.example.eseo_s8_client_server.viewmodels;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.eseo_s8_client_server.models.Coin;
import com.example.eseo_s8_client_server.models.CoinsData;
import com.example.eseo_s8_client_server.models.CoinsResponse;
import com.example.eseo_s8_client_server.network.NetworkCallBack;
import com.example.eseo_s8_client_server.network.RetrofitNetworkManager;
import com.example.eseo_s8_client_server.storage.DataRepository;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;

public class CoinsViewModel extends AndroidViewModel implements IViewModel<List<Coin>> {
    private final OkHttpClient client = new OkHttpClient();

    private final MutableLiveData<List<Coin>> data;
    private final MutableLiveData<Map<String, Bitmap>> icons;
    private final DataRepository dataRepository;
    private final CoinsData coins;

    public CoinsViewModel(@NotNull Application application) {
        super(application);
        this.dataRepository = new DataRepository(application);
        this.data = new MutableLiveData<>();
        this.dataRepository.getData().observeForever(data::postValue);
        this.icons = new MutableLiveData<>();

        this.coins = new CoinsData();
    }

    public LiveData<List<Coin>> getData() {
        return data;
    }

    public LiveData<Map<String, Bitmap>> getIcons() {
        return icons;
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

    public void getFavorites() {
        data.postValue(coins.getFavorites());
    }

    public void getAll() {
        data.postValue(coins.getCoins());
    }

    private void fetchIcons(List<Coin> coins) {
        for (Coin coin : coins) {
            String url = coin.getIconUrl();
            url.replace(".svg", ".png");
            fetchIcon(url, coin.getUuid());
        }
    }

    private void fetchIcon(String url, String uuid) {
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new NetworkCallBack.OkHttpCallback() {
            @Override
            public void onSuccess(ResponseBody response) {
                Bitmap icon = getIconFromStream(response.byteStream());
                Map<String, Bitmap> tmp = icons.getValue();
                if (tmp == null) tmp = new HashMap<>();
                tmp.put(uuid, icon);
                icons.postValue(tmp);
            }
        });
    }

    private void handleResponse(CoinsResponse response) {
        this.coins.setCoinList(response.getData());
        this.fetchIcons(coins.getCoins());
        saveCoins(coins.getCoins());
    }

    private void saveCoins(List<Coin> coins) {
        for (Coin coin : coins) {
            this.dataRepository.insertData(coin);
        }
    }

    private Bitmap getIconFromStream(InputStream stream) {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(stream);

        return BitmapFactory.decodeStream(bufferedInputStream);
    }
}
