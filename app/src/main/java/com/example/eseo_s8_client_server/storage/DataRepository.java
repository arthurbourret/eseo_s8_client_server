package com.example.eseo_s8_client_server.storage;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.eseo_s8_client_server.models.Coin;

import java.util.List;

public class DataRepository {
    private final CoinDao sampleDao;
    private final LiveData<List<Coin>> data;
    private final MutableLiveData<ColumnOrder> column = new MutableLiveData<>();

    public enum ColumnOrder {
        NONE,
        PRICE_ASC,
        PRICE_DESC,
        NAME_ASC,
        NAME_DESC
    }

    public DataRepository(Context applicationContext) {
        // init db
        AppDatabase database = AppDatabase.getDatabase(applicationContext);
        this.sampleDao = database.coinDaoDao();

        // innit data to no order
        data = Transformations.switchMap(column, col -> {
            switch (col) {
                case NONE:
                    return sampleDao.getAll();
                case PRICE_ASC:
                    return sampleDao.getAllOrderByPrice(true);
                case PRICE_DESC:
                    return sampleDao.getAllOrderByPrice(false);
                case NAME_ASC:
                    return sampleDao.getAllOrderByName(true);
                case NAME_DESC:
                    return sampleDao.getAllOrderByName(false);
            }
            return null;
        });
        fetchData();
    }

    public LiveData<List<Coin>> getData() {
        return data;
    }

    public void fetchData() {
        column.postValue(ColumnOrder.NONE);
    }

    public void fetchDataByName(boolean order) {
        column.postValue(order ? ColumnOrder.NAME_ASC : ColumnOrder.NAME_DESC);
    }

    public void fetchDataByPrice(boolean order) {
        column.postValue(order ? ColumnOrder.PRICE_ASC : ColumnOrder.PRICE_DESC);
    }

    public void insertData(Coin sampleModel) {
        AppDatabase.databaseWriteExecutor.execute(() -> sampleDao.insert(sampleModel));
    }
}
