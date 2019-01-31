package com.pdwy.dus.collection;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.pdwy.dus.collection.activity.shezidiziActivity;
import com.pdwy.dus.collection.core.BaseActivity;
import com.pdwy.dus.collection.http.Api;
import com.pdwy.dus.collection.http.InitialDataHttp;
import com.pdwy.dus.collection.http.LoginHttp;
import com.pdwy.dus.collection.http.bean.GroupingBean;
import com.pdwy.dus.collection.model.db.InputData;
import com.pdwy.dus.collection.utils.MLog;
import com.pdwy.dus.collection.utils.SharePreferencesUtils;
import com.pdwy.dus.collection.utils.ToastUtil;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 登陆activity
 * Author： by MR on 2018/7/25.
 */

public class LoginActivity extends BaseActivity {

    private int s;//分组数
    private int p;//分组指针
    private GroupingBean groupingBean;
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MLog.e("Handler==========" + msg.what);
            switch (msg.what) {      //判断标志位

                case 0:
                    dismissLoadingDialog();
                    if ("没有分组数据".equals(msg.obj.toString())) {
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();

                        Toast.makeText(LoginActivity.this, "没有新分组数据", Toast.LENGTH_SHORT).show();

                    } else
                        Toast.makeText(LoginActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();

                    break;
                case 1: //登陆成功 导入数据
                    dismissLoadingDialog();
                    ToastUtil.showMessage(LoginActivity.this, "登陆成功");
                    s = 0;
                    p = 0;
                    Calendar c = Calendar.getInstance();//
                    int mYear = c.get(Calendar.YEAR); // 获取当前年份
                    int mMonth = c.get(Calendar.MONTH) + 1;// 获取当前月份
                    int mDay = c.get(Calendar.DAY_OF_MONTH);// 获取当日期
                    int mWay = c.get(Calendar.DAY_OF_WEEK);// 获取当前日期的星期
                    int  mHour = c.get(Calendar.HOUR_OF_DAY);//时
                    int  mMinute = c.get(Calendar.MINUTE);//分


                    SharePreferencesUtils.putString(getString(R.string.user_id), msg.obj.toString());
                    SharePreferencesUtils.putString(getString(R.string.user_name),user_name.getText().toString());

                    SharePreferencesUtils.putString(getString(R.string.login_time), mYear+"年"+mMonth+"月"+mDay);
                    Date dt = new Date();
                    Long time = dt.getTime();
                    if (Radio1.isChecked())
                        time = time + 3600 * 24 * 30 * 3;
                    if (Radio2.isChecked())
                        time = time + 3600 * 24 * 30 * 3 * 2;
                    if (Radio3.isChecked())
                        time = time + 3600 * 24 * 30 * 3 * 4;
                    if (Radio4.isChecked())
                        time = time + 3600 * 24 * 30 * 3 * 4 * 20;
                    SharePreferencesUtils.putString(getString(R.string.user_time), time.toString());

                    showLoadingDialog("导入数据中");
                    initialDataHttp = new InitialDataHttp(LoginActivity.this, handler);
                    initialDataHttp.initialDataHttp1();

//                    initialDataHttp.initialDataHttp4("3");
//                    initialDataHttp.initialDataHttp5("266");


                    break;

                case 2: //分组请求成功

                    groupingBean = new GroupingBean();
                    groupingBean = (GroupingBean) msg.obj;

                    s = groupingBean.getData().size();
                    showLoadingDialog("导入数据中" + p + "/" + s);
                    initialDataHttp.initialDataHttp2(groupingBean.getData().get(0), String.valueOf(groupingBean.getData().get(0).getId()));


                    break;


                case 5: //导入数据成功

                    p++;
                    showLoadingDialog("导入数据中" + p + "/" + s);
                    if (p < s)
                        initialDataHttp.initialDataHttp2(groupingBean.getData().get(p), String.valueOf(groupingBean.getData().get(p).getId()));

                    else {
                        InputData inputData=new InputData(LoginActivity.this);
                        inputData. getCharacterThresholdBeanlist(null,null,null,null,0,null);
                        Toast.makeText(LoginActivity.this, "导入数据成功", Toast.LENGTH_SHORT).show();
                        dismissLoadingDialog();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }
                    break;
            }
        }
    };


    @BindView(R.id.tv_signin)
    TextView tv_signin;
    @BindView(R.id.user_name)
    EditText user_name;
    @BindView(R.id.user_pwd)
    EditText user_pwd;
    @BindView(R.id.Radio1)
    RadioButton Radio1;
    @BindView(R.id.Radio2)
    RadioButton Radio2;
    @BindView(R.id.Radio3)
    RadioButton Radio3;
    @BindView(R.id.Radio4)
    RadioButton Radio4;
    InitialDataHttp initialDataHttp;
    LoginHttp loginHttp;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login_layout;
    }

    @Override
    protected void onCreateAfter() {
        user_name.setText("liull");
        user_pwd.setText("12345678");
        loginHttp = new LoginHttp(this, handler);

        MLog.e("======" + SharePreferencesUtils.getString(getString(R.string.user_id), "没有"));
    }

    @OnClick({R.id.tv_signin,R.id.img_logo})
    public void signIn(View v) {
switch (v.getId()) {
    case R.id.tv_signin:
        if (user_name.getText() == null || "".equals(user_name.getText().toString().trim()) || user_pwd.getText() == null || "".equals(user_pwd.getText().toString().trim())) {
            Toast.makeText(this, "账号密码不能为空", Toast.LENGTH_SHORT).show();
        } else {
            //登陆
            showLoadingDialog("登录中");
            loginHttp.logIn(user_name.getText().toString(), user_pwd.getText().toString());
        }
        break;
    case R.id.img_logo:
        final EditText inputServer = new EditText(LoginActivity.this);
        inputServer.setText(SharePreferencesUtils.getString("dizi", "http://192.168.3.127:8080/tester"));
        final AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("设置请求地址：").setIcon(R.mipmap.logo).setView(inputServer)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String text = inputServer.getText().toString();
                SharePreferencesUtils.putString("dizi",text);
            }
        });
                builder.show();



//        startActivity(new Intent(LoginActivity.this,shezidiziActivity.class));
        break;
}
    }

}
