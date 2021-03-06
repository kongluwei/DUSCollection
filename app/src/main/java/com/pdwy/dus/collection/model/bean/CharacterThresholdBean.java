package com.pdwy.dus.collection.model.bean;

/**
 * 阈值
 * Author： by MR on 2018/8/24.
 */

public class CharacterThresholdBean {
    //性状ID
    public String characterId;
    //性状名称
    public String characterName;
    //所属品种
    public String varieties;
    //观测方式
    public String observationMethod;
    //所属模板
    public String template;
    //阈值
    public String numericalRangeOfCharacters;
    //是否异常
    public String abnormal;

    //对应关联性状的组合id
    public String relationId;
    //对应关联性状的组合名称
    public String relationName;

    //对应关联性状的组合包含的性状 （逗号隔开）
    public String characterNameList;
}
