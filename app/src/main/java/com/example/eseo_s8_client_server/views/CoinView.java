package com.example.eseo_s8_client_server.views;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eseo_s8_client_server.R;
import com.example.eseo_s8_client_server.databinding.LayoutListCoinBinding;
import com.example.eseo_s8_client_server.models.Coin;

public class CoinView extends RecyclerView.ViewHolder {
    private LayoutListCoinBinding binding;
    private ImageView imageView;

    public CoinView(@NonNull View itemView) {
        super(itemView);
    }

    @SuppressLint("SetTextI18n")
    public void initCoin(Coin coin) {
        // TODO: view binding
        imageView = itemView.findViewById(R.id.icon_coin);
        ((TextView) itemView.findViewById(R.id.name_coin)).setText(coin.getName());
        ((TextView) itemView.findViewById(R.id.symbol_coin)).setText(coin.getSymbol());
        ((TextView) itemView.findViewById(R.id.price_coin)).setText(coin.getPrice() + "");
        ((TextView) itemView.findViewById(R.id.change_coin)).setText(coin.getChange() + "");

        if (coin.isFavorite()) setFavorite();
        else unsetFavorite();

        try {
            int color = Color.parseColor(coin.getColor());
            itemView.findViewById(R.id.couleur_coin).setBackgroundColor(color);
        } catch (Exception ignored) {
        }
    }

    private void setFavorite() {
        itemView.findViewById(R.id.favorite).setBackgroundResource(R.drawable.star_gold);
    }

    private void unsetFavorite() {
        itemView.findViewById(R.id.favorite).setBackgroundResource(0);
    }

    public void setImageIcon(Drawable icon) {
        imageView.setImageDrawable(icon);
    }

    public void setOnClickListener(View.OnClickListener listener) {
        itemView.setOnClickListener(listener);
    }
}
