package com.pdwy.dus.collection.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pdwy.dus.collection.R;
import com.pdwy.dus.collection.activity.CollectionManagementActivity;
import com.pdwy.dus.collection.activity.PhotographActivity;
import com.pdwy.dus.collection.core.BaseFragment;
import com.pdwy.dus.collection.core.NoscrollListView;
import com.pdwy.dus.collection.core.SyncHorizontalScrollView;
import com.pdwy.dus.collection.model.bean.CharacterBean;
import com.pdwy.dus.collection.model.db.InputData;
import com.pdwy.dus.collection.utils.MLog;
import com.pdwy.dus.collection.utils.PopupWindowUtils;
import com.pdwy.dus.collection.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 群体性状录入
 * Author： by MR on 2018/9/3.
 */

public class PopulationTraitsFragment extends Fragment {
    private NoscrollListView mLeft;
    private LeftAdapter mLeftAdapter;

    private NoscrollListView mData;
    private DataAdapter mDataAdapter;
    InputData inputData;
    private SyncHorizontalScrollView mHeaderHorizontal;
    private SyncHorizontalScrollView mDataHorizontal;
    //测试编号list
    private List<String> mListData;
     //性状名称list
    ArrayList<String> listCharacterThresholdBeanCharacterName;
    private  String syrwbh;
    LinearLayout ll_fl_1;
    LinearLayout ll_fl_2;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_population_traits, container, false);
        ll_fl_1=getActivity().findViewById(R.id.ll_fl_1);
        ll_fl_2=getActivity().findViewById(R.id.ll_fl_2);

        ll_fl_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ll_fl_1.setVisibility(View.VISIBLE);
                ll_fl_2.setVisibility(View.GONE);
            }
        });
        initView(v);
        setDate();
        return v;
    }
    private void setDate(){
        inputData=new InputData(getActivity());
        syrwbh= getArguments().getString("syrwbh");

    }
    private void initView(View v){
        listCharacterThresholdBeanCharacterName= getArguments().getStringArrayList("listCharacterThresholdBeanCharacterName");
        if(listCharacterThresholdBeanCharacterName.size()<1){
            ToastUtil.showMessage(getActivity(),"未知错误！");
            return;
        }

        LinearLayout lin_year_title= v.findViewById(R.id.lin_year_title);

        for(int i=0;i<listCharacterThresholdBeanCharacterName.size();i++) {
            LinearLayout lin_year_title_item = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.fragment_population_traits_item, null);
            TextView tv= (TextView) lin_year_title_item.findViewById(R.id.tv_fragment_population_traits_item);
            tv.setText(listCharacterThresholdBeanCharacterName.get(i));

            lin_year_title.addView(lin_year_title_item);
        }
        for (int x=0;x<2;x++) {
            LinearLayout lin_year_title_item = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.fragment_population_traits_item, null);
            TextView tv = (TextView) lin_year_title_item.findViewById(R.id.tv_fragment_population_traits_item);
            if (x==0)
            tv.setText("备注");
            else
                tv.setText("照片");
            lin_year_title.addView(lin_year_title_item);
        }
        mLeft =  v.findViewById(R.id.lv_left);
        mData = v.findViewById(R.id.lv_data);
        mDataHorizontal =  v.findViewById(R.id.data_horizontal);
        mHeaderHorizontal =  v.findViewById(R.id.header_horizontal);

        mDataHorizontal.setScrollView(mHeaderHorizontal);
        mHeaderHorizontal.setScrollView(mDataHorizontal);

        mListData = getArguments().getStringArrayList("listCharacterThresholdBeanTestNumberlist");
        if(mListData.size()<1) {
            ToastUtil.showMessage(getActivity(),"未知错误！");
            return;
        }
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
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_population_traits_item_left, null);
                holder.tvLeft = (TextView) convertView.findViewById(R.id.tv_left);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.tvLeft.setText(mListData.get(position));

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
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if(convertView == null){
                holder = new ViewHolder();
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_population_traits_item_data, null);
                LinearLayout lin_content=convertView.findViewById(R.id.lin_content);
                  for(int i=0;i<listCharacterThresholdBeanCharacterName.size();i++) {
                      String  duplicateContent1=inputData.getDuplicateContent1(mListData.get(position),syrwbh,listCharacterThresholdBeanCharacterName.get(i));

                      LinearLayout lin_content_item = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.fragment_population_traits_item_data_item, null);
                   TextView tv= (TextView) lin_content_item.getChildAt(0);
                   if(duplicateContent1!=null)
                       tv.setText(duplicateContent1);
                    final int finalI = i;
                    tv.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(final View v) {
                           CharacterBean characterBean=inputData.getCharacterThresholdBean(listCharacterThresholdBeanCharacterName.get(finalI));

                           String codeDescription=characterBean.codeDescription;
                          final String [] codeDescriptions= codeDescription.split(",");
                           String codeValue=characterBean.codeValue;
                           final String [] codeValues= codeValue.split(",");

                           LinearLayout contentView= (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.fragment_population_traits_item_data_item_pop, null, false);
                           LinearLayout contentViewLinearLayout=contentView.findViewById(R.id.ll_sc_ll);
                           ImageView imageView=(ImageView)contentView.findViewById(R.id.iv_xztp);
                           final PopupWindowUtils popupWindowUtils=new PopupWindowUtils(contentView,400,450);
                           imageView.setOnClickListener(new View.OnClickListener() {
                               @Override
                               public void onClick(View v) {

                                   ll_fl_1.setVisibility(View.GONE);
                                   ll_fl_2.setVisibility(View.VISIBLE);
                                   popupWindowUtils.dismiss();
                               }
                           });

                           final TextView tvv= (TextView) v;

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

                           LinearLayout contentView3= (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.fragment_population_traits_item_data_item_pop_item, null, false);
                           TextView tv1=contentView3.findViewById(R.id.tv_bdzt);
                           tv1.setText("异常");
                           TextView tv2=contentView3.findViewById(R.id.tv_dm);
                           tv2.setText("是");

                           contentView3.setOnClickListener(new View.OnClickListener() {
                               @Override
                               public void onClick(View v1) {
                                   tvv.setText("异常");
                                   popupWindowUtils.dismiss();
                               }
                           });
                           contentViewLinearLayout.addView(contentView3);
//                           popupWindowUtils.showAsDropDown(v, Gravity.LEFT | Gravity.TOP , v.getWidth()/2 - 300/2, v.getHeight()/2 - 200/2);
                           popupWindowUtils.showAsDropDown(v, 0, 0);
                       }
                   });
                    lin_content.addView(lin_content_item);
                }
             for (int x=0;x<2;x++) {
                    if(x==0) {
                        LinearLayout lin_content_item = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.fragment_population_traits_item_data_item_edtext, null);
                        TextView tv= (TextView) lin_content_item.getChildAt(0);
                        tv.setText(inputData.getDuplicateContent2(mListData.get(position),syrwbh,listCharacterThresholdBeanCharacterName.get(0)));
                        lin_content.addView(lin_content_item);
                    }
                    else
                    {

                        LinearLayout lin_content_item = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.fragment_population_traits_item_data_item, null);
                        TextView tv = (TextView) lin_content_item.getChildAt(0);
                        tv.setText("拍照");
                        tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent intent=new Intent(getActivity(),PhotographActivity.class);
                                intent.putExtra("activityP","0");
                                intent.putExtra("pinzhong",getArguments().getString("pinzhong"));
                                intent.putExtra("experimentalNumber",getArguments().getString("experimentalNumber"));
                                intent.putExtra("stateOfExpression","0");
                                intent.putExtra("csbh",mListData.get(position));
                                intent.putStringArrayListExtra("xzListData",listCharacterThresholdBeanCharacterName);
                                   startActivity(intent);
                                Toast.makeText(getActivity(),"拍照",Toast.LENGTH_LONG).show();
                            }
                        });
                        lin_content.addView(lin_content_item);
                    }
             }
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

}
