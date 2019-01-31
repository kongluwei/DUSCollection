package com.pdwy.dus.collection.utils;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;

import java.util.List;

/**
 * 输入法工具类
 * Author： by MR on 2017/3/15.
 */
public class InputMethodUtils {
	private static InputMethodManager imm;

	// 显示输入法
	public static void show(Context context, View focusView) {
		imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(focusView, InputMethodManager.SHOW_FORCED);
	}

	// 隐藏输入法
	public static void hide(Context context) {
		View view = ((Activity) context).getWindow().peekDecorView();
		if (view != null && view.getWindowToken() != null) {
			imm = (InputMethodManager) context
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
	}

	// 调用该方法；键盘若显示则隐藏; 隐藏则显示
	public static void toggle(Context context) {
		imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
	}

	// 判断InputMethod的当前状态
	public static boolean isShow(Context context, View focusView) {
		Object obj = context.getSystemService(Context.INPUT_METHOD_SERVICE);
		System.out.println(obj);
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		boolean bool = imm.isActive(focusView);
		List<InputMethodInfo> mInputMethodProperties = imm
				.getEnabledInputMethodList();

		final int N = mInputMethodProperties.size();
		for (int i = 0; i < N; i++) {
			InputMethodInfo imi = mInputMethodProperties.get(i);
			if (imi.getId().equals(
					Settings.Secure.getString(context.getContentResolver(),
							Settings.Secure.DEFAULT_INPUT_METHOD))) {
				break;
			}
		}
		return bool;
	}

	public static boolean isSoftShowing(Activity activity) {
		//获取当前屏幕内容的高度
		int screenHeight = activity.getWindow().getDecorView().getHeight();
		//获取View可见区域的bottom
		Rect rect = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);

		return screenHeight - rect.bottom - getSoftButtonsBarHeight(activity) != 0;
	}

	/**
	 * 底部虚拟按键栏的高度
	 * @return
	 */
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
	private static int getSoftButtonsBarHeight(Activity activity) {
		DisplayMetrics metrics = new DisplayMetrics();
		//这个方法获取可能不是真实屏幕的高度
		activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		int usableHeight = metrics.heightPixels;
		//获取当前屏幕的真实高度
		activity.getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
		int realHeight = metrics.heightPixels;
		if (realHeight > usableHeight) {
			return realHeight - usableHeight;
		} else {
			return 0;
		}
	}
}