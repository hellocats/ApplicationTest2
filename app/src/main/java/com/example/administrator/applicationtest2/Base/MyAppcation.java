package com.example.administrator.applicationtest2.Base;

import android.app.Application;

import com.example.administrator.applicationtest2.common.CrashHandler;

/**
 * Date:2017-12-28
 * Author:he
 * Description:
 */

public class MyAppcation extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler.getInstance().init(this);
    }

}