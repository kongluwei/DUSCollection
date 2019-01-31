package com.pdwy.dus.collection.utils.helper;

import android.support.v7.appcompat.BuildConfig;

/**
 * 调试相关 (方便调试模式找出问题)
 *
 *
 * Author： by MR on 2017/3/15.
 */

public class DebugHelper {
    /**
     * 调试模式下抛出一个异常
     *
     * @param exception the exception
     */
    public static void throwException(RuntimeException exception){
        if (BuildConfig.DEBUG)
            throw exception;
        else
            android.util.Log.e("exception","",exception);
            return;
    }

    public static void throwIllegalState(String message){
        throwException(new IllegalStateException(message));
    }

    public static void throwIllegalArgument(String message){
        throwException(new IllegalArgumentException(message));
    }

    public static void throwNullPointer(String message){
        throwException(new IllegalArgumentException(message));
    }

}
