package com.example.eseo_s8_client_server.popup;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

public abstract class PopUpFragment {
    protected final View view;
    protected final PopupWindow popupWindow;

    public PopUpFragment(View view, int layout, boolean closeOnClickOut) {
        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(layout, (ViewGroup) view, false);

        // create the popup window
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        this.popupWindow = new PopupWindow(popupView, width, height, closeOnClickOut);
        this.popupWindow.setOnDismissListener(this::onClose);
        this.popupWindow.setOutsideTouchable(false);
        this.view = popupView.getRootView();
    }

    public final void displayPopUp() {
        initPopUp();
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }

    public abstract void initPopUp();

    public void onClose() {}
}
