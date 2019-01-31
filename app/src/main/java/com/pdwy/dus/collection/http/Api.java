package com.pdwy.dus.collection.http;

import android.app.DownloadManager;

import com.pdwy.dus.collection.utils.SharePreferencesUtils;

/**
 * Author： by MR on 2018/12/21.
 */

public class Api {

    //服务器地址
//    static String URL="http://192.168.3.114:8020/tester/api";

    //https://202.127.42.201  vpn地址   账号  pdwy28   密码  123qwe  安卓

//    static String URL="http://192.168.20.20:8080/tester/api";

//    static String URL="http://192.168.3.127:8080/tester/api";


    static String URL_CS="http://192.168.3.127:8080/tester";

    static String URL="/api";


    //登陆
    static String DL=URL+"/appLogin";

    //获取测试员所拥有的未完成的分组
    static String HQCSYSYYDWWCDFZ=URL+"/appGetTesterGroup";

    //获取分组下的任务
    static String HQFZXDRW=URL+"/appGetVarietyData";

    //获取分组下模板
    static String HQFZXMB=URL+"/appGetCollectionTemplateData";

    //获取生育周期
    static String HQSYZQ=URL+"/appGetBirthCycleTemplateData";

    //获取分组对应的性状数据
    static String HQFZDYXZSJ=URL+"/appGetCharacterData";
    //获取图片服务器地址
    static String HQTPFWQDZ=URL+"/appGetFileServiceIp";
    //上传
    static String SC=URL+"/appUpdateVarietyCharacterVal";
}
