package com.pdwy.dus.collection.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.pdwy.dus.collection.R;
import com.pdwy.dus.collection.activity.PhotographActivity;
import com.pdwy.dus.collection.core.BaseFragment;
import com.pdwy.dus.collection.core.ExcelListActivity;
import com.pdwy.dus.collection.core.NoscrollListView;
import com.pdwy.dus.collection.core.SyncHorizontalScrollView;
import com.pdwy.dus.collection.model.bean.MessageEvent;
import com.pdwy.dus.collection.model.db.InputData;
import com.pdwy.dus.collection.utils.MLog;
import com.pdwy.dus.collection.utils.PopupWindowUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 * 个体性状录入
 * Author： by MR on 2018/9/3.
 */

public class IndividualCharacterFragment extends Fragment {
    private NoscrollListView mLeft;
    private LeftAdapter mLeftAdapter;

    private NoscrollListView mData;
    private DataAdapter mDataAdapter;

    private SyncHorizontalScrollView mHeaderHorizontal;
    private SyncHorizontalScrollView mDataHorizontal;
    private List<String> mListData;
    ArrayList<String> duplicateContent1List;
     int mix;
     int max;
    InputData inputData;
     String yuzhi;
     String syrwbh;
     String xz;
    int iiiiii=0;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        inputData=new InputData(getActivity());

        EventBus.getDefault().register(this);
        View v=inflater.inflate(R.layout.excel_list_activity, container, false);
        initView(v);
        return v;
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {

          yuzhi= inputData.getYuZhi(messageEvent.getMessage(),messageEvent.getP());
//        duplicateContent1List=inputData.getDuplicateContent1(mListData,getArguments().getString("syrwbh"),getArguments().getString("xz"));
//        ListView lv1 = getFragmentManager().findFragmentById(R.id.fl_frgmt_administration).getView().findViewById(R.id.lv_data);
//        lv1.
        TextView tv_xz=(TextView)getActivity().findViewById(R.id.tv_xz);
        xz= tv_xz.getText().toString();
        MLog.e(xz);
        iiiiii=0;
        duplicateContent1List=inputData.getDuplicateContent1(mListData,syrwbh,xz);
                mDataAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    private void initView(View v){
        mListData= getArguments().getStringArrayList("listCharacterThresholdBeanTestNumberlist");
        syrwbh=getArguments().getString("syrwbh");
        TextView tv_xz=(TextView)getActivity().findViewById(R.id.tv_xz);
        xz= tv_xz.getText().toString();
        MLog.e(xz);
        yuzhi= inputData.getYuZhi(getArguments().getString("mbmc"),xz);
        MLog.e("yuzhi====="+yuzhi);
         duplicateContent1List=inputData.getDuplicateContent1(mListData,syrwbh,xz);

        mLeft =  v.findViewById(R.id.lv_left);
        mData = v.findViewById(R.id.lv_data);
        mDataHorizontal =  v.findViewById(R.id.data_horizontal);
        mHeaderHorizontal =  v.findViewById(R.id.header_horizontal);

        mDataHorizontal.setScrollView(mHeaderHorizontal);
        mHeaderHorizontal.setScrollView(mDataHorizontal);

        mLeftAdapter= new LeftAdapter();
        mLeft.setAdapter(mLeftAdapter);
                    mDataAdapter = new DataAdapter();
                    mData.setAdapter(mDataAdapter);
    }
    // 左边listview
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
            ViewHolder holder ;
            if (convertView == null) {
                holder = new LeftAdapter.ViewHolder();
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.item_left, null);
                holder.tvLeft = (TextView) convertView.findViewById(R.id.tv_left);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.tvLeft.setText(mListData.get(position));
            final View finalConvertView = convertView;
            //测试编号点击事件
            holder.tvLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                    final View view1 = View.inflate(getActivity(), R.layout.view_previous_data, null);
                    alertDialog
                            .setIcon(R.mipmap.ic_launcher)
                            .setView(view1)
                            .create();
                    alertDialog.setCancelable(false);
                    final AlertDialog show = alertDialog.show();
                    show.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                    TextView tv_csbh=(TextView) view1.findViewById(R.id.tv_csbh);
                    tv_csbh.setText(mListData.get(position));
                    TextView tv_xzmc=(TextView) view1.findViewById(R.id.tv_xzmc);
                    tv_xzmc.setText(getArguments().getString("xz"));
                   TextView tv_quxiao=(TextView) view1.findViewById(R.id.tv_quxiao);
                    tv_quxiao.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            show.dismiss();
                        }
                    });
                    TextView tv_chongzhi=(TextView) view1.findViewById(R.id.tv_chongzhi);
                    tv_chongzhi.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            LinearLayout ll_sc_synsj=(LinearLayout) view1.findViewById(R.id.ll_sc_synsj);
                            for(int i=0;i<ll_sc_synsj.getChildCount();i++){
                                View view= ll_sc_synsj.getChildAt(i);
                                EditText ed_item_item_ll_sc_synsj=view.findViewById(R.id.ed_item_item_ll_sc_synsj);
                                ed_item_item_ll_sc_synsj.setText("");
                            }
                        }
                    });
                    TextView tv_queren=(TextView) view1.findViewById(R.id.tv_queren);
                    tv_queren.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String ss = "";
                            ListView lv1 = getFragmentManager().findFragmentById(R.id.fl_frgmt_administration).getView().findViewById(R.id.lv_data);
                            LinearLayout ll = (LinearLayout) lv1.getChildAt(position);
                            LinearLayout lll = ll.findViewById(R.id.lin_content);

                            LinearLayout ll_sc_synsj=(LinearLayout) view1.findViewById(R.id.ll_sc_synsj);
                            for(int i=0;i<ll_sc_synsj.getChildCount();i++){
                                View view= ll_sc_synsj.getChildAt(i);
                                EditText ed_item_item_ll_sc_synsj=view.findViewById(R.id.ed_item_item_ll_sc_synsj);
                                EditText ed=(EditText)lll.getChildAt(i+i);
                                ed.setText(ed_item_item_ll_sc_synsj.getText().toString());
                            }
                            show.dismiss();

                        }
                    });
                    //获取上一年数据
            String lastYearData= inputData.getLastYearData(mListData.get(position),getArguments().getString("xz"));
            if(lastYearData==null)
                Toast.makeText(getActivity(),"没有找到上一年数据",Toast.LENGTH_SHORT).show();
                    String lastYear[] = new String[0];
                    if(lastYearData!=null)
                    lastYear =lastYearData.split(",");
                    LinearLayout ll_sc_synsj=(LinearLayout) view1.findViewById(R.id.ll_sc_synsj);
                    for(int i=1;i<21;i++) {
                        View view = LinearLayout.inflate(getActivity(), R.layout.item_ll_sc_synsj, null);
                        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.item_ll_sc_synsj);
                        View view11 = LinearLayout.inflate(getActivity(), R.layout.item_item_ll_sc_synsj, null);
                        TextView tv11=(TextView)view11.findViewById(R.id.tv_item_item_ll_sc_synsj);
                        tv11.setText(i+"");
                        linearLayout.addView(view11);
                        View view22 = LinearLayout.inflate(getActivity(), R.layout.item_item_ll_sc_synsj, null);

                        linearLayout.addView(view22);
