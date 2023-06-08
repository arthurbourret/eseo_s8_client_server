package com.example.eseo_s8_client_server.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eseo_s8_client_server.databinding.LayoutListCoinBinding;
import com.example.eseo_s8_client_server.models.Coin;
import com.example.eseo_s8_client_server.models.CoinsData;
import com.example.eseo_s8_client_server.popup.CoinPopUp;
import com.example.eseo_s8_client_server.viewmodels.CoinViewModel;
import com.example.eseo_s8_client_server.viewmodels.IconViewModel;

import java.util.List;

public class CoinRecyclerAdapter extends RecyclerView.Adapter<CoinView> {
    // TODO: une fois la récupération des images simplifiée, ne pas utilisé de VM ici. Les données sont transmises par l'activity
    private final ViewModelStoreOwner owner;
    private final CoinViewModel coinViewModel;
    private final IconViewModel iconViewModel;

    private final CoinsData coins;

    public CoinRecyclerAdapter(ViewModelStoreOwner owner) {
        this.owner = owner;
        this.coins = new CoinsData();
        this.coinViewModel = new ViewModelProvider(owner).get(CoinViewModel.class);
        this.iconViewModel = new ViewModelProvider(owner).get(IconViewModel.class);
    }

    public void setCoins(List<Coin> coins) {
        if (coins != null) this.coins.setCoinList(coins);
    }

    @NonNull
    @Override
    public CoinView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutListCoinBinding binding = LayoutListCoinBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CoinView(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CoinView holder, int position) {
        Coin coin = coins.getCoin(position);
        String uuid = coin.getUuid();

        // set holder
        holder.initCoin(coin);

        // set holder icon
        holder.setImageIcon(iconViewModel.getIcon(uuid));
        if (!iconViewModel.hasIcon(uuid))
            iconViewModel.fetchData(coin.getUuid(), coin.getIconUrl());
        iconViewModel.getData().observe((LifecycleOwner) owner,
                icons -> holder.setImageIcon(icons.get(uuid)));

        // set on click
        holder.setOnClickListener(v -> onViewClickHandler(v, uuid));
    }

    private void onViewClickHandler(View v, String uuid) {
        coinViewModel.fetchData(uuid);
        coinViewModel.getData().observe((LifecycleOwner) owner, res -> {
            // display pop up
            CoinPopUp popup = new CoinPopUp(v);
            popup.setIcon(iconViewModel.getIcon(res.getUuid()));
            popup.initPopUp(res);
            popup.displayPopUp();
        });
    }

    @Override
    public int getItemCount() {
        return (coins == null) ? 0 : coins.size();
    }
}