package com.pdwy.dus.collection.model.bean;

/**
 * 性状
 * Author： by MR on 2018/8/23.
 */

public class CharacterBean {
    //性状id
    public String characterId;
    //性状名称
    public String characterName;
    //所属品种
    public String varieties;
    //所属实验编号(分组id)
    public int groupId;
    //所属任务id
    public int varietyId;
    //所属实验编号
    public String experimentalNumber;
    //所属模板
    public String template;
    //所属测试编号
    public String testNumber;
    //所属生育期
    public String growthPeriod;

    //所属生育期范围
    public String growthPeriodTime;

    //观测方法
    public String observationMethod;
    //填写状态(0.未完成  1.保存  2.已上传)
    public String fillInTheState;
    //观测数量
    public String observationalQuantity;
    //数据单位
    public String dataUnit;
    //重复内容1
    public String duplicateContent1;
    //重复内容2
    public String duplicateContent2;
    //重复内容3
    public String duplicateContent3;
    //性状数值范围(阈值管理)
    public String numericalRangeOfCharacters;
    //正常性状表达的图片路径
    public String normalPicture;
    //异常性状表达的图片路径
    public String abnormalPicture;
    //表达状态（代码描述）(性状的表现形式)
    public String codeDescription;
    //代码值
    public String codeValue;
    //是否含有异常(0.正常  1异常)
    public String abnormal;
    //异常内容
    public String abnormalContent;
    //拍摄图片名称
    public String pictureName;

    //上一周期该任务的id
    public String beforeCycleId;
}
