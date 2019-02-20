package com.pdwy.dus.collection.model.bean;

/**
 * author : KongLW
 * e-mail : kongluweishujia@163.com
 * date   : 2019/2/2016:10
 * desc   :采集模板性状的list
 */
public class CharacterlistBean {

    /**
     * observation : VG
     * StdCharCode : yumi001
     * name : 幼苗:第一叶鞘花青甙显色强度
     * serial : 1
     */

    private String observation;
    private String StdCharCode;
    private String name;
    private int serial;

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getStdCharCode() {
        return StdCharCode;
    }

    public void setStdCharCode(String StdCharCode) {
        this.StdCharCode = StdCharCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSerial() {
        return serial;
    }

    public void setSerial(int serial) {
        this.serial = serial;
    }
}
