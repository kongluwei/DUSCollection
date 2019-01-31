package com.pdwy.dus.collection.core;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pdwy.dus.collection.R;
import com.pdwy.dus.collection.utils.MLog;
import com.pdwy.dus.collection.utils.ProgressDialogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Author： by MR on 2018/7/24.
 */

public class ExcelListActivity extends BaseActivity {
    private NoscrollListView mLeft;
    private LeftAdapter mLeftAdapter;

    private NoscrollListView mData;
    private DataAdapter mDataAdapter;

    private SyncHorizontalScrollView mHeaderHorizontal;
    private SyncHorizontalScrollView mDataHorizontal;
    private List<String> mListData;
    private Dialog waitDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.excel_list_activity;
    }

    @Override
    protected void onCreateAfter() {
//        waitDialog = ProgressDialogUtils.showDialogNew(this, "初始化列表", true);
//        waitDialog.show();
        initView();
    }


    private void initView(){

        mLeft = (NoscrollListView) findViewById(R.id.lv_left);
        mData = (NoscrollListView) findViewById(R.id.lv_data);
        mDataHorizontal = (SyncHorizontalScrollView) findViewById(R.id.data_horizontal);
        mHeaderHorizontal = (SyncHorizontalScrollView) findViewById(R.id.header_horizontal);

        mDataHorizontal.setScrollView(mHeaderHorizontal);
        mHeaderHorizontal.setScrollView(mDataHorizontal);

        mListData = new ArrayList<>();
        mListData.add("1");
        mListData.add("2");
        mListData.add("3");
        mListData.add("4");
        mListData.add("5");
        mListData.add("6");
        mListData.add("7");
        mListData.add("8");
        mListData.add("9");
        mListData.add("10");
        mListData.add("11");
        mListData.add("12");
        mListData.add("13");
        mListData.add("14");
        mListData.add("15");
        mListData.add("16");
        mListData.add("17");
        mListData.add("18");
        mListData.add("19");
        mListData.add("20");
        mListData.add("21");
        mListData.add("22");
        mListData.add("23");
        mLeftAdapter= new LeftAdapter();
        mLeft.setAdapter(mLeftAdapter);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mDataAdapter = new DataAdapter();
                    mData.setAdapter(mDataAdapter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
    class LeftAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mListData.size();
        }

        @Override
        public Object getItem(int position) {
            return mListData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(ExcelListActivity.this).inflate(R.layout.item_left, null);
                holder.tvLeft = (TextView) convertView.findViewById(R.id.tv_left);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.tvLeft.setText("第" + position + "行");

            return convertView;
        }

        class ViewHolder {
            TextView tvLeft;
        }
    }

    class DataAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mListData.size();
        }

        @Override
        public Object getItem(int position) {
            return mListData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if(convertView == null){
                holder = new ViewHolder();
                convertView = LayoutInflater.from(ExcelListActivity.this).inflate(R.layout.item_data, null);
                holder.tvData = (TextView) convertView.findViewById(R.id.tv_data);
                holder.linContent = (LinearLayout) convertView.findViewById(R.id.lin_content);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }

            return convertView;
        }

        class ViewHolder {
            TextView tvData;
            LinearLayout linContent;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        waitDialog.dismiss();
    }
}

