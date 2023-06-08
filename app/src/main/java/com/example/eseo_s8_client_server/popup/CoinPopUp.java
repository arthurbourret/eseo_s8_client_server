package com.example.eseo_s8_client_server.popup;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eseo_s8_client_server.R;
import com.example.eseo_s8_client_server.databinding.PopupCoinBinding;
import com.example.eseo_s8_client_server.models.Coin;
import com.example.eseo_s8_client_server.storage.PreferencesHelper;

public class CoinPopUp extends PopUpFragment {
    private PopupCoinBinding binding;
    // TODO: est-ce qu'on peut éviter de garder le coin ici ? en le passant dans le init ?
    private final Coin coin;

    public CoinPopUp(View view, Coin coin) {
        super(view, R.layout.popup_coin, true);
        this.coin = coin;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void initPopUp() {
        // set favorite
        if (coin.isFavorite()) {
            binding.favorite.setImageResource(R.drawable.star_gold);
        } else {
            binding.favorite.setImageResource(R.drawable.star_empty);
        }

        binding.favorite.setOnClickListener(v -> {
            if (coin.isFavorite()){
                coin.setFavorite(false);
                PreferencesHelper.getInstance().removeCoinFromFavorite(coin.getUuid());
            }else {
                coin.setFavorite(true);
                PreferencesHelper.getInstance().addCoinToFavorite(coin.getUuid());
            }
        });

        // set texts
        binding.nameCoin.setText(coin.getName());
        binding.symbolCoin.setText(coin.getSymbol());
        binding.priceCoin.setText(coin.getPrice() + "");
        binding.changeCoin.setText(coin.getChange() + "");
        binding.marketCap.setText(coin.getMarketCap());
        binding.volume24h.setText(coin.getVolume24H());
        binding.description.setText(coin.getDescription());

        // set color
        try {
            int color = Color.parseColor(coin.getColor());
            binding.couleurCoin.setBackgroundColor(color);
        } catch (Exception ignored) {
        }
    }

    public void setIcon(Drawable icon) {
        binding.iconCoin.setImageDrawable(icon);
    }
}
