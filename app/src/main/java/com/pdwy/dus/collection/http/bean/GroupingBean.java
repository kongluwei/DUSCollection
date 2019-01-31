package com.pdwy.dus.collection.http.bean;

import java.util.List;

/**
 * 获取分组
 * Author： by MR on 2018/12/21.
 */

public class GroupingBean {

    /**
     * 获取分组
     * data : [{"address":"阜阳","arrangedate":"2018-12-21 00:00:00","batch":0,"centermaterialId":1075,"centerttaskcode":"2018-5284-1-142-1-1","centerttaskcodename":"2018刘立龙水稻中1春播","creattime":"2018-12-21 09:48:36","cropName":"水稻","cropid":1,"enddate":"2018-12-22 00:00:00","examineId":5284,"examinerName":"刘立龙","guideeditioncode":"水稻2010","id":1056,"repeat":1,"reproductiontype":"中","samplenum":900,"sowingDateName":"春播","sowingdate":"1","specialcondition":"","state":"3001","stateName":"未完成","testCycleName":"第一周期","testSiteName":"阜阳","testdesign":"设计内容","testsite":38,"testyear":"1","updatetime":"2018-12-21 09:52:01","varietycount":1,"year":"2018"}]
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
         * address : 阜阳
         * arrangedate : 2018-12-21 00:00:00
         * batch : 0
         * centermaterialId : 1075
         * centerttaskcode : 2018-5284-1-142-1-1
         * centerttaskcodename : 2018刘立龙水稻中1春播
         * creattime : 2018-12-21 09:48:36
         * cropName : 水稻
         * cropid : 1
         * enddate : 2018-12-22 00:00:00
         * examineId : 5284
         * examinerName : 刘立龙
         * guideeditioncode : 水稻2010
         * id : 1056
         * repeat : 1
         * reproductiontype : 中
         * samplenum : 900
         * sowingDateName : 春播
         * sowingdate : 1
         * specialcondition :
         * state : 3001
         * stateName : 未完成
         * testCycleName : 第一周期
         * testSiteName : 阜阳
         * testdesign : 设计内容
         * testsite : 38
         * testyear : 1
         * updatetime : 2018-12-21 09:52:01
         * varietycount : 1
         * year : 2018
         */

        private String address;
        private String arrangedate;
        private int batch;
        private int centermaterialId;
        private String centerttaskcode;
        private String centerttaskcodename;
        private String creattime;
        private String cropName;
        private int cropid;
        private String enddate;
        private int examineId;
        private String examinerName;
        private String guideeditioncode;
        private int id;
        private int repeat;
        private String reproductiontype;
        private int samplenum;
        private String sowingDateName;
        private String sowingdate;
        private String specialcondition;
        private String state;
        private String stateName;
        private String testCycleName;
        private String testSiteName;
        private String testdesign;
        private int testsite;
        private String testyear;
        private String updatetime;
        private int varietycount;
        private String year;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getArrangedate() {
            return arrangedate;
        }

        public void setArrangedate(String arrangedate) {
            this.arrangedate = arrangedate;
        }

        public int getBatch() {
            return batch;
        }

        public void setBatch(int batch) {
            this.batch = batch;
        }

        public int getCentermaterialId() {
            return centermaterialId;
        }

        public void setCentermaterialId(int centermaterialId) {
            this.centermaterialId = centermaterialId;
        }

        public String getCenterttaskcode() {
            return centerttaskcode;
        }

        public void setCenterttaskcode(String centerttaskcode) {
            this.centerttaskcode = centerttaskcode;
        }

        public String getCenterttaskcodename() {
            return centerttaskcodename;
        }

        public void setCenterttaskcodename(String centerttaskcodename) {
            this.centerttaskcodename = centerttaskcodename;
        }

        public String getCreattime() {
            return creattime;
        }

        public void setCreattime(String creattime) {
            this.creattime = creattime;
        }

        public String getCropName() {
            return cropName;
        }

        public void setCropName(String cropName) {
            this.cropName = cropName;
        }

        public int getCropid() {
            return cropid;
        }

        public void setCropid(int cropid) {
            this.cropid = cropid;
        }

        public String getEnddate() {
            return enddate;
        }

        public void setEnddate(String enddate) {
            this.enddate = enddate;
        }

        public int getExamineId() {
            return examineId;
        }

        public void setExamineId(int examineId) {
            this.examineId = examineId;
        }

        public String getExaminerName() {
            return examinerName;
        }

        public void setExaminerName(String examinerName) {
            this.examinerName = examinerName;
        }

        public String getGuideeditioncode() {
            return guideeditioncode;
        }

        public void setGuideeditioncode(String guideeditioncode) {
            this.guideeditioncode = guideeditioncode;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getRepeat() {
            return repeat;
        }

        public void setRepeat(int repeat) {
            this.repeat = repeat;
        }

        public String getReproductiontype() {
            return reproductiontype;
        }

        public void setReproductiontype(String reproductiontype) {
            this.reproductiontype = reproductiontype;
        }

        public int getSamplenum() {
            return samplenum;
        }

        public void setSamplenum(int samplenum) {
            this.samplenum = samplenum;
        }

        public String getSowingDateName() {
            return sowingDateName;
        }

        public void setSowingDateName(String sowingDateName) {
            this.sowingDateName = sowingDateName;
        }

        public String getSowingdate() {
            return sowingdate;
        }

        public void setSowingdate(String sowingdate) {
            this.sowingdate = sowingdate;
        }

        public String getSpecialcondition() {
            return specialcondition;
        }

        public void setSpecialcondition(String specialcondition) {
            this.specialcondition = specialcondition;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getStateName() {
            return stateName;
        }

        public void setStateName(String stateName) {
            this.stateName = stateName;
        }

        public String getTestCycleName() {
            return testCycleName;
        }

        public void setTestCycleName(String testCycleName) {
            this.testCycleName = testCycleName;
        }

        public String getTestSiteName() {
            return testSiteName;
        }

        public void setTestSiteName(String testSiteName) {
            this.testSiteName = testSiteName;
        }

        public String getTestdesign() {
            return testdesign;
        }

        public void setTestdesign(String testdesign) {
            this.testdesign = testdesign;
        }

        public int getTestsite() {
            return testsite;
        }

        public void setTestsite(int testsite) {
            this.testsite = testsite;
        }

        public String getTestyear() {
            return testyear;
        }

        public void setTestyear(String testyear) {
            this.testyear = testyear;
        }

        public String getUpdatetime() {
            return updatetime;
        }

        public void setUpdatetime(String updatetime) {
            this.updatetime = updatetime;
        }

        public int getVarietycount() {
            return varietycount;
        }

        public void setVarietycount(int varietycount) {
            this.varietycount = varietycount;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }
    }
}
