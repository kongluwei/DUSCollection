package com.pdwy.dus.collection.core;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.pdwy.dus.collection.MainActivity;
import com.pdwy.dus.collection.R;
import com.pdwy.dus.collection.activity.PersonalCenterActivity;
import com.pdwy.dus.collection.utils.MLog;
import com.pdwy.dus.collection.utils.ProgressDialogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author： by MR on 2018/8/6.
 */

public abstract class BaseFragment extends Fragment implements View.OnClickListener{
    public View mContentView;
      LinearLayout  ll_shouye;
           LinearLayout  ll_geren;
    private Dialog waitDialog;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = inflater.inflate(setLayoutResourceID(),container,false);
                    ll_shouye=     mContentView.findViewById(R.id.ll_shouye);
                    ll_shouye.setOnClickListener(this);
                                                 ll_geren=     mContentView.findViewById(R.id.ll_geren);
                                                 ll_geren.setOnClickListener(this);
        ButterKnife.bind(getActivity());
        init();
        setUpView();
        setUpData();
        return mContentView;
    }

    /**
     * 此方法用于返回Fragment设置ContentView的布局文件资源ID
     *
     * @return 布局文件资源ID
     */
    protected abstract int setLayoutResourceID();

    /**
     * 一些View的相关操作
     */
    protected abstract void setUpView();

    /**
     * 一些Data的相关操作
     */
    protected abstract void setUpData();

    /**
     * 此方法用于初始化成员变量及获取Intent传递过来的数据
     * 注意：这个方法中不能调用所有的View，因为View还没有被初始化，要使用View在initView方法中调用
     */
    protected void init() {}

    public View getContentView() {
        return mContentView;
    }

//
//    作者：黄海佳
//    链接：https://www.jianshu.com/p/2f0fb7162a56
//    來源：简书
//    简书著作权归作者所有，任何形式的转载都请联系作者获得授权并注明出处。

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_shouye:
                waitDialog = ProgressDialogUtils.showDialogNew(getActivity(), "读取中", true);
                waitDialog.show();
                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
                break;

            case R.id.ll_geren:
                startActivity(new Intent(getActivity(), PersonalCenterActivity.class));
                break;

        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(waitDialog!=null)
            waitDialog.dismiss();
    }
}
