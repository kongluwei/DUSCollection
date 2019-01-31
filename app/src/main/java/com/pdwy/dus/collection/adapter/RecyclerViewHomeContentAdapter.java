package com.pdwy.dus.collection.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pdwy.dus.collection.R;
import com.pdwy.dus.collection.model.bean.CollectionTaskItemBean;
import com.pdwy.dus.collection.model.bean.CollectionTaskItemListBean;
import com.pdwy.dus.collection.utils.MLog;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页RecyclerView的Adapter
 * Author： by MR on 2018/7/26.
 */

public class RecyclerViewHomeContentAdapter extends RecyclerView.Adapter<RecyclerViewHomeContentAdapter.ViewHolder>{
    private ArrayList<CollectionTaskItemBean> list;
    public RecyclerViewHomeContentAdapter(ArrayList<CollectionTaskItemBean> list){
            this.list=list;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_home_con, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv_taskname.setText(list.get(position).taskName);
        holder.tv_taskname.setTag(position);
        holder.tv_varietynumber.setText("测试品种数（"+list.get(position).collectionOfVarieties+")  ;");
        holder.tv_unfinishednumber.setText("未完成采集测试品种数（"+list.get(position).collectionOfVarietiesHangInTheAir+")");
        LinearLayout ll_item ;

        for(CollectionTaskItemListBean c:list.get(position).list) {
            ll_item = (LinearLayout) LayoutInflater.from(holder.tv_taskname.getContext()).inflate(R.layout.item_rv_home_con_ll_item, null);
            LinearLayout ll= (LinearLayout) ll_item.getChildAt(0);
                    TextView tv1= (TextView) ll.getChildAt(1);
        tv1.setText(c.testNumber);
        TextView tv2= (TextView) ll.getChildAt(3);
        tv2.setText(c.needToCollect+"");
        TextView tv3= (TextView) ll.getChildAt(5);
        tv3.setText(c.haveBeenCollected+"");
        TextView tv4= (TextView) ll.getChildAt(7);
        tv4.setText(c.notCollected+"");
        TextView tv5= (TextView) ll.getChildAt(9);
        tv5.setText(c.toUpload+"");
        TextView tv6= (TextView) ll.getChildAt(11);
        tv6.setText(c.notUploaded+"");
            holder.ll.addView(ll_item);
        }



    }


    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }
    public void addItem(int position){
//        list.addAll(position,"");
        notifyItemInserted(position);
    }

    public void removeItem(int position){
        list.remove(position);
        notifyItemRemoved(position);
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_taskname;
        TextView tv_varietynumber;
        TextView tv_unfinishednumber;
        LinearLayout ll;
        ImageView iv;
        ViewHolder(final View itemView) {
            super(itemView);
            final LinearLayout ll_itemView= (LinearLayout) itemView;
            iv= itemView.findViewById(R.id.iv_zz);
            tv_taskname = itemView.findViewById(R.id.tv_taskname);
            tv_varietynumber = itemView.findViewById(R.id.tv_varietynumber);
            tv_unfinishednumber = itemView.findViewById(R.id.tv_unfinishednumber);
            ll= (LinearLayout) LayoutInflater.from(itemView.getContext()).inflate(R.layout.item_rv_home_con_ll,null);
            ll.setVisibility(View.GONE);
//            Toast.makeText(itemView.getContext(), "点击了"+itemView.getTag(), Toast.LENGTH_SHORT).show();
//
////            int p= (int) tv_taskname.getTag();
////            for(int i=0;i<list.get(0).list.size();i++) {
//                ll_item.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
////                    LinearLayout ll = (LinearLayout) ll_item.getChildAt(2);
////                    TextView tv = (TextView) ll.getChildAt(1);
//                        Toast.makeText(itemView.getContext(), "点击了"+tv_taskname.getTag(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//                ll.addView(ll_item);
////            }
            ll_itemView.addView(ll);
            //点击任务收展表格
            ((LinearLayout) itemView).getChildAt(0).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                        LinearLayout ll1111= (LinearLayout) ll_itemView.getChildAt(2);
                        if(ll1111.getVisibility()==View.VISIBLE) {
                            iv.setImageResource(R.mipmap.list_arr_close);
                            ll1111.setVisibility(View.GONE);
                        }
                        else if(ll1111.getVisibility()==View.GONE) {
                            iv.setImageResource(R.mipmap.list_arr_open);
                            ll1111.setVisibility(View.VISIBLE);
                        }

                }
            });

        }
    }

}
