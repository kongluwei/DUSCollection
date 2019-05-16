package com.pdwy.dus.collection.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.pdwy.dus.collection.R;
import com.pdwy.dus.collection.adapter.MyExtendableListViewAdapter;
import com.pdwy.dus.collection.adapter.QuickAdapter;
import com.pdwy.dus.collection.core.BaseActivity;
import com.pdwy.dus.collection.model.bean.CharacterThresholdBean;
import com.pdwy.dus.collection.model.db.InputData;
import com.pdwy.dus.collection.utils.MLog;
import com.pdwy.dus.collection.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : KongLW
 * e-mail : kongluweishujia@163.com
 * date   : 2019/4/2516:35
 * desc   :设置性状的关联
 */
public class RelationCharacterActivity extends BaseActivity{


    @BindView(R.id.ll_head_return)
    LinearLayout ll_head_return;
    @BindView(R.id.ll_head_dus)
    LinearLayout ll_head_dus;
    @BindView(R.id.ll_head_personalcenter)
    LinearLayout ll_head_personalcenter;
    @BindView(R.id.tv_head_title)
    TextView tv_head_title;

    @BindView(R.id.elv_relation_character)
    ExpandableListView elv_relation_character;

    MyExtendableListViewAdapter myExtendableListViewAdapter;
    List<CharacterThresholdBean> listExtendableListData;
    StaggeredGridLayoutManager staggeredGridLayoutManager;

