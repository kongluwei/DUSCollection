package com.pdwy.dus.collection.http.bean;

/**
 * 登陆返回bean  code=0时，调用成功，msg为用户id; code不等于0时，发生错误，msg为错误信息
 * Author： by MR on 2019/1/16.
 */

public class LogInBeanOut {


    /**
     * code : 1000
     * msg : 成功
     * data : {"UserOfficeOrganization_id":"","DepartmentSign":"","appLoginSessionID":""}
     */

    /**
     *  状态码：1000调用成功 ，3000失败
     */
    private String code;
    /**
     *  提示信息
     */
    private String msg;
    /**
     *  data数据信息
     */
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * 测试机构表示id
       **/

        private String UserOfficeOrganization_id;
        /**
         * 部门标识（总中心50， 分中心60，保藏40）
         **/
        private String DepartmentSign;
        /**
         * 登录标识id
         **/
        private String appLoginSessionID;
        /**
         * 测试机构表示id
         **/
        public String getUserOfficeOrganization_id() {
            return UserOfficeOrganization_id;
        }
        /**
         * 测试机构表示id
         **/
        public void setUserOfficeOrganization_id(String UserOfficeOrganization_id) {
            this.UserOfficeOrganization_id = UserOfficeOrganization_id;
        }
        /**
         * 部门标识（总中心50， 分中心60，保藏40）
         **/
        public String getDepartmentSign() {
            return DepartmentSign;
        }
        /**
         * 部门标识（总中心50， 分中心60，保藏40）
         **/
        public void setDepartmentSign(String DepartmentSign) {
            this.DepartmentSign = DepartmentSign;
        }
        /**
         * 登录标识id
         **/
        public String getAppLoginSessionID() {
            return appLoginSessionID;
        }
        /**
         * 登录标识id
         **/
        public void setAppLoginSessionID(String appLoginSessionID) {
            this.appLoginSessionID = appLoginSessionID;
        }
    }
}
