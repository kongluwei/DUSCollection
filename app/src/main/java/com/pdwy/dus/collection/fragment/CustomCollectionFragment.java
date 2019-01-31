package com.pdwy.dus.collection.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pdwy.dus.collection.MainActivity;
import com.pdwy.dus.collection.R;
import com.pdwy.dus.collection.activity.CrashActivity;
import com.pdwy.dus.collection.core.BaseFragment;
import com.pdwy.dus.collection.core.ExcelListActivity;
import com.pdwy.dus.collection.model.bean.CollectionTaskBean;
import com.pdwy.dus.collection.model.bean.CollectionTaskItemBean;
import com.pdwy.dus.collection.model.db.InputData;
import com.pdwy.dus.collection.utils.MLog;
import com.pdwy.dus.collection.utils.ProgressDialogUtils;

import java.util.ArrayList;

import butterknife.BindView;


/**
 * 自定义采集Fragment
 * Author： by MR on 2018/8/7.
 */

public class CustomCollectionFragment extends BaseFragment implements View.OnClickListener{

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {      //判断标志位

                case 1: // 加载完成1

                    ll_sc_custom_collection_fragment.addView((LinearLayout)msg.obj);

                    break;
                case 2: //加载完成 2
                    parentActivity.dismissLoadingDialog();
                    LinearLayout l1= (LinearLayout) ll_sc_custom_collection_fragment.getChildAt(msg.arg1);
                    l1.addView((LinearLayout)msg.obj);
                    break;
            }
        }
    };

    EditText mEtSearch;
    LinearLayout mLayoutDefaultText;
    LinearLayout ll_sc_custom_collection_fragment;
                               private InputData inputData;
    TextView tv_individual;
    TextView tv_group;
    TextView tv_abnormal;
    String syrwbh;
    //确认选择
    TextView tv_sure;

    //取消选择
    TextView tv_cancel;
    TextView textView;
    private Dialog waitDialog;
    private  MainActivity parentActivity;
    //记录点击的哪个任务
    int p=0;
    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_custom_collection;
    }

    @Override
    protected void setUpView() {
         parentActivity = (MainActivity ) getActivity();
        mEtSearch=mContentView.findViewById(R.id.et_search);
        mLayoutDefaultText=mContentView.findViewById(R.id.layout_default);
        ll_sc_custom_collection_fragment=mContentView.findViewById(R.id.ll_sc_custom_collection_fragment);
        tv_individual=mContentView.findViewById(R.id.tv_individual);
        tv_individual.setOnClickListener(this);
        tv_group=mContentView.findViewById(R.id.tv_group);
        tv_group.setOnClickListener(this);
        tv_abnormal=mContentView.findViewById(R.id.tv_abnormal);
        tv_abnormal.setOnClickListener(this);
        tv_sure=mContentView.findViewById(R.id.tv_sure);
        tv_sure.setOnClickListener(this);
        tv_cancel=mContentView.findViewById(R.id.tv_cancel);
        tv_cancel.setOnClickListener(this);


        inputData=new InputData(getActivity());

//         editText 离开监听
        mEtSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // hasFocus 为false时表示点击了别的控件，离开当前editText控件
                if (!hasFocus) {
                    if ("".equals(mEtSearch.getText().toString())) {
                        mLayoutDefaultText.setVisibility(View.VISIBLE);
                    }
                }
                else {
                    mLayoutDefaultText.setVisibility(View.GONE);
                }
            }
        });
    }



    @Override
    protected void setUpData() {

        parentActivity.showLoadingDialog("");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {



        CollectionTaskBean collectionTaskBean=new CollectionTaskBean();
        collectionTaskBean.Varieties= getArguments().getString("pinzhong");
        ArrayList<CollectionTaskItemBean>listCollectionTaskItemBean=inputData.getCollectionTaskItemBeanList(collectionTaskBean,0);

        for (int i=0;i<listCollectionTaskItemBean.size();i++) {
            final LinearLayout l = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.item_sv_custom_collection_fragment, null);

            LinearLayout l1 = (LinearLayout) l.getChildAt(0);
            TextView v = (TextView) l1.getChildAt(3);
            v.setText(listCollectionTaskItemBean.get(i).taskName);
            final CheckBox checkBox= (CheckBox) l1.getChildAt(1);
            final int finalI = i;
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(checkBox.isChecked())
                    {
                        for(int ix=2;ix<l.getChildCount();ix++){
                            if(p!= finalI)
                                uncheck();
                            checkBox.setChecked(true);
                            LinearLayout lix= (LinearLayout) l.getChildAt(ix);
                            LinearLayout lix2= (LinearLayout) lix.getChildAt(0);
                            CheckBox checkBoxlix= (CheckBox) lix2.getChildAt(1);
                            checkBoxlix.setChecked(true);
                        }
                    }
                    else{
                        for(int ix=2;ix<l.getChildCount();ix++){
                            LinearLayout lix= (LinearLayout) l.getChildAt(ix);
                            LinearLayout lix2= (LinearLayout) lix.getChildAt(0);
                            CheckBox checkBoxlix= (CheckBox) lix2.getChildAt(1);
                            checkBoxlix.setChecked(false);
                        }
                    }
                    p=finalI;
                }
            });
            Message msg = new Message();
            msg.obj = l;
            msg.what = 1; //加载完成1
            handler.sendMessage(msg);


        }
        for (int i=0;i<listCollectionTaskItemBean.size();i++) {
            LinearLayout l1= (LinearLayout) ll_sc_custom_collection_fragment.getChildAt(i);
            for (int x = 0; x < listCollectionTaskItemBean.get(i).list.size(); x++) {
                LinearLayout l2 = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.item_sv_custom_collection_fragment2, null);
                LinearLayout l12 = (LinearLayout) l2.getChildAt(0);
                final CheckBox checkBoxlix= (CheckBox) l12.getChildAt(1);
                final int finalI = i;
                checkBoxlix.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(p!= finalI) {
                            uncheck();
                            checkBoxlix.setChecked(true);
                        }
                        p= finalI;
                    }
                });
                TextView v = (TextView) l12.getChildAt(3);
                v.setText(listCollectionTaskItemBean.get(i).list.get(x).testNumber);

                Message msg = new Message();
                msg.obj = l2;
                msg.arg1=i;
                msg.what = 2; //加载完成2
                handler.sendMessage(msg);

            }
        }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.tv_individual:
                if(textView!=null)
                    textView.setTextColor(Color.WHITE);
                tv_individual.setTextColor(Color.RED);
                textView=tv_individual;
                break;
            case R.id.tv_group:
                if(textView!=null)
                    textView.setTextColor(Color.WHITE);
                tv_group.setTextColor(Color.RED);
                textView=tv_group;
                break;
            case R.id.tv_abnormal:
                if(textView!=null)
                    textView.setTextColor(Color.WHITE);
                tv_abnormal.setTextColor(Color.RED);
                textView=tv_abnormal;
                break;

            case R.id.tv_sure:
                if(textView==null){
                    Toast.makeText(getActivity(),"请选择观测方式",Toast.LENGTH_LONG).show();
                    return;
                }
                ArrayList <String> tjbhList=new ArrayList<>();
                for(int i=0;i<ll_sc_custom_collection_fragment.getChildCount();i++){
                    LinearLayout ll1= (LinearLayout) ll_sc_custom_collection_fragment.getChildAt(i);
                    LinearLayout l1 = (LinearLayout) ll1.getChildAt(0);
                    CheckBox checkBox= (CheckBox) l1.getChildAt(1);
                         for (int x=2;x<ll1.getChildCount();x++){
                             LinearLayout lix= (LinearLayout) ll1.getChildAt(x);
                             LinearLayout lix2= (LinearLayout) lix.getChildAt(0);
                             CheckBox checkBoxlix= (CheckBox) lix2.getChildAt(1);
                             if(checkBoxlix.isChecked())
                             {
                                TextView tvv= (TextView) lix2.getChildAt(3);
                                 tjbhList.add(tvv.getText().toString());
                                TextView tv= (TextView) l1.getChildAt(3);
                                syrwbh= tv.getText().toString();
                             }


                     }


                }

                if(tjbhList.size()==0){
                    Toast.makeText(getActivity(),"请选择田间编号",Toast.LENGTH_LONG).show();
                    return;

                }


