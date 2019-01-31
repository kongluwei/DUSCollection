package com.pdwy.dus.collection.activity;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.pdwy.dus.collection.R;
import com.pdwy.dus.collection.core.BaseActivity;
import com.pdwy.dus.collection.model.bean.CharacterThresholdBean;
import com.pdwy.dus.collection.model.bean.CollectionTaskBean;
import com.pdwy.dus.collection.model.bean.CollectionTaskItemBean;
import com.pdwy.dus.collection.model.bean.TemplateBean;
import com.pdwy.dus.collection.model.db.InputData;
import com.pdwy.dus.collection.utils.MLog;
import com.pdwy.dus.collection.utils.PopupWindowUtils;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 采集设置页面
 * Author： by MR on 2018/9/10.
 */

public class CollectionSettingActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.ll_head_dus)
    LinearLayout ll_head_dus;
    @BindView(R.id.ll_head_personalcenter)
    LinearLayout ll_head_personalcenter;
    @BindView(R.id.ll_head_return)
    LinearLayout ll_head_return;

    @BindView(R.id.ll_head_homepage)
    LinearLayout ll_head_homepage;
    @BindView(R.id.ll_head_homepage_r)
    LinearLayout ll_head_homepage_r;
    @BindView(R.id.tv_head_title)
    TextView tv_head_title;
    @BindView(R.id.tv_syrwbh)
    TextView tv_syrwbh;
    @BindView(R.id.tv_mbmc)
    TextView tv_mbmc;
    @BindView(R.id.tv_syq)
    TextView tv_syq;
    @BindView(R.id.tv_gcfs)
    TextView tv_gcfs;
    @BindView(R.id.tv_xz)
    TextView tv_xz;
    @BindView(R.id.bt_queren)
    TextView bt_queren;

    @BindView(R.id.ll_pop1_ll)
    LinearLayout ll_pop;
    @BindView(R.id.ll_pop2)
     LinearLayout ll_pop2;
    @BindView(R.id.ll_pop3)
     LinearLayout ll_pop3;
    @BindView(R.id.ll_pop4)
     LinearLayout ll_pop4;
    @BindView(R.id.ll_pop5)
     LinearLayout ll_pop5;
    InputData inputData;
    ArrayList<TemplateBean> list;
    ArrayList<String> tjbhList;
    String syq[];
    String xzid[];
    PopupWindowUtils popupWindowUtils1;
    PopupWindowUtils popupWindowUtils2;
    PopupWindowUtils popupWindowUtils3;
    PopupWindowUtils popupWindowUtils4;
    PopupWindowUtils popupWindowUtils5;
    PopupWindowUtils pop;
    LinearLayout contentView;
    String[] data = new String[0];
    String pinzhong;
    Intent intent;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_collection_setting;
    }

    @Override
    protected void onCreateAfter() {
        ll_head_dus.setVisibility(View.INVISIBLE);
        ll_head_personalcenter.setVisibility(View.GONE);
        ll_head_return.setVisibility(View.VISIBLE);
        ll_head_homepage.setVisibility(View.VISIBLE);
        ll_head_homepage_r.setVisibility(View.VISIBLE);
        tv_head_title.setVisibility(View.VISIBLE);
        tv_head_title.setText("采集设置");
        bt_queren.setOnClickListener(this);
         inputData=new InputData(this);
           intent=getIntent();

         pinzhong=intent.getStringExtra("pinzhong");
       list=inputData.getMoBan(pinzhong,0);
       if(list==null||list.size()==0){
           Toast.makeText(this,"没有相关信息",Toast.LENGTH_LONG).show();
           return;
       }


        if("zidingyi".equals(intent.getStringExtra("activityName"))) {
           ll_pop.setVisibility(View.INVISIBLE);
           tjbhList=intent.getStringArrayListExtra("tjbhList");
       }
        if("saoma".equals(intent.getStringExtra("activityName"))) {
           tv_syrwbh.setText(intent.getStringExtra("csbh"));
            tjbhList=new ArrayList<>();
            tjbhList.add(tv_syrwbh.getText().toString());
        }

        tv_mbmc.setText(list.get(0).templateName);
         syq=list.get(0).containGrowthPeriod.split(",");

        tv_syq.setText(syq[0]);

        tv_gcfs.setText("个体观测性状采集".equals(intent.getStringExtra("gcfs"))?"个体":"群体");
        xzid=list.get(0).containCharacter.split(",");
        int i=0;
 do{
    tv_xz.setText(inputData.getCharacterName(xzid[i], list.get(0).templateName, tv_syq.getText().toString(), tv_gcfs.getText().toString(), intent.getStringExtra("syrwbh")));
     i++;
    if(i>=xzid.length)
        break;

}
while ("".equals(tv_xz.getText()));

        ll_pop2.setOnClickListener(this);
        ll_pop3.setOnClickListener(this);
        ll_pop4.setOnClickListener(this);
        ll_pop5.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_pop2:
                popupWindowUtils2=new PopupWindowUtils(getDataView(v),ll_pop2.getMeasuredWidth(),200);
                popupWindowUtils2.showAsDropDown(v, 0, 3);
                if(pop!=null)
                    pop.dismiss();
                pop=popupWindowUtils2;

                break;
            case R.id.ll_pop3:
                popupWindowUtils3=new PopupWindowUtils(getDataView(v),ll_pop3.getMeasuredWidth(),200);
                popupWindowUtils3.showAsDropDown(v, 0, 3);
                if(pop!=null)
                    pop.dismiss();
                pop=popupWindowUtils3;

                break;
            case R.id.ll_pop4:
                popupWindowUtils4=new PopupWindowUtils(getDataView(v),ll_pop4.getMeasuredWidth(),200);
                popupWindowUtils4.showAsDropDown(v, 0, 3);
                if(pop!=null)
                    pop.dismiss();
                pop=popupWindowUtils4;

                break;
            case R.id.ll_pop5:
                popupWindowUtils5=new PopupWindowUtils(getDataView(v),ll_pop5.getMeasuredWidth(),200);
                popupWindowUtils5.showAsDropDown(v, 0, 3);

                if(pop!=null)
                    pop.dismiss();
                pop=popupWindowUtils5;
                break;
            case R.id.bt_queren:
                if("zidingyi".equals(intent.getStringExtra("activityName"))) {
                    if("".equals(tv_xz.getText().toString())||tv_xz.getText().toString()==null){

                        Toast.makeText(this,"没有该数据",Toast.LENGTH_LONG).show();
                        return;
                    }
                    Intent intent1=new Intent(CollectionSettingActivity.this,CollectionManagementActivity.class);
                    intent1.putExtra("activityName",intent.getStringExtra("activityName"));
                    intent1.putExtra("pinzhong",pinzhong);
                    intent1.putExtra("syrwbh",intent.getStringExtra("syrwbh"));
                    intent1.putExtra("mbmc",tv_mbmc.getText().toString());
                    intent1.putExtra("syq",tv_syq.getText().toString());
                    intent1.putExtra("gcfs",tv_gcfs.getText().toString());
                    intent1.putExtra("xz",tv_xz.getText().toString());
                    intent1.putStringArrayListExtra("tjbhList",tjbhList);
                    startActivity(intent1);
                }
                if("saoma".equals(intent.getStringExtra("activityName"))) {
//                    Intent intent1=new Intent(CollectionSettingActivity.this,CollectionManagementActivity.class);
//                    intent1.putExtra("activityName",intent.getStringExtra("activityName"));
//                    intent1.putExtra("pinzhong",pinzhong);
//                    intent1.putStringArrayListExtra("tjbhList",tjbhList);
//                    startActivity(intent1);
                    if("".equals(tv_xz.getText().toString())||tv_xz.getText().toString()==null){

                        Toast.makeText(this,"没有该数据",Toast.LENGTH_LONG).show();
                        return;
                    }
                    Intent intent1=new Intent(CollectionSettingActivity.this,CollectionManagementActivity.class);
                    intent1.putExtra("activityName","zidingyi");
                    intent1.putExtra("pinzhong",pinzhong);
                    intent1.putExtra("syrwbh",intent.getStringExtra("syrwbh"));
                    intent1.putExtra("mbmc",tv_mbmc.getText().toString());
                    intent1.putExtra("syq",tv_syq.getText().toString());
                    intent1.putExtra("gcfs",tv_gcfs.getText().toString());
                    intent1.putExtra("xz",tv_xz.getText().toString());
                    ArrayList<String>listXzmc=new ArrayList<>();
                    listXzmc.add(tv_xz.getText().toString());
                    intent1.putStringArrayListExtra("tjbhList",tjbhList);

                    intent1.putStringArrayListExtra("listXzmc",listXzmc);
                    startActivity(intent1);
                }
                break;
        }
    }

    private  View getDataView(final View v) {
        // 用于PopupWindow的View

        switch (v.getId()) {
            case R.id.ll_pop2:
                data = new String[list.size()];
                for (int i = 0; i < list.size(); i++) {
                    data[i] = list.get(i).templateName;
                }
            break;
            case R.id.ll_pop3:

                data = new String[syq.length];
                for (int i = 0; i < syq.length; i++) {
                    data[i] = syq[i];
                }
                break;
            case R.id.ll_pop4:

                data = new String[2];
                data[0]="个体";
                data[1]="群体";
                break;
            case R.id.ll_pop5:
             MLog.e("----------");
                data = new String[xzid.length];
                MLog.e("----------"+data.length);
                for (int i = 0; i < xzid.length; i++) {
                    data[i] = inputData.getCharacterName(xzid[i],tv_mbmc.getText().toString(),tv_syq.getText().toString(),tv_gcfs.getText().toString(),intent.getStringExtra("syrwbh"));
                }
                MLog.e("----------"+data.length);
        }
        final  String[] data1 = data;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, data1);
        contentView= (LinearLayout) LayoutInflater.from(this).inflate(R.layout.ll_pop, null, false);
        ListView lv= (ListView) contentView.getChildAt(0);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (v.getId()) {
                    case R.id.ll_pop2:
                        tv_gcfs.setText("群体");
                        setTextViewText(v,data1,position);
                        syq=list.get(position).containGrowthPeriod.split(",");
                        tv_syq.setText(syq[0]);
                        xzid=list.get(position).containCharacter.split(",");
                        tv_xz.setText(inputData.getCharacterName(xzid[0],list.get(position).templateName,tv_syq.getText().toString(),tv_gcfs.getText().toString(),intent.getStringExtra("syrwbh")));
                        popupWindowUtils2.dismiss();
                        break;
                    case R.id.ll_pop3:
                        tv_gcfs.setText("群体");
                        setTextViewText(v,data1,position);

                       tv_xz.setText(inputData.getCharacterName(xzid[0],tv_mbmc.getText().toString(),syq[position],tv_gcfs.getText().toString(),intent.getStringExtra("syrwbh")));
                        popupWindowUtils3.dismiss();
                        break;
                    case R.id.ll_pop4:
                        setTextViewText(v,data1,position);
                        tv_xz.setText(inputData.getCharacterName(xzid[0],tv_mbmc.getText().toString(),tv_syq.getText().toString(),tv_gcfs.getText().toString(),intent.getStringExtra("syrwbh")));

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
    void setTextViewText(View v,String[] data,int p){
        LinearLayout ll1= (LinearLayout)v;
        TextView tv1= (TextView) ll1.getChildAt(0);
        tv1.setText(data[p]);

    }
}
