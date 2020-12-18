package com.load.status;

import android.os.Looper;

import com.load.status.target.ITarget;

import java.util.List;

public class LoadStatusUtils {

    public static boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    public static ITarget getTargetContext(Object target, List<ITarget> targetContextList) {
        for (ITarget targetContext : targetContextList) {
            if (targetContext.equals(target)) {
                return targetContext;
            }
        }
        throw new IllegalArgumentException("No TargetContext fit it");
    }
}
