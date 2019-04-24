package com.pdwy.dus.collection.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.pdwy.dus.collection.LoginActivity;
import com.pdwy.dus.collection.MainActivity;
import com.pdwy.dus.collection.R;
import com.pdwy.dus.collection.activity.DataDetailsActivity;
import com.pdwy.dus.collection.activity.PersonalCenterActivity;
import com.pdwy.dus.collection.core.BaseFragment;
import com.pdwy.dus.collection.http.UploadDataHttp;
import com.pdwy.dus.collection.model.bean.CharacterBean;
import com.pdwy.dus.collection.model.db.InputData;
import com.pdwy.dus.collection.utils.MLog;
import com.pdwy.dus.collection.utils.SharePreferencesUtils;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * 待上传数据Fragment
 * Author： by MR on 2018/8/7.
 */

public class UploadingDataFragment extends BaseFragment {
    private MainActivity parentActivity;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {      //判断标志位
                case 0: //上传成功

                        parentActivity.dismissLoadingDialog();
                        //                        判断是否上传成功
                        Toast.makeText(parentActivity,msg.obj.toString(),Toast.LENGTH_SHORT).show();






                    break;

                case 1: // 加载完成
                    ll_ckbc_nr.addView((LinearLayout)msg.obj);
                    break;
                case 2: //上传成功
                    label2++;
                if(label2==label1){
                    parentActivity.dismissLoadingDialog();
                    //                        判断是否上传成功
                        Toast.makeText(parentActivity,"数据上传成功",Toast.LENGTH_SHORT).show();
                        setUpData();


                }


                    break;
            }
        }
    };

    EditText mEtSearch;
    LinearLayout mLayoutDefaultText;
    LinearLayout ll_ckbc_nr;
    InputData inputData;

    TextView tv_schu;
    TextView tv_schuang;
    int label1 =0;
    int label2 =0;
    ArrayList<CharacterBean> listt;
    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_uploading_data;
    }

    @Override
    protected void setUpView() {
        parentActivity= (MainActivity) getActivity();
         inputData =new InputData(getActivity());
        mEtSearch=mContentView.findViewById(R.id.et_search);
        mLayoutDefaultText=mContentView.findViewById(R.id.layout_default);
        ll_ckbc_nr=mContentView.findViewById(R.id.ll_ckbc_nr);
        tv_schu=mContentView.findViewById(R.id.tv_schu);
        tv_schuang=mContentView.findViewById(R.id.tv_schuang);


        tv_schu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int c=0;

                for(int i=0;i<ll_ckbc_nr.getChildCount();i++){
                    LinearLayout linearLayout= (LinearLayout) ll_ckbc_nr.getChildAt(i);
                    CheckBox checkBox=(CheckBox)linearLayout.findViewById(R.id.cb_sj);
                    if(checkBox.isChecked())
                        c++;

                }
                if(c==0){
                    Toast.makeText(getActivity(),"请勾选数据",Toast.LENGTH_SHORT).show();
                    return;
                }


                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("删除这"+c+"条数据");
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                final int finalC = c;
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        int c2 = 0;
                        for(int i=0;i<ll_ckbc_nr.getChildCount();i++){
                            LinearLayout linearLayout= (LinearLayout) ll_ckbc_nr.getChildAt(i);
                            CheckBox checkBox=(CheckBox)linearLayout.findViewById(R.id.cb_sj);
                            if(checkBox.isChecked()){

                                if(inputData.deleteCharacter(listt.get(i).experimentalNumber,listt.get(i).testNumber)) {
                                    c2++;
                                }
                            }

                        }
                        //判断是否删除成功
                        if(c2== finalC){
                            Toast.makeText(getActivity(),"数据删除成功",Toast.LENGTH_SHORT).show();
                            setUpData();
                        }
                        else {
                            Toast.makeText(getActivity(),"数据删除失败",Toast.LENGTH_SHORT).show();

                        }


                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        tv_schuang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int c=0;

                for(int i=0;i<ll_ckbc_nr.getChildCount();i++){
                    LinearLayout linearLayout= (LinearLayout) ll_ckbc_nr.getChildAt(i);
                    CheckBox checkBox=(CheckBox)linearLayout.findViewById(R.id.cb_sj);
                    if(checkBox.isChecked())
                        c++;

                }
                if(c==0){
                    Toast.makeText(getActivity(),"请勾选数据",Toast.LENGTH_SHORT).show();
                    return;
                }


                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                label1=c;
                label2=0;
                builder.setTitle("上传这"+c+"条数据");
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                final int finalC = c;
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        parentActivity.showLoadingDialog("数据上传中");
                        UploadDataHttp uploadDataHttp=new UploadDataHttp(getActivity(),handler);

                        for(int i=0;i<ll_ckbc_nr.getChildCount();i++){
                            LinearLayout linearLayout= (LinearLayout) ll_ckbc_nr.getChildAt(i);
                            CheckBox checkBox=(CheckBox)linearLayout.findViewById(R.id.cb_sj);
                            if(checkBox.isChecked()){

                            uploadDataHttp.uploadData(listt.get(i).groupId,listt.get(i).varietyId);

                            }

                        }



                                 }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==2){

            setUpData();
        }
    }

    @Override
    protected void setUpData() {

        ll_ckbc_nr.removeAllViews();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
       listt= inputData.getCharacterBeanList();
        Calendar cd = Calendar.getInstance();
        for(int i=0;i<listt.size();i++) {
            LinearLayout ll = (LinearLayout) LinearLayout.inflate(getActivity(), R.layout.fragment_uploading_data_item, null);
            final TextView tv_tjbh=ll.findViewById(R.id.tv_tjbh);
            tv_tjbh.setText(listt.get(i).testNumber);
            TextView tv_sybh=ll.findViewById(R.id.tv_sybh);
            tv_sybh.setText(listt.get(i).experimentalNumber);
            TextView tv_zwzs=ll.findViewById(R.id.tv_zwzs);
            tv_zwzs.setText(listt.get(i).varieties);
            TextView tv_yc=ll.findViewById(R.id.tv_yc);
            if("1".equals(inputData.getCharacterAbnormal(listt.get(i).experimentalNumber,listt.get(i).testNumber)))
                tv_yc.setText("是");
            else
                tv_yc.setText("否");
            TextView tv_cjsj=ll.findViewById(R.id.tv_cjsj);


            tv_cjsj.setText(cd.get(Calendar.YEAR)+"-"+(cd.get(Calendar.MONTH)+1)+"-"+cd.get(Calendar.DATE));
            final int finalI = i;
            ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent=new Intent(getActivity(),DataDetailsActivity.class);
                    intent.putExtra("tjbh",tv_tjbh.getText());
                    intent.putExtra("syrwbh",listt.get(finalI).experimentalNumber);
                    intent.putExtra("groupId",String.valueOf(listt.get(finalI).groupId));
                    intent.putExtra("varietyId",String.valueOf(listt.get(finalI).varietyId));
                    MLog.e(listt.get(finalI).groupId+"==="+listt.get(finalI).varietyId);
                    startActivityForResult(intent,0);
                }
            });


            Message msg = new Message();
            msg.obj = ll;
            msg.what = 1; //加载完成
            handler.sendMessage(msg);

        }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
