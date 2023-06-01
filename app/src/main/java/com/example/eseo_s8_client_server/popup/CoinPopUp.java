package com.example.eseo_s8_client_server.popup;

import android.view.View;

import com.example.eseo_s8_client_server.R;
import com.example.eseo_s8_client_server.models.Coin;
import com.example.eseo_s8_client_server.views.CoinRecyclerAdapter;

public class CoinPopUp extends PopUpFragment {
    private Coin coin;
    private CoinRecyclerAdapter.ChangeClick callBack;

    public CoinPopUp(View view, CoinRecyclerAdapter.ChangeClick callBack, Coin coin) {
        super(view, R.layout.popup_coin, true);
        this.coin = coin;
        this.callBack = callBack;
    }

    @Override
    public void initPopUp() {
        // TODO
    }

    @Override
    public void onClose() {
        callBack.changeClickAllowed();
    }
}
