package com.pdwy.dus.collection.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pdwy.dus.collection.R;
import com.pdwy.dus.collection.core.BaseActivity;
import com.pdwy.dus.collection.http.InitialDataHttp;
import com.pdwy.dus.collection.http.UploadDataHttp;
import com.pdwy.dus.collection.http.bean.PhotoHttpBean;
import com.pdwy.dus.collection.http.bean.UploadBean;
import com.pdwy.dus.collection.model.bean.PictureBean;
import com.pdwy.dus.collection.model.db.InputData;
import com.pdwy.dus.collection.utils.MLog;
import com.pdwy.dus.collection.utils.ToastUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 *
 * 上传图片  1.先请求图片服务器地址  2上传图片 返回图片在图片服务器的地址 3  上传返回的图片服务器中图片的地址
  * Author： by MR on 2018/9/13.
 */

public class PhotoViewActivity extends BaseActivity {

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(final Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {      //判断标志位
                case 0: //失败
dismissLoadingDialog();
                    Toast.makeText(PhotoViewActivity.this,msg.obj.toString(),Toast.LENGTH_LONG).show();

                    break;
                case 1: // 图片服务器地址请求成功



                    PictureBean pictureBean=new PictureBean();

                    pictureBean.pictureName=intent.getStringExtra("pictureName");
                    pictureBean.stateOfExpression=intent.getStringExtra("stateOfExpression");
                    MLog.e("=========="+pictureBean.pictureName);
                    List<PictureBean> listPictureBean=new ArrayList<>();
                    listPictureBean.add(pictureBean);
                    uploadDataHttp.uploadPhoto(listPictureBean,(PhotoHttpBean)msg.obj,0);
//                    String path= Environment.getExternalStorageDirectory().getPath()+"/DUS/"+pictureBean.pictureName+("0".equals(pictureBean.stateOfExpression)?"":"（异常）")+".jpg";
//                    final File file=new File(path);
//                    final PhotoHttpBean photoHttp=(PhotoHttpBean)msg.obj;
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            UploadImg.uploadFile(file,photoHttp);
//                            UploadImg.uploadImg(photoHttp,file);
//
//                        }
//                    }).start();

                    break;
                //图片地址上传成功
                case 2:
                    dismissLoadingDialog();
                    InputData inputData=new InputData(PhotoViewActivity.this);
                    inputData.updateZhaoPian(intent.getStringExtra("experimentalNumber"),
                            intent.getStringExtra("testNumber"),
                            intent.getStringExtra("characterName")
                            );

                    Toast.makeText(PhotoViewActivity.this,msg.obj.toString(),Toast.LENGTH_LONG).show();
                    setResult(2, null);
                    finish();
                    break;

                    //图片上传成功  去上传图片在图片服务器的地址
                case 3:
                                    PictureBean pictureBean1=new PictureBean();
                                    pictureBean1.experimentalNumber=intent.getStringExtra("experimentalNumber");
                                    pictureBean1.testNumber=intent.getStringExtra("testNumber");
                                    pictureBean1.characterName=intent.getStringExtra("characterName");
                                    MLog.e("返回的图片地址======="+msg.obj.toString());
                                    uploadDataHttp.uploadPhotoData(pictureBean1,msg.obj.toString(),0);


                    break;
            }
        }
    };
     Intent intent;
    UploadDataHttp uploadDataHttp;
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
    @BindView(R.id.tv_zpmc)
    TextView tv_zpmc;
    @BindView(R.id.tv_csbh)
    TextView tv_csbh;
    @BindView(R.id.tv_sybh)
    TextView tv_sybh;
    @BindView(R.id.tv_xzmc)
    TextView tv_xzmc;
    @BindView(R.id.tv_bdzt)
    TextView tv_bdzt;
    @BindView(R.id.tv_sc)
    TextView tv_sc;
    @BindView(R.id.tv_pssj)
    TextView tv_pssj;
    @BindView(R.id.iv_zpck)
    ImageView iv_zpck;
    @BindView(R.id.tv_schu)
    TextView tv_schu;
    @BindView(R.id.tv_schuang)
    TextView tv_schuang;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_photo_view;
    }

    @Override
    protected void onCreateAfter() {


        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            //验证是否许可权限
            for (String str : permissions) {
                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                }
            }
        }


        ll_head_dus.setVisibility(View.INVISIBLE);
        ll_head_personalcenter.setVisibility(View.GONE);
        ll_head_return.setVisibility(View.VISIBLE);
        ll_head_homepage.setVisibility(View.VISIBLE);
        ll_head_homepage_r.setVisibility(View.VISIBLE);
        tv_head_title.setVisibility(View.VISIBLE);
        tv_head_title.setText("照片查看");
        intent=getIntent();
        uploadDataHttp=new UploadDataHttp(PhotoViewActivity.this,handler);
        tv_schu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PhotoViewActivity.this);
                builder.setTitle("删除照片");
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PictureBean picture=new PictureBean();
                        picture.pictureName=intent.getStringExtra("pictureName");
                        picture.testNumber=intent.getStringExtra("testNumber");
                        picture.experimentalNumber=intent.getStringExtra("experimentalNumber");
                        picture.varieties=intent.getStringExtra("varieties");

                        InputData inputData=new InputData(PhotoViewActivity.this);

                        if(inputData.deleteZhaoPian(picture)) {
                            Toast.makeText(PhotoViewActivity.this, "删除成功", Toast.LENGTH_SHORT).show();

                            setResult(2, null);
                            finish();
                        }
                        else
                            Toast.makeText(PhotoViewActivity.this, "删除失败", Toast.LENGTH_SHORT).show();



                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        tv_schuang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PhotoViewActivity.this);
                builder.setTitle("上传照片");
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showLoadingDialog("");
                        InitialDataHttp initialDataHttp=new InitialDataHttp(PhotoViewActivity.this,handler);
                        initialDataHttp.getPhotoHttp();

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        String path= Environment.getExternalStorageDirectory().getPath()+"/DUS/"+intent.getStringExtra("pictureName")+("0".equals(intent.getStringExtra("stateOfExpression"))?"":"（异常）")+".jpg";
        MLog.e(path);
        tv_zpmc.setText(intent.getStringExtra("pictureName"));
        tv_csbh.setText(intent.getStringExtra("testNumber"));
        tv_sybh.setText(intent.getStringExtra("experimentalNumber"));
        tv_xzmc.setText(intent.getStringExtra("characterName"));
        tv_bdzt.setText("0".equals(intent.getStringExtra("stateOfExpression"))?"正常":"异常");
        tv_sc.setText(intent.getStringExtra("whetherOrNotToUpload"));
        tv_pssj.setText(intent.getStringExtra("pictureTime"));
         try {
              InputStream is = new FileInputStream(path);
             BitmapFactory.Options opts=new BitmapFactory.Options();

             opts.inTempStorage = new byte[100 * 1024];
             opts.inPurgeable = true;
             opts.inSampleSize = 4;
             opts.inInputShareable = true;
             Bitmap bitmap =BitmapFactory.decodeStream(is,null, opts);
//             Bitmap bitmap= BitmapFactory.decodeFile(path);
             iv_zpck.setImageBitmap(bitmap);

        } catch (Exception e) {

             ToastUtil.showMessage(PhotoViewActivity.this,"加载图片失败");
            e.printStackTrace();
        }

    }
}
