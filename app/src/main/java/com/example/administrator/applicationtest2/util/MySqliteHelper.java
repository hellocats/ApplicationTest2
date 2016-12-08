package com.example.administrator.applicationtest2.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by hepeng on 2016/12/8.
 */
public class MySqliteHelper extends SQLiteOpenHelper {
    /**
     * 构造函数
     * @param context   上下文对象
     * @param name      创建数据库的名称
     * @param factory   游标工厂
     * @param version   创建数据库的版本
     */
    public MySqliteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public MySqliteHelper(Context context){
        super(context,ParamDefine.DATABASE_NAME,null,ParamDefine.DATABASE_VERSION);
    }

    /**
     * 当前数据库创建时的回调函数
     * @param db 数据库对象
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("tag","----------onCreate------------");
        String sql = "create table "+ParamDefine.TABLE_NAME+"(" +
                ParamDefine._ID+" Integer primary key,"+ParamDefine.NAME+" varchar(10)," +
                ParamDefine.AGE+" Integer)";
        db.execSQL(sql);
    }

    /**
     * 当数据库版本更新时的回调函数
     * @param db 数据库对象
     * @param oldVersion 数据库旧版本
     * @param newVersion 数据库新版本
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("tag","----------onUpgrade------------");
    }

    /**
     * 当数据库打开时的回调函数
     * @param db 数据库对象
     */
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }
}
