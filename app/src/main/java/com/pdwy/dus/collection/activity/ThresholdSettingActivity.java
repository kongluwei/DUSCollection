package com.pdwy.dus.collection.activity;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pdwy.dus.collection.R;
import com.pdwy.dus.collection.core.BaseActivity;
import com.pdwy.dus.collection.model.bean.CharacterThresholdBean;
import com.pdwy.dus.collection.model.db.InputData;
import com.pdwy.dus.collection.utils.MLog;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Author： by MR on 2018/8/24.
 */

public class ThresholdSettingActivity extends BaseActivity {

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {      //判断标志位

                case 1: // 加载完成

                    ll_yuzhishezhi.addView((LinearLayout)msg.obj);
                    break;
            }
        }
    };


    @BindView(R.id.et_search)
    EditText mEtSearch;
    @BindView(R.id.layout_default)
    LinearLayout mLayoutDefaultText;

    @BindView(R.id.ll_head_return)
    LinearLayout ll_head_return;
    @BindView(R.id.ll_head_dus)
    LinearLayout ll_head_dus;
    @BindView(R.id.ll_head_personalcenter)
    LinearLayout ll_head_personalcenter;
    @BindView(R.id.tv_head_title)
    TextView tv_head_title;
    @BindView(R.id.tv_personal_out)
    TextView tv_personal_out;

    @BindView(R.id.ll_yuzhishezhi)
    LinearLayout ll_yuzhishezhi;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_threshold_setting;
    }

    @Override
    protected void onCreateAfter() {
        final InputData inputData=new InputData(this);
        ll_head_return.setVisibility(View.VISIBLE);
        ll_head_dus.setVisibility(View.GONE);
        ll_head_personalcenter.setVisibility(View.INVISIBLE);
        tv_head_title.setVisibility(View.VISIBLE);
        tv_head_title.setText("阈值设置");
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
        final ArrayList<CharacterThresholdBean> finalList=new ArrayList<>() ;
        tv_personal_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i=ll_yuzhishezhi.getChildCount();
                finalList.clear();
                if(i>0){
                    for(int p=1;p<i;p++){

                        CharacterThresholdBean characterThresholdBean=new CharacterThresholdBean();
                        LinearLayout l= (LinearLayout) ll_yuzhishezhi.getChildAt(p);
                        LinearLayout ll = (LinearLayout) l.getChildAt(0);
                        TextView tv1= (TextView) ll.getChildAt(1);
                        characterThresholdBean.characterId=tv1.getText().toString();
                        TextView tv2= (TextView) ll.getChildAt(3);
                        characterThresholdBean.characterName=tv2.getText().toString();
                        characterThresholdBean.template=getIntent().getStringExtra("banben");
                        TextView tv3= (TextView) ll.getChildAt(5);
                        characterThresholdBean.observationMethod=tv3.getText().toString();
                        LinearLayout ll4= (LinearLayout) ll.getChildAt(7);
                        EditText ed1= (EditText) ll4.getChildAt(0);
                        EditText ed2= (EditText) ll4.getChildAt(2);
                        if("".equals(ed1.getText().toString().trim())||"".equals(ed2.getText().toString().trim()))
                        {

                        }
                            else{
                            characterThresholdBean.numericalRangeOfCharacters = ed1.getText().toString().trim() + "-" + ed2.getText().toString().trim();
                        }
                        finalList.add(characterThresholdBean);
                    }

                    inputData.setYuZhi(finalList);
                    Toast.makeText(ThresholdSettingActivity.this,"保存完成",Toast.LENGTH_LONG).show();
                }

            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

        ArrayList<CharacterThresholdBean> list=inputData.getCharacterThresholdBeanList(getIntent().getStringExtra("banben"));
        for(CharacterThresholdBean c:list) {
//            if("VS".equals(c.observationMethod)||"GS".equals(c.observationMethod)) {
                LinearLayout l1 = (LinearLayout) getLayoutInflater().inflate(R.layout.item_sc_ll_yuzhishezhi, null);
                LinearLayout l2 = (LinearLayout) l1.getChildAt(0);
                TextView t0 = (TextView) l2.getChildAt(1);
                t0.setText(c.characterId);
                TextView t1 = (TextView) l2.getChildAt(3);
                t1.setText(c.characterName);
                TextView t2 = (TextView) l2.getChildAt(5);
                t2.setText(c.observationMethod);
            LinearLayout ll4= (LinearLayout) l2.getChildAt(7);
            EditText ed1= (EditText) ll4.getChildAt(0);
            EditText ed2= (EditText) ll4.getChildAt(2);
            if(c.numericalRangeOfCharacters!=null||"".equals(c.numericalRangeOfCharacters)) {
                String[] s = c.numericalRangeOfCharacters.split("-");
                if (s != null) {
                    ed1.setText(s[0]);

                    ed2.setText(s[1]);
                }
            }
                if("VS".equals(c.observationMethod)||"MS".equals(c.observationMethod)){

                    Message msg = new Message();
                    msg.obj = l1;
                    msg.what = 1; //加载完成
                    handler.sendMessage(msg);

                }



//            }

        }

                } catch (Exception e) {
                    e.printStackTrace();
                    MLog.e("异常");
                }
            }
        }).start();
    }
}
