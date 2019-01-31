package com.pdwy.dus.collection.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pdwy.dus.collection.R;


/**
 * 自定义Toast工具 可直接调用，不需要实例化
 * Author： by MR on 2017/3/15.
 */
public class ToastUtil {
	private static Handler handler = new Handler(Looper.getMainLooper());

	private static Toast toast = null;

	private static Toast toast1 = null;

	private static Object synObj = new Object();

	public static void showMessage(final Context act, final String msg) {
		showMessage(act, msg, Toast.LENGTH_SHORT);
	}

	public static void showMessage(final Context act, final int msg) {
		showMessage(act, msg, Toast.LENGTH_SHORT);
	}

	public static void showMessage(final Context act, final String msg, final int len) {
		new Thread(new Runnable() {
			public void run() {
				handler.post(new Runnable() {
					@Override
					public void run() {
						synchronized (synObj) {
							if (toast != null) {
								toast.setText(msg);
								toast.setDuration(len);
							} else {
								toast = Toast.makeText(act, msg, len);
							}
							toast.show();
						}
					}
				});
			}
		}).start();
	}

	public static void showMessage(final Context act, final int msg,
                                   final int len) {
		new Thread(new Runnable() {
			public void run() {
				handler.post(new Runnable() {
					@Override
					public void run() {
						synchronized (synObj) {
							if (toast != null) {
								toast.setText(msg);
								toast.setDuration(len);
							} else {
								toast = Toast.makeText(act, msg, len);
							}
							toast.show();
						}
					}
				});
			}
		}).start();
	}

	/**
	 * 成功
	 * 
	 * @param context
	 * @param text
	 *            文字
	 */
	public static void showMySuccessMessage(Context context, CharSequence text) {
		showMyMessage(context, text, R.mipmap.ic_launcher);
	}

	/**
	 * 失败
	 * 
	 * @param context
	 * @param text
	 *            文字
	 */
	public static void showMyFailMessage(Context context, CharSequence text) {
		showMyMessage(context, text, R.mipmap.ic_launcher);
	}

	public static void showMySuccessMessage(Context context, int msgResId) {
		showMySuccessMessage(context, context.getText(msgResId));
	}

	public static void showMyFailMessage(Context context, int msgResId) {
		showMyFailMessage(context, context.getText(msgResId));
	}

	public static void showMyMessage(Context context, int msgResId, int id) {
		showMyMessage(context, context.getString(msgResId), id);
	}

	public static void showMyMessage(Context context, CharSequence text, int id) {
		if (toast1 != null) {
			toast1.cancel();
		}
		toast1 = new Toast(context);
		LayoutInflater inflate = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflate.inflate(R.layout.login_toast, null);
		TextView content = (TextView) view.findViewById(R.id.toast_content);
		ImageView img = (ImageView) view.findViewById(R.id.toast_img);
		content.setText(text);
		if (id > 0) {
			img.setImageResource(id);
		}
		toast1.setGravity(Gravity.CENTER, 0, 0);
		toast1.setDuration(Toast.LENGTH_SHORT);
		toast1.setView(view);
		toast1.show();
	}
}
