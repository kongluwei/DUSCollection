package com.pdwy.dus.collection.model.bean;

/**
 * 测试编号
 * Author： by MR on 2018/7/26.
 */

public class CollectionTaskItemListBean {
    //所属实验任务编号
    public String experimentalNumber;
    //测试编号
    public String testNumber;
    //需采集
    public int needToCollect;
    //已采集
    public int haveBeenCollected;
    //未采集
    public int notCollected;
    //以上传
    public int toUpload;
    //未上传
    public int notUploaded;
    //是否异常
    public String abnormal;
}
