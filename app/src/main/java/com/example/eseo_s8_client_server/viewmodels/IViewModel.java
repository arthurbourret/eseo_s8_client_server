package com.example.eseo_s8_client_server.viewmodels;

import androidx.lifecycle.LiveData;

public interface IViewModel<T> {
    LiveData<T> getData();
    void generateNextValue();
}
