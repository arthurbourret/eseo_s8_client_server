package com.example.eseo_s8_client_server.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CoinsData {
    private final List<Coin> coinList = new ArrayList<>();

    public CoinsData(Coin[] list) {
        if (list != null) setCoinList(list);
    }

    public void setCoinList(Coin[] list) {
         this.coinList.addAll(Arrays.asList(list));
    }

    public Coin getCoin(int position) {
        return coinList.get(position);
    }

    public int size() {
        return coinList.size();
    }
}
