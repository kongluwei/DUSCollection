package com.pdwy.dus.collection.http.bean;

/**
 * author : KongLW
 * e-mail : kongluweishujia@163.com
 * date   : 2019/3/2517:16
 * desc   :  登陆提交
 */
public class LogInBeanEnter {


    /**
     * 账号
     */

    private String account;
    /**
     * 密码
     */
    private String passwd;
    /**
     * pad型号
     */
    private String model;
    /**
     * 客户端设备信息标识 IMEI 与 MAC等等   拼起来之后MD5
     */
    private String modelSign;
    /**
     * 授权APP类型：1协同任务APP，2 数据采集APP
     */
    private String appType;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getModelSign() {
        return modelSign;
    }

    public void setModelSign(String modelSign) {
        this.modelSign = modelSign;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }
}
