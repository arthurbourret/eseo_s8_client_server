package com.example.eseo_s8_client_server.models;

public class PriceResponse extends GenericResponse {

    private PriceResponseData data;

    public PriceResponseData getData() {
        return data;
    }

    public void setData(PriceResponseData data) {
        this.data = data;
    }
}
