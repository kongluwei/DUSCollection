package com.pdwy.dus.collection.model.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.pdwy.dus.collection.R;
import com.pdwy.dus.collection.utils.MLog;
import com.pdwy.dus.collection.utils.SharePreferencesUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Author： by MR on 2018/8/2.
 */

public class MySQLiteOpenHelper extends SQLiteOpenHelper {
    private Context context;
    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public MySQLiteOpenHelper(Context context){
        this(context, "DUS_caiji"+ SharePreferencesUtils.getString(context.getString(R.string.user_id),"")+".db",null,1);
//        this(context, "DUS_caiji.db",null,1);

        this.context = context;
    }
    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        this(context, "DUS_caiji"+SharePreferencesUtils.getString(context.getString(R.string.user_id),"")+".db",null,1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建数据库

        String sql = readAssetsStream(context, "mysql_v2.txt");
        ExcuteSQL(db, sql);
        MLog.e("===="+"数据创建完成"+db.getPath());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public static String readAssetsStream(Context context, String fileName) {
//		AssetManager assets = context.getAssets();//适用eclipse
        InputStream stream;
        try {
            stream = context.getClass().getClassLoader().getResourceAsStream("assets/" + fileName);//适用android studio
//			stream = assets.open(fileName);//适用eclipse
            byte[] buffer;
            buffer = new byte[stream.available()];
            int l = stream.read(buffer);
            stream.close();
            return new String(buffer, 0, l, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static void ExcuteSQL(SQLiteDatabase db, String sql) {
        if (db == null) {
            throw new NullPointerException();
        }
        if (sql == null) {
            throw new NullPointerException();
        }
        String[] arr = sql.split(";");
        db.beginTransaction();
        try {
            String tmp;
            for (int i = 0; i < arr.length; i++) {
                tmp = arr[i] == null?"":arr[i].trim();
                if(null == tmp || "".equals(tmp))
                    continue;
                Log.d("debug", "run sql: " + tmp);
                db.execSQL(tmp);
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("PDWY", "ERR:"+e.getMessage(),e);
        }finally{
            db.endTransaction();
        }
    }
}
