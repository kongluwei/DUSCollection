package com.pdwy.dus.collection.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pdwy.dus.collection.MainActivity;
import com.pdwy.dus.collection.R;
import com.pdwy.dus.collection.activity.CollectionManagementActivity;
import com.pdwy.dus.collection.activity.CrashActivity;
import com.pdwy.dus.collection.activity.PersonalCenterActivity;
import com.pdwy.dus.collection.core.BaseActivity;
import com.pdwy.dus.collection.core.BaseFragment;
import com.pdwy.dus.collection.core.ExcelListActivity;
import com.pdwy.dus.collection.http.bean.GrowthPeriodBean;
import com.pdwy.dus.collection.http.bean.TaskBean;
import com.pdwy.dus.collection.http.bean.TemplateBean;
import com.pdwy.dus.collection.model.bean.CharacterThresholdBean;
import com.pdwy.dus.collection.model.bean.CharacterlistBean;
import com.pdwy.dus.collection.model.bean.CollectionTaskBean;
import com.pdwy.dus.collection.model.bean.CollectionTaskItemBean;
import com.pdwy.dus.collection.model.db.InputData;
import com.pdwy.dus.collection.utils.MLog;
import com.pdwy.dus.collection.utils.PopupWindowUtils;
import com.pdwy.dus.collection.utils.ProgressDialogUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collector;

import butterknife.OnClick;

/**
 * 采集管理Fragment
 * Author： by MR on 2018/8/7.
 */

