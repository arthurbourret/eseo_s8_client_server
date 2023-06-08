package com.example.eseo_s8_client_server.views;

import android.annotation.SuppressLint;
import android.graphics.Color;
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
import com.google.android.material.tabs.TabLayout;
// TODO: attention, peu de temps à la fin du module pour terminer le projet. L'écran de détails devrait être terminé.
public class MainActivity extends AppCompatActivity {
    private CoinsViewModel viewModel;
    private CoinRecyclerAdapter adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO change binding method ?
        // TODO: oui,n utiliser le view bindingr
        setContentView(R.layout.activity_main);

        // TODO change fetch apikey method
        // TODO: essayer de ne pas utiliser les preferences dans l'activity
        PreferencesHelper.getInstance().setApiKey(NetworkConstants.KEY_HEADER_VALUE);

        // init components
        this.initTabs();
        this.initRecyclerCoinView();
        this.initViewModel();
        this.initSyncBtn();
    }

    // TODO: tabs ? je vous conseille de commencer simple (2e activity) et de faire plus complexe si vous avez le temps
    private void initTabs() {
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setSelectedTabIndicatorColor(Color.WHITE);

        // set tab all coins
        TabLayout.Tab all = tabs.newTab().setText("All");
        all.view.setOnClickListener(v -> viewModel.fetchAll());
        tabs.addTab(all, 0);
        // set tab favorites coins
        TabLayout.Tab favorites = tabs.newTab().setText("Favorites");
        favorites.view.setOnClickListener(v -> viewModel.fetchFavorites());
        tabs.addTab(favorites, 1);
    }

    private void initRecyclerCoinView() {
        adapter = new CoinRecyclerAdapter(this);
        RecyclerView recyclerView = findViewById(R.id.listCoins);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(CoinsViewModel.class);
        viewModel.fetchData();
    }

    private void initSyncBtn() {
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
}