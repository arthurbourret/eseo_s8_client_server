package com.example.eseo_s8_client_server.models;

public class Coin {
    private String uuid;
    private String iconUrl;
    private String symbol;
    private String name;
    private String color;
    private Float change;
    private Float price;
    private int marketCap;
    private int volume24H;
    private boolean favorite;

    public String getIconUrl() {
        return iconUrl;
    }
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

    public Float getChange() {
        return change;
    }

    public Float getPrice() {
        return price;
    }

    public int getMarketCap() {
        return marketCap;
    }

    public int getVolume24H() {
        return volume24H;
    }

    public boolean isFavorite() {
        return favorite;
    }
    
    public void setFavorite(boolean favorite){
        this.favorite = favorite;
    }
}
