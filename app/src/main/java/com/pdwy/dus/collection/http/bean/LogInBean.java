package com.pdwy.dus.collection.http.bean;

/**
 * 登陆返回bean  code=0时，调用成功，msg为用户id; code不等于0时，发生错误，msg为错误信息
 * Author： by MR on 2019/1/16.
 */

public class LogInBean {

    /**
     * code : 0
     * msg : 5284
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