//                Intent intent=new Intent(getActivity(),CollectionSettingActivity.class);
                Intent  intent=new Intent (getActivity(), CrashActivity.class);
                intent.putExtra("pinzhong",getArguments().getString("pinzhong"));
                intent.putExtra("activityName","zidingyi");
                intent.putExtra("gcfs",textView.getText());
                intent.putExtra("syrwbh",syrwbh);
                intent.putStringArrayListExtra("tjbhList",tjbhList);
                startActivity(intent);
                break;
            case R.id.tv_cancel:
                if(textView!=null)
                    textView.setTextColor(Color.WHITE);
                textView=null;
                uncheck();


                break;
        }
    }
    void uncheck(){
        for(int i=0;i<ll_sc_custom_collection_fragment.getChildCount();i++){
            LinearLayout ll1= (LinearLayout) ll_sc_custom_collection_fragment.getChildAt(i);
            LinearLayout l1 = (LinearLayout) ll1.getChildAt(0);

            CheckBox checkBox= (CheckBox) l1.getChildAt(1);
            checkBox.setChecked(false);

            for (int x=2;x<ll1.getChildCount();x++){
                LinearLayout lix= (LinearLayout) ll1.getChildAt(x);
                LinearLayout lix2= (LinearLayout) lix.getChildAt(0);
                CheckBox checkBoxlix= (CheckBox) lix2.getChildAt(1);
                checkBoxlix.setChecked(false);

            }
        }

    }
    //停止动画
    @Override
    public void onStop() {
        super.onStop();
        waitDialog = ProgressDialogUtils.showDialogNew(getActivity(), "初始化列表", true);
//        Intent integer=new Intent (getActivity(), ExcelListActivity.class);
//        startActivity(integer);
        waitDialog.show();

        if(waitDialog!=null)
            waitDialog.dismiss();
    }
}
