package com.pdwy.dus.collection.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pdwy.dus.collection.R;
import com.pdwy.dus.collection.activity.PhotographActivity;
import com.pdwy.dus.collection.core.BaseFragment;
import com.pdwy.dus.collection.core.NoscrollListView;
import com.pdwy.dus.collection.core.SyncHorizontalScrollView;
import com.pdwy.dus.collection.model.bean.CharacterBean;
import com.pdwy.dus.collection.model.db.InputData;
import com.pdwy.dus.collection.utils.MLog;
import com.pdwy.dus.collection.utils.PopupWindowUtils;

import java.util.ArrayList;

/**
 * 个体异常录入
 * Author： by MR on 2018/9/3.
 */

public class IndividualAbnormalityFrament extends Fragment {

    private NoscrollListView mLeft;
    private LeftAdapter mLeftAdapter;

    private NoscrollListView mData;
    private DataAdapter mDataAdapter;
    InputData inputData;
    private SyncHorizontalScrollView mHeaderHorizontal;
    private SyncHorizontalScrollView mDataHorizontal;
    private ArrayList<CharacterBean> mListData;
    private ArrayList<CharacterBean> mListData1;
    private ArrayList<CharacterBean> mListData2;
    private ArrayList<CharacterBean> mListData3;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_individual_bnormality, container, false);


        initView(v);
        setDate();
        return v;
    }
    private void setDate(){


    }
    private void initView(View v){
        inputData=new InputData(getActivity());

        if("zidingyi".equals(getArguments().getString("activityName"))) {
            mListData2 = inputData.getCharacterBeanList(getArguments().getString("pinzhong"), getArguments().getString("syrwbh"), getArguments().getString("mbmc"), getArguments().getString("syq"));

            ArrayList<String> xzmcList = getArguments().getStringArrayList("listXzmc");

            mListData3=new ArrayList<>();
            for(CharacterBean c:mListData2){

                for(String s:xzmcList){
                    if(s.equals(c.characterName))
                    {
                        mListData3.add(c);
                        continue;
                    }

                }
            }
            mListData1=new ArrayList<>();
            ArrayList<String> tjbhList = getArguments().getStringArrayList("tjbhList");
            for(CharacterBean c:mListData3){

                for(String s:tjbhList){
                    if(s.equals(c.testNumber))
                    {
                        mListData1.add(c);
                        continue;
                    }

                }
            }


        }else {
            mListData1 = inputData.getCharacterBeanList(getArguments().getString("pinzhong"), getArguments().getString("syrwbh"), getArguments().getString("mbmc"), getArguments().getString("syq"));
        }


        mListData=new ArrayList<>();
        for(CharacterBean characterBean:mListData1){
            if("VS".equals(characterBean.observationMethod)||"MS".equals(characterBean.observationMethod))
                mListData.add(characterBean);
        }

        mLeft =  v.findViewById(R.id.lv_left);
        mData = v.findViewById(R.id.lv_data);
        mDataHorizontal =  v.findViewById(R.id.data_horizontal);
        mHeaderHorizontal =  v.findViewById(R.id.header_horizontal);

        mDataHorizontal.setScrollView(mHeaderHorizontal);
        mHeaderHorizontal.setScrollView(mDataHorizontal);

//        for(int i=0;i<10;i++)
//        mListData.add(new CharacterBean());
        mLeftAdapter= new LeftAdapter();
        mLeft.setAdapter(mLeftAdapter);
        mDataAdapter = new DataAdapter();
        mData.setAdapter(mDataAdapter);

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
                holder = new LeftAdapter.ViewHolder();
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_individual_bnormality_item_left, null);
                holder.tvLeft = (TextView) convertView.findViewById(R.id.tv_left);
                holder.tv_left_xzbh=convertView.findViewById(R.id.tv_left_xzbh);
                holder.tv_left_xzmc=convertView.findViewById(R.id.tv_left_xzmc);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.tvLeft.setText(mListData.get(position).testNumber);
            holder.tv_left_xzbh.setText(mListData.get(position).characterId);
            holder.tv_left_xzmc.setText(mListData.get(position).characterName);
            return convertView;
        }

        class ViewHolder {
            TextView tvLeft;
            TextView tv_left_xzbh;
            TextView tv_left_xzmc;
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if(convertView == null){
                holder = new ViewHolder();
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_individual_bnormality_item_data, null);

                holder.tvData = (TextView) convertView.findViewById(R.id.tv_data);
                holder.linContent = (LinearLayout) convertView.findViewById(R.id.lin_content);

                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }

            String abnormalContent1=inputData.getAbnormalContent(mListData.get(position).testNumber,getArguments().getString("syrwbh"),mListData.get(position).characterName);
            String []abnormalContent = new String[0];
            if(abnormalContent1!=null)
           abnormalContent=abnormalContent1.split(",");
            else
                abnormalContent = new String[0];

            int x=0;
            if(abnormalContent1!=null&&!"".equals(abnormalContent1)){
            for(int q=0;q<holder.linContent.getChildCount()-2;q=q+2){
                TextView tvv = (TextView) holder.linContent.getChildAt(q);
                if(x==0)
                tvv.setText(abnormalContent[x]);
                else
                    tvv.setText(abnormalContent[x].substring(1));
                x++;
            }}
            TextView tvm= (TextView) holder.linContent.getChildAt(holder.linContent.getChildCount()-2);
            tvm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getActivity(),PhotographActivity.class);
                    intent.putExtra("activityP","1");
                    intent.putExtra("pinzhong",getArguments().getString("pinzhong"));
                    intent.putExtra("experimentalNumber",getArguments().getString("experimentalNumber"));
                    intent.putExtra("stateOfExpression","1");
                    intent.putExtra("csbh",mListData.get(position).testNumber);
                    ArrayList<String>listCharacterThresholdBeanCharacterName=new ArrayList<>();
                    listCharacterThresholdBeanCharacterName.add(mListData.get(position).characterName);
                    intent.putStringArrayListExtra("xzListData",listCharacterThresholdBeanCharacterName);
                    startActivity(intent);

                    Toast.makeText(getActivity(),"拍照",Toast.LENGTH_SHORT).show();
                }
            });
            return convertView;
        }

        class ViewHolder {
            TextView tvData;
            LinearLayout linContent;
        }
    }

}
