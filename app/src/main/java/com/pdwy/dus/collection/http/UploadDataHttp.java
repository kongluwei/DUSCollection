package com.pdwy.dus.collection.http;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.pdwy.dus.collection.http.bean.CharacterBean;
import com.pdwy.dus.collection.http.bean.GroupingBean;
import com.pdwy.dus.collection.http.bean.PhotoHttp2Bean;
import com.pdwy.dus.collection.http.bean.PhotoHttpBean;
import com.pdwy.dus.collection.http.bean.ReturnBean;
import com.pdwy.dus.collection.http.bean.TaskBean;
import com.pdwy.dus.collection.http.bean.UploadBean;
import com.pdwy.dus.collection.model.bean.PictureBean;
import com.pdwy.dus.collection.model.db.InputData;
import com.pdwy.dus.collection.utils.MLog;
import com.pdwy.dus.collection.utils.SharePreferencesUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.provider.CallLog.Calls.TYPE;

/**
 * ok上传数据
 * Author： by MR on 2018/12/27.
 */

public class UploadDataHttp {
    //通过构造函数来获取
    private  Gson gson;
    private InputData inputData;
    private Handler handler;
    private UploadBean uploadBean;
    private Activity activity;
    private ArrayList<com.pdwy.dus.collection.model.bean.CharacterBean> mListData;
    private ArrayList<com.pdwy.dus.collection.model.bean.CharacterBean> list;
    private OkHttpClient okHttpClient;
    public UploadDataHttp(Context context,Handler handler){
        activity=(Activity)context;
        inputData=new InputData(context);
        list=new ArrayList<>();
        gson = new Gson();
        uploadBean=new UploadBean();
        this.handler=handler;

        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(120,TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .build();
        okHttpClient.dispatcher().setMaxRequestsPerHost(1);
    }

    //上传图片
    public void uploadPhotoData(PictureBean pictureBean, String photoHttp, final int p){



//        uploadBean.setUserId(Integer.valueOf(SharePreferencesUtils.getString("USER_ID","")));
        uploadBean.setGroupId(Integer.valueOf(inputData.getCharacterGroupId(pictureBean.experimentalNumber)));
        uploadBean.setVarietyId(Integer.valueOf(inputData.getCharacterVarietyId(pictureBean.testNumber)));
        UploadBean.AbnormalDataManipulationBean abnormalDataManipulationBean= new UploadBean.AbnormalDataManipulationBean();
        List<UploadBean.AbnormalDataManipulationBean.AddBean> listAddBean=new ArrayList<>();
        UploadBean.AbnormalDataManipulationBean.AddBean addBean=new UploadBean.AbnormalDataManipulationBean.AddBean();

        addBean.setCharacterstdcode(inputData.getCharacterId(pictureBean.characterName));
        addBean.setImgurl(photoHttp);
        listAddBean.add(addBean);
        abnormalDataManipulationBean.setAdd(listAddBean);
        uploadBean.setAbnormalDataManipulation(abnormalDataManipulationBean);
        String uploadString =gson.toJson(uploadBean);
        MLog.e(uploadString);




        //MediaType  设置Content-Type 标头中包含的媒体类型值
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8")
                , uploadString);

        Request request = new Request.Builder()
                .url(SharePreferencesUtils.getString("dizi",Api.URL_CS)+Api.SC)//请求的url
                .post(requestBody)
                .addHeader("accept","application/json;charset=UTF-8")
                .addHeader("Content-Type","application/x-www-form-urlencoded; charset=utf-8")
                .addHeader("appLoginId",SharePreferencesUtils.getString("USER_ID",""))
                .build();

        //创建/Call
        Call call = okHttpClient.newCall(request);
        //加入队列 异步操作
        call.enqueue(new Callback() {
            //请求错误回调方法
            @Override
            public void onFailure(Call call, IOException e) {
                MLog.e("网络连接错误");
                Message msg=new Message();
                msg.what=0;
                msg.obj="网络连接错误";
                handler.sendMessage(msg);

            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responses=response.body().string();
                MLog.e(responses);
                ReturnBean returnBean=gson.fromJson(responses, ReturnBean.class);
                if("成功".equals(returnBean.getMsg())){
                Message msg = new Message();
                msg.what = 2;
                msg.arg1=p;
                msg.obj=returnBean.getMsg();
                handler.sendMessage(msg);}
                else {
                    MLog.e("网络连接错误");
                    Message msg=new Message();
                    msg.what=0;
                    msg.obj="网络连接错误";
                    handler.sendMessage(msg);
                }
            }
        });
    }

