package com.pdwy.dus.collection.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pdwy.dus.collection.R;
import com.pdwy.dus.collection.core.BaseActivity;
import com.pdwy.dus.collection.model.bean.PictureBean;
import com.pdwy.dus.collection.model.db.InputData;
import com.pdwy.dus.collection.utils.MLog;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;

/**
 * 性状拍照
 * Author： by MR on 2018/9/12.
 */

public class PhotographActivity extends BaseActivity {

    @BindView(R.id.ll_head_dus)
    LinearLayout ll_head_dus;
    @BindView(R.id.ll_head_personalcenter)
    LinearLayout ll_head_personalcenter;
    @BindView(R.id.ll_head_return)
    LinearLayout ll_head_return;
    @BindView(R.id.ll_head_homepage)
    LinearLayout ll_head_homepage;
    @BindView(R.id.ll_head_homepage_r)
    LinearLayout ll_head_homepage_r;
    @BindView(R.id.tv_head_title)
    TextView tv_head_title;
    Intent intent;
    @BindView(R.id.tv_csbh)
    TextView tv_csbh;
    @BindView(R.id.tv_xz)
    TextView tv_xz;
    @BindView(R.id.sc_ll_paizhao)
    LinearLayout sc_ll_paizhao;
    @BindView(R.id.ll_paizhao)
    LinearLayout ll_paizhao;
    ArrayList<String>list;
    InputData inputData;
    //相册请求码
    private static final int ALBUM_REQUEST_CODE = 1;
    //相机请求码
    private static final int CAMERA_REQUEST_CODE = 2;
    //剪裁请求码
    private static final int CROP_REQUEST_CODE = 3;
    //调用照相机返回图片文件
    private File tempFile;

