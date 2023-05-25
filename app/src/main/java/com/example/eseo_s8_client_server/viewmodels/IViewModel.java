package com.example.eseo_s8_client_server.viewmodels;

import androidx.lifecycle.LiveData;

import com.example.eseo_s8_client_server.models.SampleModel;

public interface IViewModel {
    LiveData<SampleModel> getData();
    void generateNextValue();
}
