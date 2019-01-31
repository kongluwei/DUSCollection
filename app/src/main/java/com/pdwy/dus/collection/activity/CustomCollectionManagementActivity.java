package com.pdwy.dus.collection.activity;

import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.pdwy.dus.collection.MainActivity;
import com.pdwy.dus.collection.R;
import com.pdwy.dus.collection.core.BaseActivity;
import com.pdwy.dus.collection.fragment.GroupAbnormalityFragment;
import com.pdwy.dus.collection.fragment.IndividualAbnormalityFrament;
import com.pdwy.dus.collection.fragment.IndividualCharacterFragment;
import com.pdwy.dus.collection.fragment.PopulationTraitsFragment;
import com.pdwy.dus.collection.model.bean.CharacterThresholdBean;
import com.pdwy.dus.collection.model.bean.CollectionTaskItemBean;
import com.pdwy.dus.collection.model.bean.CollectionTaskItemListBean;
import com.pdwy.dus.collection.model.bean.MessageEvent;
import com.pdwy.dus.collection.model.bean.QunTiBean;
import com.pdwy.dus.collection.model.db.InputData;
import com.pdwy.dus.collection.utils.PopupWindowUtils;
import com.pdwy.dus.collection.utils.ProgressDialogUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 自定义采集管理
 * Author： by MR on 2018/9/19.
 */

