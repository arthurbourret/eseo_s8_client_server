package com.example.eseo_s8_client_server;

import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eseo_s8_client_server.viewmodels.CoinRecyclerAdapter;
import com.example.eseo_s8_client_server.viewmodels.IViewModel;
import com.example.eseo_s8_client_server.viewmodels.MainViewModel;

public class MainActivity extends AppCompatActivity {
    private IViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        CoinRecyclerAdapter adapter = new CoinRecyclerAdapter();

        RecyclerView questionRecyclerView = findViewById(R.id.listCoins);
        questionRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        questionRecyclerView.setHasFixedSize(true);
        questionRecyclerView.setAdapter(adapter);
    }
}