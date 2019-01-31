package com.pdwy.dus.collection.http.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 上传JSON
 * Author： by MR on 2018/12/27.
 */

public class UploadBean {


    /**
     * userId : 321
     * groupId : 123
     * varietyId : 321
     * abnormalDataManipulation : {"deleted":[{"characterstdcode":"yumi001"}],"update":[{"characterstdcode":"yumi001","remark":2,"imgurl ":22}],"add":[{"characterstdcode":"yumi001","remark":2,"imgurl ":22}]}
     * abnormalGroupDataManipulation : {"deleted":[{"characterstdcode":"yumi001","columncode":2}],"update":[{"characterstdcode":"yumi001","columncode":2,"plantnum":2,"showstatecode":"1"}],"add":[{"characterstdcode":"yumi001","columncode":2,"plantnum":2,"showstatecode":"1"}]}
     * abnormalIndividualDataManipulation : {"deleted":[{"characterstdcode":"yumi001","columncode":2}],"update":[{"characterstdcode":"yumi001","columncode":2,"val":22}],"add":[{"characterstdcode":"yumi001","columncode":2,"val":22}]}
     * groupDataManipulation : {"deleted":[{"characterstdcode":"yumi001"}],"update":[{"characterstdcode":"yumi001","showstatecode":"4"}],"add":[{"characterstdcode":"yumi001","showstatecode":"4"}]}
     * individualDataManipulation : {"deleted":[{"characterstdcode":"yumi001","field":2}],"update":[{"characterstdcode":"yumi001","columncode":2,"val":22}],"add":[{"characterstdcode":"yumi001","columncode":2,"val":22}]}
     */

    private int userId;
    private int groupId;
    private int varietyId;
    private AbnormalDataManipulationBean abnormalDataManipulation;
    private AbnormalGroupDataManipulationBean abnormalGroupDataManipulation;
    private AbnormalIndividualDataManipulationBean abnormalIndividualDataManipulation;
    private GroupDataManipulationBean groupDataManipulation;
    private IndividualDataManipulationBean individualDataManipulation;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getVarietyId() {
        return varietyId;
    }

    public void setVarietyId(int varietyId) {
        this.varietyId = varietyId;
    }

    public AbnormalDataManipulationBean getAbnormalDataManipulation() {
        return abnormalDataManipulation;
    }

    public void setAbnormalDataManipulation(AbnormalDataManipulationBean abnormalDataManipulation) {
        this.abnormalDataManipulation = abnormalDataManipulation;
    }

    public AbnormalGroupDataManipulationBean getAbnormalGroupDataManipulation() {
        return abnormalGroupDataManipulation;
    }

    public void setAbnormalGroupDataManipulation(AbnormalGroupDataManipulationBean abnormalGroupDataManipulation) {
        this.abnormalGroupDataManipulation = abnormalGroupDataManipulation;
    }

    public AbnormalIndividualDataManipulationBean getAbnormalIndividualDataManipulation() {
        return abnormalIndividualDataManipulation;
    }

    public void setAbnormalIndividualDataManipulation(AbnormalIndividualDataManipulationBean abnormalIndividualDataManipulation) {
        this.abnormalIndividualDataManipulation = abnormalIndividualDataManipulation;
    }

    public GroupDataManipulationBean getGroupDataManipulation() {
        return groupDataManipulation;
    }

    public void setGroupDataManipulation(GroupDataManipulationBean groupDataManipulation) {
        this.groupDataManipulation = groupDataManipulation;
    }

    public IndividualDataManipulationBean getIndividualDataManipulation() {
        return individualDataManipulation;
    }

    public void setIndividualDataManipulation(IndividualDataManipulationBean individualDataManipulation) {
        this.individualDataManipulation = individualDataManipulation;
    }

    public static class AbnormalDataManipulationBean {
        private List<DeletedBean> deleted;
        private List<UpdateBean> update;
        private List<AddBean> add;

        public List<DeletedBean> getDeleted() {
            return deleted;
        }

        public void setDeleted(List<DeletedBean> deleted) {
            this.deleted = deleted;
        }

        public List<UpdateBean> getUpdate() {
            return update;
        }

        public void setUpdate(List<UpdateBean> update) {
            this.update = update;
        }

        public List<AddBean> getAdd() {
            return add;
        }

        public void setAdd(List<AddBean> add) {
            this.add = add;
        }

        public static class DeletedBean {
            /**
             * characterstdcode : yumi001
             */

            private String characterstdcode;

            public String getCharacterstdcode() {
                return characterstdcode;
            }

            public void setCharacterstdcode(String characterstdcode) {
                this.characterstdcode = characterstdcode;
            }
        }

        public static class UpdateBean {
            /**
             * characterstdcode : yumi001
             * remark : 2
             * imgurl  : 22
             */

            private String characterstdcode;
            private int remark;
            @SerializedName("imgurl ")
            private int _$Imgurl98;

            public String getCharacterstdcode() {
                return characterstdcode;
            }

            public void setCharacterstdcode(String characterstdcode) {
                this.characterstdcode = characterstdcode;
            }

            public int getRemark() {
                return remark;
            }

            public void setRemark(int remark) {
                this.remark = remark;
            }

            public int get_$Imgurl98() {
                return _$Imgurl98;
            }

            public void set_$Imgurl98(int _$Imgurl98) {
                this._$Imgurl98 = _$Imgurl98;
            }
        }

        public static class AddBean {
            /**
             * characterstdcode : yumi001
             * remark : 2
             * imgurl  : 22
             */

            private String characterstdcode;
            private int remark;
            private String imgurl;

            public String getCharacterstdcode() {
                return characterstdcode;
            }

            public void setCharacterstdcode(String characterstdcode) {
                this.characterstdcode = characterstdcode;
            }