    ArrayList<PictureBean> listPictureBean;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_photograph;

    }

    @Override
    protected void onCreateAfter() {
        ll_head_dus.setVisibility(View.INVISIBLE);
        ll_head_personalcenter.setVisibility(View.GONE);
        ll_head_return.setVisibility(View.VISIBLE);
        ll_head_homepage.setVisibility(View.VISIBLE);
        ll_head_homepage_r.setVisibility(View.INVISIBLE);
        tv_head_title.setVisibility(View.VISIBLE);
        tv_head_title.setText("照片拍摄");
         inputData=new InputData(PhotographActivity.this);
         intent=getIntent();
             paizhao2();
    }

    void paizhao2(){



        listPictureBean=new ArrayList<>();

        tv_csbh.setText(intent.getStringExtra("csbh"));
        tv_xz.setVisibility(View.GONE);
        list=intent.getStringArrayListExtra("xzListData");
        for(int i=0;i< list.size();i++) {
            PictureBean pictureBean=  inputData.getZhaoPian(intent.getStringExtra("experimentalNumber"),intent.getStringExtra("csbh"),list.get(i));

            listPictureBean.add(pictureBean);
//            if(intent.getStringExtra("pinzhong").equals()){
//
//
//            }
            LinearLayout ll = (LinearLayout) LinearLayout.inflate(this, R.layout.photograph_ll_item, null);
            TextView tv_csbh=(TextView)ll.findViewById(R.id.tv_csbh);
            tv_csbh.setText(list.get(i));
            TextView tv_paishe=(TextView)ll.findViewById(R.id.tv_paishe);
            final int finalI = i;
            tv_paishe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //检查当前权限（若没有该权限，值为-1；若有该权限，值为0）
                    int hasReadExternalStoragePermission1 = ActivityCompat.checkSelfPermission(getApplication(), Manifest.permission.CAMERA);
                    Log.e("PERMISION_CODE", hasReadExternalStoragePermission1 + "***");
                    if (hasReadExternalStoragePermission1 == PackageManager.PERMISSION_GRANTED) {
                        getPicFromCamera(finalI);
                    } else {
                        //若没有授权，会弹出一个对话框（这个对话框是系统的，开发者不能自己定制），用户选择是否授权应用使用系统权限
                        ActivityCompat.requestPermissions(PhotographActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
                    }

                }
            });
            TextView tv_chakan=(TextView)ll.findViewById(R.id.tv_chakan);
            tv_chakan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Intent intent=new Intent(PhotographActivity.this,PhotoViewActivity.class);

                    intent.putExtra("pictureName",listPictureBean.get(finalI).pictureName);
                    intent.putExtra("testNumber",listPictureBean.get(finalI).testNumber);
                    intent.putExtra("experimentalNumber",listPictureBean.get(finalI).experimentalNumber);
                    intent.putExtra("stateOfExpression",listPictureBean.get(finalI).stateOfExpression);
                    intent.putExtra("varieties",listPictureBean.get(finalI).varieties);
                    intent.putExtra("pictureTime",listPictureBean.get(finalI).pictureTime);
                    intent.putExtra("characterName",listPictureBean.get(finalI).characterName);
                    intent.putExtra("whetherOrNotToUpload",listPictureBean.get(finalI).whetherOrNotToUpload);
                    startActivity(intent);

                }
            });
            sc_ll_paizhao.addView(ll);



        }

    }
    void paizhao1(){
        tv_csbh.setText(intent.getStringExtra("csbh"));
        tv_xz.setText(intent.getStringExtra("xzmc"));

    }


    void getPicFromCamera(int i) {
        //用于保存调用相机拍照后所生成的文件  判断是否异常
        if("1".equals(intent.getStringExtra("activityP"))) {
            tempFile = new File(Environment.getExternalStorageDirectory().getPath() + "/DUS", tv_csbh.getText().toString() + list.get(i) + "（异常）.jpg");
        }
        else
            tempFile = new File(Environment.getExternalStorageDirectory().getPath() + "/DUS", tv_csbh.getText().toString() + list.get(i) + ".jpg");
        //跳转到调用系统相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //判断版本
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        startActivityForResult(intent, i);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (resultCode == RESULT_OK) {
            PictureBean pictureBean=new PictureBean();
            pictureBean.testNumber=tv_csbh.getText().toString();
            listPictureBean.get(requestCode).testNumber=pictureBean.testNumber;
            pictureBean.experimentalNumber=this.intent.getStringExtra("experimentalNumber");
            listPictureBean.get(requestCode).experimentalNumber=pictureBean.experimentalNumber;
            pictureBean.stateOfExpression=this.intent.getStringExtra("stateOfExpression");
            listPictureBean.get(requestCode).stateOfExpression= pictureBean.stateOfExpression;
            pictureBean.varieties=this.intent.getStringExtra("pinzhong");
            listPictureBean.get(requestCode).varieties=pictureBean.varieties;
            Calendar c = Calendar.getInstance();//
           int mYear = c.get(Calendar.YEAR); // 获取当前年份
            int mMonth = c.get(Calendar.MONTH) + 1;// 获取当前月份
            int mDay = c.get(Calendar.DAY_OF_MONTH);// 获取当日期
            int mWay = c.get(Calendar.DAY_OF_WEEK);// 获取当前日期的星期
            int  mHour = c.get(Calendar.HOUR_OF_DAY);//时
            int  mMinute = c.get(Calendar.MINUTE);//分
            pictureBean.pictureTime=mYear+"-"+mMonth+"-"+mDay;
            listPictureBean.get(requestCode).pictureTime=pictureBean.pictureTime;
            pictureBean.characterName=list.get(requestCode);
            listPictureBean.get(requestCode).characterName=pictureBean.characterName;
            pictureBean.whetherOrNotToUpload="否";
            listPictureBean.get(requestCode).whetherOrNotToUpload=pictureBean.whetherOrNotToUpload;

            pictureBean.pictureName=pictureBean.testNumber+pictureBean.characterName;
            listPictureBean.get(requestCode).pictureName=pictureBean.pictureName;

            if("1".equals(this.intent.getStringExtra("activityP"))) {
                pictureBean.pictureAddress = pictureBean.characterName + "（异常）.jpg";
            }
                else {
                pictureBean.pictureAddress = pictureBean.characterName + ".jpg";
            }

            inputData.setZhaoPian(pictureBean);



        }

    }
}
