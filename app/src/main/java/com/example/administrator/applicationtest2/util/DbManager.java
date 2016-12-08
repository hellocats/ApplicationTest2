package com.example.administrator.applicationtest2.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by hepeng on 2016/12/8.
 */
public class DbManager {
    private static MySqliteHelper helper;
    public static MySqliteHelper getIntence(Context context){
        if (helper==null) {
            helper=new MySqliteHelper(context);
        }
        return helper;
    }
    public static void execSQL(SQLiteDatabase db,String sql){
        if (db!=null) {
            if (sql!=null && !"".equals(sql)) {
                db.execSQL(sql);
            }
        }
    }
}