public class CustomCollectionManagementActivity extends BaseActivity {

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
    @BindView(R.id.ll_character_category_Choice)
    LinearLayout ll_character_category_Choice;
    @BindView(R.id.tv_head_title)
    TextView tv_head_title;
    @BindView(R.id.tv_syq)
    TextView tv_syq;
    @BindView(R.id.tv_xz)
    TextView tv_xz;
    @BindView(R.id.tv_xzxl)
    TextView tv_xzxl;
    @BindView(R.id.tv_bc)
    TextView tv_bc;
    @BindView(R.id.ll_xx)
    LinearLayout ll_xx;
    TextView tv_leibei_p;
    private Dialog waitDialog;
    private IndividualCharacterFragment individualCharacterFragment;
    private PopulationTraitsFragment populationTraitsFragment;
    private IndividualAbnormalityFrament individualAbnormalityFrament;
    private GroupAbnormalityFragment groupAbnormalityFragment;
    InputData inputData;
    ArrayList<CharacterThresholdBean> listCharacterThresholdBean;
    ArrayList<CharacterThresholdBean> listCharacterThresholdBean2;
    ArrayList<CharacterThresholdBean> listCharacterThresholdBean1;
    ArrayList<CharacterThresholdBean> listCharacterThresholdBean3;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_collection_management_layout;
    }

    @Override
    protected void onCreateAfter() {
        inView();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {

    }

    @Override
    protected boolean userEventBus() {
        return true;
    }

    void inView(){
        ll_head_dus.setVisibility(View.INVISIBLE);
        ll_head_personalcenter.setVisibility(View.GONE);
        ll_head_return.setVisibility(View.VISIBLE);
        ll_head_homepage.setVisibility(View.VISIBLE);
        ll_head_homepage_r.setVisibility(View.VISIBLE);
        tv_head_title.setVisibility(View.VISIBLE);
        tv_head_title.setText("采集管理");

        inputData=new InputData(this);
        if("zidingyi".equals(getIntent().getStringExtra("activityName"))) {

            listCharacterThresholdBean3 =inputData.getCharacterThresholdBeanlist(getIntent().getStringExtra("pinzhong"),getIntent().getStringExtra("syrwbh"),getIntent().getStringExtra("mbmc"),getIntent().getStringExtra("syq"),9,getIntent().getStringArrayListExtra("tjbhList"));
            listCharacterThresholdBean2=new ArrayList<>();
            ArrayList<String> listXzmc=getIntent().getStringArrayListExtra("listXzmc");
            for(CharacterThresholdBean c:listCharacterThresholdBean3){
                for(String s:listXzmc){
                    if(s.equals(c.characterName))
                        listCharacterThresholdBean2.add(c);
                }


            }
        }


        listCharacterThresholdBean=new ArrayList<>();
        for(CharacterThresholdBean c:listCharacterThresholdBean2){
            if("MG".equals(c.observationMethod)||"VG".equals(c.observationMethod)){

                listCharacterThresholdBean.add(c);
            }

        }

        listCharacterThresholdBean1=new ArrayList<>();
        for(CharacterThresholdBean c:listCharacterThresholdBean2){
            if("MS".equals(c.observationMethod)||"VS".equals(c.observationMethod)){

                listCharacterThresholdBean1.add(c);

            }

        }

        if("异常".equals(getIntent().getStringExtra("gcfs"))){
            ll_character_category_Choice.removeViewAt(0);
            ll_character_category_Choice.removeViewAt(0);
        }
        else {
            if (listCharacterThresholdBean1.size() == 0) {
                tv_xz.setVisibility(View.INVISIBLE);
                tv_xzxl.setVisibility(View.VISIBLE);
                ll_character_category_Choice.removeViewAt(0);
                ll_character_category_Choice.removeViewAt(1);
            }
            if (ll_character_category_Choice.getChildCount() == 4) {
                if ("个体".equals(getIntent().getStringExtra("gcfs"))) {
                    ll_character_category_Choice.removeViewAt(1);
                    ll_character_category_Choice.removeViewAt(2);
                }
                if ("群体".equals(getIntent().getStringExtra("gcfs"))) {
                    ll_character_category_Choice.removeViewAt(0);
                    ll_character_category_Choice.removeViewAt(1);
                }

            }
        }
        String syq=getIntent().getStringExtra("syq");
        tv_syq.setText("生育期："+syq);
        tv_syq.setVisibility(View.GONE);
        if(listCharacterThresholdBean1.size()>0) {
            String s=listCharacterThresholdBean1.get(0).characterName;
            tv_xz.setText("性状:" + s);
            //性状点击事件
            tv_xz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final String[] data;
                    data = new String[listCharacterThresholdBean1.size()];
                    for (int i = 0; i < listCharacterThresholdBean1.size(); i++) {
                        data[i] = listCharacterThresholdBean1.get(i).characterName;
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                            CustomCollectionManagementActivity.this, android.R.layout.simple_list_item_1, data);

                    LinearLayout contentView = (LinearLayout) LayoutInflater.from(CustomCollectionManagementActivity.this).inflate(R.layout.ll_pop, null, false);
                    final PopupWindowUtils popupWindowUtils = new PopupWindowUtils(contentView, tv_xz.getMeasuredWidth(), 200);
                    ListView lv = (ListView) contentView.getChildAt(0);
                    lv.setAdapter(adapter);
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            tv_xz.setText("性状:"+data[position]);
                            popupWindowUtils.dismiss();
                            EventBus.getDefault().post(new MessageEvent(getIntent().getStringExtra("mbmc"),tv_xz.getText().toString()));
                            TextView tv=new TextView(CustomCollectionManagementActivity.this);
                            tv.setText("个体观测性状");
                            setFragment(tv);

                        }
                    });

                    popupWindowUtils.showAsDropDown(v, 0, 0);
                }
            });
        }
        //保存
        tv_bc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                baocun(1);
            }
        });

        ll_xx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] data =new String[]{"录入","取消录入","扫描查询"};
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        CustomCollectionManagementActivity.this, android.R.layout.simple_list_item_1, data);

                LinearLayout contentView= (LinearLayout) LayoutInflater.from(CustomCollectionManagementActivity.this).inflate(R.layout.ll_pop, null, false);
                ListView lv= (ListView) contentView.getChildAt(0);
                lv.setAdapter(adapter);

                final PopupWindowUtils popupWindowUtils=new PopupWindowUtils(contentView,ll_xx.getMeasuredWidth(),150);
                popupWindowUtils.showAsDropDown(v, 0, 0);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        popupWindowUtils.dismiss();
                    }
                });
            }
        });

        CategoryOnClick categoryOnClick=new CategoryOnClick();

        tv_leibei_p= (TextView) ll_character_category_Choice.getChildAt(0);
        tv_leibei_p.setBackgroundColor(getResources().getColor(R.color.home_tead_true));
        setFragment(tv_leibei_p);
        for(int i=0;i< ll_character_category_Choice.getChildCount();i++) {
            TextView tv = (TextView) ll_character_category_Choice.getChildAt(i);

            tv.setOnClickListener(categoryOnClick);
        }
    }

    private void baocun(int iiiiii) {

        switch (tv_leibei_p.getText().toString()) {
            case "个体观测性状":
                ArrayList<QunTiBean> list1 = new ArrayList<>();

                ListView lv1 = getFragmentManager().findFragmentById(R.id.fl_frgmt_administration).getView().findViewById(R.id.lv_data);
                ListView lvl1 = getFragmentManager().findFragmentById(R.id.fl_frgmt_administration).getView().findViewById(R.id.lv_left);
                for (int p = 0; p < lvl1.getChildCount(); p++) {
                    LinearLayout lllvl = (LinearLayout) lvl1.getChildAt(p);
                    TextView tvlllvl = lllvl.findViewById(R.id.tv_left);

                    LinearLayout ll = (LinearLayout) lv1.getChildAt(p);
                    LinearLayout lll = ll.findViewById(R.id.lin_content);

                    QunTiBean qunTiBean1=new QunTiBean();
                    qunTiBean1.csbh=tvlllvl.getText().toString();
                    qunTiBean1.xzmc=tv_xz.getText().toString().substring(3);
                    String bcnr="";

                    for(int q=0;q<39;q=q+2){
                        EditText edd = (EditText) lll.getChildAt(q);
                        bcnr=bcnr+edd.getText()+",-";

                    }
                    EditText edd40 = (EditText) lll.getChildAt(40);
                    bcnr=bcnr+edd40.getText()+",-";
                    TextView edd44 = (TextView) lll.getChildAt(44);
                    qunTiBean1.abnormal=edd44.getText().toString();

                    qunTiBean1.bcnr=bcnr;
                    list1.add(qunTiBean1);
                }


                inputData.setGeTiLL(list1);


                break;
            case "个体异常性状":
                ArrayList<QunTiBean> list3 = new ArrayList<>();
                ListView lv3 = getFragmentManager().findFragmentById(R.id.fl_frgmt_administration).getView().findViewById(R.id.lv_data);
                ListView lvl3 = getFragmentManager().findFragmentById(R.id.fl_frgmt_administration).getView().findViewById(R.id.lv_left);
                for (int p = 0; p < lvl3.getChildCount(); p++) {
                    LinearLayout lllvl = (LinearLayout) lvl3.getChildAt(p);
                    TextView tvlllvl = lllvl.findViewById(R.id.tv_left);

                    TextView tv_left_xzmc = lllvl.findViewById(R.id.tv_left_xzmc);
                    LinearLayout ll = (LinearLayout) lv3.getChildAt(p);
                    LinearLayout lll = ll.findViewById(R.id.lin_content);

                    QunTiBean qunTiBean1=new QunTiBean();
                    qunTiBean1.csbh=tvlllvl.getText().toString();
                    qunTiBean1.xzmc=tv_left_xzmc.getText().toString();
                    String bcnr="";

                    for(int q=0;q<lll.getChildCount()-2;q=q+2){
                        TextView tvv = (TextView) lll.getChildAt(q);
                        bcnr=bcnr+tvv.getText()+",-";
                    }

                    qunTiBean1.bcnr=bcnr;
                    list3.add(qunTiBean1);
                }


                inputData.setGeTiYi(list3);

                break;
            case "群体观测性状":
                ArrayList<QunTiBean> list = new ArrayList<>();
                ListView lv = getFragmentManager().findFragmentById(R.id.fl_frgmt_administration).getView().findViewById(R.id.lv_data);
                ListView lvl = getFragmentManager().findFragmentById(R.id.fl_frgmt_administration).getView().findViewById(R.id.lv_left);
                for (int p = 0; p < lvl.getChildCount(); p++) {
                    LinearLayout ll = (LinearLayout) lv.getChildAt(p);
                    LinearLayout lllvl = (LinearLayout) lvl.getChildAt(p);
                    TextView tvlllvl = lllvl.findViewById(R.id.tv_left);
                    LinearLayout lll = ll.findViewById(R.id.lin_content);


                    for (int q = 0; q < lll.getChildCount()-2; q++) {

                        LinearLayout llll = (LinearLayout) lll.getChildAt(q);
                        TextView tv = (TextView) llll.getChildAt(0);
                        QunTiBean qunTiBean = new QunTiBean();
                        qunTiBean.csbh = tvlllvl.getText().toString();
                        qunTiBean.xzmc = listCharacterThresholdBean.get(q).characterName;
                        qunTiBean.bcnr = tv.getText().toString();
                        LinearLayout llll2 = (LinearLayout) lll.getChildAt(lll.getChildCount()-2);
                        TextView tv2 = (TextView) llll2.getChildAt(0);
                        qunTiBean.beizhu=tv2.getText().toString();
                        list.add(qunTiBean);
                    }
                }
                inputData.setQunTiLL(list);
                break;
            case "群体异常性状":
                ArrayList<QunTiBean> list2 = new ArrayList<>();
                ListView lv2 = getFragmentManager().findFragmentById(R.id.fl_frgmt_administration).getView().findViewById(R.id.lv_data);
                ListView lvl2 = getFragmentManager().findFragmentById(R.id.fl_frgmt_administration).getView().findViewById(R.id.lv_left);
                for (int p = 0; p < lvl2.getChildCount(); p++) {
                    LinearLayout lllvl = (LinearLayout) lvl2.getChildAt(p);
                    TextView tvlllvl = lllvl.findViewById(R.id.tv_left);

                    TextView tv_left_xzmc = lllvl.findViewById(R.id.tv_left_xzmc);
                    LinearLayout ll = (LinearLayout) lv2.getChildAt(p);
                    LinearLayout lll = ll.findViewById(R.id.lin_content);

                    QunTiBean qunTiBean1=new QunTiBean();
                    qunTiBean1.csbh=tvlllvl.getText().toString();
                    qunTiBean1.xzmc=tv_left_xzmc.getText().toString();
                    String bcnr="";

                    for(int q=0;q<35;q=q+4){
                        TextView tvv = (TextView) lll.getChildAt(q);
                        bcnr=bcnr+tvv.getText()+",-";
                        EditText edd = (EditText) lll.getChildAt(q+2);
                        bcnr=bcnr+edd.getText()+";-";
                    }
                    EditText edd36 = (EditText) lll.getChildAt(36);
                    bcnr=bcnr+edd36.getText()+";-";
                    EditText edd38 = (EditText) lll.getChildAt(38);
                    bcnr=bcnr+edd38.getText()+";-";

                    qunTiBean1.bcnr=bcnr;
                    list2.add(qunTiBean1);
                }


                inputData.setQunTiYi(list2);

                break;
            default:

                break;
        }
        if(iiiiii==1) {
            Toast.makeText(CustomCollectionManagementActivity.this, "保存完成", Toast.LENGTH_SHORT).show();
        }
    }

    boolean isGeti(String xzmc){
        boolean  b = false;
        for(CharacterThresholdBean c:listCharacterThresholdBean1){
            if(xzmc.equals(c.characterName)){
                return  true;
            }

        }

        return b;
    }
    void setFragment(View v){



        FragmentManager manager=getFragmentManager();//创建FragmentManager对象
        FragmentTransaction transaction=manager.beginTransaction();//创建FragmentTransaction对象
        Bundle bundle4 = new Bundle();
        ArrayList<String> listCharacterThresholdBeanCharacterName=new ArrayList<>();
        for(CharacterThresholdBean c:listCharacterThresholdBean){
            listCharacterThresholdBeanCharacterName.add(c.characterName);
        }
        bundle4.putString("pinzhong",getIntent().getStringExtra("pinzhong"));
        bundle4.putString("experimentalNumber",getIntent().getStringExtra("syrwbh"));
        bundle4.putString("syrwbh",getIntent().getStringExtra("syrwbh"));
        bundle4.putString("mbmc",getIntent().getStringExtra("mbmc"));
        bundle4.putString("syq",getIntent().getStringExtra("syq"));
        bundle4.putString("xz",tv_xz.getText().toString());
        //传入性状list
        bundle4.putStringArrayList("listCharacterThresholdBeanCharacterName",listCharacterThresholdBeanCharacterName);

        CollectionTaskItemBean collectionTaskItemBean=new CollectionTaskItemBean();
        collectionTaskItemBean.taskName=getIntent().getStringExtra("syrwbh");
        ArrayList<CollectionTaskItemListBean> listCharacterThresholdBeanTestNumber=inputData.getCollectionTaskItemListBeantestNumber(collectionTaskItemBean,1);
        ArrayList<String> listCharacterThresholdBeanTestNumberlist=new ArrayList<>();
        ArrayList<String> listsss=getIntent().getStringArrayListExtra("tjbhList");
        if(listsss!=null)
            for(CollectionTaskItemListBean c:listCharacterThresholdBeanTestNumber){
                for(String ssss:listsss) {
                    if (ssss.equals(c.testNumber))
                        listCharacterThresholdBeanTestNumberlist.add(c.testNumber);
                }
            }
        else
            for(CollectionTaskItemListBean c:listCharacterThresholdBeanTestNumber){
                listCharacterThresholdBeanTestNumberlist.add(c.testNumber);
            }
        //传入测试编号list
        bundle4.putStringArrayList("listCharacterThresholdBeanTestNumberlist",listCharacterThresholdBeanTestNumberlist);
        switch (((TextView) v).getText().toString()){
            case "个体观测性状":
                tv_xz.setVisibility(View.VISIBLE);
                tv_xzxl.setVisibility(View.VISIBLE);
                waitDialog = ProgressDialogUtils.showDialogNew(this, "初始化列表", true);
                waitDialog.show();
                if(individualCharacterFragment==null) {
                    individualCharacterFragment = new IndividualCharacterFragment();
                    individualCharacterFragment.setArguments(bundle4);
                }
                transaction.replace(R.id.fl_frgmt_administration,individualCharacterFragment);
                waitDialog.dismiss();

                break;
            case "群体观测性状":
                tv_xz.setVisibility(View.INVISIBLE);
                tv_xzxl.setVisibility(View.INVISIBLE);
                if(populationTraitsFragment==null) {
                    populationTraitsFragment = new PopulationTraitsFragment();
                    populationTraitsFragment.setArguments(bundle4);
                }
                transaction.replace(R.id.fl_frgmt_administration,populationTraitsFragment);

                break;
            case "个体异常性状":
                tv_xz.setVisibility(View.INVISIBLE);
                tv_xzxl.setVisibility(View.INVISIBLE);
                if(individualAbnormalityFrament==null) {
                    individualAbnormalityFrament = new IndividualAbnormalityFrament();
                    individualAbnormalityFrament.setArguments(bundle4);
                }
                transaction.replace(R.id.fl_frgmt_administration,individualAbnormalityFrament);

                break;
            case "群体异常性状":
                tv_xz.setVisibility(View.INVISIBLE);
                tv_xzxl.setVisibility(View.INVISIBLE);
                if(groupAbnormalityFragment==null) {
                    groupAbnormalityFragment = new GroupAbnormalityFragment();
                    groupAbnormalityFragment.setArguments(bundle4);
                }
                transaction.replace(R.id.fl_frgmt_administration,groupAbnormalityFragment);

                break;


        }
        transaction.commit();//最后一定要提交

    }
    class CategoryOnClick implements View.OnClickListener{


        @Override
        public void onClick(View v) {
            baocun(0);
            tv_leibei_p.setBackgroundColor(getResources().getColor(R.color.home_tead_f));
            v.setBackgroundColor(getResources().getColor(R.color.home_tead_true));
            tv_leibei_p= (TextView) v;
            setFragment(v);

        }
    }

    @Override
    public void headOnClick(View v) {
        super.headOnClick(v);
        switch (v.getId()) {
            case R.id.ll_head_return:
                finish();
                baocun(0);
                break;
            //首页（右）
            case R.id.ll_head_homepage_r:
                baocun(0);
                showLoadingDialog("");
                startActivity(new Intent(this, MainActivity.class));
                removeALLActivity();

                break;
        }
    }
}
