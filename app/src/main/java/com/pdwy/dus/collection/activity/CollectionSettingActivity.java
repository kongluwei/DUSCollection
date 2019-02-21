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
import java.util.HashSet;
import java.util.List;

import butterknife.BindView;

/**
 * 扫描采集设置页面
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
    ArrayList<com.pdwy.dus.collection.http.bean.TemplateBean> list;
    ArrayList<String> tjbhList;
    List<String> syq;
    ArrayList<String> xzid;
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
        list=inputData.getCollectionMoBan(intent.getStringExtra("syrwbh"));
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

        tv_mbmc.setText(list.get(0).getCollectiontemplatename());

        //获取 生育期list
        List<String> sortcharcodeList=inputData.getContainGrowthPeriod(inputData.getGroupId(intent.getStringExtra("syrwbh")));
        //去重
        sortcharcodeList = new ArrayList<>(new HashSet<>(sortcharcodeList));
        syq= sortcharcodeList;
        tv_syq.setText("全部");

        tv_gcfs.setText("全部");

        xzid=inputData.getCharacterList(inputData.getTemplate(intent.getStringExtra("syrwbh")),tv_syq.getText().toString(),tv_gcfs.getText().toString());
        tv_xz.setText("全部");


//        int i=0;
// do{
//    tv_xz.setText(xzid.get(i));
//     i++;
//    if(i>=xzid.size())
//        break;
//
//}
//while ("".equals(tv_xz.getText()));


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
                    if("".equals(tv_xz.getText().toString())||tv_xz.getText().toString()==null||xzid.size()<1){

                        Toast.makeText(this,"没有该数据",Toast.LENGTH_LONG).show();
                        return;
                    }
                    Intent intent1=new Intent(CollectionSettingActivity.this,CollectionManagementActivity.class);
                    intent1.putExtra("activityName",intent.getStringExtra("activityName"));
                    intent1.putExtra("pinzhong",pinzhong);
                    intent1.putExtra("syrwbh",intent.getStringExtra("syrwbh"));
                    ArrayList<String> listString =inputData.getMoBan(intent.getStringExtra("syrwbh"));

                    intent1.putExtra("mbmc",listString.get(0));

                    intent1.putExtra("syq","");
                    intent1.putExtra("gcfs",tv_gcfs.getText().toString());
                    intent1.putExtra("xz",tv_xz.getText().toString());
                    ArrayList<String>listXzmc=new ArrayList<>();
                    listXzmc.add(tv_xz.getText().toString());
                    intent1.putStringArrayListExtra("tjbhList",tjbhList);

                    if("全部".equals(tv_xz.getText().toString())) {
                        intent1.putStringArrayListExtra("listXzmc", xzid);
                    }
                        else
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
                    data[i] = list.get(i).getCollectiontemplatename();
                }
            break;
            case R.id.ll_pop3:

                data = new String[syq.size()+1];
                data[0]="全部";
                for (int i = 0; i < syq.size(); i++) {
                    data[i+1] = syq.get(i);
                }
                break;
            case R.id.ll_pop4:

                data = new String[3];
                data[0]="全部";
                data[1]="个体";
                data[2]="群体";
                break;
            case R.id.ll_pop5:
                if(xzid.size()<1) {
                    data = new String[0];
                    break;
                }
                data = new String[xzid.size()+1];
                MLog.e("----------"+data.length);
                data[0]="全部";
                for (int i = 0; i < xzid.size(); i++) {
                    data[i+1] = xzid.get(i);
                }
                MLog.e("----------"+data.length);
                break;
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
                        tv_gcfs.setText("全部");
                        setTextViewText(v,data1,position);
                        tv_syq.setText("全部");
                        xzid=inputData.getCharacterList(inputData.getTemplate(intent.getStringExtra("syrwbh")),tv_syq.getText().toString(),tv_gcfs.getText().toString());

                        tv_xz.setText("全部");
                        popupWindowUtils2.dismiss();
                        break;
                    case R.id.ll_pop3:
                        tv_gcfs.setText("全部");
                        setTextViewText(v,data1,position);

                        xzid=inputData.getCharacterList(inputData.getTemplate(intent.getStringExtra("syrwbh")),tv_syq.getText().toString(),tv_gcfs.getText().toString());

                            tv_xz.setText("全部");
                        popupWindowUtils3.dismiss();
                        break;
                    case R.id.ll_pop4:
                        setTextViewText(v,data1,position);
//                        tv_xz.setText(inputData.getCharacterName(xzid[0],tv_mbmc.getText().toString(),tv_syq.getText().toString(),tv_gcfs.getText().toString(),intent.getStringExtra("syrwbh")));

                        xzid=inputData.getCharacterList(inputData.getTemplate(intent.getStringExtra("syrwbh")),tv_syq.getText().toString(),tv_gcfs.getText().toString());

                            tv_xz.setText("全部");

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
