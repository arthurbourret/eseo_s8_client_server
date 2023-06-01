package com.example.eseo_s8_client_server;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eseo_s8_client_server.models.CoinsData;
import com.example.eseo_s8_client_server.viewmodels.CoinRecyclerAdapter;
import com.example.eseo_s8_client_server.viewmodels.IViewModel;
import com.example.eseo_s8_client_server.viewmodels.RetrofitViewModel;

public class MainActivity extends AppCompatActivity {
    private IViewModel<CoinsData> viewModel;
    private CoinRecyclerAdapter adapter;
    private static Context APPLICATION_CONTEXT;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        APPLICATION_CONTEXT = getApplicationContext();
        setContentView(R.layout.activity_main);

        adapter = new CoinRecyclerAdapter(this);
        RecyclerView recyclerView = findViewById(R.id.listCoins);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(RetrofitViewModel.class);
        viewModel.generateNextValue();

        findViewById(R.id.sync).setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "Fetch data", Toast.LENGTH_LONG).show();
            viewModel.generateNextValue();
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void updateCoins(CoinsData coinsData) {
        adapter.setCoins(coinsData);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.getData().observe(this, this::updateCoins);
    }

    @Override
    protected void onPause() {
        super.onPause();
        viewModel.getData().removeObservers(this);
    }

    public static Context getContext(){
        return APPLICATION_CONTEXT;
    }
}