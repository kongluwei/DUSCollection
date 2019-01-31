package com.pdwy.dus.collection.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pdwy.dus.collection.R;
import com.pdwy.dus.collection.core.BaseActivity;
import com.pdwy.dus.collection.core.ExcelListActivity;
import com.pdwy.dus.collection.core.NoscrollListView;
import com.pdwy.dus.collection.core.SyncHorizontalScrollView;
import com.pdwy.dus.collection.http.UploadDataHttp;
import com.pdwy.dus.collection.model.bean.CharacterBean;
import com.pdwy.dus.collection.model.db.InputData;
import com.pdwy.dus.collection.utils.MLog;
import com.pdwy.dus.collection.utils.PopupWindowUtils;

import java.util.ArrayList;

import butterknife.BindView;

/**待上传数据  数据详情
 * Author： by MR on 2018/9/5.
 */

public class DataDetailsActivity extends BaseActivity{

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {      //判断标志位

                case 1: // 数据加载完成
                    dismissLoadingDialog();
                    mLeft.setAdapter(mLeftAdapter);

                    mData.setAdapter(mDataAdapter);

                    break;
                case 2: //上传回调
                      dismissLoadingDialog();
                    if("成功".equals(msg.obj)) {
                        Toast.makeText(DataDetailsActivity.this,"上传成功",Toast.LENGTH_SHORT).show();
                        setResult(2, null);
                        finish();

                    }
                    else {
                        Toast.makeText(DataDetailsActivity.this,msg.obj.toString(),Toast.LENGTH_SHORT).show();
                    }

                    break;
            }
        }
    };

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
    @BindView(R.id.tv_csbh)
    TextView tv_csbh;
    @BindView(R.id.tv_schu)
    TextView tv_schu;
    @BindView(R.id.tv_schuang)
    TextView tv_schuang;
    private NoscrollListView mLeft;
    private LeftAdapter mLeftAdapter;

    private NoscrollListView mData;
    private DataAdapter mDataAdapter;

    private SyncHorizontalScrollView mHeaderHorizontal;
    private SyncHorizontalScrollView mDataHorizontal;
    private ArrayList<CharacterBean> mListData;
    InputData inputData;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_data_details;
    }

    @Override
    protected void onCreateAfter() {
        ll_head_dus.setVisibility(View.INVISIBLE);
        ll_head_personalcenter.setVisibility(View.GONE);
        ll_head_return.setVisibility(View.VISIBLE);
        ll_head_homepage.setVisibility(View.VISIBLE);
        ll_head_homepage_r.setVisibility(View.VISIBLE);
        tv_head_title.setVisibility(View.VISIBLE);
        tv_head_title.setText("数据详情");
        tv_csbh.setText(getIntent().getStringExtra("syrwbh")+">>"+getIntent().getStringExtra("tjbh"));

        tv_schu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DataDetailsActivity.this);
                builder.setTitle("确认删除");
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        if(inputData.deleteCharacter(getIntent().getStringExtra("syrwbh"),getIntent().getStringExtra("tjbh"))) {
                            Toast.makeText(DataDetailsActivity.this,"删除成功",Toast.LENGTH_SHORT).show();
                            setResult(2, null);
                            finish();
                        }
                        else {
                            Toast.makeText(DataDetailsActivity.this,"删除失败",Toast.LENGTH_SHORT).show();
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
                AlertDialog.Builder builder = new AlertDialog.Builder(DataDetailsActivity.this);
                builder.setTitle("上传数据");
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showLoadingDialog("上传中");
                        UploadDataHttp uploadDataHttp=new UploadDataHttp(DataDetailsActivity.this,handler);
                        MLog.e(getIntent().getStringExtra("groupId")+"===="+getIntent().getStringExtra("varietyId"));
                       uploadDataHttp.uploadData(Integer.valueOf(getIntent().getStringExtra("groupId")),Integer.valueOf(getIntent().getStringExtra("varietyId")));



                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        initView();
    }
    private void initView(){

           inputData=new InputData(this);


        mLeft = (NoscrollListView) findViewById(R.id.lv_left);
        mData = (NoscrollListView) findViewById(R.id.lv_data);
        mDataHorizontal = (SyncHorizontalScrollView) findViewById(R.id.data_horizontal);
        mHeaderHorizontal = (SyncHorizontalScrollView) findViewById(R.id.header_horizontal);

        mDataHorizontal.setScrollView(mHeaderHorizontal);
        mHeaderHorizontal.setScrollView(mDataHorizontal);



        mLeftAdapter= new LeftAdapter();
        mDataAdapter = new DataAdapter();
        // 加载品种
        showLoadingDialog("",false);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(100);
                    mListData= inputData.getCharacterBeanList(getIntent().getStringExtra("groupId"),getIntent().getStringExtra("varietyId"));

                    Message msg=new Message();
                    msg.what=1; //品种加载完成
                    handler.sendMessage(msg);
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new LeftAdapter.ViewHolder();

                convertView = LayoutInflater.from(DataDetailsActivity.this).inflate(R.layout.activity_data_details_item_left, null);
                holder.tvLeft = (TextView) convertView.findViewById(R.id.tv_left);
                holder.tvLeft2 = (TextView) convertView.findViewById(R.id.tv_left2);
                holder.tvLeft3 = (TextView) convertView.findViewById(R.id.tv_left3);
                holder.tvLeft4 = (TextView) convertView.findViewById(R.id.tv_left4);
                holder.tvLeft5 = (TextView) convertView.findViewById(R.id.tv_left5);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tvLeft.setText(mListData.get(position).characterId);
            holder.tvLeft2.setText(mListData.get(position).characterName);
            if("1".equals(mListData.get(position).abnormal)) {
                holder.tvLeft2.setTextColor(Color.RED);
                holder.tvLeft2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if("MG".equals(mListData.get(position).observationMethod)||"VG".equals(mListData.get(position).observationMethod)) {
                            LinearLayout contentView= (LinearLayout) LayoutInflater.from(DataDetailsActivity.this).inflate(R.layout.activity_data_details_pop, null, false);
                            LinearLayout ll_activity_data_details_pop_item=contentView.findViewById(R.id.ll_activity_data_details_pop_item);
                            String abnormalContent=mListData.get(position).abnormalContent;
                            if("".equals(abnormalContent)||abnormalContent==null)
                                return;
                            String [] abnormalContentS=abnormalContent.split(";");
                            for(int i=0;i<9;i++) {
                                String []abnormalContentSS=abnormalContentS[i].split(",");
                                LinearLayout contentView_item= (LinearLayout) LayoutInflater.from(DataDetailsActivity.this).inflate(R.layout.activity_data_details_pop_item, null, false);
                                TextView tv_xh= (TextView)contentView_item.findViewById(R.id.tv_xh);
                                tv_xh.setText(i+1+"");
                                TextView tv_dm= (TextView)contentView_item.findViewById(R.id.tv_dm);
                                if(i==0)
                                    tv_dm.setText(abnormalContentSS[0]);
                                else
                                    tv_dm.setText("".equals(abnormalContentSS[0].substring(1))?"-":abnormalContentSS[0].substring(1));
                                TextView tv_dmz= (TextView)contentView_item.findViewById(R.id.tv_dmz);
                                tv_dmz.setText("".equals(abnormalContentSS[1].substring(1))?"-":abnormalContentSS[1].substring(1));
                                ll_activity_data_details_pop_item.addView(contentView_item);
                            }
                            final PopupWindowUtils popupWindowUtils=new PopupWindowUtils(contentView,1100,186);
                            popupWindowUtils.showAsDropDown(v,0,0);

                        }

                        else if("MS".equals(mListData.get(position).observationMethod)||"VS".equals(mListData.get(position).observationMethod)) {
                            LinearLayout contentView= (LinearLayout) LayoutInflater.from(DataDetailsActivity.this).inflate(R.layout.activity_data_details_pop, null, false);
                            LinearLayout ll_activity_data_details_pop_item=contentView.findViewById(R.id.ll_activity_data_details_pop_item);
                            String abnormalContent=mListData.get(position).abnormalContent;
                            if("".equals(abnormalContent)||abnormalContent==null)
                                return;
                            MLog.e(abnormalContent);
                            String [] abnormalContentS=abnormalContent.split(",");
                            for(int i=0;i<abnormalContentS.length-2;i++) {
                                LinearLayout contentView_item= (LinearLayout) LayoutInflater.from(DataDetailsActivity.this).inflate(R.layout.activity_data_details_pop_items, null, false);
                                TextView tv_xh= (TextView)contentView_item.findViewById(R.id.tv_xh);
                                tv_xh.setText(i+1+"");
                                TextView tv_dmz= (TextView)contentView_item.findViewById(R.id.tv_dmz);
                                if(i==0)
                                    tv_dmz.setText(abnormalContentS[i]);
                                else
                                    tv_dmz.setText("".equals(abnormalContentS[i].substring(1))?"-":abnormalContentS[i].substring(1));
                                ll_activity_data_details_pop_item.addView(contentView_item);
                            }
                            DisplayMetrics dm2 = getResources().getDisplayMetrics();

                            final PopupWindowUtils popupWindowUtils=new PopupWindowUtils(contentView,mLeft.getMeasuredWidth()/4*6+20,170);

                            popupWindowUtils.showAsDropDown(v,0,0);


                        }


                    }
                });
            }
            else {
                holder.tvLeft2.setTextColor(Color.GRAY);
                holder.tvLeft2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
            holder.tvLeft3.setText(mListData.get(position).observationMethod);
            holder.tvLeft4.setText(mListData.get(position).observationalQuantity);
            holder.tvLeft5.setText(mListData.get(position).dataUnit);
            return convertView;

        }

        class ViewHolder {
            TextView tvLeft;
            TextView tvLeft2;
            TextView tvLeft3;
            TextView tvLeft4;
            TextView tvLeft5;
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
                convertView =LayoutInflater.from(DataDetailsActivity.this).inflate(R.layout.activity_data_details_item_data, null);


                holder.tvData = (TextView) convertView.findViewById(R.id.tv_data);
                holder.tvData2 = (TextView) convertView.findViewById(R.id.tv_data2);
                holder.tvData3 = (TextView) convertView.findViewById(R.id.tv_data3);
                holder.tvData4 = (TextView) convertView.findViewById(R.id.tv_data4);
                holder.tvData5 = (TextView) convertView.findViewById(R.id.tv_data5);
                holder.tvData6 = (TextView) convertView.findViewById(R.id.tv_data6);
                holder.tvData7 = (TextView) convertView.findViewById(R.id.tv_data7);
                holder.tvData8 = (TextView) convertView.findViewById(R.id.tv_data8);
                holder.tvData9 = (TextView) convertView.findViewById(R.id.tv_data9);
                holder.tvData10 = (TextView) convertView.findViewById(R.id.tv_data10);
                holder.tvData11 = (TextView) convertView.findViewById(R.id.tv_data11);
                holder.tvData12 = (TextView) convertView.findViewById(R.id.tv_data12);
                holder.tvData13 = (TextView) convertView.findViewById(R.id.tv_data13);
                holder.tvData14 = (TextView) convertView.findViewById(R.id.tv_data14);
                holder.tvData15 = (TextView) convertView.findViewById(R.id.tv_data15);
                holder.tvData16 = (TextView) convertView.findViewById(R.id.tv_data16);
                holder.tvData17 = (TextView) convertView.findViewById(R.id.tv_data17);
                holder.tvData18 = (TextView) convertView.findViewById(R.id.tv_data18);
                holder.tvData19 = (TextView) convertView.findViewById(R.id.tv_data19);
                holder.tvData20 = (TextView) convertView.findViewById(R.id.tv_data20);


                holder.linContent = (LinearLayout) convertView.findViewById(R.id.lin_content);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }
            if("VS".equals(mListData.get(position).observationMethod)||"MS".equals(mListData.get(position).observationMethod)){
                  String [] duplicateContent1=mListData.get(position).duplicateContent1.split(",");
                holder.tvData.setText(duplicateContent1[0]);
                holder.tvData2.setText(duplicateContent1[1].substring(1));
                holder.tvData3.setText(duplicateContent1[2].substring(1));
                holder.tvData4.setText(duplicateContent1[3].substring(1));
                holder.tvData5.setText(duplicateContent1[4].substring(1));
                holder.tvData6.setText(duplicateContent1[5].substring(1));
                holder.tvData7.setText(duplicateContent1[6].substring(1));
                holder.tvData8.setText(duplicateContent1[7].substring(1));
                holder.tvData9.setText(duplicateContent1[8].substring(1));
                holder.tvData10.setText(duplicateContent1[9].substring(1));
                holder.tvData11.setText(duplicateContent1[10].substring(1));
                holder.tvData12.setText(duplicateContent1[11].substring(1));
                holder.tvData13.setText(duplicateContent1[12].substring(1));
                holder.tvData14.setText(duplicateContent1[13].substring(1));
                holder.tvData15.setText(duplicateContent1[14].substring(1));
                holder.tvData16.setText(duplicateContent1[15].substring(1));
                holder.tvData17.setText(duplicateContent1[16].substring(1));
                holder.tvData18.setText(duplicateContent1[17].substring(1));
                holder.tvData19.setText(duplicateContent1[18].substring(1));
                holder.tvData20.setText(duplicateContent1[19].substring(1));
            }
            else{
                holder.tvData.setText(mListData.get(position).duplicateContent1);
                holder.tvData2.setText("");
                holder.tvData3.setText("");
                holder.tvData4.setText("");
                holder.tvData5.setText("");
                holder.tvData6.setText("");
                holder.tvData7.setText("");
                holder.tvData8.setText("");
                holder.tvData9.setText("");
                holder.tvData10.setText("");
                holder.tvData11.setText("");
                holder.tvData12.setText("");
                holder.tvData13.setText("");
                holder.tvData14.setText("");
                holder.tvData15.setText("");
                holder.tvData16.setText("");
                holder.tvData17.setText("");
                holder.tvData18.setText("");
                holder.tvData19.setText("");
                holder.tvData20.setText("");
            }
            return convertView;
        }

        class ViewHolder {
            TextView tvData;
            TextView tvData2;
            TextView tvData3;
            TextView tvData4;
            TextView tvData5;
            TextView tvData6;
            TextView tvData7;
            TextView tvData8;
            TextView tvData9;
            TextView tvData10;
            TextView tvData11;
            TextView tvData12;
            TextView tvData13;
            TextView tvData14;
            TextView tvData15;
            TextView tvData16;
            TextView tvData17;
            TextView tvData18;
            TextView tvData19;
            TextView tvData20;

            LinearLayout linContent;
        }
    }
}
