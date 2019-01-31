package com.pdwy.dus.collection.http;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.pdwy.dus.collection.http.bean.CharacterBean;
import com.pdwy.dus.collection.http.bean.GroupingBean;
import com.pdwy.dus.collection.http.bean.GrowthPeriodBean;
import com.pdwy.dus.collection.http.bean.PhotoHttpBean;
import com.pdwy.dus.collection.http.bean.TaskBean;
import com.pdwy.dus.collection.http.bean.TemplateBean;
import com.pdwy.dus.collection.model.db.InputData;
import com.pdwy.dus.collection.utils.MLog;
import com.pdwy.dus.collection.utils.SharePreferencesUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 获取数据
 * Author： by MR on 2018/8/23.
 */

public class InitialDataHttp {
    //通过构造函数来获取
    Gson gson;
    PhotoHttpBean photoHttpBean;
    GroupingBean groupingBean;

    private InputData inputData;

   Handler  handler;
    OkHttpClient okHttpClient;
    String photoHttp="";
    ArrayList<com.pdwy.dus.collection.model.bean.CharacterBean> list;

    Context context;
    //初始化
    public InitialDataHttp(Context context,Handler  handler){
     this.context=context;
        gson = new Gson();
        this.handler=handler;
        okHttpClient =new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15,TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build();
        okHttpClient.dispatcher().setMaxRequestsPerHost(3);
    }

    // 第一步 ：  获取分组
    public void initialDataHttp1(){
MLog.e("UID============="+ SharePreferencesUtils.getString("USER_ID",""));
        RequestBody requestBody = new FormBody.Builder()
                .add("userId", SharePreferencesUtils.getString("USER_ID",""))
                .build();
        final Request request = new Request.Builder()
                .url(SharePreferencesUtils.getString("dizi",Api.URL_CS)+Api.HQCSYSYYDWWCDFZ)
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
                MLog.e("分组请求失败onFailure: "+call.request().toString());
                Message msg =Message.obtain();
                msg.obj = "分组请求失败";
                msg.what=0;   //标志消息的标志
                handler.sendMessage(msg);

            }
//请求成功
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                MLog.e("分组数据请求成功onResponse: " + response.toString());

               String responses= response.body().string();
               MLog.e("分组返回========="+responses);



        groupingBean = gson.fromJson(responses, GroupingBean.class);


                //分组去重
//                dataBeanlist1=new ArrayList<>();
//                for(GroupingBean.DataBean dataBean:groupingBean.getData()){
//                    int p=0;
//                    for(GroupingBean.DataBean dataBean1:dataBeanlist1){
//                        if(dataBean1.getCenterttaskcodename().equals(dataBean.getCenterttaskcodename())){
//
//                            p=1;
//                            break;
//                        }
//
//                    }
//                         if(p==0)
//                        dataBeanlist1.add(dataBean);
//                }

