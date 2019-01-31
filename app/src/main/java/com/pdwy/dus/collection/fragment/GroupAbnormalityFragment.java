package com.pdwy.dus.collection.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
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

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 群体异常录入
 * Author： by MR on 2018/9/3.
 */

public class GroupAbnormalityFragment extends Fragment {

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
    // 保留小数
    DecimalFormat decimalFormat;
    //总数
    int d;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_group_abnormality, container, false);



        initView(v);
        setDate();
        return v;
    }
    private void setDate(){



//        // 禁止自动滑动
//        mDataHorizontal.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
//        mDataHorizontal.setFocusable(true);
//        mDataHorizontal.setFocusableInTouchMode(true);
//        mDataHorizontal.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                v.requestFocusFromTouch();
//                return false;
//            }
//        });

    }
    private void initView(View v){
        inputData=new InputData(getActivity());
         decimalFormat = new DecimalFormat(".00");//构造方法的字符格式这里如果小数不足2位,会以0补足.


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
             if("VG".equals(characterBean.observationMethod)||"MG".equals(characterBean.observationMethod))
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
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_group_abnormality_item_left, null);
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
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_group_abnormality_item_data, null);

                holder.linContent = (LinearLayout) convertView.findViewById(R.id.lin_content);

                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }

            String abnormalContent1=inputData.getAbnormalContent(mListData.get(position).testNumber,getArguments().getString("syrwbh"),mListData.get(position).characterName);
            // 总数
            final EditText edd36 = (EditText) holder.linContent.getChildAt(36);
            // 这是不对编辑和空背景
            edd36.setEnabled(false);
            edd36.setBackground(null);
            // 备注
            EditText edd38 = (EditText) holder.linContent.getChildAt(38);
            // 有保存的数据则写入
            if(abnormalContent1!=null&&!"".equals(abnormalContent1)){
                String []abnormalContent=abnormalContent1.split(";");
                edd36.setText(abnormalContent[9].substring(1));
                edd38.setText(abnormalContent[10].substring(1));

            int x=0;
            //循环写入1-9列数据
                for(int q=0;q<35;q=q+4){
                    String []abnormalContent2 = abnormalContent[x].split(",");

                    TextView tvv = (TextView) holder.linContent.getChildAt(q);
                     EditText edd = (EditText) holder.linContent.getChildAt(q+2);

                    if(x==0) {
                        tvv.setText(abnormalContent2[0]);
                        edd.setText(abnormalContent2[1].substring(1));
                    }
                    else {
                        tvv.setText(abnormalContent2[0].substring(1));
                        edd.setText(abnormalContent2[1].substring(1));
                    }
                    x++;


                }

            }

             // 初始化
            for(int i=0;i<35;i=i+4) {
                TextView tv= (TextView) holder.linContent.getChildAt(i);
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CharacterBean characterBean=inputData.getCharacterThresholdBean(mListData.get(position).characterName);

                        String codeDescription=characterBean.codeDescription;
                        final String [] codeDescriptions= codeDescription.split(",");

                        String codeValue=characterBean.codeValue;
                        final String [] codeValues= codeValue.split(",");

                        LinearLayout contentView= (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.fragment_population_traits_item_data_item_pop, null, false);
                        LinearLayout contentViewLinearLayout=contentView.findViewById(R.id.ll_sc_ll);
                        final TextView tvv= (TextView) v;
                        final PopupWindowUtils popupWindowUtils=new PopupWindowUtils(contentView,400,450);
                        for(int ii=0;ii<codeDescriptions.length;ii++){
                            LinearLayout contentView2= (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.fragment_population_traits_item_data_item_pop_item, null, false);
                            TextView tv1=contentView2.findViewById(R.id.tv_bdzt);
                            tv1.setText(codeDescriptions[ii]);
                            TextView tv2=contentView2.findViewById(R.id.tv_dm);
                            tv2.setText(codeValues[ii]);

                            final int finalIi = ii;
                            contentView2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v1) {
                                    tvv.setText(codeValues[finalIi]+":"+codeDescriptions[finalIi]);
                                    popupWindowUtils.dismiss();
                                }
                            });
                            contentViewLinearLayout.addView(contentView2);
                        }

                        popupWindowUtils.showAsDropDown(v,0,0);
                    }
                });


                final EditText edd = (EditText) holder.linContent.getChildAt(i + 2);
//                edd.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

                final ViewHolder finalHolder = holder;
                // EditText 在获取焦点的时候 添加点击事件
                edd.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        final EditText ed = (EditText) v;
                        if (hasFocus) {


                            ed.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                }

                                @Override
                                public void onTextChanged(CharSequence s, int start, int before, int count) {

                                }

                                @Override
                                public void afterTextChanged(Editable s) {

                                    Pattern pattern = Pattern.compile("[0-9]*");
                                    Matcher isNum = pattern.matcher(s.toString());
                                    if( isNum.matches() ){
                                        d = 0;
                                        for (int l = 2; l <= 34; l = l + 4) {
                                            TextView tvl = (TextView) finalHolder.linContent.getChildAt(l);
                                            try {
                                                d = d + Integer.valueOf(tvl.getText().toString());
                                            } catch (Exception e) {


                                            }

                                        }

//                                           String p = decimalFormat.format(d);//保留小数点后两位  format 返回的是字符串
                                        edd36.setText(String.valueOf(d));


                                    }else{
                                        ed.setText("");

                                    }


                                }
                            });
                        }


                    }
                });
            }





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
                    Toast.makeText(getActivity(),"拍照",Toast.LENGTH_LONG).show();
                }
            });

            return convertView;
        }

        class ViewHolder {
            LinearLayout linContent;
        }
    }

}
