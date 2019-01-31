package com.pdwy.dus.collection.model.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 包
 * Author： by MR on 2018/7/26.
 */

public class CollectionTaskItemBean {
    //测试名称
    public String taskName;
    //测试品种数
    public int collectionOfVarieties;
    //未完成品种数
    public int collectionOfVarietiesHangInTheAir;
    //测试编号列表
    public ArrayList<CollectionTaskItemListBean> list;
}
