package com.load.status.core;


import androidx.annotation.NonNull;

import com.load.status.LoadStatusUtils;
import com.load.status.callback.Callback;
import com.load.status.target.ActivityTarget;
import com.load.status.target.ITarget;
import com.load.status.target.ViewTarget;

import java.util.ArrayList;
import java.util.List;

public class LoadStatus {
    private static volatile LoadStatus loadSir;
    private Builder builder;

    public static LoadStatus getDefault() {
        if (loadSir == null) {
            synchronized (LoadStatus.class) {
                if (loadSir == null) {
                    loadSir = new LoadStatus();
                }
            }
        }
        return loadSir;
    }

    private LoadStatus() {
        this.builder = new Builder();
    }

    private void setBuilder(@NonNull Builder builder) {
        this.builder = builder;
    }

    private LoadStatus(Builder builder) {
        this.builder = builder;
    }

    public LoadService register(@NonNull Object target) {
        return register(target, null, null);
    }

    public LoadService register(Object target, Callback.OnReloadListener onReloadListener) {
        return register(target, onReloadListener, null);
    }

    public <T> LoadService register(Object target, Callback.OnReloadListener onReloadListener, Converter<T>
            converter) {
        ITarget targetContext = LoadStatusUtils.getTargetContext(target, builder.getTargetContextList());
        LoadLayout loadLayout = targetContext.replaceView(target, onReloadListener);
        return new LoadService<>(converter, loadLayout, builder);
    }

    public static Builder beginBuilder() {
        return new Builder();
    }

    public static class Builder {
        private List<Callback> callbacks = new ArrayList<>();
        private List<ITarget> targetContextList = new ArrayList<>();
        private Class<? extends Callback> defaultCallback;

        {
            targetContextList.add(new ActivityTarget());
            targetContextList.add(new ViewTarget());
        }

        public Builder addCallback(@NonNull Callback callback) {
            callbacks.add(callback);
            return this;
        }

        /**
         * @param targetContext
         * @return Builder
         * @since 1.3.8
         */
        public Builder addTargetContext(ITarget targetContext) {
            targetContextList.add(targetContext);
            return this;
        }

        public List<ITarget> getTargetContextList() {
            return targetContextList;
        }

        public Builder setDefaultCallback(@NonNull Class<? extends Callback> defaultCallback) {
            this.defaultCallback = defaultCallback;
            return this;
        }

        List<Callback> getCallbacks() {
            return callbacks;
        }

        Class<? extends Callback> getDefaultCallback() {
            return defaultCallback;
        }

        public void commit() {
            getDefault().setBuilder(this);
        }

        public LoadStatus build() {
            return new LoadStatus(this);
        }

    }
}
