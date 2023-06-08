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
        ImageView imageView = view.findViewById(R.id.favorite);
        if (coin.isFavorite()) {
            imageView.setImageResource(R.drawable.star_gold);
        } else {
            imageView.setImageResource(R.drawable.star_empty);
        }

        // set texts
        ((TextView) view.findViewById(R.id.name_coin)).setText(coin.getName());
        ((TextView) view.findViewById(R.id.symbol_coin)).setText(coin.getSymbol());
        ((TextView) view.findViewById(R.id.price_coin)).setText(coin.getPrice() + "");
        ((TextView) view.findViewById(R.id.change_coin)).setText(coin.getChange() + "");
        ((TextView) view.findViewById(R.id.market_cap)).setText(coin.getMarketCap());
        ((TextView) view.findViewById(R.id.volume_24h)).setText(coin.getVolume24H());

        // TODO add description

        // set color
        try {
            int color = Color.parseColor(coin.getColor());
            view.findViewById(R.id.couleur_coin).setBackgroundColor(color);
        } catch (Exception ignored) {
        }
    }

    public void setIcon(Drawable icon) {
        ((ImageView) view.findViewById(R.id.icon_coin)).setImageDrawable(icon);
    }
}
