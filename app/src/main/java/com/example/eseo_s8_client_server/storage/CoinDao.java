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

    @Query("SELECT * FROM coin_table ORDER BY " +
            "CASE WHEN :order = 1 THEN name END ASC, \n" +
            "CASE WHEN :order = 0 THEN name END DESC")
    LiveData<List<Coin>> getAllOrderByName(boolean order);

    @Query("SELECT * FROM coin_table ORDER BY " +
            "CASE WHEN :order = 1 THEN price END ASC, \n" +
            "CASE WHEN :order = 0 THEN price END DESC")
    LiveData<List<Coin>> getAllOrderByPrice(boolean order);

    @Query("SELECT * FROM coin_table ORDER BY " +
            "CASE WHEN :order = 1 THEN rank END ASC, \n" +
            "CASE WHEN :order = 0 THEN rank END DESC")
    LiveData<List<Coin>> getAllOrderByRank(boolean order);
}
