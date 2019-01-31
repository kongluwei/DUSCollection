package com.pdwy.dus.collection.http.bean;

/**
 * 数据返回json通用bean
 * Author： by MR on 2019/1/4.
 */

public class ReturnBean {

    /**
     * code : 0
     * msg : 成功
     */

    private String code;
    private String msg;

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
