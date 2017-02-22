package com.example.administrator.applicationtest2.common;

import android.os.Environment;

/**
 * Created by hepeng on 2016/12/8.
 */
public class ParamDefine {
    public static String projectDirectory = Environment.getExternalStorageDirectory().toString()+"/test";

    public static final String DATABASE_NAME = "info.db"; //数据库名称
    public static final int DATABASE_VERSION = 1;         //数据库版本号
    public static final String TABLE_NAME = "";           //表名
    public static final String _ID = "";           //id
    public static final String NAME = "";           //名字
    public static final String AGE = "";           //age
}
