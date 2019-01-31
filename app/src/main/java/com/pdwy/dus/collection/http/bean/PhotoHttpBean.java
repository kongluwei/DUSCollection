package com.pdwy.dus.collection.http.bean;

/**
 * 获取图片服务器地址
 * Author： by MR on 2019/1/10.
 */

public class PhotoHttpBean {

    /**
     * data : http://192.168.3.127:8080/fileServer
     * systemCode : tester
     * code : 0
     * msg : 成功
     */

    private String data;
    private String systemCode;
    private String code;
    private String msg;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

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
}
