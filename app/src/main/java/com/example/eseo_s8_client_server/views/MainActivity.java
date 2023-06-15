package com.example.eseo_s8_client_server.views;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eseo_s8_client_server.R;
import com.example.eseo_s8_client_server.databinding.ActivityMainBinding;
import com.example.eseo_s8_client_server.models.Listener;
import com.example.eseo_s8_client_server.popup.CoinPopUp;
import com.example.eseo_s8_client_server.viewmodels.CoinViewModel;
import com.example.eseo_s8_client_server.viewmodels.CoinsViewModel;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private CoinViewModel infoViewModel;
    private CoinsViewModel viewModel;
    private CoinRecyclerAdapter adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // init components
        this.initTabs();
        this.initRecyclerCoinView();
        this.initViewModel();
        this.initSyncBtn();
        this.initOrderBtn();
    }

    @SuppressLint("SetTextI18n")
    private void initOrderBtn() {
        String colPrice = string(R.string.col_price);
        String colName = string(R.string.col_name);
        String colRank = string(R.string.col_rank);

        binding.orderName.setOnClickListener(v -> {
            Boolean order = viewModel.orderByName();
            String col = getBtnOrderName(colName, order);
            setBtnOrderName(col, colPrice, colRank);
        });

        binding.orderPrice.setOnClickListener(v -> {
            Boolean order = viewModel.orderByPrice();
            String col = getBtnOrderName(colPrice, order);
            setBtnOrderName(colName, col, colRank);
        });

        binding.orderRank.setOnClickListener(v -> {
            Boolean order = viewModel.orderByRank();
            String col = getBtnOrderName(colRank, order);
            setBtnOrderName(colName, colPrice, col);
        });

        viewModel.getOrderMessage().observe(this, message -> Toast
                .makeText(MainActivity.this, message, Toast.LENGTH_SHORT)
                .show()
        );
    }

    private String getBtnOrderName(String base, Boolean order) {
        return base + (order == null ? "" : (order ? " ▴" : " ▾"));
    }

    private void setBtnOrderName(String colName, String colPrice, String colRank) {
        binding.orderName.setText(colName);
        binding.orderPrice.setText(colPrice);
        binding.orderRank.setText(colRank);
    }

    private void initTabs() {
        TabLayout tabs = binding.tabs;

        // set tab all coins
        TabLayout.Tab all = tabs.newTab().setText(string(R.string.tab_all));
        all.view.setOnClickListener(v -> viewModel.getAll());
        tabs.addTab(all, 0);

        // set tab favorites coins
        TabLayout.Tab favorites = tabs.newTab().setText(string(R.string.tab_favs));
        favorites.view.setOnClickListener(v -> viewModel.getFavorites());
        tabs.addTab(favorites, 1);
    }

    private final Listener openCoinPopUpListener = coin -> {
        // clean all prev observers
        infoViewModel.getData().removeObservers(MainActivity.this);

        // display pop up
        CoinPopUp popup = new CoinPopUp(MainActivity.this);
        popup.initContent(coin);
        popup.initToggleFavorite(coin, viewModel.changeFavoriteListener);

        // fetch data for one coin
        infoViewModel.getData().observe(MainActivity.this, coinDetailed -> {
            coinDetailed.setFavorite(coin.isFavorite());
            popup.initContent(coinDetailed);
        });
        infoViewModel.fetchData(coin.getUuid());

        popup.displayPopUp();
    };

    private void initRecyclerCoinView() {
        adapter = new CoinRecyclerAdapter(openCoinPopUpListener);
        RecyclerView recyclerView = binding.listCoins;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    private void initViewModel() {
        // view model for all coins
        viewModel = new ViewModelProvider(this).get(CoinsViewModel.class);
        viewModel.fetchData();

        // view model for coin's information
        infoViewModel = new ViewModelProvider(this).get(CoinViewModel.class);
        viewModel.getErrorMessage().observe(this, message -> Toast
                .makeText(MainActivity.this, "⚠ " + message, Toast.LENGTH_SHORT)
                .show()
        );
    }

    private void initSyncBtn() {
        binding.sync.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, string(R.string.fetch), Toast.LENGTH_LONG)
                    .show();
            viewModel.fetchData();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.getData().observe(this, adapter::setCoins);
    }

    @Override
    protected void onPause() {
        super.onPause();
        viewModel.getData().removeObservers(this);
    }

    private String string(int id) {
        return getResources().getString(id);
    }
}