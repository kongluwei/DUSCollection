package com.pdwy.dus.collection.utils;

import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * file存储工具类
 * Author： by MR on 2017/3/15.
 */
public class FileUtils {

    public static String SDPATH = Environment.getExternalStorageDirectory()
            + "/pos/";

//    主要功能是实现将传入bitmap数据保存为图片在一定的目录下。picName是这个保存图片的名称
    public static void saveBitmap(Bitmap bm, String picName) {
        System.out.println("-----------------------------");
        try {
            if (!isFileExist("")) {
                System.out.println("创建文件");
                File tempf = createSDDir("");
            }
            File f = new File(SDPATH, picName + ".JPEG");
            if (f.exists()) {
                f.delete();
            }
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//  方法执行的流程为  如果需要保存图片的目录不存在，便创建这个目录，然后在这个目录创建图片文件，将bitmap压缩一下，通过输出流写入到这个文件当中；
//  实际上我们在之前已经压缩过一次，所以这里也可以不用压缩，这里调用bm.compress()方法进行压缩，这个方法的第二个参数，如果是100，表示不压缩
//  主程序执行流程如下：
//  Bitmap saveBitmap = PictureUtil.getSmallBitmap(picPath,1280,720);//上传服务器的bitmap 手机横着拍照
//  FileUtils.saveBitmap(saveBitmap, requestCode+"");

    public static File createSDDir(String dirName) throws IOException {
        File dir = new File(SDPATH + dirName);
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {

            System.out.println("createSDDir:" + dir.getAbsolutePath());
            System.out.println("createSDDir:" + dir.mkdir());
        }
        return dir;
    }

    public static boolean isFileExist(String fileName) {
        File file = new File(SDPATH + fileName);
        file.isFile();
        System.out.println(file.exists());
        return file.exists();
    }

    public static void delFile(String fileName){
        File file = new File(SDPATH + fileName);
        if(file.isFile()){
            file.delete();
        }
        file.exists();
    }

    public static void deleteDir() {
        File dir = new File(SDPATH);
        if (dir == null || !dir.exists() || !dir.isDirectory())
            return;

        for (File file : dir.listFiles()) {
            if (file.isFile())
                file.delete();
            else if (file.isDirectory())
                deleteDir();
        }
        dir.delete();
    }

    public static boolean fileIsExists(String path) {
        try {
            File f = new File(path);
            if (!f.exists()) {
                return false;
            }
        } catch (Exception e) {

            return false;
        }
        return true;
    }

}
