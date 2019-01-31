package com.pdwy.dus.collection.utils;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

/**
 * Author： by MR on 2018/8/14.
 */

public class PopupWindowUtils {
    private PopupWindow window;
    public  PopupWindowUtils(View v,int x,int y){


        // 创建PopupWindow对象，其中：
        // 第一个参数是用于PopupWindow中的View，第二个参数是PopupWindow的宽度，
        // 第三个参数是PopupWindow的高度，第四个参数指定PopupWindow能否获得焦点
        window=new PopupWindow(v, x, y, true);
        // 设置PopupWindow的背景
        window.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        // 设置PopupWindow是否能响应外部点击事件
        window.setOutsideTouchable(true);
        // 设置PopupWindow是否能响应点击事件
        window.setTouchable(true);

    }


    public void dismiss() {
        window.dismiss();
    }

    public void showAsDropDown(View v, int x, int y) {
        window.showAsDropDown(v, x, y);
    }
    public void showAsDropDown(View v, int p, int x, int y) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.showAsDropDown(v,x,y,p);
        }else
            window.showAsDropDown(v, x, y);
    }

    // 显示PopupWindow，其中：
//                // 第一个参数是PopupWindow的锚点，第二和第三个参数分别是PopupWindow相对锚点的x、y偏移
//                window.showAsDropDown(ll_pop, 0, 10);
//                // 或者也可以调用此方法显示PopupWindow，其中：
//                // 第一个参数是PopupWindow的父View，第二个参数是PopupWindow相对父View的位置，
//                // 第三和第四个参数分别是PopupWindow相对父View的x、y偏移
//                // window.showAtLocation(parent, gravity, x, y);
}
