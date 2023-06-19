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
    private String nameLike = "";

    public enum ColumnOrder {
        NONE,
        PRICE_ASC,
        PRICE_DESC,
        NAME_ASC,
        NAME_DESC,
        RANK_ASC,
        RANK_DESC,
        NAME_LIKE
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
                case RANK_ASC:
                    return sampleDao.getAllOrderByRank(true);
                case RANK_DESC:
                    return sampleDao.getAllOrderByRank(false);
                case NAME_LIKE:
                    return sampleDao.getAllLike(nameLike);
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

    public void fetchDataByRank(boolean order) {
        column.postValue(order ? ColumnOrder.RANK_ASC : ColumnOrder.RANK_DESC);
    }

    public void fetchDataByNameLike(String name) {
        if (name == null) name = "";
        this.nameLike = "%" + name.toLowerCase() + "%";
        column.postValue(ColumnOrder.NAME_LIKE);
    }

    // TODO: nom de paramètre peu explicite
    public void insertData(Coin sampleModel) {
        AppDatabase.databaseWriteExecutor.execute(() -> sampleDao.insert(sampleModel));
    }
}
