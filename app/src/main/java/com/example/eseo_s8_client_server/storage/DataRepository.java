package com.example.eseo_s8_client_server.storage;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.example.eseo_s8_client_server.models.Coin;

import java.util.List;

public class DataRepository {
    private final CoinDao sampleDao;
    private final MediatorLiveData<List<Coin>> data = new MediatorLiveData<>();

    public DataRepository(Context applicationContext) {
        AppDatabase database = AppDatabase.getDatabase(applicationContext);
        this.sampleDao = database.coinDaoDao();
    }

    public LiveData<List<Coin>> getData() {
        return data;
    }

    public void fetchData() {
        this.data.addSource(sampleDao.getAll(), data::setValue);
    }

    public void fetchDataByName(boolean order) {
        this.data.addSource(sampleDao.getAllOrderByName(order), data::setValue);
    }

    public void fetchDataByPrice(boolean order) {
        this.data.addSource(sampleDao.getAllOrderByPrice(order), data::setValue);
    }

    public void insertData(Coin sampleModel) {
        AppDatabase.databaseWriteExecutor.execute(() -> sampleDao.insert(sampleModel));
    }
}
