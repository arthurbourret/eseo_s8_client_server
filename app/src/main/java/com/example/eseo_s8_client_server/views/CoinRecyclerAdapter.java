package com.example.eseo_s8_client_server.views;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eseo_s8_client_server.popup.CoinPopUp;
import com.example.eseo_s8_client_server.viewmodels.CoinViewModel;
import com.example.eseo_s8_client_server.viewmodels.IViewModel;
import com.example.eseo_s8_client_server.viewmodels.IconLoader;
import com.example.eseo_s8_client_server.R;
import com.example.eseo_s8_client_server.models.Coin;
import com.example.eseo_s8_client_server.models.CoinsData;
import com.example.eseo_s8_client_server.models.Listener;
import com.example.eseo_s8_client_server.storage.PreferencesHelper;

import java.util.HashMap;
import java.util.Map;

public class CoinRecyclerAdapter extends RecyclerView.Adapter<CoinView> {
    private final IViewModel<Coin> viewModel;
    private final ViewModelStoreOwner owner;
    private boolean clickAllowed = true;

    private final Listener listener = coin -> {
        Toast.makeText(MainActivity.getContext(), "Click on " + coin.getName(),
                        Toast.LENGTH_SHORT).show();

        // favoriteCoinUuid = coin.getUuid();
        // PreferencesHelper.getInstance().setLastCoinClick(coin.getUuid());
    };

    private String favoriteCoinUuid;

    private CoinsData coins;
    private final IconLoader loader;
    private final Map<String, Drawable> icons;

    public CoinRecyclerAdapter(ViewModelStoreOwner owner) {
        this.owner = owner;
        this.viewModel = new ViewModelProvider(owner).get(CoinViewModel.class);

        this.icons = new HashMap<>();
        this.loader = IconLoader.getInstance();

        this.favoriteCoinUuid = PreferencesHelper.getInstance().getLastCoinClick();
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
        } else loader.loadIcon(coin.getIconUrl(), icon -> {
            icons.put(uuid, icon);
            holder.setImageIcon(icon);
        });

        // set on click
        holder.setOnClickListener(v -> {
            if (!clickAllowed) return;

            viewModel.generateNextValue(uuid);
            viewModel.getData().observe((LifecycleOwner) owner, res -> {
                // display pop up
                CoinPopUp popup = new CoinPopUp(holder.itemView, () -> clickAllowed = true, res);
                popup.displayPopUp();
            });
        });
    }

    @Override
    public int getItemCount() {
        return (coins == null) ? 0 : coins.size();
    }

    public interface ChangeClick {
        public void changeClickAllowed();
    }
}