public class CollectionManagementFragment extends BaseFragment implements View.OnClickListener {
    private LinearLayout ll_pop;
    private LinearLayout ll_pop1;
    private LinearLayout ll_pop2;
    private LinearLayout ll_pop3;
    private LinearLayout ll_pop4;
    private LinearLayout ll_pop5;
    private Button bt_queren;
    LinearLayout  ll_shouye;
    LinearLayout  ll_geren;
    PopupWindowUtils popupWindowUtils;
    PopupWindowUtils popupWindowUtils1;
    PopupWindowUtils popupWindowUtils2;
    PopupWindowUtils popupWindowUtils3;
    PopupWindowUtils popupWindowUtils4;
    PopupWindowUtils popupWindowUtils5;
    PopupWindowUtils pop;
    //PopupWindow加载的布局
    LinearLayout contentView;
    //标题
    private TextView tv_pop;
    private TextView tv_syrwbh;
    private TextView tv_mbmc;
    private TextView tv_syq;
    private TextView tv_xz;
    private TextView tv_cfs;
    private Dialog waitDialog;
    InputData inputData;
    String[] data = new String[0];
    //采集模板
    ArrayList<TemplateBean> listString;
    //生育期
    GrowthPeriodBean.DataBean dataBean;
//性状list
    List<String> xzList;
    //异常RadioButton
    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_collection_management_layout;
    }

    //绑定控件
    @Override
    protected void setUpView() {
         inputData=new InputData(getActivity());

        ll_pop=mContentView.findViewById(R.id.ll_pop);
        ll_pop.setOnClickListener(this);
        ll_pop1=mContentView.findViewById(R.id.ll_pop1);
        ll_pop1.setOnClickListener(this);
        ll_pop2=mContentView.findViewById(R.id.ll_pop2);
        ll_pop2.setOnClickListener(this);
        ll_pop3=mContentView.findViewById(R.id.ll_pop3);
//        ll_pop3.setOnClickListener(this);
        ll_pop4=mContentView.findViewById(R.id.ll_pop4);
        ll_pop4.setOnClickListener(this);
        ll_pop5=mContentView.findViewById(R.id.ll_pop5);
        ll_pop5.setOnClickListener(this);
        bt_queren=mContentView.findViewById(R.id.bt_queren);
        bt_queren.setOnClickListener(this);
        tv_pop=mContentView.findViewById(R.id.tv_pop);
        tv_syrwbh=mContentView.findViewById(R.id.tv_syrwbh);
        tv_mbmc=mContentView.findViewById(R.id.tv_mbmc);
        tv_syq=mContentView.findViewById(R.id.tv_syq);
        tv_xz=mContentView.findViewById(R.id.tv_xz);
        tv_cfs=mContentView.findViewById(R.id.tv_cfs);
        ll_shouye=     mContentView.findViewById(R.id.ll_shouye);
        ll_shouye.setOnClickListener(this);
        ll_geren=     mContentView.findViewById(R.id.ll_geren);
        ll_geren.setOnClickListener(this);
    }

    //初始化数据
    @Override
    protected void setUpData() {
        tv_pop.setText(getArguments().getString("pinzhong"));
        CollectionTaskBean collectionTaskBean=new CollectionTaskBean();
        collectionTaskBean.Varieties=getArguments().getString("pinzhong");
        ArrayList<CollectionTaskItemBean> listCollectionTaskItemBean=inputData.getCollectionTaskItemBeanList(collectionTaskBean,1);
        tv_syrwbh.setText(listCollectionTaskItemBean.get(0).taskName);
        listString =inputData.getCollectionMoBan(tv_syrwbh.getText().toString());
        tv_mbmc.setText(listString.get(0).getCollectiontemplatename());
        xzList=new ArrayList<>();
        dataBean=inputData.getSYQ(getArguments().getString("pinzhong"),tv_syrwbh.getText().toString(),tv_mbmc.getText().toString());
        if(dataBean!=null) {
            tv_syq.setText(dataBean.getRemarks());


            String collectiontemplatename = listString.get(0).getCharacterlist();
            Type listType = new TypeToken<LinkedList<CharacterlistBean>>() {
            }.getType();
            Gson gson = new Gson();
            LinkedList<CharacterlistBean> characterlist = gson.fromJson(collectiontemplatename, listType);
            MLog.e(listString.get(0).getCharacterlist() + "=========" + characterlist.size());
            String[] sortcharcode = dataBean.getSortcharcode().split(",");
            MLog.e(dataBean.getSortcharcode() + "=========" + sortcharcode.length);
            for (CharacterlistBean c : characterlist) {

                for (String s2 : sortcharcode) {
                    if (c.getStdCharCode().equals(s2)) {

                        xzList.add(c.getName());
                    }
                }

            }
            tv_xz.setText(xzList.get(0));
        }else {
            tv_syq.setText("不在时间范围");
            tv_xz.setText("");
        }
    }
    private  View getDataView(final View v){
        // 用于PopupWindow的View

        switch (v.getId()){
            case R.id.ll_pop:
                ArrayList<CollectionTaskBean> listCollectionTaskBean= inputData.getCollectionTaskBeanList(1);
                data = new String[listCollectionTaskBean.size()];
                for(int i=0;i<listCollectionTaskBean.size();i++){
                    data[i]=listCollectionTaskBean.get(i).Varieties;
                }

                break;
            case R.id.ll_pop1:
                CollectionTaskBean collectionTaskBean=new CollectionTaskBean();
                collectionTaskBean.Varieties=tv_pop.getText().toString();
                ArrayList<CollectionTaskItemBean> listCollectionTaskItemBean=inputData.getCollectionTaskItemBeanList(collectionTaskBean,1);
                data = new String[listCollectionTaskItemBean.size()];
                for(int i=0;i<listCollectionTaskItemBean.size();i++){
                    data[i]=listCollectionTaskItemBean.get(i).taskName;
                }
                break;
            case R.id.ll_pop2:
                ArrayList<TemplateBean> listString =inputData.getCollectionMoBan(tv_syrwbh.getText().toString());
                data = new String[listString.size()];
                for(int i=0;i<listString.size();i++){
                    data[i]=listString.get(i).getCollectiontemplatename();
                }
                break;
            case R.id.ll_pop3:
                data = new  String[]{"生育期1", "生育期2", "生育期3", "生育期4"};
                break;
            case R.id.ll_pop4:
                ArrayList<CharacterThresholdBean> listCharacterThresholdBean =inputData.getCharacterThresholdBeanlist(tv_pop.getText().toString(),tv_syrwbh.getText().toString(),tv_mbmc.getText().toString(),tv_syq.getText().toString(),1,null);
                data = new String[xzList.size()];
                for(int i=0;i<xzList.size();i++){
                    data[i]=xzList.get(i);
                }

                break;
            case R.id.ll_pop5:
                data = new String[]{"1", "2", "3"};

                break;

        }
        final  String[] data1 = data;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_list_item_1, data1);

        contentView= (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.ll_pop, null, false);
        ListView lv= (ListView) contentView.getChildAt(0);
        lv.setAdapter(adapter);
        //pop里的listview条目点击事件
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (v.getId()){
                    case R.id.ll_pop:
                        tv_pop.setText(data1[position]);
                        CollectionTaskBean collectionTaskBean=new CollectionTaskBean();
                        collectionTaskBean.Varieties=tv_pop.getText().toString();
//                        ArrayList<CollectionTaskItemBean> listCollectionTaskItemBean=inputData.getCollectionTaskItemBeanList(collectionTaskBean,1);
//                        tv_syrwbh.setText(listCollectionTaskItemBean.get(0).taskName);
//                        ArrayList<String> listString =inputData.getMoBan(tv_syrwbh.getText().toString());
//                        tv_mbmc.setText(listString.get(0));
//                        tv_syq.setText(inputData.getSYQ(tv_pop.getText().toString(),tv_syrwbh.getText().toString(),tv_mbmc.getText().toString()).getRemarks());
//                        ArrayList<CharacterThresholdBean> listCharacterThresholdBean =inputData.getCharacterThresholdBeanlist(tv_pop.getText().toString(),tv_syrwbh.getText().toString(),tv_mbmc.getText().toString(),tv_syq.getText().toString(),1,null);
//                        tv_xz.setText(listCharacterThresholdBean.get(0).characterName);

                        ArrayList<CollectionTaskItemBean> listCollectionTaskItemBean=inputData.getCollectionTaskItemBeanList(collectionTaskBean,1);
                        tv_syrwbh.setText(listCollectionTaskItemBean.get(0).taskName);

                        listString =inputData.getCollectionMoBan(tv_syrwbh.getText().toString());
                        tv_mbmc.setText(listString.get(0).getCollectiontemplatename());
                        xzList=new ArrayList<>();
                        dataBean=inputData.getSYQ(getArguments().getString("pinzhong"),tv_syrwbh.getText().toString(),tv_mbmc.getText().toString());
                        if(dataBean!=null){
                        tv_syq.setText(dataBean.getRemarks());


                        String collectiontemplatename= listString.get(0).getCharacterlist();
                        Type listType = new TypeToken<LinkedList<CharacterlistBean>>(){}.getType();
                        Gson gson=new Gson();
                        LinkedList<CharacterlistBean> characterlist=gson.fromJson(collectiontemplatename, listType);
                        MLog.e(listString.get(0).getCharacterlist()+"========="+characterlist.size());
                        String [] sortcharcode= dataBean.getSortcharcode().split(",");
                        MLog.e(dataBean.getSortcharcode()+"========="+sortcharcode.length);
                        for(CharacterlistBean c:characterlist){

                            for(String s2:sortcharcode){
                                if(c.getStdCharCode().equals(s2)){

                                    xzList.add(c.getName());
                                }
                            }

                        }
                        tv_xz.setText(xzList.get(0));
                }else {
                    tv_syq.setText("不在时间范围");
                    tv_xz.setText("");
                }

                        popupWindowUtils.dismiss();
                        break;
                    case R.id.ll_pop1:
                        setTextViewText(v,data1,position);
//                        ArrayList<String> listString1 =inputData.getMoBan(tv_syrwbh.getText().toString());
//                        tv_mbmc.setText(listString1.get(0));
//                        tv_syq.setText(inputData.getSYQ(tv_pop.getText().toString(),tv_syrwbh.getText().toString(),tv_mbmc.getText().toString()).getRemarks());
//                        ArrayList<CharacterThresholdBean> listCharacterThresholdBean1 =inputData.getCharacterThresholdBeanlist(tv_pop.getText().toString(),tv_syrwbh.getText().toString(),tv_mbmc.getText().toString(),tv_syq.getText().toString(),1,null);
//                        tv_xz.setText(listCharacterThresholdBean1.get(0).characterName);

                        listString =inputData.getCollectionMoBan(tv_syrwbh.getText().toString());
                        tv_mbmc.setText(listString.get(0).getCollectiontemplatename());
                        dataBean=inputData.getSYQ(getArguments().getString("pinzhong"),tv_syrwbh.getText().toString(),tv_mbmc.getText().toString());
                        xzList = new ArrayList<>();
                        if(dataBean!=null) {
                            tv_syq.setText(dataBean.getRemarks());



                            String collectiontemplatename1 = listString.get(0).getCharacterlist();
                            Type listType1 = new TypeToken<LinkedList<CharacterlistBean>>() {
                            }.getType();
                            Gson gson1 = new Gson();
                            LinkedList<CharacterlistBean> characterlist1 = gson1.fromJson(collectiontemplatename1, listType1);
                            MLog.e(listString.get(0).getCharacterlist() + "=========" + characterlist1.size());
                            String[] sortcharcode1 = dataBean.getSortcharcode().split(",");
                            MLog.e(dataBean.getSortcharcode() + "=========" + sortcharcode1.length);
                            for (CharacterlistBean c : characterlist1) {

                                for (String s2 : sortcharcode1) {
                                    if (c.getStdCharCode().equals(s2)) {

                                        xzList.add(c.getName());
                                    }
                                }

                            }
                            tv_xz.setText(xzList.get(0));
                        }else {
                            tv_syq.setText("不在时间范围");
                            tv_xz.setText("");
                        }
                        popupWindowUtils1.dismiss();
                        break;
                    case R.id.ll_pop2:
                        setTextViewText(v,data1,position);
//                        tv_syq.setText(inputData.getSYQ(tv_pop.getText().toString(),tv_syrwbh.getText().toString(),tv_mbmc.getText().toString()).getRemarks());
//                        ArrayList<CharacterThresholdBean> listCharacterThresholdBean2 =inputData.getCharacterThresholdBeanlist(tv_pop.getText().toString(),tv_syrwbh.getText().toString(),tv_mbmc.getText().toString(),tv_syq.getText().toString(),1,null);
//                        tv_xz.setText(listCharacterThresholdBean2.get(0).characterName);
                        dataBean=inputData.getSYQ(getArguments().getString("pinzhong"),tv_syrwbh.getText().toString(),tv_mbmc.getText().toString());
                        xzList = new ArrayList<>();
                        if(dataBean!=null) {
                            tv_syq.setText(dataBean.getRemarks());



                            String collectiontemplatename1 = listString.get(0).getCharacterlist();
                            Type listType1 = new TypeToken<LinkedList<CharacterlistBean>>() {
                            }.getType();
                            Gson gson1 = new Gson();
                            LinkedList<CharacterlistBean> characterlist1 = gson1.fromJson(collectiontemplatename1, listType1);
                            MLog.e(listString.get(0).getCharacterlist() + "=========" + characterlist1.size());
                            String[] sortcharcode1 = dataBean.getSortcharcode().split(",");
                            MLog.e(dataBean.getSortcharcode() + "=========" + sortcharcode1.length);
                            for (CharacterlistBean c : characterlist1) {

                                for (String s2 : sortcharcode1) {
                                    if (c.getStdCharCode().equals(s2)) {

                                        xzList.add(c.getName());
                                    }
                                }

                            }
                            tv_xz.setText(xzList.get(0));
                        }else {
                            tv_syq.setText("不在时间范围");
                            tv_xz.setText("");
                        }
                        popupWindowUtils2.dismiss();
                        break;
                    case R.id.ll_pop3:
                        setTextViewText(v,data1,position);
                        popupWindowUtils3.dismiss();
                        break;
                    case R.id.ll_pop4:
                        setTextViewText(v,data1,position);
                        popupWindowUtils4.dismiss();
                        break;
                    case R.id.ll_pop5:
                        setTextViewText(v,data1,position);
                        popupWindowUtils5.dismiss();
                        break;

                }

            }
        });
        return contentView;
    }
