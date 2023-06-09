package com.example.eseo_s8_client_server.views;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eseo_s8_client_server.databinding.ActivityMainBinding;
import com.example.eseo_s8_client_server.models.Listener;
import com.example.eseo_s8_client_server.network.NetworkConstants;
import com.example.eseo_s8_client_server.popup.CoinPopUp;
import com.example.eseo_s8_client_server.storage.PreferencesHelper;
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

        // TODO change fetch apikey method
        // TODO: essayer de ne pas utiliser les preferences dans l'activity
        PreferencesHelper.getInstance().setApiKey(NetworkConstants.KEY_HEADER_VALUE);

        /* TODO list
         - display fav quand quite popup
         - mieux order btn
         - chercher bugs
         - icon appli ?
         */

        // init components
        this.initTabs();
        this.initRecyclerCoinView();
        this.initViewModel();
        this.initSyncBtn();
        this.initOrderBtn();
    }

    private void initOrderBtn() {
        binding.orderName.setOnClickListener(v -> {
            Boolean order = viewModel.orderByName();
            String message = "Nom";
            if (order != null) message += order ? " ▴" : " ▾";
            ((TextView) v).setText(message);
        });

        binding.orderPrice.setOnClickListener(v -> {
            Boolean order = viewModel.orderByPrice();
            String message = "Prix";
            if (order != null) message += order ? " ▴" : " ▾";
            ((TextView) v).setText(message);
        });

        viewModel.getOrderMessage().observe(this, message -> Toast
                .makeText(MainActivity.this, message, Toast.LENGTH_SHORT)
                .show()
        );
    }

    private void initTabs() {
        TabLayout tabs = binding.tabs;

        // set tab all coins
        TabLayout.Tab all = tabs.newTab().setText("All");
        all.view.setOnClickListener(v -> viewModel.getAll());
        tabs.addTab(all, 0);

        // set tab favorites coins
        TabLayout.Tab favorites = tabs.newTab().setText("Favorites");
        favorites.view.setOnClickListener(v -> viewModel.getFavorites());
        tabs.addTab(favorites, 1);
    }

    private void initRecyclerCoinView() {
        adapter = new CoinRecyclerAdapter(openCoinListener);
        RecyclerView recyclerView = binding.listCoins;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    private final Listener openCoinListener = coin -> {
        // clean all prev observers
        infoViewModel.getData().removeObservers(MainActivity.this);

        // display pop up
        CoinPopUp popup = new CoinPopUp(MainActivity.this);
        popup.initContent(coin);

        // fetch data for one coin
        infoViewModel.getData().observe(MainActivity.this, popup::initContent);
        infoViewModel.fetchData(coin.getUuid());

        popup.displayPopUp();
    };

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
            Toast.makeText(MainActivity.this, "Fetch data", Toast.LENGTH_LONG).show();
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
}