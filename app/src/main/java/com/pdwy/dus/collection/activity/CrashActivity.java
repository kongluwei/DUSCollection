package com.pdwy.dus.collection.activity;

import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pdwy.dus.collection.R;
import com.pdwy.dus.collection.core.BaseActivity;
import com.pdwy.dus.collection.model.bean.CharacterThresholdBean;
import com.pdwy.dus.collection.model.db.InputData;
import com.pdwy.dus.collection.utils.MLog;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 选择性状
 * Author： by MR on 2018/9/19.
 */

public class CrashActivity extends BaseActivity {
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

    @BindView(R.id.ll_xzsyq_syq)
    LinearLayout ll_xzsyq_syq;
    Intent intent;
    String pinzhong;
    String gcfs;
    String syrwbh;
    ArrayList<String> tjbhList;
    InputData inputData;
    String mbmc;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_crash;
    }

    @Override
    protected void onCreateAfter() {
        ll_head_dus.setVisibility(View.INVISIBLE);
        ll_head_personalcenter.setVisibility(View.GONE);
        ll_head_return.setVisibility(View.VISIBLE);
        ll_head_homepage.setVisibility(View.VISIBLE);
        ll_head_homepage_r.setVisibility(View.VISIBLE);
        tv_head_title.setVisibility(View.VISIBLE);
        tv_head_title.setText("选择性状");

          inputData=new InputData(this);
         intent=getIntent();
         pinzhong=intent.getStringExtra("pinzhong");
          gcfs=intent.getStringExtra("gcfs");
         syrwbh=intent.getStringExtra("syrwbh");
        tjbhList=intent.getStringArrayListExtra("tjbhList");
         mbmc=inputData.getTemplate(syrwbh);

          MLog.e(syrwbh);
          //获取 生育期list
        List<String> sortcharcodeList=inputData.getContainGrowthPeriod(inputData.getGroupId(syrwbh));
        //去重
        sortcharcodeList = new ArrayList<>(new HashSet<>(sortcharcodeList));
        for (int i=0;i<sortcharcodeList.size();i++) {

            LinearLayout linearLayout = (LinearLayout) LinearLayout.inflate(this, R.layout.activity_crash_ll_item, null);
            TextView tv=(TextView)linearLayout.findViewById(R.id.tv_xzmc);
            tv.setText(sortcharcodeList.get(i));
            ArrayList<String> list=inputData.getCharacterList(mbmc,sortcharcodeList.get(i),gcfs);
            MLog.e(mbmc+"====="+list.size());
            for (String xzmc:list) {
                LinearLayout linearLayout2 = (LinearLayout) LinearLayout.inflate(this, R.layout.activity_crash_ll_item_item, null);
                final CheckBox cb2=(CheckBox)linearLayout2.findViewById(R.id.cb_xzmc);
                TextView tv2=(TextView)linearLayout2.findViewById(R.id.tv_xzmc);

                tv2.setText(xzmc);
                tv2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(cb2.isChecked())
                        cb2.setChecked(false);
                        else
                            cb2.setChecked(true);
                    }
                });
                linearLayout.addView(linearLayout2);
            }
            ll_xzsyq_syq.addView(linearLayout);
        }
    }

    @OnClick({R.id.tv_queren})
    public void OnClick(View v){

        switch (v.getId()){

            case R.id.tv_queren:
                ArrayList<String> listXzmc = new ArrayList<>();
                for(int i=1;i<ll_xzsyq_syq.getChildCount();i++) {
                    LinearLayout ll1= (LinearLayout) ll_xzsyq_syq.getChildAt(i);
                    for(int y=2;y<ll1.getChildCount();y++){

                        LinearLayout ll2= (LinearLayout) ll1.getChildAt(y);
                        CheckBox cb_xzmc=(CheckBox)ll2.findViewById(R.id.cb_xzmc);
                        TextView tv=(TextView)ll2.findViewById(R.id.tv_xzmc);
                        MLog.e(tv.getText().toString());

                        if(cb_xzmc.isChecked())
                        listXzmc.add(tv.getText().toString());

                    }
                }
//                CharacterThresholdBean characterThresholdBean=new CharacterThresholdBean();
//                characterThresholdBean.characterId=c.getString(c.getColumnIndex("characterId"));
//                characterThresholdBean.characterName=c.getString(c.getColumnIndex("characterName"));
//                characterThresholdBean.varieties=c.getString(c.getColumnIndex("varieties"));
//                characterThresholdBean.template=c.getString(c.getColumnIndex("template"));
//                characterThresholdBean.observationMethod=c.getString(c.getColumnIndex("observationMethod"));
//                characterThresholdBean.numericalRangeOfCharacters=c.getString(c.getColumnIndex("numericalRangeOfCharacters"));
//                characterThresholdBean.abnormal=c.getString(c.getColumnIndex("abnormal"));
//                Intent intent1=new Intent(this,CustomCollectionManagementActivity.class);
                Intent intent1=new Intent(this,CollectionManagementActivity.class);
                intent1.putExtra("activityName",intent.getStringExtra("activityName"));

                intent1.putExtra("pinzhong",pinzhong);
                intent1.putExtra("syrwbh",syrwbh);
                intent1.putExtra("mbmc",mbmc);
//                intent1.putExtra("syq","发芽期");
                 if("个体观测性状采集".equals(gcfs))
                intent1.putExtra("gcfs","个体");
                else if("群体观测性状采集".equals(gcfs))
                    intent1.putExtra("gcfs","群体");
                 else if("异常性状采集".equals(gcfs))
                     intent1.putExtra("gcfs","异常");
                if(listXzmc==null||listXzmc.size()==0){
                    Toast.makeText(this,"请选择性状",Toast.LENGTH_LONG).show();
                    return;
                }
                intent1.putExtra("xz",listXzmc.get(0));
                intent1.putStringArrayListExtra("tjbhList",tjbhList);

                intent1.putStringArrayListExtra("listXzmc",listXzmc);
                startActivity(intent1);
                break;

        }
    }
}
