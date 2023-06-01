package com.example.eseo_s8_client_server.models;

public class BasicResponse {
    private String status;
    private Data data;

    public String getStatus() {
        return status;
    }

    public Coin getData() {
        return data.getCoin();
    }

    class Data {
        private Coin coin;

        public Coin getCoin() {
            return coin;
        }
    }
}
