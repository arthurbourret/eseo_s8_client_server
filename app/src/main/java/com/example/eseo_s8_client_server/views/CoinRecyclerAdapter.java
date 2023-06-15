package com.example.eseo_s8_client_server.views;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eseo_s8_client_server.databinding.LayoutListCoinBinding;
import com.example.eseo_s8_client_server.models.Coin;
import com.example.eseo_s8_client_server.models.CoinsData;
import com.example.eseo_s8_client_server.models.Listener;

import java.util.List;

public class CoinRecyclerAdapter extends RecyclerView.Adapter<CoinView> {
    private final CoinsData coins;
    private final Listener openPopUpListener;

    public CoinRecyclerAdapter(Listener listener) {
        this.coins = new CoinsData();
        this.openPopUpListener = listener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setCoins(List<Coin> coins) {
        if (coins == null) return;
        this.coins.setCoinList(coins);
        this.notifyDataSetChanged();
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

        // set holder
        holder.initCoin(coin);
        // set on click
        holder.setOnClickListener(v -> openPopUpListener.onClick(coin));
    }

    @Override
    public int getItemCount() {
        return coins.size();
    }
}