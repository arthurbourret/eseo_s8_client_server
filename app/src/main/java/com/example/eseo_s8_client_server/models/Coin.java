package com.example.eseo_s8_client_server.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "coin_table")
public class Coin {

    @NonNull
    @PrimaryKey()
    private final String uuid;

    @ColumnInfo(name = "icon_url")
    private final String iconUrl;

    @ColumnInfo(name = "symbol")
    private final String symbol;

    @ColumnInfo(name = "name")
    private final String name;

    @ColumnInfo(name = "color")
    private final String color;

    @ColumnInfo(name = "description")
    private final String description;

    @ColumnInfo(name = "rank")
    private final Integer rank;

    @ColumnInfo(name = "change")
    private final Float change;

    @ColumnInfo(name = "price")
    private final Float price;

    @ColumnInfo(name = "market_cap")
    private final String marketCap;

    @SerializedName("24hVolume")
    @ColumnInfo(name = "volume_24h")
    private final String volume24H;

    private boolean favorite;

    public Coin(@NonNull String uuid, String iconUrl, String symbol, String name,
                String color, String description, Integer rank, Float change,
                Float price, String marketCap, String volume24H) {
        this.uuid = uuid;
        this.iconUrl = iconUrl;
        this.symbol = symbol;
        this.name = name;
        this.color = color;
        this.description = description;
        this.rank = rank;
        this.change = change;
        this.price = price;
        this.marketCap = marketCap;
        this.volume24H = volume24H;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    @NonNull
    public String getUuid() {
        return uuid;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public String getDescription() {
        return description;
    }

    public Integer getRank() {
        return rank;
    }

    public Float getChange() {
        return change;
    }

    public Float getPrice() {
        return price;
    }

    public String getMarketCap() {
        return marketCap;
    }

    public String getVolume24H() {
        return volume24H;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
