package com.example.eseo_s8_client_server.models;

import java.util.List;

public class CoinsResponse {
    private String status;
    private Data data;

    public String getStatus() {
        return status;
    }

    public List<Coin> getData() {
        return data.getCoins();
    }

    static class Data {
        private List<Coin> coins;

        public List<Coin> getCoins() {
            return coins;
        }
    }
}