    //上传数据
    public void uploadData(int groupId, int varietyId ){
        mListData= inputData.getCharacterBeanList(String.valueOf(groupId),String.valueOf(varietyId));

//        uploadBean.setUserId(Integer.valueOf(SharePreferencesUtils.getString("USER_ID","")));
        uploadBean.setGroupId(groupId);
        uploadBean.setVarietyId(varietyId);

        MLog.e(mListData.size()+"=");
        //个体
        UploadBean.IndividualDataManipulationBean individualDataManipulationBean=new UploadBean.IndividualDataManipulationBean();
        List<UploadBean.IndividualDataManipulationBean.AddBeanXXXX> listgt = new ArrayList<>();
        //群体
        UploadBean.GroupDataManipulationBean groupDataManipulationBean=new UploadBean.GroupDataManipulationBean();
        List<UploadBean.GroupDataManipulationBean.AddBeanXXX> listqt = new ArrayList<>();

        //个体异常
        UploadBean.AbnormalIndividualDataManipulationBean abnormalIndividualDataManipulationBean=new UploadBean.AbnormalIndividualDataManipulationBean();
        List<UploadBean.AbnormalIndividualDataManipulationBean.AddBeanXX> listgty = new ArrayList<>();


//群体异常
        UploadBean.AbnormalGroupDataManipulationBean abnormalGroupDataManipulationBean = new UploadBean.AbnormalGroupDataManipulationBean();

        List<UploadBean.AbnormalGroupDataManipulationBean.AddBeanX> listqty = new ArrayList<>();

        for(int m=0;m<mListData.size();m++) {
            com.pdwy.dus.collection.model.bean.CharacterBean characterBean=mListData.get(m);

            //个体性状
           if("MS".equals(characterBean.observationMethod)||"VS".equals(characterBean.observationMethod)) {
               //正常
                  String[] duplicateContent11 = characterBean.duplicateContent1.split(",");
               for (int i = 0; i < duplicateContent11.length-2; i++) {
                   UploadBean.IndividualDataManipulationBean.AddBeanXXXX addBeanXXXX = new UploadBean.IndividualDataManipulationBean.AddBeanXXXX();
                   addBeanXXXX.setCharacterstdcode(characterBean.characterId);
                   addBeanXXXX.setColumncode(i+1);
                   if("-".equals(duplicateContent11[i])){

                   }
                   else {
                       if(!"".equals(duplicateContent11[i])&&!"-".equals(duplicateContent11[i])) {
                           if (i == 0)
                               addBeanXXXX.setVal(duplicateContent11[i]);
                           else
                               addBeanXXXX.setVal(duplicateContent11[i].substring(1, duplicateContent11[i].length()));
                       }

                   }
                   listgt.add(addBeanXXXX);
               }

               individualDataManipulationBean.setAdd(listgt);
               uploadBean.setIndividualDataManipulation(individualDataManipulationBean);


               //异常
               if (characterBean.abnormalContent != null &&! "".equals(characterBean.abnormalContent)) {

                   String[] duplicateContent12 = characterBean.abnormalContent.split(",");
                   for (int i = 0; i < duplicateContent12.length-2; i++) {
                       UploadBean.AbnormalIndividualDataManipulationBean.AddBeanXX addBeanXX = new UploadBean.AbnormalIndividualDataManipulationBean.AddBeanXX();
                       addBeanXX.setCharacterstdcode(characterBean.characterId);
                       addBeanXX.setColumncode(i+1);
                       if("-".equals(duplicateContent12[i])||"".equals(duplicateContent12[i])){

                       }
                       else{
                           if(i==0)
                           addBeanXX.setVal(duplicateContent12[i]);
                           else
                           addBeanXX.setVal(duplicateContent12[i].substring(1));
                       }

                       listgty.add(addBeanXX);
                   }
                   abnormalIndividualDataManipulationBean.setAdd(listgty);
                   uploadBean.setAbnormalIndividualDataManipulation(abnormalIndividualDataManipulationBean);
               }

           }else  if("MG".equals(characterBean.observationMethod)||"VG".equals(characterBean.observationMethod)) {


                   UploadBean.GroupDataManipulationBean.AddBeanXXX addBeanXXX = new UploadBean.GroupDataManipulationBean.AddBeanXXX();
                   addBeanXXX.setCharacterstdcode(characterBean.characterId);
                   if(!"".equals(characterBean.duplicateContent1)) {
                       if("异常".equals(characterBean.duplicateContent1)){


                       }
                       else {

                           String duplicateContent1[] = characterBean.duplicateContent1.split(":");
                           addBeanXXX.setShowstatecode(duplicateContent1[0]);
                       }
                   }
               listqt.add(addBeanXXX);

               groupDataManipulationBean.setAdd(listqt);
               uploadBean.setGroupDataManipulation(groupDataManipulationBean);


               // 异常
               if (characterBean.abnormalContent != null && !"".equals(characterBean.abnormalContent)) {
                   MLog.e(characterBean.abnormalContent);
                     String[] duplicateContent12 = characterBean.abnormalContent.split(";");

                   for (int i = 0; i < duplicateContent12.length-3; i++) {
                       UploadBean.AbnormalGroupDataManipulationBean.AddBeanX addBeanX = new UploadBean.AbnormalGroupDataManipulationBean.AddBeanX();
                       addBeanX.setCharacterstdcode(characterBean.characterId);
                       addBeanX.setColumncode(i+1);
                       String[]  duplicateContent13= duplicateContent12[i].split(",");
                       if(i==0) {

                           String s[]=duplicateContent13[0].split(":");
                           addBeanX.setShowstatecode(s[0]);
                           if(duplicateContent13[1].length()>1)
                           addBeanX.setPlantnum(duplicateContent13[1].substring(1));
                       }
                       else
                       {
                           String s[]=duplicateContent13[0].substring(1).split(":");
                           addBeanX.setShowstatecode(s[0]);
                           if(duplicateContent13[1].length()>1)
                           addBeanX.setPlantnum(duplicateContent13[1].substring(1));
                       }
                       listqty.add(addBeanX);
                   }




                   abnormalGroupDataManipulationBean.setAdd(listqty);
                   uploadBean.setAbnormalGroupDataManipulation(abnormalGroupDataManipulationBean);
               }
           }


        }

          String uploadString =gson.toJson(uploadBean);
            MLog.e(uploadString);




        //MediaType  设置Content-Type 标头中包含的媒体类型值
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8")
                , uploadString);

