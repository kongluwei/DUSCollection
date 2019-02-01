package com.pdwy.dus.collection.model.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.pdwy.dus.collection.http.bean.GrowthPeriodBean;
import com.pdwy.dus.collection.model.bean.CharacterBean;
import com.pdwy.dus.collection.model.bean.CharacterThresholdBean;
import com.pdwy.dus.collection.model.bean.CollectionTaskBean;
import com.pdwy.dus.collection.model.bean.CollectionTaskItemBean;
import com.pdwy.dus.collection.model.bean.CollectionTaskItemListBean;
import com.pdwy.dus.collection.model.bean.PictureBean;
import com.pdwy.dus.collection.model.bean.QunTiBean;
import com.pdwy.dus.collection.model.bean.TemplateBean;
import com.pdwy.dus.collection.utils.MLog;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 数据库数据操作
 * Author： by MR on 2018/8/23.
 */

public class InputData {
    MySQLiteOpenHelper msoh;

    public InputData(Context c) {
        msoh = new MySQLiteOpenHelper(c);

    }

    private void setUser(){

    }
    //判断任务是否存在
    public boolean isExistence(int syrwbh){
        SQLiteDatabase  db = msoh.getWritableDatabase();
        if(db==null)
            return false;

        db.beginTransaction();// 开启事务
//        Cursor c = db.rawQuery("select * from Character ", null);
        Cursor c = db.rawQuery("select * from Character where  groupId = ?",
                new String[]{String.valueOf(syrwbh)});

        if (c.getCount()>0) {
            db.endTransaction();
            db.close();
            return true;
        }
        else {
            db.endTransaction();
            db.close();
            return false;
        }
    }

    //初始数据库数据
    public   void initialCharacter(ArrayList<CharacterBean> list){
        SQLiteDatabase  db = msoh.getWritableDatabase();


        db.beginTransaction();// 开启事务

        for(CharacterBean c:list) {
            ContentValues values = new ContentValues();
            values.put("characterId", c.characterId);
            values.put("characterName", c.characterName);
            values.put("varieties", c.varieties);
            values.put("experimentalNumber", c.experimentalNumber);
            values.put("template", c.template);
            values.put("testNumber", c.testNumber);
            values.put("groupId", c.groupId);
            values.put("varietyId", c.varietyId);
            values.put("growthPeriod", c.growthPeriod);
            values.put("observationMethod", c.observationMethod);
            values.put("fillInTheState", c.fillInTheState);
            values.put("observationalQuantity", c.observationalQuantity);
            values.put("dataUnit", c.dataUnit);
            values.put("duplicateContent1", c.duplicateContent1);
            values.put("duplicateContent2", c.duplicateContent2);
            values.put("duplicateContent3", c.duplicateContent3);
            values.put("numericalRangeOfCharacters", c.numericalRangeOfCharacters);
            values.put("normalPicture", c.normalPicture);
            values.put("abnormalPicture", c.abnormalPicture);
            values.put("codeDescription", c.codeDescription);
            values.put("codeValue", c.codeValue);
            values.put("abnormal", c.abnormal);
            values.put("abnormalContent", c.abnormalContent);
            values.put("pictureName", c.pictureName);
            db.insert("Character", null, values);
        }




        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();

    }

