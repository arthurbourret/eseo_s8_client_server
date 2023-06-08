package com.example.eseo_s8_client_server.storage;

import androidx.lifecycle.LiveData;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.eseo_s8_client_server.models.Coin;

@Dao
public interface CoinDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Coin coin);

    @Query("SELECT * FROM coin_table")
    LiveData<List<Coin>> getAll();
}
