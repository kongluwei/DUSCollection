package com.pdwy.dus.collection;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Process;


import com.pdwy.dus.collection.utils.MLog;

import org.greenrobot.eventbus.EventBus;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;



/**
 * Author： by MR on 2017/3/15.
 */

public class App extends Application {

    /**
     * 全局一个context(就一个,虽然是静态,但是不会导致内存泄露,因为如果这个context生命周期结束,说明应用也停止了)
     */
    public static Context appContext;

    public static App instance;
    private List<Activity> oList;//用于存放所有启动的Activity的集合
    @Override
    public void onCreate() {
        super.onCreate();
        //过滤非主进程的东西
        if (!mainApplication()) {
            return;
        }
        appContext = getApplicationContext();

        //==========初始化相关==========
//        initAppManager();
        initLogController();
        instance = this;
        oList = new ArrayList<Activity>();
        //xutils的初始化
        x.Ext.init(this);
    }

    /**
     * Init log controller.初始化日志控制器
     */
    private void initLogController() {
        //如果是调试模式,则输出日志,否则不输出(防止信息泄露)
        if (BuildConfig.DEBUG) {
            MLog.plant(new MLog.DebugTree());
        } else {
            MLog.plant(new MLog.ReleaseTree());
        }
    }

    /**
     * 初始化 App管理器(监听生命周期和软件的前后台切换)
     */
    private void initAppManager() {
        AppManager.getInstance().init(this);
        //前后台切换监听,切换的时候会发Eventbus事件,需要用到这个的地方直接接受Eventbus即可
        AppManager.getInstance().setOnAppForegroundChange(new AppManager.OnAppForegroundChange() {
            @Override
            public void onChange(boolean isForeground) {
                EventBus.getDefault().post(new ToForegroundEvent(isForeground));
            }
        });
    }

    /**
     * 判断主进程的Application
     *
     * @return the boolean
     */
    private boolean mainApplication() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 判断App是否在前台
     *
     * @return true 前台
     */
    public static boolean isAppOnForeground() {
        ActivityManager activityManager = (ActivityManager) appContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasksInfo = activityManager.getRunningTasks(1);
        if (tasksInfo.size() > 0) {
            // 应用程序位于堆栈的顶层
            if (App.appContext.getPackageName().equals(tasksInfo.get(0).topActivity.getPackageName())) {
                return true;
            }
        }
        return false;
    }


    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static App getInstance() {
        return (App) appContext;
    }
    /**
     * 添加Activity
     */
    public void addActivity_(Activity activity) {
// 判断当前集合中不存在该Activity
        if (!oList.contains(activity)) {
            oList.add(activity);//把当前Activity添加到集合中
        }
    }

    /**
     * 销毁单个Activity
     */
    public void removeActivity_(Activity activity) {
//判断当前集合中存在该Activity
        if (oList.contains(activity)) {
            oList.remove(activity);//从集合中移除
            activity.finish();//销毁当前Activity
        }
    }

    /**
     * 销毁所有的Activity
     */
    public void removeALLActivity_() {
        //通过循环，把集合中的所有Activity销毁
        for (Activity activity : oList) {
            activity.finish();
        }
    }
}



