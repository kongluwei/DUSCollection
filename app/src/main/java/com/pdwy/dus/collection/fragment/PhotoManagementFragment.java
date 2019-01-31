package com.pdwy.dus.collection.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pdwy.dus.collection.MainActivity;
import com.pdwy.dus.collection.R;
import com.pdwy.dus.collection.activity.PhotoViewActivity;
import com.pdwy.dus.collection.core.BaseFragment;
import com.pdwy.dus.collection.http.InitialDataHttp;
import com.pdwy.dus.collection.http.UploadDataHttp;
import com.pdwy.dus.collection.http.bean.PhotoHttpBean;
import com.pdwy.dus.collection.model.bean.PictureBean;
import com.pdwy.dus.collection.model.db.InputData;
import com.pdwy.dus.collection.utils.MLog;

import java.util.ArrayList;
import java.util.List;

/**
 * 照片管理Fragment
 * Author： by MR on 2018/8/7.
 */

public class PhotoManagementFragment extends BaseFragment {
    private MainActivity parentActivity;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {      //判断标志位
                case 0: //失败
                    parentActivity.dismissLoadingDialog();
                    Toast.makeText(parentActivity, msg.obj.toString(), Toast.LENGTH_LONG).show();

                    break;
                case 1: // 图片服务器地址请求成功

                    for (int i = 0; i < ll_zpgl_ietm.getChildCount(); i++) {
                        LinearLayout linearLayout = (LinearLayout) ll_zpgl_ietm.getChildAt(i);
                        CheckBox checkBox = (CheckBox) linearLayout.findViewById(R.id.cb_zp);
                        if (checkBox.isChecked()) {
                            PictureBean pictureBean = new PictureBean();

                            pictureBean.pictureName = list.get(i).pictureName;
                            pictureBean.stateOfExpression = list.get(i).stateOfExpression;
                            List<PictureBean> listPictureBean = new ArrayList<>();
                            listPictureBean.add(pictureBean);
                            uploadDataHttp.uploadPhoto(listPictureBean, (PhotoHttpBean) msg.obj, i);
                        }

                    }


//                    String path= Environment.getExternalStorageDirectory().getPath()+"/DUS/"+pictureBean.pictureName+("0".equals(pictureBean.stateOfExpression)?"":"（异常）")+".jpg";
//                    final File file=new File(path);
//                    final PhotoHttpBean photoHttp=(PhotoHttpBean)msg.obj;
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            UploadImg.uploadFile(file,photoHttp);
//                            UploadImg.uploadImg(photoHttp,file);
//
//                        }
//                    }).start();

                    break;
                //图片地址上传成功
                case 2:
                    parentActivity.dismissLoadingDialog();
                    Toast.makeText(parentActivity, msg.obj.toString(), Toast.LENGTH_LONG).show();


                    inputData.updateZhaoPian(list.get(msg.arg1).experimentalNumber, list.get(msg.arg1).testNumber, list.get(msg.arg1).characterName);
                    setUpData();
                    break;

                //图片上传成功  去上传图片在图片服务器的地址
                case 3:

                    MLog.e("返回的图片地址=======" + msg.obj.toString());
                    uploadDataHttp.uploadPhotoData(list.get(msg.arg1), msg.obj.toString(), msg.arg1);


                    break;
                case 9: // 加载完成
                    ll_zpgl_ietm.addView((LinearLayout) msg.obj);

                    break;


            }
        }
    };
    UploadDataHttp uploadDataHttp;
    EditText mEtSearch;
    LinearLayout mLayoutDefaultText;
    LinearLayout ll_zpgl_ietm;
    InputData inputData;
    ArrayList<PictureBean> list;
    TextView tv_schu;
    TextView tv_schuang;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_photo_management;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 2) {
            MLog.e("================");
            setUpData();
        }
    }

    @Override
    protected void setUpView() {
        parentActivity = (MainActivity) getActivity();
        uploadDataHttp = new UploadDataHttp(parentActivity, handler);
        inputData = new InputData(getActivity());
        mEtSearch = mContentView.findViewById(R.id.et_search);
        mLayoutDefaultText = mContentView.findViewById(R.id.layout_default);
        ll_zpgl_ietm = mContentView.findViewById(R.id.ll_zpgl_ietm);
        tv_schu = mContentView.findViewById(R.id.tv_schu);
        tv_schuang = mContentView.findViewById(R.id.tv_schuang);
        tv_schu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int c = 0;

                for (int i = 0; i < ll_zpgl_ietm.getChildCount(); i++) {
                    LinearLayout linearLayout = (LinearLayout) ll_zpgl_ietm.getChildAt(i);
                    CheckBox checkBox = (CheckBox) linearLayout.findViewById(R.id.cb_zp);
                    if (checkBox.isChecked())
                        c++;

                }
                if (c == 0) {
                    Toast.makeText(getActivity(), "请勾选数据", Toast.LENGTH_SHORT).show();
                    return;
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("删除勾选的" + c + "个照片");
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                final int finalC = c;
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int c2 = 0;
                        for (int i = 0; i < ll_zpgl_ietm.getChildCount(); i++) {
                            LinearLayout linearLayout = (LinearLayout) ll_zpgl_ietm.getChildAt(i);
                            CheckBox checkBox = (CheckBox) linearLayout.findViewById(R.id.cb_zp);
                            if (checkBox.isChecked()) {

                                if (inputData.deleteZhaoPian(list.get(i))) {
                                    c2++;
                                }
                            }

                        }
                        //判断照片是否删除成功
                        if (c2 == finalC) {
                            Toast.makeText(getActivity(), "照片删除成功", Toast.LENGTH_SHORT).show();
                            setUpData();
                        } else {
                            Toast.makeText(getActivity(), "照片删除失败", Toast.LENGTH_SHORT).show();

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
                int c = 0;

                for (int i = 0; i < ll_zpgl_ietm.getChildCount(); i++) {
                    LinearLayout linearLayout = (LinearLayout) ll_zpgl_ietm.getChildAt(i);
                    CheckBox checkBox = (CheckBox) linearLayout.findViewById(R.id.cb_zp);
                    if (checkBox.isChecked())
                        c++;

                }
                if (c == 0) {
                    Toast.makeText(getActivity(), "请勾选数据", Toast.LENGTH_SHORT).show();
                    return;
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("上传勾选的" + c + "个照片");
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        parentActivity.showLoadingDialog("上传中");
                        InitialDataHttp initialDataHttp = new InitialDataHttp(getActivity(), handler);
                        initialDataHttp.getPhotoHttp();

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
//         editText 离开监听
        mEtSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // hasFocus 为false时表示点击了别的控件，离开当前editText控件
                if (!hasFocus) {
                    if ("".equals(mEtSearch.getText().toString())) {
                        mLayoutDefaultText.setVisibility(View.VISIBLE);
                    }
                } else {
                    mLayoutDefaultText.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    protected void setUpData() {
        ll_zpgl_ietm.removeAllViews();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    list = inputData.getZhaoPianList();

                    for (int i = 0; i < list.size(); i++) {
                        LinearLayout ll = (LinearLayout) LinearLayout.inflate(getActivity(), R.layout.fragment_photo_management_item, null);
                        final int finalI = i;
                        ll.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getActivity(), PhotoViewActivity.class);

                                intent.putExtra("pictureName", list.get(finalI).pictureName);
                                intent.putExtra("testNumber", list.get(finalI).testNumber);
                                intent.putExtra("experimentalNumber", list.get(finalI).experimentalNumber);
                                intent.putExtra("stateOfExpression", list.get(finalI).stateOfExpression);
                                intent.putExtra("varieties", list.get(finalI).varieties);
                                intent.putExtra("pictureTime", list.get(finalI).pictureTime);
                                intent.putExtra("characterName", list.get(finalI).characterName);
                                intent.putExtra("whetherOrNotToUpload", list.get(finalI).whetherOrNotToUpload);
                                intent.putExtra("pictureAddress", list.get(finalI).pictureAddress);
                                startActivityForResult(intent, 0);
                            }
                        });
                        TextView tv_zpmc = (TextView) ll.findViewById(R.id.tv_zpmc);
                        tv_zpmc.setText(list.get(i).pictureName);
                        TextView tv_csbh = (TextView) ll.findViewById(R.id.tv_csbh);
                        tv_csbh.setText(list.get(i).testNumber);
                        TextView tv_sybh = (TextView) ll.findViewById(R.id.tv_sybh);
                        tv_sybh.setText(list.get(i).experimentalNumber);
                        TextView tv_bdzt = (TextView) ll.findViewById(R.id.tv_bdzt);
                        tv_bdzt.setText("0".equals(list.get(i).stateOfExpression) ? "正常" : "异常");
                        TextView tv_zw = (TextView) ll.findViewById(R.id.tv_zw);
                        tv_zw.setText(list.get(i).varieties);
                        TextView tv_pssj = (TextView) ll.findViewById(R.id.tv_pssj);
                        tv_pssj.setText(list.get(i).pictureTime);
                        TextView tv_sc = (TextView) ll.findViewById(R.id.tv_sc);
                        tv_sc.setText(list.get(i).whetherOrNotToUpload);
                        TextView tv_xzmc = (TextView) ll.findViewById(R.id.tv_xzmc);
                        tv_xzmc.setText(list.get(i).characterName);

                        Message msg = new Message();
                        msg.obj = ll;
                        msg.what = 9; //加载完成
                        handler.sendMessage(msg);


                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }


}
