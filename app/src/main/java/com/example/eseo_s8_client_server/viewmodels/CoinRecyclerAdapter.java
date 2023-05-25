package com.example.eseo_s8_client_server.viewmodels;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eseo_s8_client_server.R;
import com.example.eseo_s8_client_server.models.Coin;

import java.util.List;

public class CoinRecyclerAdapter extends RecyclerView.Adapter<CoinView> {
    private final List<Coin> coins;

    public CoinRecyclerAdapter(List<Coin> coins) {
        this.coins=coins;
    }

    @NonNull
    @Override
    public CoinView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_coin,
                parent, false);
        return new CoinView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CoinView holder, int position) {
        holder.initCoin(position, coins.get(position));
    }

    @Override
    public int getItemCount() {
        return this.coins.size();
    }
}