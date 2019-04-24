package com.pdwy.dus.collection.http.bean;

import java.util.List;

/**
 * 获取任务的bean
 * Author： by MR on 2018/12/21.
 */

public class TaskBean {


    /**
     * data : [{"id":2182,"varietyType":1,"beforeCycleId":0,"testCode":"20192000152A","blockCode":"AA1A6"},{"id":2183,"varietyType":2,"beforeCycleId":0,"testCode":"20192000152B","blockCode":"AA5A6","fallBlockCode":"20192000152B111AA5A6"},{"id":2184,"varietyType":1,"beforeCycleId":0,"testCode":"20195000110A"},{"id":2185,"varietyType":2,"beforeCycleId":0,"testCode":"20195000110B"}]
     * code : 0
     * msg : 成功
     */

    private String code;
    private String msg;
    private List<DataBean> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 2182
         * varietyType : 1
         * beforeCycleId : 0
         * testCode : 20192000152A
         * blockCode : AA1A6
         * fallBlockCode : 20192000152B111AA5A6
         */

        private int id;
        private int varietyType;
        private int beforeCycleId;
        private String testCode;
        private String blockCode;
        private String fallBlockCode;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getVarietyType() {
            return varietyType;
        }

        public void setVarietyType(int varietyType) {
            this.varietyType = varietyType;
        }

        public int getBeforeCycleId() {
            return beforeCycleId;
        }

        public void setBeforeCycleId(int beforeCycleId) {
            this.beforeCycleId = beforeCycleId;
        }

        public String getTestCode() {
            return testCode;
        }

        public void setTestCode(String testCode) {
            this.testCode = testCode;
        }

        public String getBlockCode() {
            return blockCode;
        }

        public void setBlockCode(String blockCode) {
            this.blockCode = blockCode;
        }

        public String getFallBlockCode() {
            return fallBlockCode;
        }

        public void setFallBlockCode(String fallBlockCode) {
            this.fallBlockCode = fallBlockCode;
        }
    }
}