//控件点击事件
    @Override
    public void onClick(View v) {
                  switch (v.getId()){
                      case R.id.ll_pop:
                          popupWindowUtils=new PopupWindowUtils(getDataView(v),150,110);
                          popupWindowUtils.showAsDropDown(v, -15, 10);
                          if(pop!=null)
                              pop.dismiss();
                          pop=popupWindowUtils;
                          break;
                      case R.id.ll_pop1:
                          popupWindowUtils1=new PopupWindowUtils(getDataView(v),ll_pop1.getMeasuredWidth(),200);
                          popupWindowUtils1.showAsDropDown(v, 0, 3);
                          if(pop!=null)
                              pop.dismiss();
                          pop=popupWindowUtils1;
                          break;
                      case R.id.ll_pop2:
                          popupWindowUtils2=new PopupWindowUtils(getDataView(v),ll_pop1.getMeasuredWidth(),200);
                          popupWindowUtils2.showAsDropDown(v, 0, 3);
                          if(pop!=null)
                              pop.dismiss();
                          pop=popupWindowUtils2;
                          break;
                      case R.id.ll_pop3:
                          popupWindowUtils3=new PopupWindowUtils(getDataView(v),ll_pop1.getMeasuredWidth(),200);
                          popupWindowUtils3.showAsDropDown(v, 0, 3);
                          if(pop!=null)
                              pop.dismiss();
                          pop=popupWindowUtils3;
                          break;
                      case R.id.ll_pop4:
                          popupWindowUtils4=new PopupWindowUtils(getDataView(v),ll_pop1.getMeasuredWidth(),200);
                          popupWindowUtils4.showAsDropDown(v, 0, 3);
                          if(pop!=null)
                              pop.dismiss();
                          pop=popupWindowUtils4;
                          break;
                      case R.id.ll_pop5:
                          popupWindowUtils5=new PopupWindowUtils(getDataView(v),ll_pop1.getMeasuredWidth(),150);
                          popupWindowUtils5.showAsDropDown(v, 0, 3);
                          if(pop!=null)
                              pop.dismiss();
                          pop=popupWindowUtils5;
                          break;
                      case R.id.bt_queren:
                          if(xzList==null||xzList.size()<1) {
                              Toast.makeText(getActivity(), "没有性状数据", Toast.LENGTH_SHORT).show();
                              return;
                          }

                          waitDialog = ProgressDialogUtils.showDialogNew(getActivity(), "初始化列表", true);
                          waitDialog.show();
//                          Intent  integer=new Intent (getActivity(), ExcelListActivity.class);

                          Intent  integer=new Intent (getActivity(), CollectionManagementActivity.class);

                          integer.putExtra("pinzhong",tv_pop.getText().toString());
                          integer.putExtra("syrwbh",tv_syrwbh.getText().toString());
                          ArrayList<String> listString =inputData.getMoBan(tv_syrwbh.getText().toString());

                          integer.putExtra("mbmc",listString.get(0));
                          integer.putExtra("syq",tv_syq.getText().toString());
                          integer.putExtra("xz",tv_xz.getText().toString());
                          integer.putExtra("cfs",tv_cfs.getText().toString());
                          ArrayList<CharacterThresholdBean> listCharacterThresholdBean =inputData.getCharacterThresholdBeanlist(tv_pop.getText().toString(),tv_syrwbh.getText().toString(),tv_mbmc.getText().toString(),tv_syq.getText().toString(),1,null);
                          ArrayList<String> listXzmc=new ArrayList<>() ;
                          for(int i=0;i<listCharacterThresholdBean.size();i++){
                              listXzmc.add(listCharacterThresholdBean.get(i).characterName);
                          }
                          integer.putStringArrayListExtra("listXzmc", (ArrayList<String>) xzList);
startActivity(integer);
//                          waitDialog.dismiss();
                          break;
                      case R.id.ll_shouye:
                          waitDialog = ProgressDialogUtils.showDialogNew(getActivity(), "读取中", true);
                          waitDialog.show();
                          startActivity(new Intent(getActivity(), MainActivity.class));
                          getActivity().finish();
                          break;

                      case R.id.ll_geren:
                          startActivity(new Intent(getActivity(), PersonalCenterActivity.class));
                          break;
                  }
    }

    void setTextViewText(View v,String[] data,int p){
        LinearLayout ll1= (LinearLayout)v;
        TextView tv1= (TextView) ll1.getChildAt(0);
        tv1.setText(data[p]);

    }

    @Override
    public void onPause() {
        super.onPause();
        if(waitDialog!=null)
            waitDialog.dismiss();
    }


}
