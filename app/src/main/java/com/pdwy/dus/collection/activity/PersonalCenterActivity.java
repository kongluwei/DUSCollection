package com.pdwy.dus.collection.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pdwy.dus.collection.LoginActivity;
import com.pdwy.dus.collection.MainActivity;
import com.pdwy.dus.collection.R;
import com.pdwy.dus.collection.core.BaseActivity;
import com.pdwy.dus.collection.utils.SharePreferencesUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 个人中心
 * Author： by MR on 2018/8/1.
 */

public class PersonalCenterActivity extends BaseActivity{
    @BindView(R.id.ll_head_homepage_l)
    LinearLayout ll_head_homepage_l;
    @BindView(R.id.ll_head_dus)
    LinearLayout ll_head_dus;
    @BindView(R.id.ll_head_personalcenter)
    LinearLayout ll_head_personalcenter;
    @BindView(R.id.tv_head_title)
    TextView tv_head_title;
    @BindView(R.id.tv_user_name)
    TextView tv_user_name;
    @BindView(R.id.tv_login_time)
    TextView tv_login_time;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_personal_center;
    }

    @Override
    protected void onCreateAfter() {
             inView();
    }
    void inView(){
        ll_head_homepage_l.setVisibility(View.VISIBLE);
        ll_head_dus.setVisibility(View.GONE);
        ll_head_personalcenter.setVisibility(View.INVISIBLE);
        tv_head_title.setVisibility(View.VISIBLE);
        tv_head_title.setText("个人中心");
        tv_user_name.setText(SharePreferencesUtils.getString(getString(R.string.user_name),""));
        tv_login_time.setText(SharePreferencesUtils.getString(getString(R.string.login_time),""));
    }
    @OnClick({R.id.tv_personal_out,R.id.ll_personal_functional_introduction,
    R.id.ll_personal_data_update,R.id.ll_personal_threshold_management,
            R.id.ll_personal_version_information,R.id.ll_personal_relation_haracter,
            R.id.iv_personal_head_portrait
    })
    //导航栏点击事件
    void onClick(View v){
          switch (v.getId()){
              //退出登录
              case R.id.tv_personal_out:


                  AlertDialog.Builder builder = new AlertDialog.Builder(this);
                  builder.setTitle("退出到登录页面");
                  builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialog, int which) {

                      }
                  });
                  builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialog, int which) {

                          SharePreferencesUtils.putString(getString(R.string.user_id), "");
                          removeALLActivity();
                          startActivity(new Intent(PersonalCenterActivity.this, LoginActivity.class));

                      }
                  });
                  AlertDialog dialog = builder.create();
                  dialog.show();

                  break;
              //功能介绍
              case R.id.ll_personal_functional_introduction:
                  startActivity(new Intent(this,FunctionalIntroductionActivity.class));
                  break;
              //数据更新
              case R.id.ll_personal_data_update:
                  AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                  builder1.setTitle("数据更新");
                  builder1.setMessage("是否进行数据更新");
                  builder1.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialog, int which) {

                      }
                  });
                  builder1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialog, int which) {
                          Toast.makeText(PersonalCenterActivity.this,"数据更新",Toast.LENGTH_SHORT).show();
                          Date dt= new Date();
                          Long time= dt.getTime();//这就是距离1970年1月1日0点0分0秒的毫秒数

                          File dbFile = new File(Environment.getDataDirectory().getAbsolutePath()+"/data/"+getPackageName()+"/databases/"+"DUS_caiji"+ SharePreferencesUtils.getString(getString(R.string.user_name),"")+".db");
                          FileInputStream fis = null;
                          FileOutputStream fos = null;
                          try {
                              //文件复制到sd卡中
                              fis = new FileInputStream(dbFile);



                              fos = new FileOutputStream(Environment.getExternalStorageDirectory().getAbsolutePath()+"/copy"+time+".db");
                              int len = 0;
                              byte[] buffer = new byte[2048];
                              while(-1!=(len=fis.read(buffer))){
                                  fos.write(buffer, 0, len);
                              }
                              fos.flush();

                          } catch (Exception e) {
                              e.printStackTrace();
                          }finally{
                              //关闭数据流
                              try{
                                  if(fos!=null)fos.close();
                                  if (fis!=null)fis.close();
                              }catch(IOException e){
                                  e.printStackTrace();
                              }
                              //通知MTP数据库更新

                              Uri data = Uri.fromFile(new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/copy"+time+".db"));
                              sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, data));
                          }



                      }
                  });
                  AlertDialog dialog1 = builder1.create();
                  dialog1.show();

                  break;
              //阈值管理
              case R.id.ll_personal_threshold_management:
                  Intent intent1=new Intent(this,ThresholdManagementActivity.class);
                  intent1.putExtra("activityP","1");
                  startActivity(intent1);
                  break;
              //版本信息
              case R.id.ll_personal_version_information:
//                  Toast.makeText(this,"已是最新版本",Toast.LENGTH_SHORT).show();
                  startActivity(new Intent(this,VersionInformationActivity.class));
                  break;
              //关联性状设置
              case R.id.ll_personal_relation_haracter:
                  Intent intent2=new Intent(this,ThresholdManagementActivity.class);
                  intent2.putExtra("activityP","2");
                  startActivity(intent2);

                  break;
              //头像
              case R.id.iv_personal_head_portrait:

                  break;
          }
    }


}
