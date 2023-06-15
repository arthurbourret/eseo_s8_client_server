package com.example.eseo_s8_client_server.popup;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.example.eseo_s8_client_server.R;
import com.example.eseo_s8_client_server.databinding.PopupCoinBinding;
import com.example.eseo_s8_client_server.models.Coin;
import com.example.eseo_s8_client_server.models.Listener;
import com.squareup.picasso.Picasso;

public class CoinPopUp implements PopUp {
    private final PopupCoinBinding binding;
    protected PopupWindow popupWindow;

    public CoinPopUp(Context context) {
        this.binding = PopupCoinBinding.inflate(LayoutInflater.from(context));
        this.setupDisplayer();
    }

    private void setupDisplayer() {
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;

        this.popupWindow = new PopupWindow(binding.getRoot(), width, height, true);
        this.popupWindow.setOnDismissListener(this::onClose);
        this.popupWindow.setOutsideTouchable(false);
    }

    @SuppressLint("SetTextI18n")
    public void initContent(Coin coin) {
        // show favorite status
        if (coin.isFavorite()) {
            setFavorite();
        } else {
            unsetFavorite();
        }

        // set close btn
        binding.closePopUp.setOnClickListener(v -> popupWindow.dismiss());

        // set texts
        binding.nameCoin.setText(coin.getName());
        binding.symbolCoin.setText("(" + coin.getSymbol() + ")");
        binding.priceCoin.setText(coin.getPrice() + "");
        binding.rankCoin.setText(coin.getRank() + "");
        binding.changeCoin.setText(coin.getChange() + "");
        binding.marketCap.setText(coin.getMarketCap());
        binding.volume24h.setText(coin.getVolume24H());
        binding.description.setText(coin.getDescription());

        // set icon
        Picasso.get().load(coin.getIconUrl().replace(".svg", ".png"))
                .into(binding.iconCoin);

        // set color
        try {
            int color = Color.parseColor(coin.getColor());
            binding.couleurCoin.setBackgroundColor(color);
        } catch (Exception ignored) {
        }
    }

    public void initToggleFavorite(Coin coin, Listener callback) {
        binding.favorite.setOnClickListener(v -> {
            if (coin.isFavorite()) {
                unsetFavorite();
            } else {
                setFavorite();
            }

            callback.onClick(coin);
        });
    }

    private void setFavorite() {
        binding.favorite.setImageResource(R.drawable.star_gold);
    }

    private void unsetFavorite() {
        binding.favorite.setImageResource(R.drawable.star_empty);
    }

    @Override
    public final void displayPopUp() {
        popupWindow.showAtLocation(binding.getRoot(), Gravity.CENTER, 0, 0);
    }

    @Override
    public void onClose() {
    }
}
