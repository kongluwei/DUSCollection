package com.pdwy.dus.collection.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

/**
 * dialog工具类
 * Author： by MR on 2017/3/15.
 */
public class DialogUtil {
    private OnOkClickListener mOnOkClickListener;
    private OnNoClickListener mOnNoClickListener;
    private OnCancelClickListener mOnCancelClickListener;

    private AlertDialog.Builder builder;

    public DialogUtil(Context context) {
        if (builder == null){
            builder = new AlertDialog.Builder(context);
        }
    }

    /**
     * 普通dialog
     * @param title    标题
     * @param describe 描述
     * @param okStr    ok按钮的文字
     * @param noStr    no按钮的文字
     */
    public void selectDialog(String title, String describe, String okStr, String noStr){
        if (builder == null)
            return;

        title = TextUtils.isEmpty(title.trim())?"":title;
        describe = TextUtils.isEmpty(describe.trim())?"":describe;
        okStr = TextUtils.isEmpty(okStr) ? "ok" : okStr;
        noStr = TextUtils.isEmpty(noStr) ? "no" : noStr;

        builder.setTitle(title);
        builder.setMessage(describe);

        builder.setPositiveButton(okStr, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (mOnOkClickListener!=null)
                    mOnOkClickListener.onOkClick();

                dialog.dismiss();
            }
        });

        builder.setNegativeButton(noStr, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (mOnNoClickListener!=null)
                    mOnNoClickListener.onNoClick();

                dialog.dismiss();
            }
        });

        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (mOnCancelClickListener!=null)
                    mOnCancelClickListener.onCancelClick();
            }
        });
        builder.setCancelable(false);
        builder.show();
    }


    /**
     * 点击OK键时的监听回调
     */
    public interface  OnOkClickListener{
        void onOkClick();
    }

    public void setOnOkClickListener(OnOkClickListener listener) {
        mOnOkClickListener = listener;
    }

    /**
     * 点击OK键时的监听回调
     */
    public interface  OnNoClickListener{
        void onNoClick();
    }

    public void setOnNoClickListener(OnNoClickListener listener) {
        mOnNoClickListener = listener;
    }

    /**
     * dialog取消时的监听回调
     */
    public interface  OnCancelClickListener{
        void onCancelClick();
    }

    public void setOnCancelClickListener(OnCancelClickListener listener) {
        mOnCancelClickListener = listener;
    }

}
