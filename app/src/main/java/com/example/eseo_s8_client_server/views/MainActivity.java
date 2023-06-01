package com.example.eseo_s8_client_server.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eseo_s8_client_server.R;
import com.example.eseo_s8_client_server.models.CoinsData;
import com.example.eseo_s8_client_server.network.NetworkConstants;
import com.example.eseo_s8_client_server.storage.PreferencesHelper;
import com.example.eseo_s8_client_server.viewmodels.CoinsViewModel;
import com.example.eseo_s8_client_server.viewmodels.IViewModel;

public class MainActivity extends AppCompatActivity {
    private IViewModel<CoinsData> viewModel;
    private CoinRecyclerAdapter adapter;
    private static Context APPLICATION_CONTEXT;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO mettre dans Application
        APPLICATION_CONTEXT = getApplicationContext();
        setContentView(R.layout.activity_main);

        // TODO separe en * methodes

        // TODO change fetch apikey method
        PreferencesHelper.getInstance().setApiKey(NetworkConstants.KEY_HEADER_VALUE);

        adapter = new CoinRecyclerAdapter(this);
        RecyclerView recyclerView = findViewById(R.id.listCoins);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(CoinsViewModel.class);
        viewModel.fetchData();

        findViewById(R.id.sync).setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "Fetch data", Toast.LENGTH_LONG).show();
            viewModel.fetchData();
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

    public static Context getContext() {
        return APPLICATION_CONTEXT;
    }
}