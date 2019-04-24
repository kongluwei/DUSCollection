package com.pdwy.dus.collection.http.bean;

import java.util.List;

/**
 * author : KongLW
 * e-mail : kongluweishujia@163.com
 * date   : 2019/4/2410:15
 * desc   :
 */
public class BeforeCycleBeanOut {

    /**
     * data : {"abnormalGroups":[{"characterstdcode":"yumi001","columncode":1,"plantnum":2,"showstatecode":"1"}],"abnormalIndividuals":[{"characterstdcode":"yumi014","columncode":2,"val":26}],"abnormals":[{"characterstdcode":"yumi001","imgurl":"","remark":"异型株群体备注1"}],"groups":[{"characterstdcode":"yumi001","showstatecode":"4"}],"individuals":[{"characterstdcode":"yumi014","columncode":2,"val":22}]}
     * code : 0
     * msg : 成功
     */

    private DataBean data;
    private String code;
    private String msg;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
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

    public static class DataBean {
        private List<AbnormalGroupsBean> abnormalGroups;
        private List<AbnormalIndividualsBean> abnormalIndividuals;
        private List<AbnormalsBean> abnormals;
        private List<GroupsBean> groups;
        private List<IndividualsBean> individuals;

        public List<AbnormalGroupsBean> getAbnormalGroups() {
            return abnormalGroups;
        }

        public void setAbnormalGroups(List<AbnormalGroupsBean> abnormalGroups) {
            this.abnormalGroups = abnormalGroups;
        }

        public List<AbnormalIndividualsBean> getAbnormalIndividuals() {
            return abnormalIndividuals;
        }

        public void setAbnormalIndividuals(List<AbnormalIndividualsBean> abnormalIndividuals) {
            this.abnormalIndividuals = abnormalIndividuals;
        }

        public List<AbnormalsBean> getAbnormals() {
            return abnormals;
        }

        public void setAbnormals(List<AbnormalsBean> abnormals) {
            this.abnormals = abnormals;
        }

        public List<GroupsBean> getGroups() {
            return groups;
        }

        public void setGroups(List<GroupsBean> groups) {
            this.groups = groups;
        }

        public List<IndividualsBean> getIndividuals() {
            return individuals;
        }

        public void setIndividuals(List<IndividualsBean> individuals) {
            this.individuals = individuals;
        }

        public static class AbnormalGroupsBean {
            /**
             * characterstdcode : yumi001
             * columncode : 1
             * plantnum : 2
             * showstatecode : 1
             */

            private String characterstdcode;
            private int columncode;
            private int plantnum;
            private String showstatecode;

            public String getCharacterstdcode() {
                return characterstdcode;
            }

            public void setCharacterstdcode(String characterstdcode) {
                this.characterstdcode = characterstdcode;
            }

            public int getColumncode() {
                return columncode;
            }

            public void setColumncode(int columncode) {
                this.columncode = columncode;
            }

            public int getPlantnum() {
                return plantnum;
            }

            public void setPlantnum(int plantnum) {
                this.plantnum = plantnum;
            }

            public String getShowstatecode() {
                return showstatecode;
            }

            public void setShowstatecode(String showstatecode) {
                this.showstatecode = showstatecode;
            }
        }

        public static class AbnormalIndividualsBean {
            /**
             * characterstdcode : yumi014
             * columncode : 2
             * val : 26
             */

            private String characterstdcode;
            private int columncode;
            private int val;

            public String getCharacterstdcode() {
                return characterstdcode;
            }

            public void setCharacterstdcode(String characterstdcode) {
                this.characterstdcode = characterstdcode;
            }

            public int getColumncode() {
                return columncode;
            }

            public void setColumncode(int columncode) {
                this.columncode = columncode;
            }

            public int getVal() {
                return val;
            }

            public void setVal(int val) {
                this.val = val;
            }
        }

        public static class AbnormalsBean {
            /**
             * characterstdcode : yumi001
             * imgurl :
             * remark : 异型株群体备注1
             */

            private String characterstdcode;
            private String imgurl;
            private String remark;

            public String getCharacterstdcode() {
                return characterstdcode;
            }

            public void setCharacterstdcode(String characterstdcode) {
                this.characterstdcode = characterstdcode;
            }

            public String getImgurl() {
                return imgurl;
            }

            public void setImgurl(String imgurl) {
                this.imgurl = imgurl;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }
        }

        public static class GroupsBean {
            /**
             * characterstdcode : yumi001
             * showstatecode : 4
             */

            private String characterstdcode;
            private String showstatecode;

            public String getCharacterstdcode() {
                return characterstdcode;
            }

            public void setCharacterstdcode(String characterstdcode) {
                this.characterstdcode = characterstdcode;
            }

            public String getShowstatecode() {
                return showstatecode;
            }

            public void setShowstatecode(String showstatecode) {
                this.showstatecode = showstatecode;
            }
        }

        public static class IndividualsBean {
            /**
             * characterstdcode : yumi014
             * columncode : 2
             * val : 22
             */

            private String characterstdcode;
            private int columncode;
            private int val;

            public String getCharacterstdcode() {
                return characterstdcode;
            }

            public void setCharacterstdcode(String characterstdcode) {
                this.characterstdcode = characterstdcode;
            }

            public int getColumncode() {
                return columncode;
            }

            public void setColumncode(int columncode) {
                this.columncode = columncode;
            }

            public int getVal() {
                return val;
            }

            public void setVal(int val) {
                this.val = val;
            }
        }
    }
}
