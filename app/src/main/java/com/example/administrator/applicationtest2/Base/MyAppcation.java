package com.example.administrator.applicationtest2.Base;

import android.app.Application;
import android.content.IntentFilter;
import android.util.Log;

import com.example.administrator.applicationtest2.common.CrashHandler;
import com.example.administrator.applicationtest2.receiver.ScreenLockReceiver;
import com.example.administrator.applicationtest2.service.GTPushService;
import com.igexin.sdk.PushManager;
import com.igexin.sdk.PushService;

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
		// com.getui.demo.DemoPushService 为第三方自定义推送服务
		PushManager.getInstance().initialize(getApplicationContext(), PushService.class);
		// com.getui.demo.DemoIntentService 为第三方自定义的推送服务事件接收类
		PushManager.getInstance().registerPushIntentService(getApplicationContext(), GTPushService.class);
		Log.e("test2","启动程序");

		ScreenLockReceiver mScreenLockReceiver = new ScreenLockReceiver();
		IntentFilter filter = new IntentFilter();
		//添加action
		filter.addAction("android.intent.action.SCREEN_OFF");
		//注册广播接收者
		this.registerReceiver(mScreenLockReceiver,filter);

	}

}