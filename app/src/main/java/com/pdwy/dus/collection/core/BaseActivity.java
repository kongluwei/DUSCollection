package com.pdwy.dus.collection.core;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;


import com.pdwy.dus.collection.App;
import com.pdwy.dus.collection.MainActivity;
import com.pdwy.dus.collection.R;
import com.pdwy.dus.collection.activity.PersonalCenterActivity;
import com.pdwy.dus.collection.utils.InputMethodUtils;
import com.pdwy.dus.collection.utils.ProgressDialogUtils;
import com.pdwy.dus.collection.utils.ToastUtil;
import com.pdwy.dus.collection.utils.helper.DebugHelper;


import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;


/**
 * BaseActivity 封装
 * Author： by MR on 2017/3/15.
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected BaseActivity mContext;
    private boolean onDestroy;
    private App application;
    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onDestroy = false;
        mContext = this;
        if (application == null) {
            // 得到Application对象
            application = (App) getApplication();
        }

        addActivity();// 调用添加方法
        //设置为横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
//            SystemBarTintManager tintManager = new SystemBarTintManager(this);
//            tintManager.setStatusBarTintEnabled(true);
//            tintManager.setStatusBarTintResource(R.color.theme_color);// 通知栏所需颜色
        }
        onCreateBefore(savedInstanceState);

        if (getLayoutId() != 0) {// 设置布局,如果子类有返回布局的话
            setContentView(getLayoutId());
            ButterKnife.bind(this);
        } else {
            //没有提供ViewId
            DebugHelper.throwIllegalState("没有提供正确的LayoutId");
            return;
        }
        initBase(savedInstanceState);
        //留给子类重写
        onCreateAfter();
    }

    /**
     * setContentView之前调用
     *
     * @param savedInstanceState
     */
    protected void onCreateBefore(Bundle savedInstanceState) {
    }

    @LayoutRes
    protected abstract int getLayoutId();

    /**
     * setContentView之后调用
     *
     *
     */
    protected abstract void onCreateAfter();

    @Override
    protected void onResume() {
        super.onResume();
        //页面统计
//        Tracker.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //页面统计
//        Tracker.onPause(this);
        dismissLoadingDialog();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        onDestroy = true;
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        inputMethodHide(this);
    }

    /**
     * 关闭Activity
     */
    protected void finishActivity() {
        finish();
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    /**
     * 重写返回键
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishActivity();
        Log.i(getClass().getCanonicalName(), "onBackPressed");
    }

    /**
     * 隐藏输入法
     */
    public void inputMethodHide(Context context) {
        InputMethodUtils.hide(context);
    }

    public void showSuccessTip(int id) {
        ToastUtil.showMySuccessMessage(getApplicationContext(), id);
    }

    public void showFailTip(int id) {

        ToastUtil.showMyFailMessage(getApplicationContext(), id);
    }

    public void showSuccessTip(String str) {
        ToastUtil.showMySuccessMessage(getApplicationContext(), str);
    }

    public void showFailTip(String str) {
        ToastUtil.showMyFailMessage(getApplicationContext(), str);
    }

    protected void initBase(Bundle savedInstanceState) {
        //初始化EventBus
        if (userEventBus()) {
            EventBus.getDefault().register(this);
        }

        //初始化其他,后续按需求来
    }

    //如果要使用Eventbus,则重写返回true
    protected boolean userEventBus() {
        return false;
    }


    //兼容低版本的 判断Activity是否已经被关闭了
    public boolean isDestroyed() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                return super.isDestroyed();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return onDestroy;
    }

    //界面动画
    public void clearOverridePendingTransition() {
        overridePendingTransition(-1, -1);
    }

    public void overridePendingTransitionEnter() {
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    public void overridePendingTransitionBack() {
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    public void overridePendingAlphaEnter() {
        overridePendingTransition(R.anim.fade_in, -1);
    }

    public void overridePendingAlphaBack() {
        overridePendingTransition(-1, R.anim.fade_out);
    }

    public void overridePendingTransitionBottomEnter() {
        overridePendingTransition(R.anim.push_bottom_in, R.anim.push_static);
    }

    public void overridePendingTransitionBottomBack() {
        overridePendingTransition(R.anim.push_static, R.anim.push_bottom_out);
    }


    /**
     * 加载动画ImageView
     */
    private Dialog waitDialog;

    /**
     * 显示Loading动画
     *
     * @param msg
     */
    public void showLoadingDialog(String msg) {
        showLoadingDialog(msg, false);
    }

    public void showLoadingDialog(String msg, boolean b) {
        if (isFinishing() || isDestroyed())
            return;
        if (waitDialog == null) {
            if (TextUtils.isEmpty(msg))
                msg = "请稍等";
            waitDialog = ProgressDialogUtils.showDialogNew(this, msg, true);
            waitDialog.setCancelable(!b);
            waitDialog.show();
        } else {
            if (waitDialog.isShowing()) {
                waitDialog.dismiss();
            }
            TextView testView = (TextView) waitDialog.findViewById(R.id.tipTextView);
            testView.setText(msg);

            waitDialog.setCancelable(!b);
            waitDialog.show();
        }
    }

    /**
     * 取消Loading动画
     */
    public void dismissLoadingDialog() {
        if (waitDialog != null && !isDestroyed() && waitDialog.isShowing()) {
            waitDialog.dismiss();
        }
    }

    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = 0x04000000;//WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
    public void headOnClick(View v){
             switch (v.getId()){
                 //首页（左）
                 case R.id.ll_head_homepage_l:
                     showLoadingDialog("");
                     startActivity(new Intent(this, MainActivity.class));
                     removeALLActivity();
                     break;
                     //返回
                 case R.id.ll_head_return:
                     finish();
                     break;
                     //首页（右）
                 case R.id.ll_head_homepage_r:
                     showLoadingDialog("");
                     startActivity(new Intent(this, MainActivity.class));
                     removeALLActivity();
                     break;
                     //个人中心
                 case R.id.ll_head_personalcenter:

                     startActivity(new Intent(this, PersonalCenterActivity.class));
                     break;
             }
    }
    // 添加Activity方法
    public void addActivity() {
        application.addActivity_(mContext);// 调用myApplication的添加Activity方法
    }
    //销毁当前Activity方法
    public void removeActivity() {
        application.removeActivity_(mContext);// 调用myApplication的销毁单个Activity方法
    }
    //销毁所有Activity方法
    public void removeALLActivity() {
        application.removeALLActivity_();// 调用myApplication的销毁所有Activity方法
    }

}