                if(groupingBean.getData()!=null&&groupingBean.getData().size()>0) {
                    Message msg =Message.obtain();
                    msg.obj = groupingBean;
                    msg.what=2;   //标志消息的标志
                    handler.sendMessage(msg);


                }
                else {
                 MLog.e("没有分组数据");
                    Message msg =Message.obtain();
                msg.obj = "没有分组数据";
                    msg.what=0;   //标志消息的标志
                    handler.sendMessage(msg);
                }
            }
        });


    }

    //第二步： 获取分组下的任务
    public void initialDataHttp2(final GroupingBean.DataBean dataBean, final String groupId){

        RequestBody requestBody = new FormBody.Builder()
                .add("groupId",groupId)
                .add("userId", SharePreferencesUtils.getString("USER_ID",""))
                .build();
        final Request request = new Request.Builder()
                .url(SharePreferencesUtils.getString("dizi",Api.URL_CS)+Api.HQFZXDRW)
                //添加请求头
                .addHeader("accept","application/json;charset=UTF-8")
                .addHeader("Content-Type","application/x-www-form-urlencoded; charset=utf-8")
                .post(requestBody)//默认就是GET请求，可以不写
                .build();


        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                MLog.e("任务请求失败onFailure: "+call.toString());
                Message msg =Message.obtain();
                msg.obj = "任务请求失败";
                msg.what=0;   //标志消息的标志
                handler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                MLog.e("onResponse: " + response.toString());

                String responses= response.body().string();
                MLog.e("任务返回========="+responses);

//                MLog.e(taskBean.getData().get(0).getTestCode());

//                Message msg =Message.obtain();
//                msg.obj = "任务请求成功";
//                msg.what=3;   //标志消息的标志
//                handler.sendMessage(msg);

                MLog.e("分组id==========="+groupId);
                initialDataHttp3(dataBean,groupId,gson.fromJson(responses, TaskBean.class));
                initialDataHttp4(groupId);
                initialDataHttp5(groupId);

            }
        });


    }

    // 依据分组id获取模板
    public void initialDataHttp4(final String groupId){

        RequestBody requestBody = new FormBody.Builder()
                .add("groupId",groupId)
                .add("userId", SharePreferencesUtils.getString("USER_ID",""))
                .build();
        final Request request = new Request.Builder()
                .url(SharePreferencesUtils.getString("dizi",Api.URL_CS)+Api.HQFZXMB)
                //添加请求头
                .addHeader("accept","application/json;charset=UTF-8")
                .addHeader("Content-Type","application/x-www-form-urlencoded; charset=utf-8")
                .post(requestBody)//默认就是GET请求，可以不写
                .build();


        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                MLog.e("模板请求失败onFailure: "+call.toString());
                Message msg =Message.obtain();

//                msg.obj = "任务请求失败";
//                msg.what=0;   //标志消息的标志
//                handler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                MLog.e("onResponse: " + response.toString());

                String responses= response.body().string();
                MLog.e("模板返回========="+responses);
                // http 包下的
                TemplateBean templateBean= gson.fromJson(responses, TemplateBean.class);


              if("0".equals(templateBean.getCode())) {
                  inputData=new InputData(context);
                  inputData.setTemplate(templateBean);
              }
              MLog.e("模板========"+templateBean.getMsg());
//                taskBean = gson.fromJson(responses, TaskBean.class);
//                MLog.e(taskBean.getData().get(0).getTestCode());

//                initialDataHttp3(groupId);
            }
        });


    }
   // 依据分组id获取生育期
    public void initialDataHttp5(final String groupId){

        RequestBody requestBody = new FormBody.Builder()
                .add("groupId",groupId)
                .add("userId",SharePreferencesUtils.getString("USER_ID",""))
                .build();
        final Request request = new Request.Builder()
                .url(SharePreferencesUtils.getString("dizi",Api.URL_CS)+Api.HQSYZQ)
                //添加请求头
                .addHeader("accept","application/json;charset=UTF-8")
                .addHeader("Content-Type","application/x-www-form-urlencoded; charset=utf-8")
                .post(requestBody)//默认就是GET请求，可以不写
                .build();


        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                MLog.e("生育期请求失败onFailure: "+call.toString());
                Message msg =Message.obtain();
//                msg.obj = "任务请求失败";
//                msg.what=0;   //标志消息的标志
//                handler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                MLog.e("onResponse: " + response.toString());

                String responses= response.body().string();
                MLog.e("生育周期返回========="+responses);



                GrowthPeriodBean growthPeriodBean = gson.fromJson(responses, GrowthPeriodBean.class);
if("0".equals(growthPeriodBean.getCode())){
    inputData=new InputData(context);
    inputData.setGrowthPeriod(growthPeriodBean);

}

//                initialDataHttp3(groupId);
            }
        });


    }


    //第三步 ： 获取分组下的性状
    public void initialDataHttp3(final GroupingBean.DataBean dataBean, final String groupId, final TaskBean taskBean){


        RequestBody requestBody = new FormBody.Builder()
                .add("groupId", groupId)
                .build();
        final Request request = new Request.Builder()
                .url(SharePreferencesUtils.getString("dizi",Api.URL_CS)+Api.HQFZDYXZSJ)
                //添加请求头
                .addHeader("accept","application/json;charset=UTF-8")
                .addHeader("Content-Type","application/x-www-form-urlencoded; charset=utf-8")
                .post(requestBody)//默认就是GET请求，可以不写
                .build();


        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                MLog.e("性状类别请求失败onFailure: "+call.toString());
                Message msg =Message.obtain();
                msg.obj = "性状请求失败";
                msg.what=0;   //标志消息的标志
                handler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                MLog.e("onResponse: " + response.toString());
                String responses= response.body().string();
                MLog.e("性状返回========="+responses);
                CharacterBean characterBean; characterBean = gson.fromJson(responses, CharacterBean.class);
//                MLog.e(characterBean.getData().get(0).getName());

                 if("成功".equals(groupingBean.getMsg())&&"成功".equals(taskBean.getMsg())&&"成功".equals(characterBean.getMsg())){
                     inputData=new InputData(context);
                     list=new ArrayList<>();
                         MLog.e("分组编号=============="+dataBean.getId());
                         if(inputData.isExistence(dataBean.getId())){
                             Message msg =Message.obtain();
                             msg.obj = groupingBean;
                             msg.what=5;   //标志消息的标志
                             handler.sendMessage(msg);

                             return;
                         }


                         String containCharacter="";
                                 for(int p=0;p<taskBean.getData().size();p++){

                                     for(int t=0;t<characterBean.getData().size();t++){
                                         com.pdwy.dus.collection.model.bean.CharacterBean character= new com.pdwy.dus.collection.model.bean.CharacterBean();
                                         character.characterId=characterBean.getData().get(t).getStdCharCode();
                                 character.characterName=characterBean.getData().get(t).getName();
                                 character.varieties=dataBean.getCropName();
                                 character.experimentalNumber=dataBean.getCenterttaskcodename();
                                 character.template=dataBean.getGuideeditioncode();
                                 character.testNumber=taskBean.getData().get(p).getTestCode();
                                 character.groupId=dataBean.getId();
                                 character.varietyId=taskBean.getData().get(p).getId();
                                 character.growthPeriod="发芽";
                                 character.growthPeriodTime="8.20-9.01;";
                                 character.observationalQuantity="40";
                                 character.observationMethod=characterBean.getData().get(t).getObservation();
                                 character.fillInTheState="0";
                                 character.dataUnit=characterBean.getData().get(t).getUnit();
                                 String codeDescription="";
                                 String codeValue="";
                                 if(characterBean.getData().get(t).getShowState().size()>0)
                                 for(int s=0;s<characterBean.getData().get(t).getShowState().size();s++){
                                     codeDescription=codeDescription+characterBean.getData().get(t).getShowState().get(s).getName();
                                     codeValue=codeValue+characterBean.getData().get(t).getShowState().get(s).getCode();
                                     if(s<characterBean.getData().get(t).getShowState().size()){
                                         codeDescription=codeDescription+",";
                                         codeValue=codeValue+",";
                                     }
                                 }
                                 character.codeDescription=codeDescription;
                                 character.codeValue=codeValue;
                                 list.add(character);
                                 if(p==0) {
                                     containCharacter = containCharacter + characterBean.getData().get(t).getStdCharCode();
                                     if (t<characterBean.getData().size()-1){
                                         containCharacter=containCharacter+",";
                                     }
                                 }
                             }

                         }
                         MLog.e("containCharacter========"+containCharacter);

//导入模板
                         inputData.initialTemplate(dataBean.getCropName(),dataBean.getGuideeditioncode(),containCharacter);
                     //导入性状
                     inputData.initialCharacter(list);

                     Message msg =Message.obtain();
                     msg.obj = groupingBean;
                     msg.what=5;   //标志消息的标志
                     handler.sendMessage(msg);
                 }

          else {
                     MLog.e("分组==="+groupingBean.getMsg()+"任务==="+taskBean.getMsg()+"性状==="+characterBean.getMsg());

                     Message msg =Message.obtain();
                     msg.obj = "数据请求失败";
                     msg.what=0;   //标志消息的标志
                     handler.sendMessage(msg);
                 }

            }
        });


    }

    //获取图片服务器地址
    public void getPhotoHttp(){

        RequestBody requestBody = new FormBody.Builder()
                .build();
        final Request request = new Request.Builder()
                .url(SharePreferencesUtils.getString("dizi",Api.URL_CS)+Api.HQTPFWQDZ)
                //添加请求头
                .addHeader("accept","application/json;charset=UTF-8")
                .addHeader("Content-Type","application/x-www-form-urlencoded; charset=utf-8")
                .post(requestBody)//默认就是GET请求，可以不写
                .build();


        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                MLog.e("图片服务器地址请求失败onFailure: "+call.toString());
                Message msg =Message.obtain();
                msg.obj = "请求失败";
                msg.what=0;   //标志消息的标志
                handler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                MLog.e("onResponse: " + response.toString());

                String responses= response.body().string();
                MLog.e("图片服务器地址请求成功onResponse: ");
                MLog.e("图片服务器地址请求返回========="+responses);

                photoHttpBean = gson.fromJson(responses, PhotoHttpBean.class);

                if("成功".equals(photoHttpBean.getMsg())){
                    Message msg =Message.obtain();
                    msg.obj = photoHttpBean;
                    msg.what=1;   //标志消息的标志
                    handler.sendMessage(msg);


                }else {

                    Message msg =Message.obtain();
                    msg.obj = "请求失败";
                    msg.what=0;   //标志消息的标志
                    handler.sendMessage(msg);
                }

            }
        });



    }

}

//
//        作者：Jdqm
//        链接：https://www.jianshu.com/p/da4a806e599b
//        來源：简书
//        简书著作权归作者所有，任何形式的转载都请联系作者获得授权并注明出处。

//        InputData inputData=new InputData(c);
//
//        ArrayList<CharacterBean> list=new ArrayList<>();
//        CharacterBean characterBean=new CharacterBean();
//
//
//        list.add(characterBean);
//        inputData.initialCharacter(list);
