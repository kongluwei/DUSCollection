package com.pdwy.dus.collection.activity;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pdwy.dus.collection.R;
import com.pdwy.dus.collection.core.BaseActivity;

import butterknife.BindView;

/**
 * 功能介绍
 * Author： by MR on 2018/8/15.
 */

public class FunctionalIntroductionActivity extends BaseActivity {
    @BindView(R.id.ll_head_return)
    LinearLayout ll_head_return;
    @BindView(R.id.ll_head_dus)
    LinearLayout ll_head_dus;
    @BindView(R.id.ll_head_personalcenter)
    LinearLayout ll_head_personalcenter;
    @BindView(R.id.tv_head_title)
    TextView tv_head_title;

    @Override
    protected int getLayoutId() {
        return R.layout.acrivityfunctional_introduction;
    }

    @Override
    protected void onCreateAfter() {
        ll_head_return.setVisibility(View.VISIBLE);
        ll_head_dus.setVisibility(View.GONE);
        ll_head_personalcenter.setVisibility(View.INVISIBLE);
        tv_head_title.setVisibility(View.VISIBLE);
        tv_head_title.setText("功能介绍");
    }
}
