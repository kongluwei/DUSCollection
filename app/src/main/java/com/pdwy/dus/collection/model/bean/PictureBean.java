package com.pdwy.dus.collection.model.bean;

/**
 * 图片
 * Author： by MR on 2018/8/23.
 */

public class PictureBean {
    //分组id（所属测试编号（田间编号））
    public String groupId;
    //任务id（所属测试编号（田间编号））
    public String varietyId;
    //性状id
    public String characterId;

    //拍摄图片名称
    public String pictureName;
    //所属测试编号
    public String testNumber;
    //所属实验编号
    public String experimentalNumber;
    //表达状态(0.正常  1异常)
    public String stateOfExpression;
    //所属品种
    public String varieties;
    //拍摄图片时间
    public String pictureTime;
    //性状名称
    public String characterName;
    //图片地址
    public String pictureAddress;
    //是否上传(0.未完成  1.保存  2.已上传)
    public String whetherOrNotToUpload;

}
