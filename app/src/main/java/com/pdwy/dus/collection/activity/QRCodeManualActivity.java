package com.pdwy.dus.collection.activity;

import android.animation.TimeAnimator;
import android.content.Intent;
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
        et_qr_code_number.setText("");
        inputData= new InputData(this);
    }

    @OnClick({R.id.ll__qr_code_number,R.id.tv_qr_code_number})
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.tv_qr_code_number:
                try {
                    String s[]=et_qr_code_number.getText().toString().split(">>");
                    ArrayList<String> mbmcList= inputData.getTemplates(s[0],s[1]);
                    if(mbmcList==null||mbmcList.size()==0) {
                        Toast.makeText(this,"没有此编号",Toast.LENGTH_LONG).show();
                        return;
                    }
//                Toast.makeText(this,"没有此编号", Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(QRCodeManualActivity.this,CollectionSettingActivity.class);
                    intent.putExtra("activityName","saoma");
                    intent.putExtra("pinzhong",inputData.getVarieties(s[0],s[1]));

                    intent.putExtra("syrwbh",s[0]);
                    intent.putExtra("csbh",s[1]);

                    intent.putStringArrayListExtra("mbmcList",mbmcList);

                    startActivity(intent);
                }
            catch (Exception e){
                Toast.makeText(this,"没有此编号",Toast.LENGTH_LONG).show();
            }
                break;
            case R.id.ll__qr_code_number:
                startActivityForResult(new Intent(this,QRCodeActivity.class), 1);
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
