package com.example.eseo_s8_client_server.viewmodels;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;

import androidx.annotation.NonNull;

import com.caverock.androidsvg.SVG;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class IconLoader {
    @SuppressLint("StaticFieldLeak")
    private static final IconLoader SINGLETON = new IconLoader();
    private static final OkHttpClient client = new OkHttpClient();
    private Context context;

    private IconLoader() {}

    public static IconLoader getInstance() {
        return SINGLETON;
    }

    public void setContext(Context context) {
        SINGLETON.context = context;
    }

    public void loadIcon(String url, CallBackIcon callback) {
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                if (!response.isSuccessful() || response.body() == null) return;

                try {
                    Drawable icon;
                    InputStream stream = response.body().byteStream();
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(stream);

                    if (url.contains(".svg")) {
                        SVG svg = SVG.getFromInputStream(bufferedInputStream);
                        icon = new PictureDrawable(svg.renderToPicture());
                    } else {
                        Bitmap iconBmp = BitmapFactory.decodeStream(bufferedInputStream);
                        icon = new BitmapDrawable(context.getResources(), iconBmp);
                    }

                    callback.call(icon);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    interface CallBackIcon {
        void call(Drawable icon);
    }
}
