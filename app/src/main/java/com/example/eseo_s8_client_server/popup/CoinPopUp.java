package com.example.eseo_s8_client_server.popup;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.example.eseo_s8_client_server.CoinApplication;
import com.example.eseo_s8_client_server.R;
import com.example.eseo_s8_client_server.databinding.PopupCoinBinding;
import com.example.eseo_s8_client_server.models.Coin;
import com.example.eseo_s8_client_server.storage.PreferencesHelper;

public class CoinPopUp implements PopUp {
    private final PopupCoinBinding binding;
    private View view;
    protected PopupWindow popupWindow;

    public CoinPopUp(View parent) {
        LayoutInflater inflater = (LayoutInflater) CoinApplication.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.binding = PopupCoinBinding.inflate(inflater, (ViewGroup) parent, false);
        this.setupPopup();
    }

    private void setupPopup() {
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        this.popupWindow = new PopupWindow(view, width, height, true);
        this.popupWindow.setOnDismissListener(this::onClose);
        this.popupWindow.setOutsideTouchable(false);
        this.view = binding.getRoot();
    }

    @SuppressLint("SetTextI18n")
    public void initPopUp(Coin coin) {
        // set favorite
        if (coin.isFavorite()) {
            binding.favorite.setImageResource(R.drawable.star_gold);
        } else {
            binding.favorite.setImageResource(R.drawable.star_empty);
        }

        binding.favorite.setOnClickListener(v -> {
            if (coin.isFavorite()){

                PreferencesHelper.getInstance().removeCoinFromFavorite(coin);
            }else {

                PreferencesHelper.getInstance().addCoinToFavorite(coin);
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

    @Override
    public final void displayPopUp() {
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }

    @Override
    public void onClose() {}
}
