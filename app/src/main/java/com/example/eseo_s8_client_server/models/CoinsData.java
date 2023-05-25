package com.example.eseo_s8_client_server.models;

import java.util.List;

public class CoinsData {
    private final List<Coin> coinList;

    public CoinsData(List<Coin> list) {
        this.coinList = list;
    }

    public List<Coin> getCoinList() {
        return coinList;
    }
}