    InputData inputData;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_relation_character;
    }

    @Override
    protected void onCreateAfter() {


        initView();
        setData();
        setAdapter();
    }




    private void initView(){
        ll_head_return.setVisibility(View.VISIBLE);
        ll_head_dus.setVisibility(View.GONE);
        ll_head_personalcenter.setVisibility(View.INVISIBLE);
        tv_head_title.setVisibility(View.VISIBLE);
        tv_head_title.setText("关联性状设置");
        //长按时间
        elv_relation_character.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, final long id) {
                int itemType = ExpandableListView.getPackedPositionType(id);
                AlertDialog.Builder builder = new AlertDialog.Builder(RelationCharacterActivity.this);

                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int  groupPosition = ExpandableListView.getPackedPositionGroup(id);
                        inputData.deleteRelationCharacter(listExtendableListData.get(groupPosition),getIntent().getStringExtra("banben"));
                        Intent intent=new Intent(RelationCharacterActivity.this,RelationCharacterActivity.class);
                        intent.putExtra("banben",getIntent().getStringExtra("banben"));
                        startActivity(intent);
                        finish();

                    }
                });




                if ( itemType == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
                    int childPosition = ExpandableListView.getPackedPositionChild(id);
                    int groupPosition = ExpandableListView.getPackedPositionGroup(id);
//                    builder.setTitle("删除此条记录");
//                    AlertDialog dialog = builder.create();
//                    dialog.show();
//                    Toast.makeText(getActivity(), "childPosition"+childPosition+"groupPosition"+groupPosition, Toast.LENGTH_SHORT).show();
                    //do your per-item callback here
                    return true; //true if we consumed the click, false if not. consume : 消耗

                } else if(itemType == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
                    int  groupPosition = ExpandableListView.getPackedPositionGroup(id);
                    builder.setTitle("删除此组记录");
                    AlertDialog dialog = builder.create();
                    dialog.show();


//                    Toast.makeText(getActivity(),"groupPosition"+groupPosition, Toast.LENGTH_SHORT).show();
                    //do your per-group callback here
                    return true; //true if we consumed the click, false if not

                } else {
                    // null item; we don't consume the click
                    return false;
                }



            }
        });


        inputData=new InputData(this);

    }
    private void setData() {
        if(listExtendableListData==null)
        listExtendableListData=inputData.getRelationCharacter(getIntent().getStringExtra("banben"));
        else {
            listExtendableListData.clear();
            listExtendableListData=inputData.getRelationCharacter(getIntent().getStringExtra("banben"));
        }
    }

    private void setAdapter() {

         myExtendableListViewAdapter=new MyExtendableListViewAdapter(this,listExtendableListData);
        elv_relation_character.setAdapter(myExtendableListViewAdapter);
    }
    @OnClick({R.id.tv_relation_character_add})
    public void onClick(View v){
        switch (v.getId()){

            case R.id.tv_relation_character_add:


                final List<CharacterThresholdBean> list= inputData.getCharacterThresholdBeanList(getIntent().getStringExtra("banben"));




                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                final View view1 = View.inflate(this, R.layout.view_relation_character, null);
                // 瀑布流
                staggeredGridLayoutManager =  new StaggeredGridLayoutManager(3, LinearLayoutManager.HORIZONTAL);


                final RecyclerView rv_relation_character_xz=view1.findViewById(R.id.rv_relation_character_xz);
                //设置布局管理器
                rv_relation_character_xz.setLayoutManager(staggeredGridLayoutManager);


               final QuickAdapter<CharacterThresholdBean> quickAdapter= new QuickAdapter<CharacterThresholdBean>(list) {
                    @Override
                    public int getLayoutId(int viewType) {
                        return R.layout.item_rv_relation_chararter;
                    }

                    @Override
                    public void convert(QuickAdapter.VH holder, CharacterThresholdBean data, int position) {

                        final CheckBox rb_xzmc =holder.getView(R.id.rb_xzmc);
                        rb_xzmc.setText(data.characterName);
                        // 已经关联过的性状不能再选择
                        if(!"".equals(data.relationId)&&data.relationId!=null)
                            rb_xzmc.setEnabled(false);

                    }


                };

//设置Adapter
                rv_relation_character_xz.setAdapter(quickAdapter);

                alertDialog
                        .setIcon(R.mipmap.ic_launcher)
                        .setView(view1)
                        .create();
                alertDialog.setCancelable(true);
                final AlertDialog show = alertDialog.show();
                show.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);

                final EditText ed_relation_character_name=(EditText) view1.findViewById(R.id.ed_relation_character_name);
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

                        //在获取RecyclerView没有滑动到item是 获取的view为空 会报空指针异常
                        //目前的解决办法 异常可以忽略
                        try {
                            for (int p=0;p<list.size();p++) {
                                MLog.e("list.size()====="+p);
//                            LinearLayout linearLayout= (LinearLayout) staggeredGridLayoutManager.findViewByPosition(p);
                                LinearLayout linearLayout= (LinearLayout) staggeredGridLayoutManager.getChildAt(p);
                                CheckBox checkBox= (CheckBox) linearLayout.findViewById(R.id.rb_xzmc);
                                checkBox.setChecked(false);
                            }
                        }
                        catch (Exception e){

                            MLog.e("条目没有加载完成");
                        }

                    }
                });
                TextView tv_queren=(TextView) view1.findViewById(R.id.tv_queren);
                tv_queren.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                       String relationName= ed_relation_character_name.getText().toString().trim();
                        if("".equals(relationName)||relationName==null) {
                            ToastUtil.showMessage(RelationCharacterActivity.this, "组合名字不能为空");
                            return;
                        }
                        //记录选择了几个性状
                        int i=0;
                        List<CharacterThresholdBean> listS=new ArrayList<>();
                        try {
                            // 循环获取item里的数据 判断是否被选中
                            for (int p=0;p<list.size();p++) {
                                LinearLayout linearLayout= (LinearLayout) staggeredGridLayoutManager.getChildAt(p);
                                CheckBox checkBox= (CheckBox) linearLayout.findViewById(R.id.rb_xzmc);
                                // 被选择了
                                if(checkBox.isChecked()){

                                    listS.add(list.get(p));
                                    i++;


                                }
                            }

                        }
                           catch (Exception e){


                            MLog.e("条目没有加载完成");



                           }finally {
                            if(i<2)
                                ToastUtil.showMessage(RelationCharacterActivity.this,"至少选择两个性状");
                                // 写入 并判断是否存在
                            else  if(inputData.setRelationCharacter(listS,relationName,getIntent().getStringExtra("banben"))>0)
                                ToastUtil.showMessage(RelationCharacterActivity.this,"已有这项组合，请修改名称");
                                //都成功才关闭
                            else {
                                show.dismiss();
                                setData();
                               setAdapter();

                                elv_relation_character.collapseGroup(elv_relation_character.getCount()-1);
                                elv_relation_character.expandGroup(elv_relation_character.getCount()-1);
                            }
                        }

                    }
                });



                break;

        }

    }


}
