package com.pdwy.dus.collection.http.bean;

/**
 * 获取 分组采集模板 返回
 * Author： by MR on 2019/1/21.
 */

public class TemplateBean {


    /**
     * updatetime : 2019-01-21 16:17:48
     * characterlist : [{"observation":"VG","StdCharCode":"shuidao003","name":"基部叶:叶鞘颜色","serial":1},{"observation":"VG","StdCharCode":"shuidao042","name":"植株:生长习性","serial":2},{"observation":"VG","StdCharCode":"shuidao006","name":"倒二叶:叶片绿色程度","serial":3},{"observation":"VG","StdCharCode":"shuidao007","name":"倒二叶:叶片花青甙显色","serial":4},{"observation":"VG","StdCharCode":"shuidao011","name":"倒二叶:姿态","serial":5},{"observation":"VG","StdCharCode":"shuidao012","name":"倒二叶:叶片茸毛密度","serial":6},{"observation":"VG","StdCharCode":"shuidao013","name":"倒二叶:叶耳花青甙显色","serial":7},{"observation":"VG","StdCharCode":"shuidao016","name":"倒二叶:叶舌长度","serial":8},{"observation":"VG","StdCharCode":"shuidao097","name":"倒二叶:叶舌形状","serial":9},{"observation":"MG","StdCharCode":"shuidao020","name":"穗:抽穗期","serial":10},{"observation":"VG","StdCharCode":"shuidao021","name":"剑叶:姿态（初期）","serial":11},{"observation":"VG","StdCharCode":"shuidao022","name":"穗:芒","serial":12},{"observation":"VG","StdCharCode":"shuidao023","name":"仅适用于有芒的品种:穗:芒颜色（初期）","serial":13},{"observation":"VG","StdCharCode":"shuidao024","name":"剑叶:叶片卷曲类型","serial":14},{"observation":"VG","StdCharCode":"shuidao037","name":"小穗:外颖颖尖花青甙显色强度（初期）","serial":18},{"observation":"VG","StdCharCode":"shuidao033","name":"小穗:柱头颜色","serial":19},{"observation":"MS","StdCharCode":"shuidao043","name":"植株:单株穗数","serial":21},{"observation":"MS","StdCharCode":"shuidao041","name":"茎秆:直径","serial":22},{"observation":"VG","StdCharCode":"shuidao044","name":"茎秆:基部茎节包露","serial":24},{"observation":"VG","StdCharCode":"shuidao045","name":"茎秆:节花青甙显色","serial":25},{"observation":"VG","StdCharCode":"shuidao061","name":"小穗:外颖茸毛密度","serial":28},{"observation":"MS","StdCharCode":"shuidao050","name":"剑叶:叶片长度","serial":29},{"observation":"MS","StdCharCode":"shuidao051","name":"剑叶:叶片宽度","serial":30},{"observation":"VG","StdCharCode":"shuidao052","name":"剑叶:姿态（后期）","serial":31},{"observation":"VG","StdCharCode":"shuidao059","name":"穗:姿态","serial":33},{"observation":"VG","StdCharCode":"shuidao058","name":"穗:二次枝梗类型","serial":34},{"observation":"VG","StdCharCode":"shuidao056","name":"穗:分枝姿态","serial":35},{"observation":"VG","StdCharCode":"shuidao055","name":"穗:抽出度","serial":36},{"observation":"MS","StdCharCode":"shuidao054","name":"穗:长度","serial":37},{"observation":"MS","StdCharCode":"shuidao074","name":"穗:每穗粒数","serial":38},{"observation":"MS","StdCharCode":"shuidao075","name":"穗:结实率","serial":39},{"observation":"VG","StdCharCode":"shuidao098","name":"小穗:外颖颖尖花青甙显色强度（后期）","serial":40},{"observation":"VG","StdCharCode":"shuidao077","name":"小穗:护颖长度","serial":41},{"observation":"VG","StdCharCode":"shuidao070","name":"谷粒:外颖颜色","serial":42},{"observation":"VG","StdCharCode":"shuidao071","name":"谷粒:外颖修饰色","serial":43},{"observation":"MS","StdCharCode":"shuidao082","name":"谷粒:千粒重","serial":44},{"observation":"MS","StdCharCode":"shuidao079","name":"谷粒:长度","serial":45},{"observation":"MS","StdCharC:"糙米:颜色","serial":51},{"observation":"VG","StdCharCode":"shuidao092","name":"糙米:香味","serial":52},{"observation":"VG","StdCharCode":"shuidao083","name":"谷粒:外颖苯酚反应","serial":53},{"observation":"VG","StdCharCode":"shuidao084","name":"谷粒:外颖苯酚反应强度","serial":54},{"observation":"VG","StdCharCode":"shuidao089","name":"精米:胚乳类型","serial":55},{"observation":"MG","StdCharCode":"shuidao090","name":"精米:直链淀粉含量","serial":56},{"observation":"VG","StdCharCode":"shuidao091","name":"精米:碱消值","serial":57},{"observation":"VG","StdCharCode":"shuidao093","name":"抗性:白叶枯病","serial":58}]
     * remark : null
     * subcentergroupId : 3
     * cropId : 1
     * code : 0
     * characternum : 50
     * msg : 成功
     * varietybaseId : null
     * reproductiontype : 常规种
     * id : 2
     * collectiontemplatename : 2019年水稻1
     * describe :
     * creattime : 2019-01-21 16:17:48
     * guideedition : 水稻2012
     * samplenum : null
     */

    private String updatetime;
    private String characterlist;
    private Object remark;
    private String subcentergroupId;
    private int cropId;
    private String code;
    private int characternum;
    private String msg;
    private Object varietybaseId;
    private String reproductiontype;
    private int id;
    private String collectiontemplatename;
    private String describe;
    private String creattime;
    private String guideedition;
    private Object samplenum;

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public String getCharacterlist() {
        return characterlist;
    }

    public void setCharacterlist(String characterlist) {
        this.characterlist = characterlist;
    }

    public Object getRemark() {
        return remark;
    }

    public void setRemark(Object remark) {
        this.remark = remark;
    }

    public String getSubcentergroupId() {
        return subcentergroupId;
    }

    public void setSubcentergroupId(String subcentergroupId) {
        this.subcentergroupId = subcentergroupId;
    }

    public int getCropId() {
        return cropId;
    }

    public void setCropId(int cropId) {
        this.cropId = cropId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getCharacternum() {
        return characternum;
    }

    public void setCharacternum(int characternum) {
        this.characternum = characternum;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getVarietybaseId() {
        return varietybaseId;
    }

    public void setVarietybaseId(Object varietybaseId) {
        this.varietybaseId = varietybaseId;
    }

    public String getReproductiontype() {
        return reproductiontype;
    }

    public void setReproductiontype(String reproductiontype) {
        this.reproductiontype = reproductiontype;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCollectiontemplatename() {
        return collectiontemplatename;
    }

    public void setCollectiontemplatename(String collectiontemplatename) {
        this.collectiontemplatename = collectiontemplatename;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getCreattime() {
        return creattime;
    }

    public void setCreattime(String creattime) {
        this.creattime = creattime;
    }

    public String getGuideedition() {
        return guideedition;
    }

    public void setGuideedition(String guideedition) {
        this.guideedition = guideedition;
    }

    public Object getSamplenum() {
        return samplenum;
    }

    public void setSamplenum(Object samplenum) {
        this.samplenum = samplenum;
    }
}
