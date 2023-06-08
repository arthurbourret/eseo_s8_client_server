package com.example.eseo_s8_client_server.views;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eseo_s8_client_server.databinding.LayoutListCoinBinding;
import com.example.eseo_s8_client_server.models.Coin;
import com.example.eseo_s8_client_server.models.CoinsData;
import com.example.eseo_s8_client_server.viewmodels.CoinViewModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CoinRecyclerAdapter extends RecyclerView.Adapter<CoinView> {
    // TODO: une fois la récupération des images simplifiée, ne pas utilisé de VM ici. Les données sont transmises par l'activity
    private final CoinViewModel coinViewModel;


    private final CoinsData coins;
    private final Map<String, Bitmap> icons;

    public CoinRecyclerAdapter(ViewModelStoreOwner owner) {
        this.coins = new CoinsData();
        this.coinViewModel = new ViewModelProvider(owner).get(CoinViewModel.class);
        this.icons = new HashMap<>();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setCoins(List<Coin> coins) {
        if (coins == null) return;
        this.coins.setCoinList(coins);
        this.notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setIcons(Map<String, Bitmap> icons) {
        this.icons.putAll(icons);
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
        String uuid = coin.getUuid();

        // set holder
        holder.initCoin(coin);
        //set holder icon
        holder.setImageIcon(icons.get(uuid));
        // set on click
        holder.setOnClickListener(v -> onViewClickHandler(v, uuid));
    }

    private void onViewClickHandler(View v, String uuid) {
        coinViewModel.fetchData(uuid);
        // TODO : arthur il y a du rouge
        //coinViewModel.getData().observe((LifecycleOwner) owner, res -> {
        // display pop up
        //    CoinPopUp popup = new CoinPopUp(v);
        //    popup.initPopUp(res);
        //    popup.displayPopUp();
        //});
    }

    @Override
    public int getItemCount() {
        return (coins == null) ? 0 : coins.size();
    }
}