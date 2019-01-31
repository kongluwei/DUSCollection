package com.pdwy.dus.collection.utils.helper;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

/**
 * 在ui线程中执行
 * Author： by MR on 2017/3/15.
 */
public class ThreadHelper {
    private static Handler mHandler;

    /**
     * 在ui线程中执行
     *
     * @param runnable the runnable
     */
    public static void runOnUiThread(@NonNull Runnable runnable) {
        //如果当前线程是主线程,直接执行
        if (isOnMainThread()) {
            runnable.run();
            return;
        }

        if (mHandler == null) {
            synchronized (ThreadHelper.class) {
                if (mHandler == null) {
                    mHandler = new Handler(Looper.getMainLooper());
                }
            }
        }

        mHandler.post(runnable);
    }

    public static void runOnUiThread(@NonNull Runnable runnable, long delay) {
        if (delay <= 0) {
            runOnUiThread(runnable);
            return;
        }

        if (mHandler == null) {
            synchronized (ThreadHelper.class) {
                if (mHandler == null) {
                    mHandler = new Handler(Looper.getMainLooper());
                }
            }
        }

        mHandler.postDelayed(runnable, delay);
    }
    /**
     * 计算runnable运行时间
     *
     * @param runnable the runnable
     * @return the long
     */
    public static long calculateTime(@NonNull Runnable runnable) {
        long start = System.currentTimeMillis();
        runnable.run();

        return System.currentTimeMillis() - start;
    }

    /**
     * 是否在后台线程执行
     *
     * @return true 当前线程是后台线程
     */
    public static boolean isOnBackgroundThread() {
        return !isOnMainThread();
    }

    /**
     * 判断当前线程是否是主线程
     *
     * @return true 当前线程是主线程
     */
    public static boolean isOnMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

}