    //初始数据库数据 模板
    public   void initialTemplate(String varieties,String templateName,String containCharacter){
        SQLiteDatabase  db = msoh.getWritableDatabase();


        db.beginTransaction();// 开启事务
        boolean p=false;
        Cursor c = db.rawQuery("select * from Template where templateName = ?",
                new String[]{templateName});
        if(c.getCount()>0)
            p=true;
        if(!p) {
            ContentValues values3 = new ContentValues();
            values3.put("varieties", varieties); //品种
            values3.put("templateName", templateName); //模板
            values3.put("containGrowthPeriod", "发芽,");
            values3.put("growthPeriodTime", "8.20-9.01;");
            values3.put("containCharacter", containCharacter);
            db.insert("Template", null, values3);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }
   // 写入分组下的模板
    public void setTemplate(com.pdwy.dus.collection.http.bean.TemplateBean templateBean){
        SQLiteDatabase  db = msoh.getWritableDatabase();


        db.beginTransaction();// 开启事务
        boolean p=false;
        Cursor c = db.rawQuery("select * from CollectionTemplate where subcentergroupId = ? and collectiontemplatename = ?",
                new String[]{templateBean.getSubcentergroupId(),templateBean.getCollectiontemplatename()});
        if(c.getCount()>0)
            p=true;
        if(!p) {
            ContentValues values3 = new ContentValues();
            values3.put("subcentergroupId", templateBean.getSubcentergroupId()); //品种
            values3.put("id", templateBean.getId()); //模板
            values3.put("collectiontemplatename", templateBean.getCollectiontemplatename());
            values3.put("guideedition", templateBean.getGuideedition());
            values3.put("characternum", templateBean.getCharacternum());
            values3.put("characterlist", templateBean.getCharacterlist());
            db.insert("CollectionTemplate", null, values3);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();

    }

    //写入分组下的生育期
    public void setGrowthPeriod(GrowthPeriodBean growthPeriod){
        SQLiteDatabase  db = msoh.getWritableDatabase();

        db.beginTransaction();// 开启事务
        boolean p=false;
        Cursor c = db.rawQuery("select * from BirthCycle where subcentergroupId = ? and remarks = ?",
                new String[]{growthPeriod.getData().get(0).getSubcentergroupId(),growthPeriod.getData().get(0).getRemarks()});
        if(c.getCount()>0)
            p=true;
        if(!p) {
            List<GrowthPeriodBean.DataBean> dataBeans=growthPeriod.getData();
            for(GrowthPeriodBean.DataBean dataBean:dataBeans) {
                ContentValues values3 = new ContentValues();
                values3.put("subcentergroupId", dataBean.getSubcentergroupId()); //品种
                values3.put("id", dataBean.getId()); //模板
                values3.put("serialnum", dataBean.getSerialnum());
                values3.put("remarks", dataBean.getRemarks());
                values3.put("standardcode", dataBean.getStandardcode());
                values3.put("starttime", dataBean.getStarttime());
                values3.put("endtime", dataBean.getEndtime());
                values3.put("sortcharcode", dataBean.getSortcharcode());
                values3.put("name", dataBean.getName());
                db.insert("BirthCycle", null, values3);
            }
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();

    }
    //获取品种名称Varieties的list
    public   ArrayList<CollectionTaskBean> getCollectionTaskBeanVarieties(){
        ArrayList<CollectionTaskBean> list=null;
        SQLiteDatabase  db = msoh.getWritableDatabase();
        if(db==null)
            return list;
        db.beginTransaction();// 开启事务
//        Cursor c = db.rawQuery("select * from Character ", null);
        Cursor c =db.query("Character", null, null, null, null, null, null);
        MLog.e("------==="+c.getCount()+"一共条性状要采集（重复）");
        list=new ArrayList<>();
        while (c.moveToNext()) {

            boolean b=false;
            for(CollectionTaskBean collectionTaskBean:list){
                if(c.getString(c.getColumnIndex("varieties")).equals(collectionTaskBean.Varieties)){
                    b=true;
                    break;
                }

            }
            if(b)
                continue;
            CollectionTaskBean collectionTaskBean=new CollectionTaskBean();
            collectionTaskBean.Varieties=c.getString(c.getColumnIndex("varieties"));

            list.add(collectionTaskBean);
//            Log.e("db.String","----"+s);
        }
        db.endTransaction();                                    //关闭事务
        db.close();
        return list;
    }


    //获取包名称taskName的list
    public ArrayList<CollectionTaskItemBean> getCollectionTaskItemBeantaskName(CollectionTaskBean collectionTaskBean){
        ArrayList<CollectionTaskItemBean> list2=null;
        SQLiteDatabase  db = msoh.getWritableDatabase();
        if(db==null)
            return list2;
        db.beginTransaction();// 开启事务
//        Cursor c = db.rawQuery("select * from Character ", null);
//        Cursor c =db.query("Character", null, null, null, null, null, null);
        Cursor c = db.rawQuery("select * from Character where varieties = ?",
                new String[]{collectionTaskBean.Varieties});
        list2=new ArrayList<>();
        while (c.moveToNext()) {

            boolean b=false;
            for(CollectionTaskItemBean collectionTaskItemBean:list2){
                if(c.getString(c.getColumnIndex("experimentalNumber")).equals(collectionTaskItemBean.taskName)){
                    b=true;
                    break;
                }

            }
            if(b)
                continue;
            CollectionTaskItemBean collectionTaskItemBean=new CollectionTaskItemBean();
            collectionTaskItemBean.taskName=c.getString(c.getColumnIndex("experimentalNumber"));

            list2.add(collectionTaskItemBean);
//            Log.e("db.String","----"+s);
        }
        db.endTransaction();                                    //关闭事务
        db.close();


        return list2;
    }

    //获取测试编号testNumber的list   list.size就是测试品种数
    public ArrayList<CollectionTaskItemListBean> getCollectionTaskItemListBeantestNumber(CollectionTaskItemBean collectionTaskItemBean ,int p){
        ArrayList<CollectionTaskItemListBean> list3=null;

        SQLiteDatabase  db = msoh.getWritableDatabase();
        if(db==null)
            return list3;
        db.beginTransaction();// 开启事务
//        Cursor c = db.rawQuery("select * from Character ", null);
//        Cursor c =db.query("Character", null, null, null, null, null, null);
        Cursor c = null;

        if(p==0){

            c = db.rawQuery("select * from Character where experimentalNumber = ?",
                    new String[]{collectionTaskItemBean.taskName});
        }

        if(p==1){
            c = db.rawQuery("select * from Character where experimentalNumber = ?",
                    new String[]{collectionTaskItemBean.taskName});
        }
        list3=new ArrayList<>();
        while (c.moveToNext()) {

            boolean b=false;
            for(CollectionTaskItemListBean collectionTaskItemListBean:list3){
                if(c.getString(c.getColumnIndex("testNumber")).equals(collectionTaskItemListBean.testNumber)){
                    b=true;
                    break;
                }

            }
            if(b)
                continue;
            CollectionTaskItemListBean collectionTaskItemListBean=new CollectionTaskItemListBean();
            collectionTaskItemListBean.testNumber=c.getString(c.getColumnIndex("testNumber"));
            collectionTaskItemListBean.abnormal=c.getString(c.getColumnIndex("abnormal"));
            collectionTaskItemListBean.experimentalNumber=collectionTaskItemBean.taskName;
            list3.add(collectionTaskItemListBean);
//            Log.e("db.String","----"+s);
        }
        db.endTransaction();                                    //关闭事务
        db.close();
        return list3;
    }


    //测试需要采集性状个数 (依据测试编号)
    public int getWhole(CollectionTaskItemListBean collectionTaskItemListBean){

        SQLiteDatabase  db = msoh.getWritableDatabase();
        db.beginTransaction();// 开启事务
        Cursor c = db.rawQuery("select * from Character where experimentalNumber = ? and testNumber = ?",
                new String[]{collectionTaskItemListBean.experimentalNumber,collectionTaskItemListBean.testNumber});
        int i=c.getCount();
        db.endTransaction();                                    //关闭事务
        db.close();
        return i;
    }
    //测试未采集性状个数 (依据测试编号)
    public int getNot(CollectionTaskItemListBean collectionTaskItemListBean){

        SQLiteDatabase  db = msoh.getWritableDatabase();
        db.beginTransaction();// 开启事务
        Cursor c = db.rawQuery("select * from Character where experimentalNumber = ? and testNumber = ? and fillInTheState = ?",
                new String[]{collectionTaskItemListBean.experimentalNumber,collectionTaskItemListBean.testNumber,"0"});
        int i=c.getCount();
        db.endTransaction();                                    //关闭事务
        db.close();
        return i;
    }
    //测试已经采集性状个数 (依据测试编号)
    public int getAlready(CollectionTaskItemListBean collectionTaskItemListBean){

        SQLiteDatabase  db = msoh.getWritableDatabase();
        db.beginTransaction();// 开启事务
        Cursor c = db.rawQuery("select * from Character where experimentalNumber = ? and testNumber = ? and fillInTheState = ?",
                new String[]{collectionTaskItemListBean.experimentalNumber,collectionTaskItemListBean.testNumber,"1"});
        int i=c.getCount();
        db.endTransaction();                                    //关闭事务
        db.close();
        return i;
    }
    //测试已经上传性状个数 (依据测试编号)
    public int getReport(CollectionTaskItemListBean collectionTaskItemListBean){

        SQLiteDatabase  db = msoh.getWritableDatabase();
        db.beginTransaction();// 开启事务
        Cursor c = db.rawQuery("select * from Character where experimentalNumber = ? and testNumber = ? and fillInTheState = ?",
                new String[]{collectionTaskItemListBean.experimentalNumber,collectionTaskItemListBean.testNumber,"2"});
        int i=c.getCount();
        db.endTransaction();                                    //关闭事务
        db.close();
        return i;
    }

    //获取CollectionTaskItemListBean  list  测试编号
    public ArrayList<CollectionTaskItemListBean> getCollectionTaskItemListBeanList(CollectionTaskItemBean collectionTaskItemBean){
        ArrayList<CollectionTaskItemListBean> list3=getCollectionTaskItemListBeantestNumber(collectionTaskItemBean,0);
        for(int i=0;i<list3.size();i++){

            //需采集
            int x=getWhole(list3.get(i));
            list3.get(i).needToCollect=x;
            //以上传
            int z=getReport(list3.get(i));
            list3.get(i).toUpload=z;

            //以采集
            int y=getAlready(list3.get(i))+z;
            list3.get(i).haveBeenCollected=y;
            //未采集
            int n=getNot(list3.get(i));
            list3.get(i).notCollected=n;

            //为上传
            list3.get(i).notUploaded=y-z;

        }


        return list3;
    }
//获取CollectionTaskItemBean  list  包
public ArrayList<CollectionTaskItemBean> getCollectionTaskItemBeanList(CollectionTaskBean collectionTaskBean,int p){
    ArrayList<CollectionTaskItemBean> list2=getCollectionTaskItemBeantaskName(collectionTaskBean);
    if(p==0) {
        for (int i = 0; i < list2.size(); i++) {
            ArrayList<CollectionTaskItemListBean> list3 = getCollectionTaskItemListBeanList(list2.get(i));

            list2.get(i).collectionOfVarieties = list3.size();
            list2.get(i).collectionOfVarietiesHangInTheAir = getCollectionOfVarietiesHangInTheAir(list2.get(i));
            list2.get(i).list = list3;

        }
    }

    return list2;
}


    //获取CollectionTaskBean  list  包
    public ArrayList<CollectionTaskBean> getCollectionTaskBeanList(int p){
        ArrayList<CollectionTaskBean> list=getCollectionTaskBeanVarieties();
        if(p==0){
            for(int i=0;i<list.size();i++){
                ArrayList<CollectionTaskItemBean> list2=getCollectionTaskItemBeanList(list.get(i),0);
                list.get(i).list=list2;


            }
        }



        return list;
    }


//获取包的为完成品种数
public int getCollectionOfVarietiesHangInTheAir(CollectionTaskItemBean collectionTaskItemBean){
    //已上传完毕个数
    int q=0;
        ArrayList<CollectionTaskItemListBean> list3=getCollectionTaskItemListBeantestNumber(collectionTaskItemBean,0);

        for(int i=0;i<list3.size();i++){
            //需采集
            int x=getWhole(list3.get(i));
            //以上传
            int z=getReport(list3.get(i));
            if (x!=z){

                q++;
            }

        }


    return q;
}
    //根据测试编号获取对应的性状list
    public ArrayList<CharacterBean> getCharacterBeanList(String groupId,String varietyId){
        ArrayList<CharacterBean> list=new ArrayList<>();

        SQLiteDatabase  db = msoh.getWritableDatabase();
        if(db==null)
            return list;
        db.beginTransaction();// 开启事务

        Cursor c =db.rawQuery("select * from Character where groupId = ? and varietyId = ? and fillInTheState = ?",
                new String[]{groupId,varietyId,"1"});

        while (c.moveToNext()) {

            CharacterBean characterBean=new CharacterBean();
            characterBean.characterId= c.getString(c.getColumnIndex("characterId"));
            characterBean.characterName= c.getString(c.getColumnIndex("characterName"));
            characterBean.observationMethod= c.getString(c.getColumnIndex("observationMethod"));
            characterBean.observationalQuantity= c.getString(c.getColumnIndex("observationalQuantity"));
            characterBean.dataUnit= c.getString(c.getColumnIndex("dataUnit"));
            characterBean.duplicateContent1= c.getString(c.getColumnIndex("duplicateContent1"));
            characterBean.abnormal= c.getString(c.getColumnIndex("abnormal"));
            characterBean.abnormalContent= c.getString(c.getColumnIndex("abnormalContent"));
            list.add(characterBean);
        }

        db.endTransaction();                                    //关闭事务
        db.close();
    return list;
    }
    //根据实验编号名称（分组编号）获取实验编号（分组编号）id
    public String getCharacterGroupId(String experimentalNumber){
        String groupId = "";
        SQLiteDatabase  db = msoh.getWritableDatabase();
        if(db==null)
            return "";
        db.beginTransaction();// 开启事务
        Cursor   c =db.rawQuery("select * from Character where experimentalNumber = ?",
                new String[]{experimentalNumber});

        if (c.getCount()>0) {

            while (c.moveToNext()) {
                groupId = c.getString(c.getColumnIndex("groupId"));
                break;
            }
        }
        db.endTransaction();                                    //关闭事务
        db.close();
        return groupId;
    }
    //根据任务编号名称（测试编号）获取任务编号（测试编号）id
    public String getCharacterVarietyId(String testNumber){
        String varietyId = "";
        SQLiteDatabase  db = msoh.getWritableDatabase();
        if(db==null)
            return "";
        db.beginTransaction();// 开启事务
        Cursor   c =db.rawQuery("select * from Character where testNumber = ?",
                new String[]{testNumber});

        if (c.getCount()>0) {

            while (c.moveToNext()) {
                varietyId = c.getString(c.getColumnIndex("varietyId"));
                break;
            }
        }
        db.endTransaction();                                    //关闭事务
        db.close();
        return varietyId;
    }
    //根据性状名称获取性状id
    public String getCharacterId(String characterName){
        String characterId = "";
        SQLiteDatabase  db = msoh.getWritableDatabase();
        if(db==null)
            return "";
        db.beginTransaction();// 开启事务
        Cursor   c =db.rawQuery("select * from Character where characterName = ?",
                new String[]{characterName});

        if (c.getCount()>0) {

            while (c.moveToNext()) {
                characterId = c.getString(c.getColumnIndex("characterId"));
                break;
            }
        }
        db.endTransaction();                                    //关闭事务
        db.close();
        return characterId;
    }
    //根据性状Id获取性状名称
    public String getCharacterName(String character,String template,String growthPeriod,String observationMethod,String syrwbh){
        String characterName = null;
        SQLiteDatabase  db = msoh.getWritableDatabase();
        if(db==null)
            return "";
        db.beginTransaction();// 开启事务
        Cursor   c =db.rawQuery("select * from Character where characterId = ? and template = ? and growthPeriod = ? and experimentalNumber = ?",
                new String[]{character,template,growthPeriod,syrwbh});
        while (c.moveToNext()) {
            if("个体".equals(observationMethod)) {
                if("MS".equals(c.getString(c.getColumnIndex("observationMethod")))||"VS".equals(c.getString(c.getColumnIndex("observationMethod"))))
                characterName = c.getString(c.getColumnIndex("characterName"));
                else
                    characterName="";
            }
            if("群体".equals(observationMethod)) {
                if("MG".equals(c.getString(c.getColumnIndex("observationMethod")))||"VG".equals(c.getString(c.getColumnIndex("observationMethod"))))
                    characterName = c.getString(c.getColumnIndex("characterName"));
                else
                    characterName="";
            }
        }
        db.endTransaction();                                    //关闭事务
        db.close();
        return characterName;
    }


  //拍照和自定义采集获取模板表
    public ArrayList<TemplateBean> getMoBan(String ss, int p){
        ArrayList<TemplateBean> list=new ArrayList<>();
        SQLiteDatabase  db = msoh.getWritableDatabase();
        if(db==null)
            return list;
        db.beginTransaction();// 开启事务
//        Cursor c = db.rawQuery("select * from Character ", null);
        Cursor   c =db.rawQuery("select * from Template where varieties = ?",
                    new String[]{ss});
        while (c.moveToNext()) {
            TemplateBean templateBean=new TemplateBean();
            templateBean.templateName=c.getString(c.getColumnIndex("templateName"));
            templateBean.varieties=c.getString(c.getColumnIndex("varieties"));
            templateBean.containGrowthPeriod=c.getString(c.getColumnIndex("containGrowthPeriod"));
            templateBean.growthPeriodTime=c.getString(c.getColumnIndex("growthPeriodTime"));
            templateBean.containCharacter=c.getString(c.getColumnIndex("containCharacter"));
            list.add(templateBean);
        }
        db.endTransaction();                                    //关闭事务
        db.close();
        return list;
    }


//获取模板列表list
    public ArrayList<String> getMoBan(String ss){
        ArrayList<String> list=null;
        SQLiteDatabase  db = msoh.getWritableDatabase();
        if(db==null)
            return list;
        db.beginTransaction();// 开启事务
//        Cursor c = db.rawQuery("select * from Character ", null);
        Cursor c;
        if(ss==null)
         c =db.query("Character", null, null, null, null, null, null);
        else
            c =db.rawQuery("select * from Character where experimentalNumber = ?",
                    new String[]{ss});
        MLog.e("------==="+c.getCount());
        list=new ArrayList<>();
        while (c.moveToNext()) {
            boolean b=false;
            for(String s:list){
                if(c.getString(c.getColumnIndex("template")).equals(s)){
                    b=true;
                    break;
                }
            }
            if(b)
                continue;

            list.add(c.getString(c.getColumnIndex("template")));
//            Log.e("db.String","----"+s);
        }
        db.endTransaction();                                    //关闭事务
        db.close();
        return list;
    }

    //获取性状list  导入进阈值表
    public ArrayList<CharacterThresholdBean> getCharacterThresholdBeanlist(String zw,String sybh,String mb,String syq,int p,ArrayList<String> listString){
        if(listString!=null)
        MLog.e(listString.toString());
        ArrayList<CharacterThresholdBean> list=null;
        SQLiteDatabase  db = msoh.getWritableDatabase();
        if(db==null)
            return list;
        db.beginTransaction();// 开启事务
        Cursor c;
        if(p==0)//全部的
         c =db.query("Character", null, null, null, null, null, null);
        else if(p==2)//已保存的
            c = db.rawQuery("select * from Character where varieties = ? and experimentalNumber = ? and template = ? and growthPeriod = ? and fillInTheState = ?",
                    new String[]{zw,sybh,mb,syq,"0"});

        else if(p==9)
            c = db.rawQuery("select * from Character where varieties = ? and experimentalNumber = ? and template = ?",
                    new String[]{zw,sybh,mb});
        else //获取生育期下性状
            c = db.rawQuery("select * from Character where varieties = ? and experimentalNumber = ? and template = ? and growthPeriod = ?",
                    new String[]{zw,sybh,mb,syq});
        list=new ArrayList<>();

        while (c.moveToNext()) {
            boolean b=false;
            for(CharacterThresholdBean s:list){
                if(c.getString(c.getColumnIndex("characterName")).equals(s.characterName)){
                    b=true;
                    break;
                }
            }
            if(b)
                continue;

            CharacterThresholdBean characterThresholdBean=new CharacterThresholdBean();
            characterThresholdBean.characterId=c.getString(c.getColumnIndex("characterId"));
            characterThresholdBean.characterName=c.getString(c.getColumnIndex("characterName"));
            characterThresholdBean.varieties=c.getString(c.getColumnIndex("varieties"));
            characterThresholdBean.template=c.getString(c.getColumnIndex("template"));
            characterThresholdBean.observationMethod=c.getString(c.getColumnIndex("observationMethod"));
            characterThresholdBean.numericalRangeOfCharacters=c.getString(c.getColumnIndex("numericalRangeOfCharacters"));
            characterThresholdBean.abnormal=c.getString(c.getColumnIndex("abnormal"));
            if (p == 9) {
                for(String listStringItem:listString) {
                    if (listStringItem.equals(c.getString(c.getColumnIndex("testNumber")))){
                        list.add(characterThresholdBean);
                        break;
                    }
                }

            }
            else
            list.add(characterThresholdBean);
//            Log.e("db.String","----"+s);
        }


        db.endTransaction();                                    //关闭事务
        db.close();
        //是否导入阈值表
        if(p==0)
        setYuZhi(list);
        return list;

    }
    //读取全部照片
    public  ArrayList<PictureBean> getZhaoPianList(){
        SQLiteDatabase  db = msoh.getWritableDatabase();
        db.beginTransaction();// 开启事务
        ArrayList<PictureBean>list=new ArrayList<>();
        Cursor c =db.query("Picture", null, null, null, null, null, null);
        while (c.moveToNext()) {
            PictureBean pictureBean=new PictureBean();
            pictureBean.pictureName=c.getString(c.getColumnIndex("pictureName"));
            pictureBean.testNumber=c.getString(c.getColumnIndex("testNumber"));
            pictureBean.experimentalNumber=c.getString(c.getColumnIndex("experimentalNumber"));
            pictureBean.stateOfExpression=c.getString(c.getColumnIndex("stateOfExpression"));
            pictureBean.varieties=c.getString(c.getColumnIndex("varieties"));
            pictureBean.pictureTime=c.getString(c.getColumnIndex("pictureTime"));
            pictureBean.characterName=c.getString(c.getColumnIndex("characterName"));
            pictureBean.whetherOrNotToUpload=c.getString(c.getColumnIndex("whetherOrNotToUpload"));

            list.add(pictureBean);
        }
        db.endTransaction();                                    //关闭事务
        db.close();
return list;
    }

    //保存照片
    public void  setZhaoPian(PictureBean pian){
        SQLiteDatabase  db = msoh.getWritableDatabase();
        db.beginTransaction();// 开启事务
        ContentValues values = new ContentValues();
        values.put("pictureName", pian.pictureName);
        values.put("testNumber", pian.testNumber);
        values.put("experimentalNumber", pian.experimentalNumber);
        values.put("stateOfExpression", pian.stateOfExpression);
        values.put("varieties", pian.varieties);
        values.put("pictureTime", pian.pictureTime);
        values.put("characterName", pian.characterName);
        values.put("pictureAddress", pian.pictureAddress);
        values.put("whetherOrNotToUpload", pian.whetherOrNotToUpload);
        db.insert("Picture",null,values);
        db.setTransactionSuccessful();
        db.endTransaction();                                    //关闭事务
        db.close();
    }

    //读取一组照片
    public PictureBean  getZhaoPian(String syrwbh ,String csbh,String xzmc){
        MLog.e(syrwbh+"=-="+csbh+"=="+xzmc);
        PictureBean pictureBean=new PictureBean();
        SQLiteDatabase  db = msoh.getWritableDatabase();
        db.beginTransaction();// 开启事务
       Cursor c= db.rawQuery("select * from Picture where experimentalNumber = ? and testNumber = ? and characterName = ?",
                new String[]{syrwbh,csbh,xzmc});


        while (c.moveToNext()) {

            pictureBean.pictureName=c.getString(c.getColumnIndex("pictureName"));
            pictureBean.testNumber=c.getString(c.getColumnIndex("testNumber"));
            pictureBean.experimentalNumber=c.getString(c.getColumnIndex("experimentalNumber"));
            pictureBean.stateOfExpression=c.getString(c.getColumnIndex("stateOfExpression"));
            pictureBean.varieties=c.getString(c.getColumnIndex("varieties"));
            pictureBean.pictureTime=c.getString(c.getColumnIndex("pictureTime"));
            pictureBean.characterName=c.getString(c.getColumnIndex("characterName"));
            pictureBean.whetherOrNotToUpload=c.getString(c.getColumnIndex("whetherOrNotToUpload"));
            pictureBean.pictureAddress=c.getString(c.getColumnIndex("pictureAddress"));

        }
        db.endTransaction();                                    //关闭事务
        db.close();

        return pictureBean;
    }

    //读取一组照片
    public PictureBean  updateZhaoPian(String syrwbh ,String csbh,String xzmc){
        MLog.e(syrwbh+"=-="+csbh+"=="+xzmc);
        PictureBean pictureBean=new PictureBean();
        SQLiteDatabase  db = msoh.getWritableDatabase();
        db.beginTransaction();// 开启事务
        ContentValues values = new ContentValues();
        values.put("whetherOrNotToUpload","是");

        db.update("Picture", values, "experimentalNumber = ? and testNumber = ? and characterName = ?", new String[]{syrwbh,csbh,xzmc});
        db.setTransactionSuccessful();
        db.endTransaction();                                    //关闭事务
        db.close();

        return pictureBean;
    }
    //删除照片

    public boolean  deleteZhaoPian(PictureBean pian){

        try {
            SQLiteDatabase  db = msoh.getWritableDatabase();
            db.beginTransaction();// 开启事务
            db.delete("Picture", "pictureName = ? and testNumber = ? and experimentalNumber = ? and varieties = ?", new String[]{pian.pictureName,pian.testNumber,pian.experimentalNumber,pian.varieties});
            db.setTransactionSuccessful();
            db.endTransaction();                                    //关闭事务
            db.close();
            return true;
        }
       catch (Exception x){
           MLog.e("删除照片出错："+pian.characterName);
           return false;
       }


    }

    //保存个体观察性状录入表
    public void setGeTiLL(ArrayList<QunTiBean> list){
        SQLiteDatabase  db = msoh.getWritableDatabase();
        db.beginTransaction();// 开启事务
        for(QunTiBean q:list) {
            ContentValues values = new ContentValues();
            values.put("duplicateContent1", q.bcnr);
            boolean isPreservation=false;
            String bcnr[]=q.bcnr.split(",-");

            for(String s:bcnr){
                if(!"".equals(s))
                    isPreservation=true;

            }


            if("异常".equals(q.abnormal)) {
                values.put("abnormal", "1");
                isPreservation=true;
            }
            else
                values.put("abnormal", "0");

            if(isPreservation)
                values.put("fillInTheState", "1"); // 是否可上报
            else
                values.put("fillInTheState", "0"); // 是否可上报
            db.update("Character", values, "experimentalNumber = ?  and testNumber = ?  and characterName = ?", new String[]{q.experimentalNumber,q.csbh,q.xzmc});
            MLog.e(q.bcnr+q.csbh+q.xzmc+q.abnormal+isPreservation);
        }
        db.setTransactionSuccessful();
        db.endTransaction();                                    //关闭事务
        db.close();
    }
    //保存群体观察性状录入表
    public void setQunTiLL(ArrayList<QunTiBean> list){
        SQLiteDatabase  db = msoh.getWritableDatabase();
        db.beginTransaction();// 开启事务
        for(QunTiBean q:list) {
            ContentValues values = new ContentValues();
            values.put("duplicateContent1", q.bcnr);


            if(!"".equals(q.bcnr))
            values.put("fillInTheState", "1"); // 是否可上报
            else
                values.put("fillInTheState", "0"); // 是否可上报

            if("异常".equals(q.bcnr))
                values.put("abnormal", "1");
            else
                values.put("abnormal", "0");
            values.put("duplicateContent2", q.beizhu);
                db.update("Character", values, "experimentalNumber = ?  and testNumber = ?  and characterName = ?", new String[]{q.experimentalNumber,q.csbh,q.xzmc});

        }
        db.setTransactionSuccessful();
        db.endTransaction();                                    //关闭事务
        db.close();
    }
    //保存个体异常性状录入表
    public void setGeTiYi(ArrayList<QunTiBean> list){
        SQLiteDatabase  db = msoh.getWritableDatabase();
        db.beginTransaction();// 开启事务
        for(QunTiBean q:list) {
            ContentValues values = new ContentValues();
            values.put("abnormalContent", q.bcnr);
            values.put("fillInTheState", "1"); // 是否可上报

            db.update("Character", values, "experimentalNumber = ?  and testNumber = ?  and characterName = ?", new String[]{q.experimentalNumber,q.csbh,q.xzmc});
            MLog.e(q.bcnr+q.csbh+q.xzmc);
        }
        db.setTransactionSuccessful();
        db.endTransaction();                                    //关闭事务
        db.close();
    }

    //保存群体异常性状录入表
    public void setQunTiYi(ArrayList<QunTiBean> list){
        SQLiteDatabase  db = msoh.getWritableDatabase();
        db.beginTransaction();// 开启事务
        for(QunTiBean q:list) {
            ContentValues values = new ContentValues();
            values.put("abnormalContent", q.bcnr);
            values.put("fillInTheState", "1"); // 是否可上报

            db.update("Character", values, "experimentalNumber = ?  and testNumber = ?  and characterName = ?", new String[]{q.experimentalNumber,q.csbh,q.xzmc});
            MLog.e(q.bcnr+q.csbh+q.xzmc);
        }
        db.setTransactionSuccessful();
        db.endTransaction();                                    //关闭事务
        db.close();
    }
    //删除已保存的性状

    public boolean  deleteCharacter(String experimentalNumber,String tjbh){

        try {
            SQLiteDatabase  db = msoh.getWritableDatabase();
            db.beginTransaction();// 开启事务
            ContentValues values = new ContentValues();
            values.put("duplicateContent1", "");
            values.put("duplicateContent2", "");
            values.put("duplicateContent3", "");
            values.put("abnormal", "0");
            values.put("abnormalContent", "");
            values.put("fillInTheState", "0");
           int i= db.update("Character", values, "experimentalNumber = ? and testNumber = ?", new String[]{experimentalNumber,tjbh});
MLog.e("=========="+i+"----"+tjbh);
            db.setTransactionSuccessful();
            db.endTransaction();                                    //关闭事务
            db.close();
            return true;
        }
        catch (Exception x){
            MLog.e("删除数据出错："+tjbh);
            return false;
        }


    }

    //获取已保存性状的DuplicateContent1
    public ArrayList<String> getDuplicateContent1(List<String> csbhList, String syrwbh, String xzmc){
        ArrayList<String> list=new ArrayList<>();
        SQLiteDatabase  db = msoh.getWritableDatabase();
        db.beginTransaction();// 开启事务
        for(String csbh:csbhList) {
            Cursor c = db.rawQuery("select * from Character where testNumber = ? and experimentalNumber = ? and characterName = ? and fillInTheState = ?",
                    new String[]{csbh, syrwbh,xzmc.substring(3),"1"});
            while (c.moveToNext()) {
                list.add(c.getString(c.getColumnIndex("duplicateContent1")));
            }
        }
        db.endTransaction();                                    //关闭事务
        db.close();
        return list;
    }

    //获取已保存性状的DuplicateContent1
    public String getDuplicateContent1i(String csbh, String syrwbh, String xzmc){
        String duplicateContent1 = null;
        SQLiteDatabase  db = msoh.getWritableDatabase();
        db.beginTransaction();// 开启事务
        Cursor c = db.rawQuery("select * from Character where testNumber = ? and experimentalNumber = ? and characterName = ? and fillInTheState = ?",
                new String[]{csbh, syrwbh,xzmc.substring(3),"1"});
        while (c.moveToNext()) {
            duplicateContent1=c.getString(c.getColumnIndex("duplicateContent1"));
        }
        db.endTransaction();                                    //关闭事务
        db.close();
        return duplicateContent1;
    }

    //获取已保存性状的DuplicateContent1
    public String getDuplicateContent1(String csbh, String syrwbh, String xzmc){
        String duplicateContent1 = null;
        SQLiteDatabase  db = msoh.getWritableDatabase();
        db.beginTransaction();// 开启事务
        Cursor c = db.rawQuery("select * from Character where testNumber = ? and experimentalNumber = ? and characterName = ? and fillInTheState = ?",
                new String[]{csbh, syrwbh,xzmc,"1"});
        while (c.moveToNext()) {
            duplicateContent1=c.getString(c.getColumnIndex("duplicateContent1"));
        }
        db.endTransaction();                                    //关闭事务
        db.close();
        return duplicateContent1;
    }
    //获取已保存性状的DuplicateContent2
    public String getDuplicateContent2(String csbh, String syrwbh, String xzmc){
        String duplicateContent1 = null;
        SQLiteDatabase  db = msoh.getWritableDatabase();
        db.beginTransaction();// 开启事务
        Cursor c = db.rawQuery("select * from Character where testNumber = ? and experimentalNumber = ? and characterName = ? and fillInTheState = ?",
                new String[]{csbh, syrwbh,xzmc,"1"});
        while (c.moveToNext()) {
            duplicateContent1=c.getString(c.getColumnIndex("duplicateContent2"));
        }
        db.endTransaction();                                    //关闭事务
        db.close();
        return duplicateContent1;
    }
    //获取已保存性状的abnormal
    public String getAbnormal(String csbh, String syrwbh, String xzmc){
        String abnormal = null;
        SQLiteDatabase  db = msoh.getWritableDatabase();
        db.beginTransaction();// 开启事务
        Cursor c = db.rawQuery("select * from Character where testNumber = ? and experimentalNumber = ? and characterName = ? and fillInTheState = ?",
                new String[]{csbh, syrwbh,xzmc.substring(3),"1"});
        while (c.moveToNext()) {
            abnormal=c.getString(c.getColumnIndex("abnormal"));
        }
        db.endTransaction();                                    //关闭事务
        db.close();

        return "1".equals(abnormal)?"异常":"";
    }
    //获取已保存个体异常性状的abnormalContent
    public String getAbnormalContent(String csbh, String syrwbh, String xzmc){
        String abnormalContent = null;
        SQLiteDatabase  db = msoh.getWritableDatabase();
        db.beginTransaction();// 开启事务
        Cursor c = db.rawQuery("select * from Character where testNumber = ? and experimentalNumber = ? and characterName = ? and fillInTheState = ?",
                new String[]{csbh, syrwbh,xzmc,"1"});
        while (c.moveToNext()) {
            abnormalContent=c.getString(c.getColumnIndex("abnormalContent"));
        }
        db.endTransaction();                                    //关闭事务
        db.close();

        return abnormalContent;
    }
    //获取性状阈值范围
    public String getYuZhi(String mbmc,String xz){
        String yuzhi=null;
        SQLiteDatabase  db = msoh.getWritableDatabase();
        db.beginTransaction();// 开启事务
        Cursor c = db.rawQuery("select * from CharacterThreshold where characterName = ? and template = ?",
                new String[]{xz.substring(3),mbmc});
        while (c.moveToNext()) {
            yuzhi=c.getString(c.getColumnIndex("numericalRangeOfCharacters"));

        }
        db.endTransaction();                                    //关闭事务
        db.close();
        return yuzhi;
    }

//写入性状阈值范围
    public void setYuZhi(ArrayList<CharacterThresholdBean> list){
        MLog.e("CharacterThresholdBeanList==============="+list.size());
        SQLiteDatabase  db = msoh.getWritableDatabase();
        db.beginTransaction();// 开启事务
        Cursor c;
        for(CharacterThresholdBean c1:list) {

            ContentValues values = new ContentValues();
            values.put("characterName", c1.characterName);
            values.put("characterId", c1.characterId);
            values.put("observationMethod", c1.observationMethod);
            values.put("numericalRangeOfCharacters", c1.numericalRangeOfCharacters);
            values.put("template", c1.template);
             c = db.rawQuery("select * from CharacterThreshold where template=? and  characterId = ?",
                    new String[]{c1.template,c1.characterId});
             //有就修改 没有就新增
            if(c.getCount()>0) {
                //为空不录入
                db.update("CharacterThreshold", values, "template=? and characterId = ?", new String[]{c1.template,c1.characterId});
            }
            else
                db.insert("CharacterThreshold", null, values);


        }

        MLog.e("性状阈值保存完成"+list.size());
        db.setTransactionSuccessful();
        db.endTransaction();                                    //关闭事务
        db.close();

    }


    //根据使用编号获取使用任务编号
    public String getExperimentalNumber(String csbh){
        String experimentalNumber=null;
        SQLiteDatabase  db = msoh.getWritableDatabase();
        if(db==null)
            return null;
        db.beginTransaction();// 开启事务
        Cursor c = db.rawQuery("select * from Character where testNumber = ?",
                new String[]{csbh});
        while (c.moveToNext()) {
            experimentalNumber=c.getString(c.getColumnIndex("experimentalNumber"));
            break;

        }
        db.endTransaction();                                    //关闭事务
        db.close();

        return experimentalNumber;
    }

    //根据测试编号获取对应模板
    public ArrayList<String> getTemplates(String syrwbh,String csbh){
        ArrayList<String> templateList=null;
        SQLiteDatabase  db = msoh.getWritableDatabase();
        if(db==null)
            return null;
        db.beginTransaction();// 开启事务
        templateList=new ArrayList<>();
        Cursor c = db.rawQuery("select * from Character where experimentalNumber = ? and testNumber = ?",
                new String[]{syrwbh,csbh});
        while (c.moveToNext()) {
            templateList.add(c.getString(c.getColumnIndex("template")));

        }
        db.endTransaction();                                    //关闭事务
        db.close();

        return templateList;
    }
    //根据测试编号获取对应品种
    public String getVarieties(String syrwbh,String csbh){
        String templateList=null;
        SQLiteDatabase  db = msoh.getWritableDatabase();
        if(db==null)
            return null;
        db.beginTransaction();// 开启事务
        Cursor c = db.rawQuery("select * from Character where experimentalNumber = ? and testNumber = ?",
                new String[]{syrwbh,csbh});
        while (c.moveToNext()) {
            templateList=c.getString(c.getColumnIndex("varieties"));
             continue;
        }
        db.endTransaction();                                    //关闭事务
        db.close();

        return templateList;
    }
    //根据实验任务编号获取对应模板
    public String getTemplate(String syrwbh){
        String template=null;
        SQLiteDatabase  db = msoh.getWritableDatabase();
        if(db==null)
            return null;
        db.beginTransaction();// 开启事务
        Cursor c = db.rawQuery("select * from Character where experimentalNumber = ?",
                new String[]{syrwbh});
        while (c.moveToNext()) {
            template=c.getString(c.getColumnIndex("template"));
            break;

        }
        db.endTransaction();                                    //关闭事务
        db.close();

        return template;
    }

    //根据模板、生育期、观测方式获取性状

    public ArrayList<String> getCharacterList(String mbmc,String syq,String gcfs){

        ArrayList<String> list=null;
        SQLiteDatabase  db = msoh.getWritableDatabase();
        if(db==null)
            return null;
        db.beginTransaction();// 开启事务
        list=new ArrayList<>();
        Cursor c2 = db.rawQuery("select * from Template where templateName = ?",
                new String[]{mbmc});
        String containCharacter = null;

        while (c2.moveToNext()) {
            containCharacter=c2.getString(c2.getColumnIndex("containCharacter"));

        }
        MLog.e(mbmc+"====="+containCharacter);
        String[]containCharacter2= containCharacter.split(",");
        for(int i=0;i<containCharacter2.length;i++){
            Cursor c3 = db.rawQuery("select * from Character where template = ? and characterId = ?",
                    new String[]{mbmc,containCharacter2[i]});
            String characterName = null;
            String growthPeriod=null;
            String observationMethod=null;
            while (c3.moveToNext()) {
                characterName=c3.getString(c3.getColumnIndex("characterName"));
                growthPeriod=c3.getString(c3.getColumnIndex("growthPeriod"));
                observationMethod=c3.getString(c3.getColumnIndex("observationMethod"));
             break;
            }
if(syq.equals(growthPeriod)) {
                switch (gcfs){
                    case "个体观测性状采集":
                        if("MS".equals(observationMethod)||"VS".equals(observationMethod))
                            list.add(characterName);
                        break;
                    case "群体观测性状采集":
                        if("MG".equals(observationMethod)||"VG".equals(observationMethod))
                            list.add(characterName);
                        break;
                    case "异常性状采集":
                            list.add(characterName);
                        break;
                }


}
        }
        db.endTransaction();                                    //关闭事务
        db.close();
        return list;
    }

    //根据模板获取生育期

    public String getContainGrowthPeriod(String mbmc){
        String containGrowthPeriod=null;
        SQLiteDatabase  db = msoh.getWritableDatabase();
        if(db==null)
            return null;
        db.beginTransaction();// 开启事务
        Cursor c2 = db.rawQuery("select * from Template where templateName = ?",
                new String[]{mbmc});
        while (c2.moveToNext()) {
            containGrowthPeriod=c2.getString(c2.getColumnIndex("containGrowthPeriod"));

        }
        db.endTransaction();                                    //关闭事务
        db.close();
        MLog.e("======="+containGrowthPeriod);
        return containGrowthPeriod;
    }

    //获取上一年保存的数据
    public String getLastYearData(String csbh){
        String lastYearData=null;
        String front=csbh.substring(0,4);
        String behind=csbh.substring(4);
        String xin=(Integer.valueOf(front)-1)+behind;
        SQLiteDatabase  db = msoh.getWritableDatabase();
        if(db==null)
            return null;
        db.beginTransaction();// 开启事务
        Cursor c = db.rawQuery("select * from Character where testNumber = ?",
                new String[]{xin});
        while (c.moveToNext()) {
            lastYearData=c.getString(c.getColumnIndex("duplicateContent1"));
        }
        db.endTransaction();                                    //关闭事务
        db.close();

        return lastYearData;
    }

    //根据性状名称获取性状Bean

    public CharacterBean getCharacterThresholdBean(String name){
        CharacterBean characterBean =new CharacterBean();
        SQLiteDatabase  db = msoh.getWritableDatabase();
        if(db==null)
            return null;
        db.beginTransaction();// 开启事务
        Cursor c = db.rawQuery("select * from Character where characterName = ?",
                new String[]{name});
        while (c.moveToNext()) {
            characterBean.characterId=c.getString(c.getColumnIndex("characterId"));
            characterBean.characterName=c.getString(c.getColumnIndex("characterName"));
            characterBean.codeDescription=c.getString(c.getColumnIndex("codeDescription"));
            characterBean.codeValue=c.getString(c.getColumnIndex("codeValue"));
break;

        }
        db.endTransaction();                                    //关闭事务
        db.close();
        return characterBean;

    }

//获取性状阈值范围CharacterThresholdBeanlsit
    public ArrayList<CharacterThresholdBean> getCharacterThresholdBeanList(String s){
        ArrayList<CharacterThresholdBean> list=null;
        SQLiteDatabase  db = msoh.getWritableDatabase();
        MLog.e("------==="+s);
        if(db==null)
            return list;
        db.beginTransaction();// 开启事务
        Cursor c = db.rawQuery("select * from CharacterThreshold where template = ?",
                new String[]{s});
//        Cursor c =db.query("CharacterThreshold", null, null, null, null, null, null);
        MLog.e("------==="+c.getCount());
        list=new ArrayList<>();
        while (c.moveToNext()) {

            CharacterThresholdBean characterThresholdBean=new CharacterThresholdBean();
            characterThresholdBean.characterId=c.getString(c.getColumnIndex("characterId"));
            characterThresholdBean.characterName=c.getString(c.getColumnIndex("characterName"));
            characterThresholdBean.varieties=c.getString(c.getColumnIndex("varieties"));
            characterThresholdBean.template=c.getString(c.getColumnIndex("template"));
            characterThresholdBean.observationMethod=c.getString(c.getColumnIndex("observationMethod"));
            characterThresholdBean.numericalRangeOfCharacters=c.getString(c.getColumnIndex("numericalRangeOfCharacters"));

            list.add(characterThresholdBean);
//            Log.e("db.String","----"+s);
        }


        db.endTransaction();                                    //关闭事务
        db.close();

        return list;

    }

    //获取有异常性状的list
    public ArrayList<CharacterBean> getCharacterBeanList(String pz,String syrwbh,String mbmc,String syq){
        ArrayList<CharacterBean> list=new ArrayList<>();
        SQLiteDatabase  db = msoh.getWritableDatabase();
        MLog.e(pz+""+syrwbh+mbmc+syq);
        db.beginTransaction();// 开启事务
        Cursor c;
        if(syq==null||"".equals(syq))
             c = db.rawQuery("select * from Character where varieties = ? and experimentalNumber = ? and template =? and abnormal = ?",
                    new String[]{pz,syrwbh,mbmc,"1"});
            else
        c = db.rawQuery("select * from Character where varieties = ? and experimentalNumber = ? and template =? and growthPeriod = ? and abnormal = ?",
                new String[]{pz,syrwbh,mbmc,syq,"1"});
        while (c.moveToNext()) {
            CharacterBean characterBean = new CharacterBean();
            characterBean.characterId=c.getString(c.getColumnIndex("characterId"));
            characterBean.testNumber=c.getString(c.getColumnIndex("testNumber"));
            characterBean.observationMethod=c.getString(c.getColumnIndex("observationMethod"));
            characterBean.characterName=c.getString(c.getColumnIndex("characterName"));

            list.add(characterBean);
        }
        db.endTransaction();                                    //关闭事务
        db.close();
        return list;
    }
    //获取已保存的异常性状的异常值

    public String getCharacterBeanAbnormalContent(String tjbh,String xzmc){
        MLog.e(tjbh+xzmc);
        String abnormalContent = null;

        SQLiteDatabase  db = msoh.getWritableDatabase();
        if(db==null)
            return null;
        db.beginTransaction();// 开启事务
        Cursor c = db.rawQuery("select * from Character where testNumber = ? and characterName = ?",
                new String[]{tjbh,xzmc});
        while (c.moveToNext()) {
            abnormalContent=c.getString(c.getColumnIndex("abnormalContent"));


        }
        db.endTransaction();                                    //关闭事务
        db.close();
        return abnormalContent;
    }

    //查询测试编号（田间编号）下是否有异常数据
    public  String getCharacterAbnormal( String experimentalNumber,String tjbh){
        SQLiteDatabase  db = msoh.getWritableDatabase();

        db.beginTransaction();// 开启事务
        Cursor c = db.rawQuery("select * from Character where experimentalNumber = ? and testNumber = ?",
                new String[]{experimentalNumber,tjbh});

        String abnormal="0";
        while (c.moveToNext()) {
            CharacterBean characterBean=new CharacterBean();
            characterBean.abnormal=c.getString(c.getColumnIndex("abnormal"));
            if("1".equals(characterBean.abnormal)){
                abnormal="1";
                break;
            }

        }
        db.endTransaction();                                    //关闭事务
        db.close();
        return abnormal;
    }


    //获取已保存的性状list
    public ArrayList<CharacterBean>  getCharacterBeanList(){
        ArrayList<CharacterBean> list =new ArrayList<>();
        SQLiteDatabase  db = msoh.getWritableDatabase();

        if(db==null)
            return null;
        db.beginTransaction();// 开启事务
        Cursor c = db.rawQuery("select * from Character where fillInTheState = ?",
                new String[]{"1"});
        while (c.moveToNext()) {
            CharacterBean characterBean=new CharacterBean();
            characterBean.testNumber=c.getString(c.getColumnIndex("testNumber"));
            characterBean.experimentalNumber=c.getString(c.getColumnIndex("experimentalNumber"));
            characterBean.varieties=c.getString(c.getColumnIndex("varieties"));
            characterBean.groupId=Integer.valueOf(c.getString(c.getColumnIndex("groupId")));
            characterBean.varietyId=Integer.valueOf(c.getString(c.getColumnIndex("varietyId")));

//            characterBean.abnormal=c.getString(c.getColumnIndex("abnormal"));
//            characterBean.abnormal=getCharacterAbnormal(characterBean.testNumber);
            //去重
            int p=0;
            for(CharacterBean cb:list){
                if(characterBean.testNumber.equals(cb.testNumber)&&characterBean.experimentalNumber.equals(cb.experimentalNumber))
                p=1;
            }
            if(p==0)
            list.add(characterBean);
        }
        db.endTransaction();                                    //关闭事务
        db.close();

        return list;
    }


    //获取当前时间对应作物的生育期

    public String  getSYQ(String zw,String sybh,String mb,String s){

        SQLiteDatabase  db = msoh.getWritableDatabase();

        if(db==null)
            return "";
        db.beginTransaction();// 开启事务
        Cursor c = db.rawQuery("select * from Character where varieties = ? and experimentalNumber = ? and template = ?",
                new String[]{zw,sybh,mb});
        MLog.e("------==="+zw+sybh+mb+s+"====="+c.getCount());
        String growthPeriod = "";
        while (c.moveToNext()) {
            growthPeriod =c.getString(c.getColumnIndex("growthPeriod"));
            MLog.e("------==="+zw+sybh+mb+s+"====="+c.getCount()+growthPeriod);
            break;

        }

        db.endTransaction();                                    //关闭事务
        db.close();
        return growthPeriod;
    }


    //


    //        db.beginTransaction();
//        // 清空店铺信息Store表数据
//        db.delete("Character", null, null);
//        ContentValues values = new ContentValues();// 开启事务
//        values.put("characterId", "12");        //
//        values.put("characterName", "21");        //
//        values.put("characterDescribe", "yum1");        //
//        values.put("characterNumericalRange", "beijin");     //
//        values.put("abnormal", "110");       //
//        db.insert("Character", null, values);
////
//        db.insert("Character", null, values);
//        Cursor c = db.rawQuery("select * from Character where characterId = ?", new String[]{"12"});
//        while (c.moveToNext()) {
//          String s=  c.getString(c.getColumnIndex("characterNumericalRange"));
//            Log.e("db.String","----"+s);
//        }
//        db.endTransaction();                                    //关闭事务
//        db.close();
    public static ArrayList getSingle(ArrayList list){
        ArrayList newList = new ArrayList();     //创建新集合
        Iterator it = list.iterator();        //根据传入的集合(旧集合)获取迭代器
        while(it.hasNext()){          //遍历老集合
            Object obj = it.next();       //记录每一个元素
            if(!newList.contains(obj)){      //如果新集合中不包含旧集合中的元素
                newList.add(obj);       //将元素添加
            }
        }
        return newList;
    }

}
