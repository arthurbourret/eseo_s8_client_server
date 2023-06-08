package com.example.eseo_s8_client_server.models;
// TODO: décomposer, un fichier pour chaque classe, List plutôt que []
public class ListResponse {
    private String status;
    private Data data;

    public String getStatus() {
        return status;
    }

    public Coin[] getData() {
        return data.getCoins();
    }

    static class Data {
        private Coin[] coins;

        public Coin[] getCoins() {
            return coins;
        }
    }
}
