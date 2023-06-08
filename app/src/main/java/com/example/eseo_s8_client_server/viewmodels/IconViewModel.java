package com.example.eseo_s8_client_server.viewmodels;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.caverock.androidsvg.SVG;
import com.example.eseo_s8_client_server.CoinApplication;
import com.example.eseo_s8_client_server.network.NetworkCallBack;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
// TODO: je pense qu'on est sur de l'over engineering. Regarder la librairie Picasso pour loader les images
public class IconViewModel extends ViewModel implements IViewModel<Map<String, Drawable>> {
    private final Map<String, Drawable> icons;
    private final MutableLiveData<Map<String, Drawable>> data = new MutableLiveData<>();

    private final OkHttpClient client = new OkHttpClient();
    private final Resources resources;

    public IconViewModel() {
        this.resources = CoinApplication.getContext().getResources();
        this.icons = new HashMap<>();
    }

    public boolean hasIcon(String uuid) {
        return icons.containsKey(uuid) && icons.get(uuid) != null;
    }

    public Drawable getIcon(String uuid) {
        return icons.get(uuid);
    }

    @Override
    public LiveData<Map<String, Drawable>> getData() {
        return data;
    }

    @Override
    public void fetchData(Object... parameters) {
        if (parameters == null || parameters.length < 1
                || (!(parameters[0] instanceof String))
                || (!(parameters[1] instanceof String)))
            return;
        String uuid = (String) parameters[0];
        String url = (String) parameters[1];

        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new NetworkCallBack.OkHttpCallback() {
            @Override
            public void onSuccess(ResponseBody response) {
                Drawable icon = getIconFromStream(response.byteStream(), url);
                icons.put(uuid, icon);
                data.postValue(icons);
            }
        });
    }

    private Drawable getIconFromStream(InputStream stream, String url) {
        Drawable icon = null;
        BufferedInputStream bufferedInputStream = new BufferedInputStream(stream);

        if (url.contains(".svg")) {
            try {
                SVG svg = SVG.getFromInputStream(bufferedInputStream);
                icon = new PictureDrawable(svg.renderToPicture());
            } catch (Exception ignored) {
            }
        } else {
            Bitmap iconBmp = BitmapFactory.decodeStream(bufferedInputStream);
            icon = new BitmapDrawable(resources, iconBmp);
        }

        return icon;
    }
}
