package com.example.eseo_s8_client_server.models;

public class ListResponse extends GenericResponse {

    private CoinsData data;

    public CoinsData getData() {
        return data;
    }

    public void setData(CoinsData data) {
        this.data = data;
    }
}
