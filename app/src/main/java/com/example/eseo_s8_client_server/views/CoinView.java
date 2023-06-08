package com.example.eseo_s8_client_server.views;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eseo_s8_client_server.R;
import com.example.eseo_s8_client_server.databinding.LayoutListCoinBinding;
import com.example.eseo_s8_client_server.models.Coin;

public class CoinView extends RecyclerView.ViewHolder {
    private final LayoutListCoinBinding binding;

    public CoinView(@NonNull LayoutListCoinBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    @SuppressLint("SetTextI18n")
    public void initCoin(Coin coin) {
        binding.nameCoin.setText(coin.getName());
        binding.symbolCoin.setText(coin.getSymbol());
        binding.priceCoin.setText(coin.getPrice() + "");
        binding.changeCoin.setText(coin.getChange() + "");

        if (coin.isFavorite()) setFavorite();
        else unsetFavorite();

        try {
            int color = Color.parseColor(coin.getColor());
            binding.couleurCoin.setBackgroundColor(color);
        } catch (Exception ignored) {
        }
    }

    private void setFavorite() {
        itemView.findViewById(R.id.favorite).setBackgroundResource(R.drawable.star_gold);
    }

    private void unsetFavorite() {
        itemView.findViewById(R.id.favorite).setBackgroundResource(0);
    }

    public void setImageIcon(Bitmap icon) {
        binding.iconCoin.setImageBitmap(icon);
    }

    public void setOnClickListener(View.OnClickListener listener) {
        itemView.setOnClickListener(listener);
    }
}
