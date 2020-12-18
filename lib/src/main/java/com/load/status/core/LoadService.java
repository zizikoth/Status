package com.load.status.core;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.load.status.callback.Callback;
import com.load.status.callback.SuccessCallback;

import java.util.List;

public class LoadService<T> {
    private final LoadLayout loadLayout;
    private final Converter<T> converter;

    LoadService(Converter<T> converter, LoadLayout loadLayout, LoadStatus.Builder builder) {
        this.converter = converter;
        this.loadLayout = loadLayout;
        initCallback(builder);
    }

    private void initCallback(LoadStatus.Builder builder) {
        List<Callback> callbacks = builder.getCallbacks();
        final Class<? extends Callback> defaultCallback = builder.getDefaultCallback();
        if (callbacks != null && callbacks.size() > 0) {
            for (Callback callback : callbacks) {
                loadLayout.setupCallback(callback);
            }
        }
        if (defaultCallback != null) {
            loadLayout.showCallback(defaultCallback);
        }
    }

    public void showSuccess() {
        loadLayout.showCallback(SuccessCallback.class);
    }

    public void showCallback(Class<? extends Callback> callback) {
        loadLayout.showCallback(callback);
    }

    public void showWithConverter(T t) {
        if (converter == null) {
            throw new IllegalArgumentException("You haven't set the Converter.");
        }
        loadLayout.showCallback(converter.map(t));
    }

    public LoadLayout getLoadLayout() {
        return loadLayout;
    }

    public Class<? extends Callback> getCurrentCallback() {
        return loadLayout.getCurrentCallback();
    }

    /**
     * obtain rootView if you want keep the toolbar in Fragment
     *
     * @since 1.2.2
     * @deprecated
     */
    public LinearLayout getTitleLoadLayout(Context context, ViewGroup rootView, View titleView) {
        LinearLayout newRootView = new LinearLayout(context);
        newRootView.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        newRootView.setLayoutParams(layoutParams);
        rootView.removeView(titleView);
        newRootView.addView(titleView);
        newRootView.addView(loadLayout, layoutParams);
        return newRootView;
    }

    /**
     * modify the callback dynamically
     *
     * @param callback  which callback you want modify(layout, event)
     * @param transport a interface include modify logic
     * @since 1.2.2
     */
    public LoadService<T> setCallBack(Class<? extends Callback> callback, Transport transport) {
        loadLayout.setCallBack(callback, transport);
        return this;
    }
}
