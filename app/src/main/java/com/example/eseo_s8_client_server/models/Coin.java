package com.example.eseo_s8_client_server.models;

public class Coin {
    private String uuid;
    private String iconUrl;
    private String symbol;
    private String name;
    private String color;
    private String description;
    private Integer rank;
    private Float change;
    private Float price;
    private Long marketCap;
    private Long volume24H;
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

    public Long getMarketCap() {
        return marketCap;
    }

    public Long getVolume24H() {
        return volume24H;
    }

    public boolean isFavorite() {
        return favorite;
    }
    
    public void setFavorite(boolean favorite){
        this.favorite = favorite;
    }
}
