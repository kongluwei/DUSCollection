package com.pdwy.dus.collection.http;

import android.util.Log;
import android.widget.Toast;

import com.pdwy.dus.collection.http.bean.PhotoHttpBean;
import com.pdwy.dus.collection.utils.MLog;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

/**
 * Author： by MR on 2019/1/18.
 */

public class UploadImg {

    private static final String TAG = "uploadFile";
    private static final int TIME_OUT = 10*1000;   //超时时间
    private static final String CHARSET = "utf-8"; //设置编码
    /**
     * android上传文件到服务器
     * @param file  需要上传的文件
     * @return  返回响应的内容
     */
    public static String uploadFile(File file, PhotoHttpBean photoHttp){

        String result = null;
        if (!file.exists()) {
            MLog.e("文件不存在");
        } else {
            String BOUNDARY = UUID.randomUUID().toString();  //边界标识   随机生成
            String PREFIX = "--", LINE_END = "\r\n";
            String CONTENT_TYPE = "multipart/form-data";   //内容类型

            try {
                URL url = new URL(photoHttp.getData()+"/api/"+photoHttp.getSystemCode()+"/file/upload");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(TIME_OUT);
                conn.setConnectTimeout(TIME_OUT);
                conn.setDoInput(true);  //允许输入流
                conn.setDoOutput(true); //允许输出流
                conn.setUseCaches(false);  //不允许使用缓存
                conn.setRequestMethod("POST");  //请求方式
                conn.setRequestProperty("Charset", CHARSET);  //设置编码
                conn.setRequestProperty("connection", "keep-alive");
                conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);
                conn.setRequestProperty("action", "upload");
                conn.connect();

                if (file != null) {
                    /**
                     * 当文件不为空，把文件包装并且上传
                     */
                    DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
                    StringBuffer sb = new StringBuffer();
                    sb.append(PREFIX);
                    sb.append(BOUNDARY);
                    sb.append(LINE_END);
                    /**
                     * 这里重点注意：
                     * name里面的值为服务器端需要key   只有这个key 才可以得到对应的文件
                     * filename是文件的名字，包含后缀名的   比如:abc.png
                     */

                    sb.append("Content-Disposition: form-data; name=\""+photoHttp.getSystemCode()+"\"; filename=\"" + file.getName() + "\"" + LINE_END);
                    sb.append("Content-Type: application/octet-stream; charset=" + CHARSET + LINE_END);
                    sb.append(LINE_END);
                    dos.write(sb.toString().getBytes());
                    InputStream is = new FileInputStream(file);
                    byte[] bytes = new byte[1024];
                    int len = 0;
                    while ((len = is.read(bytes)) != -1) {
                        dos.write(bytes, 0, len);
                    }
                    is.close();
                    dos.write(LINE_END.getBytes());
                    byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END).getBytes();
                    dos.write(end_data);
                    dos.flush();
                    /**
                     * 获取响应码  200=成功
                     * 当响应成功，获取响应的流
                     */
                    int res = conn.getResponseCode();
                    if (res == 200) {
                        InputStream input = conn.getInputStream();
                        StringBuffer sb1 = new StringBuffer();
                        int ss;
                        while ((ss = input.read()) != -1) {
                            sb1.append((char) ss);
                        }
                        result = sb1.toString();
                        MLog.e(result);
                    }
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static  void usis(){

    }

    public static void uploadImg(PhotoHttpBean photoHttp, final File file) {
        RequestParams params = new RequestParams(photoHttp.getData()+"/api/"+photoHttp.getSystemCode()+"/file/upload");

        params.setMultipart(true);//设置表单传送
        params.setCancelFast(true);//设置可以立即被停止
        params.addBodyParameter(photoHttp.getSystemCode(), file, "multipart/form-data");

        Callback.Cancelable cancelable = x.http().post(params, new Callback.ProgressCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("uploadImg", "result-->" + result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i("uploadImg", "ex-->" + ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.i("upload", "onCancelled");
            }

            @Override
            public void onFinished() {
            }

            @Override
            public void onWaiting() {

            }

            @Override
            public void onStarted() {

            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {

			/*	Log.i("uploadImg","total-->"+total);
				Log.i("upload","isDownloading-->"+isDownloading);
				Log.i("uploadImg","current-->"+current);*/
            }
        });

        //cancelable.cancel();//终止上传
    }

}
