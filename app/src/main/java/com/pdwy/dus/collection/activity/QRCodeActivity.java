package com.pdwy.dus.collection.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.zxing.Result;
import com.pdwy.dus.collection.R;
import com.pdwy.dus.collection.core.BaseActivity;
import com.pdwy.dus.collection.utils.MLog;
import com.pdwy.dus.collection.utils.ToastUtil;

import butterknife.BindView;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * 扫描二维码
 * Author： by KLW on 2018/7/31.
 */

public class QRCodeActivity extends BaseActivity implements ZXingScannerView.ResultHandler {

    @BindView(R.id.ll_head_return)
    LinearLayout ll_head_return;
    @BindView(R.id.ll_head_dus)
    LinearLayout ll_head_dus;
    @BindView(R.id.ll_head_personalcenter)
    LinearLayout ll_head_personalcenter;
    private ZXingScannerView mScannerView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_qr_code;

    }
    @Override
    protected void onCreateAfter() {
        mScannerView =  findViewById(R.id.scanner_view);
        ll_head_return.setVisibility(View.VISIBLE);
        ll_head_dus.setVisibility(View.GONE);
        ll_head_personalcenter.setVisibility(View.GONE);
    }


    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }
    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here
        // If you would like to resume scanning, call this method below:
        mScannerView.resumeCameraPreview(this);

        if (rawResult != null && rawResult.getText() != null) {
            String text = rawResult.getText();
            MLog.i("二维码扫描结果：" + text);
            Intent intent = new Intent();
            intent.putExtra("dataReturn", text);
            setResult(RESULT_OK, intent);
            finish();
        } else {
            ToastUtil.showMessage(this, "未扫描到二维码");
        }
    }

}
