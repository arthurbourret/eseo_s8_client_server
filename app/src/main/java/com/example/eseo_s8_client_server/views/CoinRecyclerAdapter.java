package com.example.eseo_s8_client_server.views;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eseo_s8_client_server.R;
import com.example.eseo_s8_client_server.models.Coin;
import com.example.eseo_s8_client_server.models.CoinsData;
import com.example.eseo_s8_client_server.popup.CoinPopUp;
import com.example.eseo_s8_client_server.viewmodels.CoinViewModel;
import com.example.eseo_s8_client_server.viewmodels.IViewModel;
import com.example.eseo_s8_client_server.viewmodels.IconViewModel;

import java.util.HashMap;
import java.util.Map;

public class CoinRecyclerAdapter extends RecyclerView.Adapter<CoinView> {
    private final IViewModel<Coin> viewModel;
    private final ViewModelStoreOwner owner;
    private boolean clickAllowed = true;
    private final ChangeClick toggleClickAllowed = () -> clickAllowed = !clickAllowed;

    private CoinsData coins;
    private final IconViewModel loader;
    private final Map<String, Drawable> icons;

    public CoinRecyclerAdapter(ViewModelStoreOwner owner) {
        this.owner = owner;
        this.viewModel = new ViewModelProvider(owner).get(CoinViewModel.class);

        this.icons = new HashMap<>();
        this.loader = IconViewModel.getInstance();
    }

    public void setCoins(CoinsData coins) {
        this.icons.clear();
        if (coins != null) this.coins = coins;
    }

    @NonNull
    @Override
    public CoinView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_list_coin, parent, false);
        return new CoinView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CoinView holder, int position) {
        Coin coin = coins.getCoin(position);
        String uuid = coin.getUuid();
        // set holder
        holder.initCoin(coin);

        // give image to holder
        holder.setImageIcon(null);
        if (icons.containsKey(uuid) && icons.get(uuid) != null) {
            holder.setImageIcon(icons.get(uuid));
            // TODO setIcon of pop up
        } else loader.loadIcon(coin.getIconUrl(), icon -> {
            icons.put(uuid, icon);
            holder.setImageIcon(icon);
            // TODO setIcon of pop up
        });

        // set on click
        holder.setOnClickListener(v -> onViewClickHandler(v, uuid));
    }

    @Override
    public int getItemCount() {
        return (coins == null) ? 0 : coins.size();
    }

    private void onViewClickHandler(View v, String uuid) {
        if (!clickAllowed) return;

        toggleClickAllowed.changeClickAllowed();
        viewModel.fetchData(uuid);
        viewModel.getData().observe((LifecycleOwner) owner, res -> {
            // display pop up
            CoinPopUp popup = new CoinPopUp(v, res, toggleClickAllowed);
            popup.displayPopUp();
        });
    }

    public interface ChangeClick {
        void changeClickAllowed();
    }
}