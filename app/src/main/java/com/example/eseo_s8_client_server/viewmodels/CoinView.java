package com.example.eseo_s8_client_server.viewmodels;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eseo_s8_client_server.R;
import com.example.eseo_s8_client_server.models.Coin;

public class CoinView extends RecyclerView.ViewHolder {


    public CoinView(@NonNull View itemView) {
        super(itemView);
    }

    public void initCoin(int position, Coin coin)
    {
        ((TextView) itemView.findViewById(R.id.name_coin)).setText(coin.getName());
        ((TextView) itemView.findViewById(R.id.symbol_coin)).setText(coin.getSymbol());
        ((TextView) itemView.findViewById(R.id.price_coin)).setText(coin.getPrice()+"");
        ((TextView) itemView.findViewById(R.id.change_coin)).setText(coin.getChange()+"");
        itemView.findViewById(R.id.couleur_coin).setBackgroundColor(Color.parseColor(coin.getColor()));
    }

}
