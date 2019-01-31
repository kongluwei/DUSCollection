package com.pdwy.dus.collection.http.bean;

import java.util.List;

/**
 * 获取任务的bean
 * Author： by MR on 2018/12/21.
 */

public class TaskBean {

    /**
     * data : [{"id":98515,"testCode":"20182000265A","blockCode":"BA1A1"},{"id":99759,"testCode":"20182000265B","blockCode":"BA2A1"},{"id":99760,"testCode":"20182000265C","blockCode":"BA3A1"}]
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
         * id : 98515
         * testCode : 20182000265A
         * blockCode : BA1A1
         */

        private int id;
        private String testCode;
        private String blockCode;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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
    }
}
