package com.example.eseo_s8_client_server.viewmodels;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eseo_s8_client_server.R;
import com.example.eseo_s8_client_server.models.Coin;
import com.example.eseo_s8_client_server.models.CoinsData;

import java.util.HashMap;
import java.util.Map;

public class CoinRecyclerAdapter extends RecyclerView.Adapter<CoinView> {
    private CoinsData coins;
    private final IconLoader loader;
    private final Map<String, Drawable> icons;

    public CoinRecyclerAdapter(Context context) {
        this.icons = new HashMap<>();
        loader = IconLoader.getInstance();
        loader.setContext(context);
    }

    public void setCoins(CoinsData coins) {
        this.icons.clear();
        if (coins != null) this.coins = coins;
    }

    @NonNull
    @Override
    public CoinView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_list_coin, parent, false);
        return new CoinView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CoinView holder, int position) {
        Coin coin = coins.getCoin(position);
        String uuid = coin.getUuid();
        holder.initCoin(position, coin);

        holder.setImageIcon(null);
        if (icons.containsKey(uuid) && icons.get(uuid) != null) {
            holder.setImageIcon(icons.get(uuid));
        } else loader.loadIcon(coin.getIconUrl(), icon -> {
            icons.put(uuid, icon);
            holder.setImageIcon(icon);
        });
    }

    @Override
    public int getItemCount() {
        return (coins == null) ? 0 : coins.size();
    }
}