//                        View view33 = LinearLayout.inflate(getActivity(), R.layout.item_item_ll_sc_synsj, null);
//
//                        linearLayout.addView(view33);
//                        linearLayout.addViewInLayout(view33,1,);
                        View view44 =  LinearLayout.inflate(getActivity(), R.layout.item_item_ll_sc_synsj2, null);
                        TextView text44=view44.findViewById(R.id.ed_item_item_ll_sc_synsj);
                        if(lastYearData!=null)
                            text44.setText((lastYear[i].equals("0"))?"":lastYear[i]);
                        linearLayout.addView(view44);
                        ll_sc_synsj.addView(view);
                    }
                }
            });
            return convertView;
        }

        class ViewHolder {
            /**
             * 测试编号
             */
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

            if (yuzhi != null && !"".equals(yuzhi)) {
                String yuzhi2[] = yuzhi.split("-");
                mix = Integer.valueOf(yuzhi2[0]);
                max = Integer.valueOf(yuzhi2[1]);
            }
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.item_data, null);
                holder.tvData = (TextView) convertView.findViewById(R.id.tv_data);
                holder.linContent = (LinearLayout) convertView.findViewById(R.id.lin_content);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            TextView tvp = (TextView) holder.linContent.getChildAt(holder.linContent.getChildCount() - 4);
            tvp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), PhotographActivity.class);
                    intent.putExtra("activityP", "0");
                    intent.putExtra("pinzhong", getArguments().getString("pinzhong"));
                    intent.putExtra("experimentalNumber", getArguments().getString("experimentalNumber"));
                    intent.putExtra("stateOfExpression", "0");
                    intent.putExtra("csbh", mListData.get(position));

                    ArrayList<String> listCharacterThresholdBeanCharacterName = new ArrayList<>();
                    TextView tv = (TextView) getActivity().findViewById(R.id.tv_xz);
                    listCharacterThresholdBeanCharacterName.add(tv.getText().toString().substring(3));
                    intent.putStringArrayListExtra("xzListData", listCharacterThresholdBeanCharacterName);
                    startActivity(intent);

                    Toast.makeText(getActivity(), "拍照", Toast.LENGTH_SHORT).show();
                }
            });
            String duplicateContent11=inputData.getDuplicateContent1i(mListData.get(position),syrwbh,xz);

            iiiiii++;
            if(duplicateContent11!=null&&!"".equals(duplicateContent11))
            {
            String[] duplicateContent1;
//            if (duplicateContent1List != null && duplicateContent1List.size() > 0) {
//
//                duplicateContent1 = duplicateContent1List.get(position).split(",");
//            } else
//                duplicateContent1 = new String[0];
                duplicateContent1=duplicateContent11.split(",");
            int x = 0;
            for (int q = 0; q < 41; q = q + 2) {
                final EditText edd = (EditText) holder.linContent.getChildAt(q);
                    if (x == 0)
                        edd.setText(duplicateContent1[x]);
                    else
                        edd.setText(duplicateContent1[x].substring(1));
                    x++;
//                edd.setFocusable(false);
//
//                edd.setFocusableInTouchMode(false);
                edd.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (yuzhi != null && !"".equals(yuzhi)) {

                            Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
                            if ("".equals(s.toString()))
                                return;
                            if (!pattern.matcher(s.toString()).matches()) {
                                edd.setText("");
                                return;
                            }


                            if (Integer.valueOf(s.toString()) > max)
                                edd.setTextColor(Color.RED);
                            else if (Integer.valueOf(s.toString()) < mix)
                                edd.setTextColor(Color.RED);
                            else
                                edd.setTextColor(Color.BLACK);
                        }
                    }
                });
            }

        }else{
                int x = 0;
                for (int q = 0; q < 41; q = q + 2) {
                    final EditText edd = (EditText) holder.linContent.getChildAt(q);
                    if (x == 0)
                        edd.setText("");
                    else
                        edd.setText("");
                    x++;
//                edd.setFocusable(false);
//
//                edd.setFocusableInTouchMode(false);
                    edd.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            if (yuzhi != null && !"".equals(yuzhi)) {

                                Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
                                if ("".equals(s.toString()))
                                    return;
                                if (!pattern.matcher(s.toString()).matches()) {
                                    edd.setText("");
                                    return;
                                }


                                if (Integer.valueOf(s.toString()) > max)
                                    edd.setTextColor(Color.RED);
                                else if (Integer.valueOf(s.toString()) < mix)
                                    edd.setTextColor(Color.RED);
                                else
                                    edd.setTextColor(Color.BLACK);
                            }
                        }
                    });
                }

            }
            final TextView tvy= (TextView) holder.linContent.getChildAt(holder.linContent.getChildCount()-2);
            tvy.setText(inputData.getAbnormal(mListData.get(position),syrwbh,xz));
            tvy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {

                    LinearLayout contentView= (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.fragment_population_traits_item_data_item_pop, null, false);
                    LinearLayout contentViewLinearLayout=contentView.findViewById(R.id.ll_sc_ll);
                     final PopupWindowUtils popupWindowUtils=new PopupWindowUtils(contentView,300,280);
                    LinearLayout contentView3= (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.fragment_population_traits_item_data_item_pop_item, null, false);

                    TextView tv1=contentView3.findViewById(R.id.tv_bdzt);
                    tv1.setText("异常");
                    TextView tv2=contentView3.findViewById(R.id.tv_dm);
                    tv2.setText("是");

                    contentView3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v1) {
                            tvy.setText("异常");
                            popupWindowUtils.dismiss();
                        }
                    });
                    contentViewLinearLayout.addView(contentView3);
                    LinearLayout contentView4= (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.fragment_population_traits_item_data_item_pop_item, null, false);

                    TextView tv12=contentView4.findViewById(R.id.tv_bdzt);
                    tv12.setText("异常");
                    TextView tv22=contentView4.findViewById(R.id.tv_dm);
                    tv22.setText("否");

                    contentView4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v1) {
                            tvy.setText("");
                            popupWindowUtils.dismiss();
                        }
                    });
                    contentViewLinearLayout.addView(contentView4);
                    popupWindowUtils.showAsDropDown(v,0,0);



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
