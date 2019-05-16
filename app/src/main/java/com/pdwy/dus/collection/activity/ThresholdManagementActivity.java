package com.pdwy.dus.collection.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.pdwy.dus.collection.R;
import com.pdwy.dus.collection.core.BaseActivity;
import com.pdwy.dus.collection.model.db.InputData;

import java.lang.reflect.Array;
import java.util.ArrayList;

import butterknife.BindView;

/**
 * 阈值管理模板选择
 * Author： by MR on 2018/8/15.
 */

public class ThresholdManagementActivity extends BaseActivity {
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

    @BindView(R.id.lv_yuzhi)
    ListView lv_yuzhi;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_threshold_management;
    }

    @Override
    protected void onCreateAfter() {
        ll_head_return.setVisibility(View.VISIBLE);
        ll_head_dus.setVisibility(View.GONE);
        ll_head_personalcenter.setVisibility(View.INVISIBLE);
        tv_head_title.setVisibility(View.VISIBLE);

        if("1".equals(getIntent().getStringExtra("activityP")))
            tv_head_title.setText("阈值管理");
        else
            tv_head_title.setText("关联性状管理");

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

        InputData inputData=new InputData(this);
        final ArrayList<String> list=inputData.getMoBan(null);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1,list);

        lv_yuzhi.setAdapter(adapter);

        lv_yuzhi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                if("1".equals(getIntent().getStringExtra("activityP")))
                    //设置阈值
                 intent =new Intent(ThresholdManagementActivity.this,ThresholdSettingActivity.class);
               else
                   // 设置关联性状
                   intent =new Intent(ThresholdManagementActivity.this,RelationCharacterActivity.class);
                intent.putExtra("banben",list.get(position));
                startActivity(intent);
            }
        });
    }


}