        Request request = new Request.Builder()
                .url(SharePreferencesUtils.getString("dizi",Api.URL_CS)+Api.SC)//请求的url
                .post(requestBody)
                .addHeader("accept","application/json;charset=UTF-8")
                .addHeader("Content-Type","application/x-www-form-urlencoded; charset=utf-8")
                .addHeader("appLoginId",SharePreferencesUtils.getString("USER_ID",""))

                .build();

        //创建/Call
        Call call = okHttpClient.newCall(request);
        //加入队列 异步操作
        call.enqueue(new Callback() {
            //请求错误回调方法
            @Override
            public void onFailure(Call call, IOException e) {
                MLog.e("网络连接错误");
                Message msg=new Message();
                msg.what=0;
                msg.obj="网络连接错误";
                handler.sendMessage(msg);

            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responses=response.body().string();
                MLog.e(responses);
                ReturnBean returnBean=gson.fromJson(responses, ReturnBean.class);
                    Message msg = new Message();
                    msg.what = 2;
                    msg.obj=returnBean.getMsg();
                    handler.sendMessage(msg);
            }
        });
//        ---------------------
//                作者：Programmer_knight
//        来源：CSDN
//        原文：https://blog.csdn.net/knight1996/article/details/77995547
//        版权声明：本文为博主原创文章，转载请附上博文链接！
    }

    //上传图片到图片服务器
    public boolean uploadPhoto(List<PictureBean>  listPictureBean, PhotoHttpBean photoHttp, final int p){

        MultipartBody.Builder mbody=new MultipartBody.Builder().setType(MultipartBody.FORM);

        List<File> fileList=new ArrayList<File>();

        for(PictureBean pictureBean:listPictureBean) {
            String path = Environment.getExternalStorageDirectory().getPath() + "/DUS/" + pictureBean.pictureName + ("0".equals(pictureBean.stateOfExpression) ? "" : "（异常）") + ".jpg";
            MLog.e("SD卡图片地址："+path);
            File img1 = new File(path);
            fileList.add(img1);
        }
        for(File file:fileList){
            if(file.exists()){
                mbody.addFormDataPart("files",file.getName(),RequestBody.create(MediaType.parse("image/png"),file));
                continue;
            }else {
                MLog.e("图片：" + file.getPath() + "不存在");
                Message msg = new Message();
                msg.what = 0;
                msg.obj="图片：" + file.getPath() + "不存在";
                handler.sendMessage(msg);
              return false;
            }
        }

        RequestBody requestBody =mbody.build();
        Request request = new Request.Builder()
                .header("Authorization", "Client-ID " + "...")
                .url(photoHttp.getData()+"/api/"+photoHttp.getSystemCode()+"/file/upload")
                .post(requestBody)
                .addHeader("accept","application/json;charset=UTF-8")
                .addHeader("Content-Type","application/x-www-form-urlencoded; charset=utf-8")
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message msg = new Message();
                msg.what = 0;
                msg.obj="上传失败";
                handler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
               String response1= response.body().string();
                MLog.e(response1);

                PhotoHttp2Bean photoHttp2Bean= gson.fromJson(response1,PhotoHttp2Bean.class);
                if("0".equals(photoHttp2Bean.getCode())) {
                    Message msg = new Message();
                    msg.what = 3;
                    msg.arg1=p;
                    msg.obj = photoHttp2Bean.getData().getPath();
                    handler.sendMessage(msg);
                }else
                {  Message msg = new Message();
                    msg.what = 0;
                    msg.obj="上传失败";
                    handler.sendMessage(msg);

                }
            }
        });

        return true;
    }

    public boolean uploadPhoto1(PictureBean pictureBean,PhotoHttpBean photoHttp){
        String path= Environment.getExternalStorageDirectory().getPath()+"/DUS/"+pictureBean.pictureName+("0".equals(pictureBean.stateOfExpression)?"":"（异常）")+".jpg";
        File file=new File(path);
//        File file = new File(Environment.getExternalStorageDirectory(), "dd.mp4");
        if (!file.exists()) {
            Toast.makeText(activity, "文件不存在", Toast.LENGTH_SHORT).show();
        } else {
            RequestBody fileBody = RequestBody.create(MediaType.parse(TYPE), file);
            RequestBody requestBody = new MultipartBody.Builder().setType(MediaType.parse("multipart/form-data;charset=utf-8")).addFormDataPart("files", file.getName(), fileBody).build();

            Request requestPostFile = new Request.Builder()
                    .url(photoHttp.getData()+"/api/"+photoHttp.getSystemCode()+"/file/upload")
                    .post(requestBody)
                    .build();






            okHttpClient.newCall(requestPostFile).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    String response1= response.body().string();
                    MLog.e(response1);

                    PhotoHttp2Bean photoHttp2Bean= gson.fromJson(response1,PhotoHttp2Bean.class);
//                if("成功".equals(photoHttp2Bean.getMsg())) {
                    Message msg = new Message();
                    msg.what = 3;
                    msg.obj = photoHttp2Bean.getData().getPath();
                    handler.sendMessage(msg);
//                }else
//                {  Message msg = new Message();
//                    msg.what = 0;
//                    msg.obj="上传失败";
//                    handler.sendMessage(msg);
//
//                }



//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            tvShow.setText(response.toString());
//                        }
//                    });
                }
            });
        }

        return true;
    }
}
