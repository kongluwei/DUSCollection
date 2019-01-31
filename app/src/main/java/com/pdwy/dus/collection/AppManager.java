package com.pdwy.dus.collection;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;


import com.pdwy.dus.collection.utils.MLog;
import com.pdwy.dus.collection.utils.helper.BaseLifecycleCallback;

import java.util.Stack;



/**
 * 在Application.onCreate注册,全局生命周期控制,引用当前应用所有Activity
 * Author： by MR on 2017/3/15.
 */
public final class AppManager extends BaseLifecycleCallback {
    private static Stack<Activity> activityStack;
    private static AppManager appManager;

    private static boolean isActive;  //前后台切换标志,true为当前应用进入前台
    private OnAppForegroundChange mOnAppForegroundChange;

    private AppManager() {
        activityStack = new Stack<Activity>();
    }

    /**
     * 单例
     *
     * @return the instance
     */
    public static AppManager getInstance() {
        if (appManager == null) {
            synchronized (AppManager.class) {
                if (appManager == null) {
                    appManager = new AppManager();
                }
            }
        }

        return appManager;
    }

    public synchronized void init(Application application) {
        if (application == null)
            return;
        //注册全局回调
        application.registerActivityLifecycleCallbacks(this);
    }

    /**
     * 设置监听
     *
     * @param onAppForegroundChange the on app foreground change
     */
    public void setOnAppForegroundChange(OnAppForegroundChange onAppForegroundChange) {
        mOnAppForegroundChange = onAppForegroundChange;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        addActivity(activity);
    }

    @Override
    public void onActivityResumed(Activity activity) {
        super.onActivityStarted(activity);
        //当前Activity是后台切换到前台
        if (!isActive) {
            MLog.i("==========软件进入前台");
            isActive = true;
            if (mOnAppForegroundChange != null) {
                mOnAppForegroundChange.onChange(isActive);
            }
        }
    }

    @Override
    public void onActivityStopped(Activity activity) {
        if (isActive && !App.isAppOnForeground()) {
            MLog.i("==========软件进入后台");
            isActive = false;

            if (mOnAppForegroundChange != null) {
                mOnAppForegroundChange.onChange(isActive);
            }
        }
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        removeActivity(activity);
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity getCurrentActivity() {
        if (activityStack.size() == 0) {
            //CrashReport.postCatchedException(new IllegalStateException("当前Activity为空"));
            return null;
        }
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                Activity activity = activityStack.get(i);
                if (!activity.isFinishing()) {
                    activity.finish();
                }
            }
        }
        activityStack.clear();
    }

    public void removeActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
        }
    }

    public void removeAllWithoutItself(Activity activity) {
        activityStack.clear();
        addActivity(activity);
    }

    interface OnAppForegroundChange {
        /**
         * 前后台切换
         *
         * @param isForeground true:前台
         */
        void onChange(boolean isForeground);
    }
}

