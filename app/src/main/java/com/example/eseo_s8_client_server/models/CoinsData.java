package com.example.eseo_s8_client_server.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CoinsData {
    private final List<Coin> coinList = new ArrayList<>();

    public CoinsData() {}

    public void setCoinList(Coin[] list) {
        if (list == null) return;

        this.coinList.clear();
        this.coinList.addAll(Arrays.asList(list));
    }

    public void setCoinList(List<Coin> list) {
        if (list == null) return;

        this.coinList.clear();
        this.coinList.addAll(list);
    }

    public int size() {
        return coinList.size();
    }

    public Coin getCoin(int position) {
        return coinList.get(position);
    }

    public List<Coin> getCoins() {
        return coinList;
    }

    public List<Coin> getFavorites() {
        return coinList.stream().filter(Coin::isFavorite).collect(Collectors.toList());
    }
}
