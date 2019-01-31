package com.pdwy.dus.collection.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;

import com.pdwy.dus.collection.App;


/**
 * 获取bitmap工具类，兼容低版本SDK
 * Author： by MR on 2017/3/15.
 */
public class BitmapUtils {
    public static Drawable getDrawable(Context context, int drawableId) {
        if (drawableId <= 0) {
            return null;
        }
        if (context == null) {
            context = App.appContext;
        }

        Drawable drawable;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drawable = context.getDrawable(drawableId);
        } else {
            drawable = context.getResources().getDrawable(drawableId);
        }
        return drawable;
    }

    public enum Format {
        JPG, PNG, GIF, UNKNOWN
    }
}
