package com.example.eseo_s8_client_server.storage;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.eseo_s8_client_server.models.Coin;

import java.util.List;

public class DataRepository {
    private final CoinDao sampleDao;
    private LiveData<List<Coin>> data;

    public DataRepository(Context applicationContext) {
        AppDatabase database = AppDatabase.getDatabase(applicationContext);
        this.sampleDao = database.coinDaoDao();
    }

    public LiveData<List<Coin>> getData() {
        return data;
    }

    public void fetchData() {
        this.data = sampleDao.getAll();
    }

    public void fetchDataByName(boolean order) {
        this.data = sampleDao.getAllOrderByName(order);
    }

    public void fetchDataByPrice(boolean order) {
        this.data = sampleDao.getAllOrderByPrice(order);
    }

    public void insertData(Coin sampleModel) {
        AppDatabase.databaseWriteExecutor.execute(() -> sampleDao.insert(sampleModel));
    }
}
