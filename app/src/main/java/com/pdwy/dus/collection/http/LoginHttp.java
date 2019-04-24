package com.pdwy.dus.collection.http;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;
import com.pdwy.dus.collection.http.bean.GroupingBean;
import com.pdwy.dus.collection.http.bean.LogInBean;
import com.pdwy.dus.collection.http.bean.LogInBeanEnter;
import com.pdwy.dus.collection.http.bean.LogInBeanOut;
import com.pdwy.dus.collection.utils.MLog;
import com.pdwy.dus.collection.utils.SharePreferencesUtils;
import com.pdwy.dus.collection.utils.SystemUtil;
import com.pdwy.dus.collection.utils.ToastUtil;
import com.pdwy.dus.collection.utils.UniquePsuedoIDUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 登陆 okhttp
 * Author： by MR on 2019/1/16.
 */

public class LoginHttp {
    private Handler handler;
    private  Context context;
    private Gson gson;
    private OkHttpClient okHttpClient;
    public LoginHttp(Context context,Handler handler){
        this.context=context;
        this.handler=handler;
        gson = new Gson();
        okHttpClient =new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(15,TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .build();
        okHttpClient.dispatcher().setMaxRequestsPerHost(1);
    }

    public   void  logIn(String account  , String passWord){
              MLog.e(account+"==="+passWord);
//        RequestBody requestBody = new FormBody.Builder()
//                .add("account", account)
//                .add("passWord", passWord)
//                .build();

        LogInBeanEnter logInBeanEnter = new LogInBeanEnter();
        logInBeanEnter.setAccount(account);
        logInBeanEnter.setPasswd(passWord);
        logInBeanEnter.setModel(SystemUtil.getDeviceBrand() + "  " + SystemUtil.getSystemModel());
        logInBeanEnter.setModelSign(UniquePsuedoIDUtil.getUniquePsuedoIdMD5());
        logInBeanEnter.setAppType("2");

        String uploadString = gson.toJson(logInBeanEnter);
        MLog.e(uploadString);
        //MediaType  设置Content-Type 标头中包含的媒体类型值
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8")
                , uploadString);


        final Request request = new Request.Builder()
                .url(SharePreferencesUtils.getString("dizi",Api.URL_CS)+Api.DL)
                //添加请求头
                .addHeader("accept","application/json;charset=UTF-8")
                .addHeader("Content-Type","application/x-www-form-urlencoded; charset=utf-8")
                .post(requestBody)//默认就是GET请求，可以不写
                .build();


        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            //请求失败
            public void onFailure(Call call, IOException e) {
                MLog.e("登陆请求失败onFailure: "+call.request().toString());
                Message msg =Message.obtain();
                msg.obj = "登陆请求失败";
                msg.what=0;   //标志消息的标志
                handler.sendMessage(msg);

            }
            //请求成功
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                MLog.e("登陆请求成功onResponse: " + response.toString());

                String responses= response.body().string();
                MLog.e("登陆返回========="+responses);
                if(responses==null||"".equals(responses)){
                    Message msg =Message.obtain();
                    msg.obj = "账号或密码错误！";
                    msg.what=0;   //标志消息的标志
                    handler.sendMessage(msg);
                    return;
                }






//                Message msg =Message.obtain();
//                msg.obj = "5284";
//                msg.what=1;   //标志消息的标志
//                handler.sendMessage(msg);
//                ToastUtil.showMessage(context,"登陆成功");
                try {
                    LogInBeanOut logInBean=gson.fromJson(responses, LogInBeanOut.class);
                    if("1000".equals(logInBean.getCode())){
                        Message msg =Message.obtain();
                        msg.obj = logInBean.getData().getAppLoginSessionID();
                        msg.what=1;   //标志消息的标志  成功
                        handler.sendMessage(msg);
                    }else {
                        Message msg =Message.obtain();
                        msg.obj = logInBean.getMsg();
                        msg.what=0;   //标志消息的标志
                        handler.sendMessage(msg);

                    }
                }catch (Exception e){

                    MLog.e("异常=============");
                }



            }
        });



    }

}
