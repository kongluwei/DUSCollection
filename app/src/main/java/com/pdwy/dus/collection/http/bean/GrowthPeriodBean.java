package com.pdwy.dus.collection.http.bean;

import java.util.List;

/**
 *
 * 分组下生育期周期
 * Author： by MR on 2019/1/26.
 */

public class GrowthPeriodBean {

    /**
     * data : [{"id":98515,"subcentergroupId":"1062","serialnum":"00","remarks":"成熟期","name":"干种子","standardcode":"","starttime":"2019-01-08 00:00:00","endtime":"2018-12-01 00:00:00","remindtime":"2019-01-17 00:00:00","sortcharcode":"shuidao021,shuidao022,shuidao023"}]
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
         * subcentergroupId : 1062
         * serialnum : 00
         * remarks : 成熟期
         * name : 干种子
         * standardcode :
         * starttime : 2019-01-08 00:00:00
         * endtime : 2018-12-01 00:00:00
         * remindtime : 2019-01-17 00:00:00
         * sortcharcode : shuidao021,shuidao022,shuidao023
         */

        private int id;
        private String subcentergroupId;
        private String serialnum;
        private String remarks;
        private String name;
        private String standardcode;
        private String starttime;
        private String endtime;
        private String remindtime;
        private String sortcharcode;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getSubcentergroupId() {
            return subcentergroupId;
        }

        public void setSubcentergroupId(String subcentergroupId) {
            this.subcentergroupId = subcentergroupId;
        }

        public String getSerialnum() {
            return serialnum;
        }

        public void setSerialnum(String serialnum) {
            this.serialnum = serialnum;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getStandardcode() {
            return standardcode;
        }

        public void setStandardcode(String standardcode) {
            this.standardcode = standardcode;
        }

        public String getStarttime() {
            return starttime;
        }

        public void setStarttime(String starttime) {
            this.starttime = starttime;
        }

        public String getEndtime() {
            return endtime;
        }

        public void setEndtime(String endtime) {
            this.endtime = endtime;
        }

        public String getRemindtime() {
            return remindtime;
        }

        public void setRemindtime(String remindtime) {
            this.remindtime = remindtime;
        }

        public String getSortcharcode() {
            return sortcharcode;
        }

        public void setSortcharcode(String sortcharcode) {
            this.sortcharcode = sortcharcode;
        }
    }
}
