package com.pdwy.dus.collection.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * 获取和设备相关的数据
 * Author： by MR on 2017/3/15.
 */
public class DeviceUtil {
	private static final String TAG = "DeviceUtil";

	private static int SCREEN_WIDTH, SCREEN_HEIGHT;
	private static String DEVICE_IMEI, MOBILE_NUM;

	/**
	 * @param context
	 * @return
	 */
	public static int getScreenWidth(Context context) {
		if (SCREEN_WIDTH == 0) {
			initWidthHeight(context);
		}
		return SCREEN_WIDTH;
	}

	public static int getScreenHeight(Context context) {
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		return windowManager.getDefaultDisplay().getHeight();
	}

	private static void initWidthHeight(Context context) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics dm = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(dm);
		SCREEN_WIDTH = dm.widthPixels;
		SCREEN_HEIGHT = dm.heightPixels;
	}

	public static String getDeviceIMEI(Context context) {
		if (TextUtils.isEmpty(DEVICE_IMEI)) {
			initDeviceInfo(context);
		}
		return DEVICE_IMEI;
	}

	public static String getMobileNum(Context context) {
		if (TextUtils.isEmpty(MOBILE_NUM)) {
			initDeviceInfo(context);
		}

		if (TextUtils.isEmpty(MOBILE_NUM)) {
			return MOBILE_NUM;
		}

		if (MOBILE_NUM.length() > 11) {
			int len = MOBILE_NUM.length();
			int start = len - 11;
			MOBILE_NUM = MOBILE_NUM.substring(start);
		}
		return MOBILE_NUM;
	}

	private static void initDeviceInfo(Context context) {
		try {
			TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			DEVICE_IMEI = manager.getDeviceId();
			MOBILE_NUM = manager.getLine1Number();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 获取手机状态栏高度
	public static int getStatusBarHeight(Context context) {
		int resId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resId > 0) {
			return context.getResources().getDimensionPixelSize(resId);
		}
		return 0;
	}

	/**
	 * @param @param  context
	 * @param @return
	 * @return String 返回类型
	 * @throws
	 * @Title: getNetworkType
	 * @Description: 获取网络类型
	 */
	public static String getNetworkType(Context context) {
		String networkType = "unknown";

		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if (networkInfo != null) {
			networkType = networkInfo.getTypeName();
		}
		return networkType;
	}

	/**
	 * 判断手机是否联网状态
	 *
	 * @param context
	 * @return
	 */
	public static boolean deviceConnect(Context context) {
		boolean result = false;
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (manager == null) {
			return result;
		} else {
			NetworkInfo info = manager.getActiveNetworkInfo();
			if (info != null && info.isConnected()) {
				result = true;
			} else {
				result = false;
			}
		}
		return result;
	}

	/**
	 * @param @return
	 * @return String 返回类型
	 * @throws
	 * @Title: getDeviceModel
	 * @Description: 获取机器型号
	 */
	public static String getDeviceModel() {
		return android.os.Build.MODEL;
	}

	/**
	 * @param @return
	 * @return String 返回类型
	 * @throws
	 * @Title: getDeviceOSVersionCode
	 * @Description: 获取系统版本号
	 */
	public static int getDeviceOSVersionCode() {
		return android.os.Build.VERSION.SDK_INT;
	}

	/**
	 * @param @return
	 * @return String 返回类型
	 * @throws
	 * @Title: getDeviceOSVersionName
	 * @Description: 获取系统版本名
	 */
	public static String getDeviceOSVersionName() {
		return android.os.Build.VERSION.RELEASE;
	}
	/*
         * 获取应用名
         */
	public static String getVersionName(Context context){
		String versionName = null;
		try {
			//获取包管理者
			PackageManager pm = context.getPackageManager();
			//获取packageInfo
			PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
			//获取versionName
			versionName = info.versionName;
		} catch (PackageManager.NameNotFoundException e) {

			e.printStackTrace();
		}
		return versionName;
	}
	/*
	 * 获取应用版本
	 */
	public static int getVersionCode(Context context){

		int versionCode = 0;
		try {
			//获取包管理者
			PackageManager pm = context.getPackageManager();
			//获取packageInfo
			PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
			//获取versionCode
			versionCode = info.versionCode;
		} catch (PackageManager.NameNotFoundException e) {

			e.printStackTrace();
		}
		return versionCode;
	}
}
