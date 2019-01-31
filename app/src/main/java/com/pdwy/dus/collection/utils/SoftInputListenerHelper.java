package com.pdwy.dus.collection.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;


public class SoftInputListenerHelper {

    Context context;

    public static void setListener(Activity activity, SoftInputListener softInputListener) {
        //每个对象单独持有，防止内存泄露
        new SoftInputListenerHelper(activity, softInputListener);
    }

    private View mChildOfContent;
    private int usableHeightPrevious;


    private SoftInputListenerHelper(Activity activity, final SoftInputListener softInputListener) {
        context = activity;
        final FrameLayout content = (FrameLayout) activity.findViewById(android.R.id.content);
        mChildOfContent = content.getChildAt(0);
        mChildOfContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                int usableHeightNow = computeUsableHeight() + microAdjust;
                if (usableHeightNow != usableHeightPrevious) {
                    int usableHeightSansKeyboard = content.getHeight();
                    int heightDifference = usableHeightSansKeyboard - usableHeightNow;
                    if (heightDifference == 0 && usableHeightPrevious > usableHeightNow) {
                        heightDifference = usableHeightPrevious - usableHeightNow;
                    }
                    if (heightDifference > (usableHeightSansKeyboard / 4)) {
                        // keyboard probably just became visible
                        if (softInputListener != null) {
                            if (mChildOfContent.getHeight() < DeviceUtil.getScreenHeight(context) - heightDifference / 2) {
                                softInputListener.onSoftInputVisible(0);
                            } else {
                                softInputListener.onSoftInputVisible(heightDifference);
                            }
                        }
                    } else {
                        // keyboard probably just became hidden
//                        MLog.e("内容高度2：" + mChildOfContent.getHeight() + " 屏幕高度:" + DeviceUtil.getScrrenHeight(context) + " 状态栏高度:" + DeviceUtil.getStatusBarHeight(context));
                        if (usableHeightNow != usableHeightSansKeyboard) {
                            microAdjust = DeviceUtil.getStatusBarHeight(context);
                        }
                        if (softInputListener != null) {
                            softInputListener.onSoftInputHidden();
                        }
                    }
                    usableHeightPrevious = usableHeightNow;
                }
            }
        });
    }

    int microAdjust = 0; //有些手机 会有状态栏高度的差距

    private int computeUsableHeight() {
        Rect r = new Rect();
        mChildOfContent.getWindowVisibleDisplayFrame(r);
        int result = r.bottom - r.top;
        return result;
    }

    public interface SoftInputListener {
        void onSoftInputVisible(int softInputHeight);

        void onSoftInputHidden();
    }
}