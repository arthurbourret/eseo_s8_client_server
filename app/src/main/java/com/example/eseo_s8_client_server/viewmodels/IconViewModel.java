package com.example.eseo_s8_client_server.viewmodels;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;

import androidx.annotation.NonNull;

import com.caverock.androidsvg.SVG;
import com.example.eseo_s8_client_server.CoinApplication;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class IconViewModel {
    // TODO adapt to IViewModel

    private final Resources resources;
    @SuppressLint("StaticFieldLeak")
    private static final IconViewModel SINGLETON = new IconViewModel();
    private final OkHttpClient client = new OkHttpClient();

    private IconViewModel() {
        resources = CoinApplication.getContext().getResources();
    }

    public static IconViewModel getInstance() {
        return SINGLETON;
    }

    public void loadIcon(String url, CallBackIcon callback) {
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                // TODO error fetch
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                if (!response.isSuccessful() || response.body() == null) return;
                // TODO error fetch

                try {
                    Drawable icon;
                    InputStream stream = response.body().byteStream();
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(stream);

                    if (url.contains(".svg")) {
                        SVG svg = SVG.getFromInputStream(bufferedInputStream);
                        icon = new PictureDrawable(svg.renderToPicture());
                    } else {
                        Bitmap iconBmp = BitmapFactory.decodeStream(bufferedInputStream);
                        icon = new BitmapDrawable(resources, iconBmp);
                    }

                    callback.call(icon);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public interface CallBackIcon {
        void call(Drawable icon);
    }
}
