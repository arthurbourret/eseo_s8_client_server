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
import com.example.eseo_s8_client_server.views.CoinView;

public class CoinPopUp extends PopUpFragment {
    private Coin coin;
    private CoinRecyclerAdapter.ChangeClick callBack;
    private ImageView imageView;

    public CoinPopUp(View view, Coin coin, Drawable icon, CoinRecyclerAdapter.ChangeClick callBack) {
        super(view, R.layout.popup_coin, true);
        this.coin = coin;
        this.callBack = callBack;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void initPopUp() {
        // TODO

        imageView = view.findViewById(R.id.icon_coin);
        imageView = view.findViewById(R.id.favorite);
        if (coin.isFavorite()) {
            imageView.setImageResource(R.drawable.star_gold);
        }else {
            imageView.setImageResource(R.drawable.star_empty);
        }
        ((TextView) view.findViewById(R.id.name_coin)).setText(coin.getName());
        ((TextView) view.findViewById(R.id.symbol_coin)).setText(coin.getSymbol());
        ((TextView) view.findViewById(R.id.price_coin)).setText(coin.getPrice()+"");
        ((TextView) view.findViewById(R.id.change_coin)).setText(coin.getChange()+"");
        ((TextView) view.findViewById(R.id.market_cap)).setText(Math.toIntExact(coin.getMarketCap()));
        ((TextView) view.findViewById(R.id.volume_24h)).setText(Math.toIntExact(coin.getVolume24H()));

        try {
            int color = Color.parseColor(coin.getColor());
            view.findViewById(R.id.couleur_coin).setBackgroundColor(color);
        } catch (Exception ignored) {}

    }



    @Override
    public void onClose() {
        callBack.changeClickAllowed();
    }
}