            public int getRemark() {
                return remark;
            }

            public void setRemark(int remark) {
                this.remark = remark;
            }

            public String get_Imgurl() {
                return imgurl;
            }

            public void setImgurl(String imgurl) {
                this.imgurl = imgurl;
            }
        }
    }

    public static class AbnormalGroupDataManipulationBean {
        private List<DeletedBeanX> deleted;
        private List<UpdateBeanX> update;
        private List<AddBeanX> add;

        public List<DeletedBeanX> getDeleted() {
            return deleted;
        }

        public void setDeleted(List<DeletedBeanX> deleted) {
            this.deleted = deleted;
        }

        public List<UpdateBeanX> getUpdate() {
            return update;
        }

        public void setUpdate(List<UpdateBeanX> update) {
            this.update = update;
        }

        public List<AddBeanX> getAdd() {
            return add;
        }

        public void setAdd(List<AddBeanX> add) {
            this.add = add;
        }

        public static class DeletedBeanX {
            /**
             * characterstdcode : yumi001
             * columncode : 2
             */

            private String characterstdcode;
            private int columncode;

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
        }

        public static class UpdateBeanX {
            /**
             * characterstdcode : yumi001
             * columncode : 2
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

        public static class AddBeanX {
            /**
             * characterstdcode : yumi001
             * columncode : 2
             * plantnum : 2
             * showstatecode : 1
             */

            private String characterstdcode;
            private int columncode;
            private String plantnum;
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

            public String getPlantnum() {
                return plantnum;
            }

            public void setPlantnum(String plantnum) {
                this.plantnum = plantnum;
            }

            public String getShowstatecode() {
                return showstatecode;
            }

            public void setShowstatecode(String showstatecode) {
                this.showstatecode = showstatecode;
            }
        }
    }

    public static class AbnormalIndividualDataManipulationBean {
        private List<DeletedBeanXX> deleted;
        private List<UpdateBeanXX> update;
        private List<AddBeanXX> add;

        public List<DeletedBeanXX> getDeleted() {
            return deleted;
        }

        public void setDeleted(List<DeletedBeanXX> deleted) {
            this.deleted = deleted;
        }

        public List<UpdateBeanXX> getUpdate() {
            return update;
        }

        public void setUpdate(List<UpdateBeanXX> update) {
            this.update = update;
        }

        public List<AddBeanXX> getAdd() {
            return add;
        }

        public void setAdd(List<AddBeanXX> add) {
            this.add = add;
        }

        public static class DeletedBeanXX {
            /**
             * characterstdcode : yumi001
             * columncode : 2
             */

            private String characterstdcode;
            private int columncode;

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
        }

        public static class UpdateBeanXX {
            /**
             * characterstdcode : yumi001
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

        public static class AddBeanXX {
            /**
             * characterstdcode : yumi001
             * columncode : 2
             * val : 22
             */

            private String characterstdcode;
            private int columncode;
            private String val;

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

            public String getVal() {
                return val;
            }

            public void setVal(String val) {
                this.val = val;
            }
        }
    }

    public static class GroupDataManipulationBean {
        private List<DeletedBeanXXX> deleted;
        private List<UpdateBeanXXX> update;
        private List<AddBeanXXX> add;

        public List<DeletedBeanXXX> getDeleted() {
            return deleted;
        }

        public void setDeleted(List<DeletedBeanXXX> deleted) {
            this.deleted = deleted;
        }

        public List<UpdateBeanXXX> getUpdate() {
            return update;
        }

        public void setUpdate(List<UpdateBeanXXX> update) {
            this.update = update;
        }

        public List<AddBeanXXX> getAdd() {
            return add;
        }

        public void setAdd(List<AddBeanXXX> add) {
            this.add = add;
        }

        public static class DeletedBeanXXX {
            /**
             * characterstdcode : yumi001
             */

            private String characterstdcode;

            public String getCharacterstdcode() {
                return characterstdcode;
            }

            public void setCharacterstdcode(String characterstdcode) {
                this.characterstdcode = characterstdcode;
            }
        }

        public static class UpdateBeanXXX {
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

        public static class AddBeanXXX {
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
    }

    public static class IndividualDataManipulationBean {
        private List<DeletedBeanXXXX> deleted;
        private List<UpdateBeanXXXX> update;
        private List<AddBeanXXXX> add;

        public List<DeletedBeanXXXX> getDeleted() {
            return deleted;
        }

        public void setDeleted(List<DeletedBeanXXXX> deleted) {
            this.deleted = deleted;
        }

        public List<UpdateBeanXXXX> getUpdate() {
            return update;
        }

        public void setUpdate(List<UpdateBeanXXXX> update) {
            this.update = update;
        }

        public List<AddBeanXXXX> getAdd() {
            return add;
        }

        public void setAdd(List<AddBeanXXXX> add) {
            this.add = add;
        }

        public static class DeletedBeanXXXX {
            /**
             * characterstdcode : yumi001
             * field : 2
             */

            private String characterstdcode;
            private int field;

            public String getCharacterstdcode() {
                return characterstdcode;
            }

            public void setCharacterstdcode(String characterstdcode) {
                this.characterstdcode = characterstdcode;
            }

            public int getField() {
                return field;
            }

            public void setField(int field) {
                this.field = field;
            }
        }

        public static class UpdateBeanXXXX {
            /**
             * characterstdcode : yumi001
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

        public static class AddBeanXXXX {
            /**
             * characterstdcode : yumi001
             * columncode : 2
             * val : 22
             */

            private String characterstdcode;
            private int columncode;
            private String val;

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

            public String getVal() {
                return val;
            }

            public void setVal(String val) {
                this.val = val;
            }
        }
    }
}
