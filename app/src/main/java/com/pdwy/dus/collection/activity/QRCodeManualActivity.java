package com.pdwy.dus.collection.activity;

import android.Manifest;
import android.animation.TimeAnimator;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pdwy.dus.collection.R;
import com.pdwy.dus.collection.core.BaseActivity;
import com.pdwy.dus.collection.model.db.InputData;
import com.pdwy.dus.collection.utils.MLog;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 二维码
 * Author： by MR on 2018/7/31.
 */

public class QRCodeManualActivity extends BaseActivity{
    @BindView(R.id.ll_head_homepage_l)
     LinearLayout ll_head_homepage_l;
    @BindView(R.id.ll_head_dus)
     LinearLayout ll_head_dus;
    @BindView(R.id.tv_head_title)
     TextView tv_head_title;
    @BindView(R.id.et_qr_code_number)
    EditText et_qr_code_number;
    @BindView(R.id.tv_qr_code_number)
    TextView tv_qr_code_number;
    @BindView(R.id.ll__qr_code_number)
    LinearLayout ll__qr_code_number;
    InputData inputData;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_qr_code_manual;
    }

    @Override
    protected void onCreateAfter() {
        inView();
    }
    void inView(){
        ll_head_homepage_l.setVisibility(View.VISIBLE);
        ll_head_dus.setVisibility(View.GONE);
        tv_head_title.setVisibility(View.VISIBLE);
        tv_head_title.setText("扫描采集");
//        et_qr_code_number.setText("20192000011A");
        inputData= new InputData(this);
    }

    @OnClick({R.id.ll__qr_code_number,R.id.tv_qr_code_number})
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.tv_qr_code_number:
                try {
                    String csbh= et_qr_code_number.getText().toString();
                    ArrayList<String> mbmcList= inputData.getTemplates(csbh);
                    if(mbmcList==null||mbmcList.size()==0) {
                        Toast.makeText(this,"没有此编号",Toast.LENGTH_LONG).show();
                        return;
                    }
//                Toast.makeText(this,"没有此编号", Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(QRCodeManualActivity.this,CollectionSettingActivity.class);
                    intent.putExtra("activityName","saoma");
                    intent.putExtra("pinzhong",inputData.getVarieties(csbh));

                    intent.putExtra("syrwbh",inputData.getExperimentalNumber(csbh));
                    intent.putExtra("csbh",csbh);

                    intent.putStringArrayListExtra("mbmcList",mbmcList);

                    startActivity(intent);
                }
            catch (Exception e){
                Toast.makeText(this,"没有此编号",Toast.LENGTH_LONG).show();
            }
                break;
            case R.id.ll__qr_code_number:
                //检查当前权限（若没有该权限，值为-1；若有该权限，值为0）
                int hasReadExternalStoragePermission1 = ActivityCompat.checkSelfPermission(getApplication(), Manifest.permission.CAMERA);
                Log.e("PERMISION_CODE", hasReadExternalStoragePermission1 + "***");
                if (hasReadExternalStoragePermission1 == PackageManager.PERMISSION_GRANTED) {
                    startActivityForResult(new Intent(this,QRCodeActivity.class), 1);
                } else {
                    //若没有授权，会弹出一个对话框（这个对话框是系统的，开发者不能自己定制），用户选择是否授权应用使用系统权限
                    ActivityCompat.requestPermissions(QRCodeManualActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
                }

                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case 1 :
                try {
                    String dataReturn = data.getStringExtra("dataReturn");
                    et_qr_code_number.setText(dataReturn);
                    MLog.i("二维码扫描结果：" + "-----------" + dataReturn);

                }catch (Exception e){

                }

                break;
            default : break;
        }

    }
}
