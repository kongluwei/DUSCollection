package com.pdwy.dus.collection;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pdwy.dus.collection.activity.QRCodeManualActivity;
import com.pdwy.dus.collection.adapter.RecyclerViewHomeContentAdapter;
import com.pdwy.dus.collection.core.BaseActivity;
import com.pdwy.dus.collection.fragment.CollectionManagementFragment;
import com.pdwy.dus.collection.fragment.CustomCollectionFragment;
import com.pdwy.dus.collection.fragment.PhotoManagementFragment;
import com.pdwy.dus.collection.fragment.UploadingDataFragment;
import com.pdwy.dus.collection.http.InitialDataHttp;
import com.pdwy.dus.collection.model.bean.CharacterBean;
import com.pdwy.dus.collection.model.bean.CollectionTaskBean;
import com.pdwy.dus.collection.model.bean.CollectionTaskItemBean;
import com.pdwy.dus.collection.model.bean.CollectionTaskItemListBean;
import com.pdwy.dus.collection.model.bean.MessageEvent;
import com.pdwy.dus.collection.model.db.InputData;
import com.pdwy.dus.collection.model.db.MySQLiteOpenHelper;
import com.pdwy.dus.collection.utils.MLog;
import com.pdwy.dus.collection.utils.SharePreferencesUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
/**
 *
 * 主界面   emmmm
 *
 */
public class MainActivity extends BaseActivity {


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {      //判断标志位

                case 0:
                    dismissLoadingDialog();
                    Toast.makeText(MainActivity.this,msg.obj.toString(),Toast.LENGTH_SHORT).show();
                    break;
                case 1: // 品种加载完成
                    dismissLoadingDialog();
                    ll_home_head.addView((LinearLayout) msg.obj);
                    inView();


                    break;
                case 2: //导入数据成功


                    break;
            }
        }
    };

    @BindView(R.id.hsc_home_head)
    HorizontalScrollView hsc_home_head;
    @BindView(R.id.ll_home_head)
    LinearLayout ll_home_head;

    @BindView(R.id.rv_home_content)
    RecyclerView rv_home_content;
    @BindView(R.id.fl_frgmt_main)
    FrameLayout fl_frgmt_main;
    @BindView(R.id.ll_include)
    LinearLayout ll_include;
    //首页RecyclerView的Adapter
    protected RecyclerViewHomeContentAdapter mAdapter;
    protected ArrayList<CollectionTaskBean> list;
    protected ArrayList<CollectionTaskItemBean> listItem;
    //自定义采集Fragment
    private CustomCollectionFragment collectionFragment;
    //采集管理
    private CollectionManagementFragment collectionManagementFragment;
    //待上传数据
    private UploadingDataFragment uploadingDataFragment;
    //照片管理
    private PhotoManagementFragment photoManagementFragment;
    private InputData inputData;
    private String pinzhong = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    //    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreateAfter() {
        //获取设备唯一标识码】
        TelephonyManager telManager = (TelephonyManager) this
                .getSystemService(Context.TELEPHONY_SERVICE);

        @SuppressLint("MissingPermission") String imei = telManager.getDeviceId();

        if ("864761020069136".equals(imei)) {
            MLog.e("已注册设备");
        } else {
            MLog.e("否");
        }

        MLog.e("======"+SharePreferencesUtils.getString(getString(R.string.user_id), "没有"));

        //判断是否登录
        if ("".equals(SharePreferencesUtils.getString(getString(R.string.user_id), ""))) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        } else {
            long l = Long.parseLong(SharePreferencesUtils.getString(getString(R.string.user_time), ""));
            Date dt = new Date();
            Long time = dt.getTime();
            if ("".equals(SharePreferencesUtils.getString(getString(R.string.user_id), "")) || time > l) {
                startActivity(new Intent(this, LoginActivity.class));
                finish();

            }


        }


        //检查当前权限（若没有该权限，值为-1；若有该权限，值为0）
        int hasReadExternalStoragePermission = ContextCompat.checkSelfPermission(getApplication(), Manifest.permission.CAMERA);
        Log.e("PERMISION_CODE", hasReadExternalStoragePermission + "***");
        if (hasReadExternalStoragePermission == PackageManager.PERMISSION_GRANTED) {

        } else {
            //若没有授权，会弹出一个对话框（这个对话框是系统的，开发者不能自己定制），用户选择是否授权应用使用系统权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
        }

