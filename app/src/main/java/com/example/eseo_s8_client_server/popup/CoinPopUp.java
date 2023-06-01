package com.example.eseo_s8_client_server.popup;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eseo_s8_client_server.R;
import com.example.eseo_s8_client_server.models.Coin;
import com.example.eseo_s8_client_server.views.CoinRecyclerAdapter;

public class CoinPopUp extends PopUpFragment {
    private final Coin coin;
    private final CoinRecyclerAdapter.ChangeClick callBack;

    public CoinPopUp(View view, Coin coin, CoinRecyclerAdapter.ChangeClick callBack) {
        super(view, R.layout.popup_coin, true);
        this.coin = coin;
        this.callBack = callBack;
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
        ((TextView) view.findViewById(R.id.market_cap)).setText(Math.toIntExact(coin.getMarketCap()));
        ((TextView) view.findViewById(R.id.volume_24h)).setText(Math.toIntExact(coin.getVolume24H()));

        // set color
        try {
            int color = Color.parseColor(coin.getColor());
            view.findViewById(R.id.couleur_coin).setBackgroundColor(color);
        } catch (Exception ignored) {}
    }

    public void setIcon(Drawable icon) {
        // TODO set coin icon
    }


    @Override
    public void onClose() {
        callBack.changeClickAllowed();
    }
}
