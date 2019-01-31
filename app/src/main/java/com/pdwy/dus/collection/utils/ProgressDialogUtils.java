package com.pdwy.dus.collection.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pdwy.dus.collection.R;


/**
 * 通用ProgressDialog
 * Author： by MR on 2017/3/15.
 */
public class ProgressDialogUtils {
	public static Dialog showDialog(Context context, String strContent) {
		return showDialogNew(context, strContent, false);
	}


	public static Dialog showDialogNew(Context context, String strContent, boolean canceledOnTouchOutside) {
		if (context != null) {
			LayoutInflater inflater = LayoutInflater.from(context);
			View v = inflater.inflate(R.layout.view_loading_dialog, null);
			RelativeLayout layout = (RelativeLayout) v.findViewById(R.id.dialog_view);
			TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);
			tipTextView.setText(strContent);
			Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);
			loadingDialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
			loadingDialog.setContentView(layout, new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.MATCH_PARENT,
					RelativeLayout.LayoutParams.MATCH_PARENT));
			loadingDialog.setCancelable(false);
			loadingDialog.show();
			return loadingDialog;
		}
		return null;
	}

	/**
	 * @param infoDialog
	 */
	public static void Close(Dialog infoDialog) {
		if (infoDialog != null) {
			infoDialog.dismiss();
		}
		infoDialog = null;
	}

}