//取消严格模式  FileProvider
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }

        //创建照片文件夹
        isFolderExists(Environment.getExternalStorageDirectory().getPath() + "/DUS");

        inputData = new InputData(getApplicationContext());
        showLoadingDialog("");
        // 加载品种
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    inDate();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    @Override
    protected void onStop() {
        super.onStop();
        dismissLoadingDialog();
    }

    //加载品种
    void inDate() {

        list = inputData.getCollectionTaskBeanList(0);
        if(list.size()==0) {
            Message msg = new Message();
            msg.obj = "没有分组数据";
            msg.what = 0; //品种加载完成
            handler.sendMessage(msg);
        }
        if (list.size() == 0)
            return;
        pinzhong = list.get(0).Varieties;
        int p = 0;
        for (CollectionTaskBean c : list) {
            LinearLayout v = (LinearLayout) getLayoutInflater().inflate(R.layout.home_head_item, null);
            v.setOnClickListener(new HomeHOnClick());
            if (p == 0) {
                v.setBackgroundColor(getResources().getColor(R.color.home_tead_true));
                v.getChildAt(1).setVisibility(View.VISIBLE);
                listItem = c.list;
            }
            TextView tv = (TextView) v.getChildAt(0);
            tv.setText(c.Varieties);


            Message msg = new Message();
            msg.obj = v;
            msg.what = 1; //品种加载完成
            handler.sendMessage(msg);
            p++;
        }

    }

    //初始化View 加载内容
    void inView() {

        //初始化RecyclerView
        inRecyclerView(listItem);
    }

    //初始化RecyclerView lhm13277613502
    void inRecyclerView(ArrayList<CollectionTaskItemBean> listItem) {
//设置RecyclerView管理器
        rv_home_content.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//初始化适配器
        mAdapter = new RecyclerViewHomeContentAdapter(listItem);
//设置添加或删除item时的动画，这里使用默认动画
        rv_home_content.setItemAnimator(new DefaultItemAnimator());
//设置适配器
        rv_home_content.setAdapter(mAdapter);
    }

    //下方点击事件
    @OnClick({R.id.ll_qRcode, R.id.ll_custom_collection, R.id.collection_management, R.id.ll_to_be_uploaded, R.id.ll_photo_management})
    public void inOnCl(View v) {
        FragmentManager manager = getFragmentManager();//创建FragmentManager对象
        FragmentTransaction transaction = manager.beginTransaction();//创建FragmentTransaction对象
        switch (v.getId()) {
            //二维码
            case R.id.ll_qRcode:
                Intent intent = new Intent(v.getContext(), QRCodeManualActivity.class);
                intent.putExtra("pinzhong", pinzhong);
                startActivity(intent);
                break;
            //自定义采集
            case R.id.ll_custom_collection:
                LinearLayout ll2 = (LinearLayout) v;
                TextView tv2 = (TextView) ll2.getChildAt(1);
                ll_home_head.setVisibility(View.GONE);
                hsc_home_head.setVisibility(View.GONE);
                rv_home_content.setVisibility(View.GONE);
                ll_include.setVisibility(View.GONE);
                fl_frgmt_main.setVisibility(View.VISIBLE);
                Bundle bundle1 = new Bundle();

                bundle1.putString("pinzhong", pinzhong);

                if (collectionFragment == null) {
                    collectionFragment = new CustomCollectionFragment();
                    collectionFragment.setArguments(bundle1);
                }
                transaction.replace(R.id.fl_frgmt_main, collectionFragment);
                break;
            //采集管理
            case R.id.collection_management:
                if ("".equals(pinzhong))
                    return;
                LinearLayout ll3 = (LinearLayout) v;
                TextView tv3 = (TextView) ll3.getChildAt(1);
//                startActivity(new Intent(this, CollectionManagementActivity.class));2872479089
                ll_home_head.setVisibility(View.GONE);
                hsc_home_head.setVisibility(View.GONE);
                rv_home_content.setVisibility(View.GONE);
                ll_include.setVisibility(View.GONE);
                fl_frgmt_main.setVisibility(View.VISIBLE);
                Bundle bundle2 = new Bundle();

                bundle2.putString("pinzhong", pinzhong);
                if (collectionManagementFragment == null) {
                    collectionManagementFragment = new CollectionManagementFragment();
                    collectionManagementFragment.setArguments(bundle2);
                }
                transaction.replace(R.id.fl_frgmt_main, collectionManagementFragment);
                break;
            //待上传数据
            case R.id.ll_to_be_uploaded:
                LinearLayout ll4 = (LinearLayout) v;
                TextView tv4 = (TextView) ll4.getChildAt(1);

                ll_home_head.setVisibility(View.GONE);
                hsc_home_head.setVisibility(View.GONE);
                rv_home_content.setVisibility(View.GONE);
                ll_include.setVisibility(View.GONE);
                fl_frgmt_main.setVisibility(View.VISIBLE);
                Bundle bundle3 = new Bundle();

                bundle3.putString("pinzhong", pinzhong);
                if (uploadingDataFragment == null) {
                    uploadingDataFragment = new UploadingDataFragment();
                    uploadingDataFragment.setArguments(bundle3);
                }
                transaction.replace(R.id.fl_frgmt_main, uploadingDataFragment);

                break;
            //照片管理
            case R.id.ll_photo_management:
                LinearLayout ll5 = (LinearLayout) v;
                TextView tv5 = (TextView) ll5.getChildAt(1);
                ll_home_head.setVisibility(View.GONE);
                hsc_home_head.setVisibility(View.GONE);
                rv_home_content.setVisibility(View.GONE);
                ll_include.setVisibility(View.GONE);
                fl_frgmt_main.setVisibility(View.VISIBLE);
                Bundle bundle4 = new Bundle();

                bundle4.putString("pinzhong", pinzhong);
                if (photoManagementFragment == null) {
                    photoManagementFragment = new PhotoManagementFragment();
                    photoManagementFragment.setArguments(bundle4);
                }
                transaction.replace(R.id.fl_frgmt_main, photoManagementFragment);

                break;

        }
        transaction.commit();//最后一定要提交
    }

    //品种选择
    class HomeHOnClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.ll_home_head_item:
                    LinearLayout ll = (LinearLayout) v;
                    TextView tv = (TextView) ll.getChildAt(0);
                    for (int i = 0; i < ll_home_head.getChildCount(); i++) {
                        LinearLayout ll1 = (LinearLayout) ll_home_head.getChildAt(i);
                        TextView tv1 = (TextView) ll1.getChildAt(0);
                        if (tv.getText().equals(tv1.getText())) {
                            ll1.setBackgroundColor(getResources().getColor(R.color.home_tead_true));
                            ll1.getChildAt(1).setVisibility(View.VISIBLE);
                            for (CollectionTaskBean c : list) {
                                if (tv.getText().equals(c.Varieties)) {
                                    Log.e("CollectionTaskBean", c.Varieties);
                                    listItem = c.list;
                                    pinzhong = c.Varieties;
                                }
                            }

                        } else {

                            ll1.setBackgroundColor(getResources().getColor(R.color.home_tead_f));
                            ll1.getChildAt(1).setVisibility(View.INVISIBLE);
                        }
                    }
//              listItem.notify();
//            mAdapter.notifyDataSetChanged();
//            rv_home_content.notifyAll();
                    inRecyclerView(listItem);
                    break;

            }

        }
    }

    boolean isFolderExists(String strFolder) {
        File file = new File(strFolder);

        if (!file.exists()) {
            if (file.mkdir()) {
                return true;
            } else
                return false;
        }
        return true;
    }


    //用户选择是否同意授权后，会回调这个方法
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (permissions[0].equals(Manifest.permission.READ_EXTERNAL_STORAGE) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //用户同意授权，执行读取文件的代码
            } else {
                //若用户不同意授权，直接暴力退出应用。
                // 当然，这里也可以有比较温柔的操作。
//                finish();
            }
        }
    }

    //将之前获取音乐信息的代码单独封装到了一个方法里面
    private void obtainMediaInfo() {
        Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        cursor.moveToFirst();
        do {
            String title = cursor.getString(cursor.getColumnIndex("title"));
            Log.e("TITLE", title);
        } while (cursor.moveToNext());
    }

}
